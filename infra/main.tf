terraform {
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "6.17.0"
        }
    }
}

locals {
    projectName = "garage"
    eks_service_role_managed_policies = [
        "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy",
        "arn:aws:iam::aws:policy/AmazonEKSServicePolicy",
        "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly",
        "arn:aws:iam::aws:policy/AmazonVPCFullAccess"
    ]
    eks_node_group_role_managed_policies = [
        "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy",
        "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly",
        "arn:aws:iam::aws:policy/AmazonVPCFullAccess"
    ]
    awsRegion = "us-east-1"
}

variable "accountId" {
    description = "AWS Account ID"
    type        = string
}

variable "awsProfile" {
    description = "AWS Profile"
    type        = string
}

variable "roleName" {
    description = "IAM Role name for EKS access"
    type        = string
}

provider "aws" {
    region = local.awsRegion
    profile = var.awsProfile
}

// VPC personalizada com CIDR block 10.0.0.0/16
resource "aws_vpc" "main" {
    cidr_block           = "10.0.0.0/16"
    enable_dns_support   = true
    enable_dns_hostnames = true
    tags = {
        name = "${local.projectName}-main-vpc"
    }
}

// subnet pública
resource "aws_subnet" "public_subnet" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.1.0/24"
    availability_zone = "${local.awsRegion}a"
    map_public_ip_on_launch = true
    tags = {
        name = "${local.projectName}-public-subnet"
    }
}

# Subnet Privada para banco de dados
resource "aws_subnet" "private_subnet" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.2.0/24"
    availability_zone = "${local.awsRegion}a"
    map_public_ip_on_launch = false
    tags = {
        name = "${local.projectName}-private-subnet"
    }
}

# Segunda subnet privada para RDS (obrigatório)
resource "aws_subnet" "private_subnet_b" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.3.0/24"
    availability_zone = "${local.awsRegion}b"
    map_public_ip_on_launch = false
    tags = {
        name = "${local.projectName}-private-subnet-b"
    }
}

// DB Subnet Group para RDS
resource "aws_db_subnet_group" "main" {
    name       = "${local.projectName}-db-subnet-group"
    subnet_ids = [aws_subnet.private_subnet.id, aws_subnet.private_subnet_b.id]

    tags = {
        Name = "${local.projectName}-db-subnet-group"
    }
}

// Security Group para RDS
resource "aws_security_group" "rds" {
    name_prefix = "${local.projectName}-rds-sg"
    vpc_id      = aws_vpc.main.id

    ingress {
        from_port       = 5432
        to_port         = 5432
        protocol        = "tcp"
        security_groups = [aws_security_group.main.id]
    }

    tags = {
        name = "${local.projectName}-rds-security-group"
    }
}

// RDS PostgreSQL
resource "aws_db_instance" "postgres" {
    identifier = "${local.projectName}-postgres"

    engine         = "postgres"
    engine_version = "15.7"
    instance_class = "db.t3.micro"

    allocated_storage = 20
    storage_type      = "gp2"

    db_name  = "garage"
    username = "postgres"
    password = "postgres123"

    vpc_security_group_ids = [aws_security_group.rds.id]
    db_subnet_group_name   = aws_db_subnet_group.main.name

    skip_final_snapshot = true

    tags = {
        Name = "${local.projectName}-postgres"
    }
}

// Security Group para o EKS cluster
resource "aws_security_group" "main" {
    name_prefix = "${local.projectName}-eks-sg"
    vpc_id      = aws_vpc.main.id

    ingress {
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["10.0.0.0/16"]
    }

    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        name = "${local.projectName}-eks-security-group"
    }
}

// Duas IAM Roles: uma para o EKS Cluster e outra para o Node Group
resource "aws_iam_role" "eks_service_role" {
    name = "${local.projectName}-eks-cluster-role"

    assume_role_policy = jsonencode({
        Version = "2012-10-17"
        Statement = [{
            Effect = "Allow"
            Principal = {
                Service = "eks.amazonaws.com"
            }
            Action = "sts:AssumeRole"
        }]
    })
}

resource "aws_iam_role_policy_attachment" "eks_service_role_attachments" {
    for_each   = toset(local.eks_service_role_managed_policies)

    role       = aws_iam_role.eks_service_role.name
    policy_arn = each.value
}

resource "aws_iam_role" "eks_node_group_role" {
    name = "${local.projectName}-eks-node-group-role"

    assume_role_policy = jsonencode({
        Version = "2012-10-17"
        Statement = [{
            Effect = "Allow"
            Principal = {
                Service = "ec2.amazonaws.com"
            }
            Action = "sts:AssumeRole"
        }]
    })
}

resource "aws_iam_role_policy_attachment" "eks_node_group_role_attachments" {
    for_each   = toset(local.eks_node_group_role_managed_policies)

    role       = aws_iam_role.eks_node_group_role.name
    policy_arn = each.value
}

// cluster Kubernetes (EKS) na AWS para orquestrar a aplicação
resource "aws_eks_cluster" "eks_cluster" {
    name = "${local.projectName}-cluster"
    role_arn = aws_iam_role.eks_service_role.arn

    vpc_config {
        subnet_ids = [
            aws_subnet.public_subnet.id,
            aws_subnet.private_subnet.id,
            aws_subnet.private_subnet_b.id
        ]
        security_group_ids = [
            aws_security_group.main.id
        ]
    }

    access_config {
        authentication_mode = "API_AND_CONFIG_MAP"
    }

    depends_on = [
        aws_iam_role.eks_service_role
    ]
}

// Node Groups: Instancias EC2 que executam os Workloads do Kubernetes
resource "aws_eks_node_group" "main" {
    cluster_name = aws_eks_cluster.eks_cluster.name
    node_group_name = "node-group-01"
    node_role_arn = aws_iam_role.eks_node_group_role.arn
    subnet_ids = [
        aws_subnet.private_subnet.id
    ]
    instance_types = [
        "t3.medium"
    ]
    scaling_config {
        desired_size = 2
        max_size     = 3
        min_size     = 1
    }
    update_config {
        max_unavailable = 1
    }
    depends_on = [
        aws_eks_cluster.eks_cluster
    ]
}

// controle de acesso no EKS, permitindo que usuarios interajam com o cluster
resource "aws_eks_access_entry" "access-entry" {
    cluster_name  = aws_eks_cluster.eks_cluster.name
    principal_arn = "arn:aws:iam::${var.accountId}:role/${var.roleName}"
    kubernetes_groups = [
    ]
    type = "STANDARD"
}

resource "aws_eks_access_policy_association" "eks-policy" {
    cluster_name  = aws_eks_cluster.eks_cluster.name
    policy_arn    = "arn:aws:eks::aws:cluster-access-policy/AmazonEKSClusterAdminPolicy"
    principal_arn = "arn:aws:iam::${var.accountId}:role/${var.roleName}"
    access_scope {
        type = "cluster"
    }
}

// Para poder interagir com o Kubernetes, é necessário gerar um arquivo kubeconfig, que será usado pelo kubectl para se conectar ao cluster.
output "kubeconfig" {
    value = aws_eks_cluster.eks_cluster.endpoint
}

output "rds_endpoint" {
    value = aws_db_instance.postgres.endpoint
}

output "rds_port" {
    value = aws_db_instance.postgres.port
}


terraform {
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "6.17.0"
        }
    }
}

provider "aws" {
    region = "sa-east-1"
}

variable "accountId" {
    description = "AWS Account ID"
    type        = string
}

variable "roleName" {
    description = "IAM Role name for EKS access"
    type        = string
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
    availability_zone = "sa-east-1a"
    map_public_ip_on_launch = true
    tags = {
        name = "${local.projectName}-public-subnet"
    }
}

# Subnet Privada para banco de dados
resource "aws_subnet" "private_subnet" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.2.0/24"
    availability_zone = "sa-east-1a"
    map_public_ip_on_launch = true
    tags = {
        name = "${local.projectName}-private-subnet"
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
            aws_subnet.private_subnet.id
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
    scaling_config = {
        desired_size = 2
        max_size     = 3
        min_size     = 1
    }
    update_config = {
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


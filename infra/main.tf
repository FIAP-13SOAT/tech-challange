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
    awsRegion = "us-east-1"
}

variable "accountId" {
    description = "AWS Account ID"
    type        = string
}

variable "roleName" {
    description = "IAM Role name for EKS access"
    type        = string
}

provider "aws" {
    region = local.awsRegion
}

########################################
# NETWORK - VPC & INTERNET ACCESS
########################################

# VPC principal da aplicação
# Responsável por isolar toda a infraestrutura
# CIDR definido para permitir expansão futura
resource "aws_vpc" "main" {
    cidr_block           = "10.0.0.0/16"
    enable_dns_support   = true
    enable_dns_hostnames = true
    tags = {
        name = "${local.projectName}-main-vpc"
    }
}

# Internet Gateway
# Permite comunicação entre recursos da VPC
# e a internet pública
resource "aws_internet_gateway" "main" {
    vpc_id = aws_vpc.main.id
    tags = {
        Name = "${local.projectName}-igw"
    }
}

// subnet pública
resource "aws_subnet" "public_subnet" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.1.0/24"
    availability_zone = "${local.awsRegion}a"
    map_public_ip_on_launch = true
    tags = {
        Name = "${local.projectName}-public-subnet"
        "kubernetes.io/role/elb" = "1"
    }
}

// Segunda subnet pública para EKS (diferentes AZs)
// EKS exige subnets em pelo menos 2 zonas de disponibilidade (AZs)
resource "aws_subnet" "public_subnet_b" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.4.0/24"
    availability_zone = "${local.awsRegion}b"
    map_public_ip_on_launch = true
    tags = {
        Name = "${local.projectName}-public-subnet-b"
        "kubernetes.io/role/elb" = "1"
    }
}


########################################
# PUBLIC ROUTING
########################################

# Permite que recursos em subnets públicas tenham acesso direto à internet
resource "aws_route_table" "public" {
    vpc_id = aws_vpc.main.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.main.id
    }

    tags = {
        Name = "${local.projectName}-public-rt"
    }
}

// Associação da route table com subnet pública A
resource "aws_route_table_association" "public_a" {
    subnet_id      = aws_subnet.public_subnet.id
    route_table_id = aws_route_table.public.id
}

// Associação da route table com subnet pública B
resource "aws_route_table_association" "public_b" {
    subnet_id      = aws_subnet.public_subnet_b.id
    route_table_id = aws_route_table.public.id
}

# Subnet Privada para banco de dados
resource "aws_subnet" "private_subnet" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.2.0/24"
    availability_zone = "${local.awsRegion}a"
    map_public_ip_on_launch = false
    tags = {
        Name = "${local.projectName}-private-subnet"
        "kubernetes.io/role/internal-elb" = "1"
    }
}

# Segunda subnet privada para RDS (obrigatório)
resource "aws_subnet" "private_subnet_b" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.3.0/24"
    availability_zone = "${local.awsRegion}b"
    map_public_ip_on_launch = false
    tags = {
        Name = "${local.projectName}-private-subnet-b"
        "kubernetes.io/role/internal-elb" = "1"
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

########################################
# SECURITY GROUPS
########################################

// SG do RDS
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

########################################
# DATABASE
########################################

// RDS PostgreSQL
resource "aws_db_instance" "postgres" {
    identifier = "${local.projectName}-postgres"

    engine         = "postgres"
    engine_version = "16.11"
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

########################################
# KUBERNETES (EKS)
########################################

// cluster Kubernetes (EKS) na AWS para orquestrar a aplicação
// Usando LabRole pois AWS Academy não permite criação de IAM Roles
resource "aws_eks_cluster" "eks_cluster" {
    name = "${local.projectName}-cluster"
    role_arn = "arn:aws:iam::${var.accountId}:role/LabRole"

    vpc_config {
        subnet_ids = [
            aws_subnet.public_subnet.id,
            aws_subnet.public_subnet_b.id,
            aws_subnet.private_subnet.id,
            aws_subnet.private_subnet_b.id
        ]
        security_group_ids = [
            aws_security_group.main.id
        ]
        endpoint_public_access  = true
        endpoint_private_access = false
    }

    access_config {
        authentication_mode = "API_AND_CONFIG_MAP"
    }

    depends_on = [
        # Removido dependência de roles criadas
    ]
}

// Node Groups: Instancias EC2 que executam os Workloads do Kubernetes
resource "aws_eks_node_group" "main" {
    cluster_name = aws_eks_cluster.eks_cluster.name
    node_group_name = "node-group-01"
    node_role_arn = "arn:aws:iam::${var.accountId}:role/LabRole"
    subnet_ids = [
        aws_subnet.public_subnet.id,
        aws_subnet.public_subnet_b.id
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

########################################
# EKS ACCESS - AWS ACADEMY (voclabs)
########################################

# Permite que a role "voclabs" (usada pelo AWS Academy) tenha acesso ao cluster EKS
# Sem isso, comandos como: `kubectl get nodes` falham com erro de autenticação
resource "aws_eks_access_entry" "access-voclabs" {
    cluster_name  = aws_eks_cluster.eks_cluster.name

    # Role real usada na autenticação via AWS CLI
    # aws sts get-caller-identity
    # -> assumed-role/voclabs/...
    principal_arn = "arn:aws:iam::${var.accountId}:role/voclabs"

    # STANDARD = acesso via RBAC do Kubernetes
    type = "STANDARD"
}

# Associa uma policy administrativa ao cluster
# para a role voclabs, permite kubectl apply, kubectl get nodes, criação de recursos no cluster
resource "aws_eks_access_policy_association" "eks-policy-voclabs" {
    cluster_name  = aws_eks_cluster.eks_cluster.name

    # Policy oficial da AWS para admin do cluster
    policy_arn = "arn:aws:eks::aws:cluster-access-policy/AmazonEKSClusterAdminPolicy"

    principal_arn = "arn:aws:iam::${var.accountId}:role/voclabs"

    access_scope {
        # Escopo em nível de cluster
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


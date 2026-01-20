########################################
# SECURITY GROUPS
########################################

# Security Group do RDS - controla acesso ao banco de dados
resource "aws_security_group" "rds" {
    name_prefix = "${local.projectName}-rds-sg"
    vpc_id      = aws_vpc.main.id

    tags = {
        name = "${local.projectName}-rds-security-group"
    }
}

# Regra separada para evitar dependência cíclica e garantir clareza
resource "aws_security_group_rule" "rds_ingress_eks_cluster" {
    type                     = "ingress"
    from_port                = 5432
    to_port                  = 5432
    protocol                 = "tcp"
    security_group_id        = aws_security_group.rds.id
    source_security_group_id = aws_eks_cluster.eks_cluster.vpc_config[0].cluster_security_group_id
    description              = "Allow EKS Cluster to access RDS"
}

# Permite acesso dos nodes do EKS ao RDS
resource "aws_security_group_rule" "rds_ingress_eks_nodes" {
    type                     = "ingress"
    from_port                = 5432
    to_port                  = 5432
    protocol                 = "tcp"
    security_group_id        = aws_security_group.rds.id
    source_security_group_id = aws_security_group.main.id
    description              = "Allow EKS Nodes to access RDS"
}

# Security Group para o EKS cluster - controla tráfego de rede
resource "aws_security_group" "main" {
    name_prefix = "${local.projectName}-eks-sg"
    vpc_id      = aws_vpc.main.id

    # Permite comunicação interna com API Server do EKS e outros componentes do cluster
    ingress {
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["10.0.0.0/16"]
    }

    # Libera comunicação entre os próprios nodes (kubectl logs, exec, etc)
    ingress {
        from_port = 10250
        to_port   = 10250
        protocol  = "tcp"
        self      = true
    }

    # Permite acesso ao RDS
    egress {
        from_port       = 5432
        to_port         = 5432
        protocol        = "tcp"
        security_groups = [aws_security_group.rds.id]
        description     = "Allow nodes to access RDS"
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

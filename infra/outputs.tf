########################################
# OUTPUTS
########################################

# Endpoint do cluster EKS para configuração do kubectl
output "kubeconfig" {
    description = "Endpoint do cluster EKS"
    value       = aws_eks_cluster.eks_cluster.endpoint
}

# Endpoint do banco RDS para conexão da aplicação
output "rds_endpoint" {
    description = "Endpoint do banco PostgreSQL"
    value       = aws_db_instance.postgres.endpoint
}

# Porta do banco RDS
output "rds_port" {
    description = "Porta do banco PostgreSQL"
    value       = aws_db_instance.postgres.port
}

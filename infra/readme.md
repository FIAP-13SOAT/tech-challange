# Infraestrutura AWS com Terraform

Esse projeto usa Terraform para criar a infraestrutura EKS na AWS.

## Pré-requisitos

1. **Instalar Terraform**: https://developer.hashicorp.com/terraform/install
2. **Configurar AWS CLI**: `aws configure` com suas credenciais
3. **Verificar acesso**: `aws sts get-caller-identity`

## Configuração

1. **Criar arquivo terraform.tfvars**:
```hcl
accountId = "123456789012"  # Seu AWS Account ID
roleName  = "LabRole"        # Nome da sua IAM Role
```

2. **Descobrir seus valores**:
```bash
# Account ID
aws sts get-caller-identity --query Account --output text

# Role Name (geralmente LabRole para AWS Academy)
aws sts get-caller-identity --query Arn --output text
```

## Deploy da Infraestrutura

```bash
# 1. Inicializar Terraform
AWS_PROFILE=awsacademy terraform init

# 2. Validar configuração
AWS_PROFILE=awsacademy terraform validate

# 3. Ver plano de execução
AWS_PROFILE=awsacademy terraform plan

# 4. Aplicar mudanças
AWS_PROFILE=awsacademy terraform apply
```

## Recursos Criados

- VPC com subnets pública e privada
- Security Group para EKS
- Cluster EKS
- Node Group com instâncias t3.medium
- IAM Roles e políticas necessárias
- Access entries para administração

## Conectar ao Cluster

```bash
# Configurar kubectl
aws eks update-kubeconfig --region sa-east-1 --name garage-cluster

# Verificar conexão
kubectl get nodes
```

## Destruir Infraestrutura

```bash
terraform destroy
```

#### 1. Provisionar Infraestrutura (Terraform)
```bash
# Navegar para o diretório de infraestrutura
cd infra

# Criar arquivo terraform.tfvars
echo 'accountId = "123456789012"' > terraform.tfvars
echo 'roleName = "LabRole"' >> terraform.tfvars

# Descobrir seus valores reais
aws sts get-caller-identity --query Account --output text  # Account ID
aws sts get-caller-identity --query Arn --output text      # Role Name

# Executar Terraform
terraform init
terraform validate
terraform plan
terraform apply

# Anotar os outputs: rds_endpoint e kubeconfig
```

#### 2. Configurar kubectl
```bash
# Configurar acesso ao cluster EKS
aws eks update-kubeconfig --region sa-east-1 --name garage-cluster

# Verificar conexão
kubectl get nodes
```

#### 3. Atualizar ConfigMap com RDS
```bash
# Editar k8s/configmap.yaml com o endpoint do RDS obtido no passo 1
# Substituir DB_HOST pelo valor real do output rds_endpoint
```

#### 4. Deploy da Aplicação (Kubernetes)
```bash
# Voltar para raiz do projeto
cd ..

# Aplicar manifestos Kubernetes
kubectl apply -f k8s/

# Verificar status
kubectl get pods
kubectl get services

# Obter URL da aplicação
kubectl get service garage-service
```

#### 5. Acessar Aplicação
```bash
# A aplicação estará disponível no EXTERNAL-IP do LoadBalancer
# Aguardar alguns minutos para o LoadBalancer ficar ativo
echo "Aplicação: http://$(kubectl get service garage-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')"
echo "Swagger: http://$(kubectl get service garage-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')/swagger-ui/index.html"
```

#### 6. Limpeza (Opcional)
```bash
# Remover aplicação
kubectl delete -f k8s/

# Destruir infraestrutura
cd infra
terraform destroy
```

Veja detalhes completos do terraform em: [infra/README.md](./infra/README.md)

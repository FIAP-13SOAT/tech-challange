# Guia de Execução

## Requisitos Mínimos
- Docker 20.10+
- Docker Compose 2.0+

## Como Executar

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd tech-challange
```

### 2. Execute com Docker
```bash
docker-compose up --build
```

### 3. Para desenvolvimento (banco em debug)
```bash
docker-compose -f docker-compose-dev.yml up --build
```

### 4. Acesse a aplicação
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **PostgreSQL**: localhost:5432

### 5. Para parar a aplicação
```bash
docker-compose down
```

### 6. Execução na AWS (EKS)

Para construção e atualização da infraestrutura AWS usando Terraform acesse [a documentação da infra em terraform](infra/TERRAFORM.md)
O deploy é automatizado quando o merge é feito na main. Se houver curiosidade em como fazer manualmente verifique [Deploy em produção manualmente](infra/DEPLOY_PROD_MANUALLY.md)

## Documentação da API

A documentação completa da API está disponível em: http://localhost:8080/swagger-ui/index.html

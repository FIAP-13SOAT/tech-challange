# tech-challange

## Tecnologias
- Java 21
- Maven
- Spring Boot
- PostgreSQL
- Docker

## Requisitos Mínimos
- Docker 20.10+
- Docker Compose 2.0+
- 4GB RAM disponível
- 2GB espaço em disco

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

### 3. Acesse a aplicação
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **PostgreSQL**: localhost:5432

### 4. Para parar a aplicação
```bash
docker-compose down
```

## Documentação

- Documentação da API disponibilizada em: http://localhost:8080/swagger-ui/index.html

## Testes:

### Verificar cobertura de testes:

1. Subir container docker do SonarQube:
```bash
    docker compose -f docker-compose-sonar.yml up -d
```

2. Acessar o SonarQube em http://localhost:9000 (usuário e senha padrão: admin/admin)
3. Configurar novo usuário e senha
4. Configurar projecto com as seguintes informações:
   - Project Key: garage-tech-challenge
   - Project Name: garage-tech-challenge

5. Configurar token de autenticação (gerar token e copiar)
6. Alterar `pom.xml` adicionando o token gerado na seção `<sonar.login>`:
```xml
    <sonar.login>sqp_57baccf38dce58b0f202e1fe45cb0a81f1f29256</sonar.login>
```

7.Executar maven clean:
```bash
    mvn clean verify sonar:sonar
```

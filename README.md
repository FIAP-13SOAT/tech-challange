# SOAT Tech-challenge

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

### 2.1. Para desenvolvimento (banco em debug)
```bash
docker-compose -f docker-compose-dev.yml up --build
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
Para executar a análise de cobertura de testes localmente com o SonarQube:

#### Para o primeiro acesso:
1.  **Subir o container docker do SonarQube:**
    ```bash
    docker compose -f docker-compose-sonar.yml up -d
    ```

2.  **Acessar a interface do SonarQube:**
    * Abra `http://localhost:9000` no seu navegador.
    * Faça login com as credenciais padrão: `admin` / `admin`f

3.  **Configurar nova senha:**
    * O SonarQube solicitará que você altere a senha padrão. Faça isso para continuar.

4.  **Desabilitar autenticação forçada:**
    * Este passo simplifica a análise local, permitindo que o Maven crie o projeto automaticamente na primeira vez, **sem a necessidade de gerar tokens** ou configurar o projeto manualmente na UI.
    * Navegue até: `Administration` (Administração) > `Configuration` (Configuração) > `General Settings` (Configurações Gerais).
    * No menu da esquerda, clique em `Security` (Segurança).
    * Encontre a opção **`Force user authentication`** (Forçar autenticação do usuário) e **DESATIVE-A** (mude o seletor para `false`).
    * Navegue até: `Administration` (Administração) > `Security` (Segurança) > `Global permissions` (Permissões Gerais).
    * Marque as opções de Execute Analysis e Create projects para a role Anyone

5.  **Executar a análise:**
    * Com o SonarQube rodando e a autenticação desabilitada, basta executar o comando Maven no diretório do projeto:
    ```bash
    mvn clean verify sonar:sonar
    ```
    * Na primeira execução, o scanner do Sonar irá criar automaticamente o projeto `garage-tech-challenge` na sua instância local e enviar o relatório de análise. Você poderá ver os resultados em `http://localhost:9000`.

6.  **Verificar resultado:**
    * Abra `http://localhost:9000` no seu navegador.
    * Verifique a última análise executada.

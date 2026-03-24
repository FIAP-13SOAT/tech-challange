# Plano de Implementação: Finalização do Projeto de Pós-Graduação

## Visão Geral

Plano de implementação para finalizar os 13 requisitos pendentes do projeto "Garage", distribuídos em cinco repositórios. As tarefas seguem a ordem lógica de dependência: build → pipelines → documentação → configuração de segurança → artefatos arquiteturais.

## Tarefas

- [x] 1. Configuração de build do auth-issuer
  - [x] 1.1 Criar arquivo `go.mod` na raiz do repositório `auth-issuer`
    - Definir módulo como `com.fiapchallenge/tech-challange-auth-issuer`
    - Configurar versão do Go compatível com o código-fonte (1.25)
    - Declarar dependências: `github.com/aws/aws-lambda-go`, `github.com/lib/pq`, `github.com/golang-jwt/jwt/v5`
    - Executar `go mod tidy` para gerar o `go.sum` com checksums de todas as dependências
    - _Requisitos: 1.1, 1.2_

  - [x] 1.2 Criar `Dockerfile` na raiz do repositório `auth-issuer`
    - Implementar multi-stage build: compilação com `golang:1.25-alpine`, execução com `public.ecr.aws/lambda/provided:al2023`
    - Compilar com `CGO_ENABLED=0 GOOS=linux` gerando binário `bootstrap`
    - Copiar binário para `${LAMBDA_RUNTIME_DIR}/bootstrap`
    - _Requisitos: 1.3_

  - [ ]* 1.3 Escrever testes de validação dos artefatos de build do auth-issuer
    - Verificar existência de `go.mod`, `go.sum` e `Dockerfile`
    - Verificar nome do módulo e versão do Go no `go.mod`
    - Verificar multi-stage build e imagem base Lambda no `Dockerfile`
    - _Requisitos: 1.1, 1.2, 1.3_

- [x] 2. Pipeline CI/CD do auth-issuer
  - [x] 2.1 Criar workflow GitHub Actions em `.github/workflows/pipeline.yml` no `auth-issuer`
    - Configurar trigger em push na branch principal
    - Job **build**: checkout, setup Go, `go mod download`, `go build`, `go test`
    - Job **docker**: build da imagem Docker e push para ECR (`garage-auth-issuer`), condicionado ao sucesso do build
    - Job **deploy**: atualização da Lambda via `aws lambda update-function-code`, condicionado ao sucesso do docker
    - Configurar secrets: `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_SESSION_TOKEN`
    - Em caso de falha no build, o status check do commit deve reportar o erro
    - _Requisitos: 2.1, 2.2, 2.3, 2.4_

  - [ ]* 2.2 Escrever teste de propriedade para pipeline CI/CD do auth-issuer
    - Verificar que o workflow contém jobs de build, docker e deploy na ordem correta
    - Verificar triggers e condições de falha
    - _Requisitos: 2.1, 2.2, 2.3, 2.4_

- [x] 3. Checkpoint — Verificar build e pipeline do auth-issuer
  - Garantir que `go.mod`, `go.sum`, `Dockerfile` e workflow estão corretos. Perguntar ao usuário se há dúvidas.

- [x] 4. Documentação de API do auth-issuer
  - [x] 4.1 Atualizar `README.md` do `auth-issuer` com documentação da API
    - Adicionar seção de documentação da API com link funcional para Swagger/Postman
    - Documentar endpoint `POST /login`: formato do corpo (CPF/CNPJ), códigos de resposta (200, 400, 404), formato do token JWT
    - _Requisitos: 3.1, 3.2_

  - [x] 4.2 Criar coleção Postman em `docs/postman_collection.json` no `auth-issuer`
    - Exportar coleção com request para `POST /login` incluindo exemplos de request/response
    - _Requisitos: 3.1, 3.2_

- [x] 5. Atualização do LB Listener ARN no garage-cloud-stack
  - [x] 5.1 Atualizar `eks_lb_listener_arn` em `garage-cloud-stack/infra/terraform.tfvars`
    - Documentar o comando para obter o ARN: `aws elbv2 describe-listeners`
    - Preencher a variável com o ARN real do listener do Load Balancer interno do EKS
    - Isso ativará os recursos condicionais no `api_gateway.tf` (VPC Link, integração HTTP_PROXY, rota protegida)
    - _Requisitos: 4.1, 4.2_

- [x] 6. Pipeline CI/CD do garage-database-infra
  - [x] 6.1 Criar workflow GitHub Actions em `.github/workflows/pipeline.yml` no `garage-database-infra`
    - Em pull request: executar `terraform init` + `terraform plan`, reportar resultado no status check
    - Em merge na branch principal: executar `terraform init` + `terraform apply -auto-approve`
    - Configurar concurrency para evitar applies simultâneos
    - Em caso de falha no `terraform plan`, reportar erro no status check do PR
    - Configurar secrets AWS
    - _Requisitos: 5.1, 5.2, 5.3_

  - [ ]* 6.2 Escrever teste de propriedade para pipeline CI/CD Terraform
    - **Property 3: Pipelines CI/CD contêm os steps obrigatórios**
    - Verificar que o workflow contém `terraform plan` em PRs e `terraform apply` em merge
    - **Valida: Requisitos 5.1, 5.2**

- [x] 7. Documentação do garage-database-infra
  - [x] 7.1 Criar `docs/database-justification.md` no `garage-database-infra`
    - Documentar justificativa da escolha do PostgreSQL
    - Incluir critérios de seleção (compatibilidade RDS, ACID, maturidade, custo)
    - Incluir alternativas consideradas (MySQL, DynamoDB)
    - Incluir decisão final com justificativa
    - _Requisitos: 6.1_

  - [x] 7.2 Criar `docs/er-diagram.md` no `garage-database-infra`
    - Criar diagrama ER em Mermaid com entidades: Customer, Vehicle, ServiceOrder, Part, Budget, User
    - Representar relacionamentos e cardinalidades entre as entidades
    - _Requisitos: 6.2_

  - [x] 7.3 Criar `docs/entity-relationships.md` no `garage-database-infra`
    - Descrever textualmente os relacionamentos entre as entidades do sistema
    - _Requisitos: 6.3_

- [x] 8. Checkpoint — Verificar pipelines e documentação de infraestrutura
  - Garantir que pipelines do database-infra e documentação estão corretos. Perguntar ao usuário se há dúvidas.

- [x] 9. Pipeline CI/CD do garage-observability-stack
  - [x] 9.1 Criar workflow GitHub Actions em `.github/workflows/pipeline.yml` no `garage-observability-stack`
    - Em pull request: executar `terraform init` + `terraform plan`, reportar resultado no status check
    - Em merge na branch principal: executar `terraform init` + `terraform apply -auto-approve`
    - Configurar concurrency para evitar applies simultâneos
    - Configurar secrets: AWS credentials + `DATADOG_API_KEY`, `DATADOG_APP_KEY`
    - Configurar variáveis de ambiente para providers Datadog, Helm e Kubernetes
    - Em caso de falha no `terraform plan`, reportar erro no status check do PR
    - _Requisitos: 7.1, 7.2, 7.3_

  - [ ]* 9.2 Escrever teste de propriedade para pipeline CI/CD do observability-stack
    - **Property 3: Pipelines CI/CD contêm os steps obrigatórios**
    - Verificar que o workflow contém `terraform plan` em PRs e `terraform apply` em merge
    - **Valida: Requisitos 7.1, 7.2**

- [x] 10. Validação da stack de observabilidade
  - [x] 10.1 Validar monitores Datadog em `datadog_monitors.tf` no `garage-observability-stack`
    - Verificar que os 5 monitores existem com thresholds corretos: latência P95 (1000ms/2000ms), CPU (80%/95%), memória (80%/95%), health check (2 falhas), erro de OS (5%/15%)
    - _Requisitos: 8.1, 8.2, 8.3, 8.4, 8.5_

  - [x] 10.2 Validar dashboard operacional em `datadog_dashboard.tf` no `garage-observability-stack`
    - Verificar que o dashboard contém todos os widgets obrigatórios: volume diário de OS, tempo médio por fase, contagem de erros, latência P50/P90/P99, consumo CPU/memória, uptime 24h/7d
    - _Requisitos: 8.6, 8.7, 8.8, 8.9, 8.10, 8.11_

  - [x] 10.3 Validar configuração de logs JSON com correlation ID
    - Verificar configuração do Datadog Agent para logs no Helm chart
    - Garantir que a aplicação Spring Boot emite logs em formato JSON com correlation ID
    - Configurar logback/log4j2 se necessário
    - _Requisitos: 8.12_

  - [ ]* 10.4 Escrever teste de propriedade para thresholds de monitores
    - **Property 1: Thresholds de monitores respeitam os valores especificados**
    - **Valida: Requisitos 8.1, 8.2, 8.3, 8.5**

  - [ ]* 10.5 Escrever teste de propriedade para widgets do dashboard
    - **Property 2: Dashboard operacional contém todos os widgets obrigatórios**
    - **Valida: Requisitos 8.6, 8.7, 8.8, 8.9, 8.10, 8.11**

- [x] 11. Documentação de API do tech-challange
  - [x] 11.1 Adicionar dependência `springdoc-openapi-starter-webmvc-ui` ao `pom.xml` do `tech-challange`
    - Configurar `OpenApiConfig.java` com informações do projeto
    - Garantir que `/swagger-ui.html` está acessível
    - _Requisitos: 9.1, 9.2_

  - [x] 11.2 Atualizar `README.md` do `tech-challange` com link para documentação da API
    - Adicionar link funcional para `/swagger-ui.html`
    - Descrever brevemente os endpoints REST disponíveis
    - _Requisitos: 9.1, 9.2_

- [x] 12. Checkpoint — Verificar observabilidade e documentação de APIs
  - Garantir que monitores, dashboard, logs e documentação de APIs estão corretos. Perguntar ao usuário se há dúvidas.

- [x] 13. Configuração de Branch Protection em todos os repositórios
  - [x] 13.1 Configurar Branch Protection no `auth-issuer`
    - Identificar branch principal (main/master)
    - Exigir ao menos 1 aprovação de revisão antes do merge
    - _Requisitos: 10.1_

  - [x] 13.2 Configurar Branch Protection no `garage-cloud-stack`
    - Identificar branch principal (main/master)
    - Exigir ao menos 1 aprovação de revisão antes do merge
    - _Requisitos: 10.2_

  - [x] 13.3 Configurar Branch Protection no `garage-database-infra`
    - Identificar branch principal (main/master)
    - Exigir ao menos 1 aprovação de revisão antes do merge
    - _Requisitos: 10.3_

  - [x] 13.4 Configurar Branch Protection no `garage-observability-stack`
    - Identificar branch principal (main/master)
    - Exigir ao menos 1 aprovação de revisão antes do merge
    - _Requisitos: 10.4_

  - [x] 13.5 Configurar Branch Protection no `tech-challange`
    - Identificar branch principal (main/master)
    - Exigir ao menos 1 aprovação de revisão antes do merge
    - _Requisitos: 10.5_

- [x] 14. Acesso do colaborador soat-architecture
  - [x] 14.1 Adicionar `soat-architecture` como colaborador em todos os 5 repositórios
    - Adicionar com permissão de leitura ou superior em: `auth-issuer`, `garage-cloud-stack`, `garage-database-infra`, `garage-observability-stack`, `tech-challange`
    - Pode ser feito via GitHub API ou Settings > Collaborators
    - _Requisitos: 11.1, 11.2, 11.3, 11.4, 11.5_

- [x] 15. Checkpoint — Verificar configuração de segurança dos repositórios
  - Garantir que branch protection e acesso do colaborador estão configurados em todos os repositórios. Perguntar ao usuário se há dúvidas.

- [x] 16. RFCs de decisões arquiteturais
  - [x] 16.1 Criar `tech-challange/docs/adr/0004-escolha-plataforma-cloud-aws.md`
    - Documentar escolha da AWS como plataforma cloud
    - Incluir seções: Contexto, Decisão, Critérios de Seleção (ecossistema FIAP, serviços gerenciados EKS/RDS/Lambda, custo AWS Academy), Alternativas Consideradas, Consequências
    - _Requisitos: 12.1_

  - [x] 16.2 Criar `tech-challange/docs/adr/0005-escolha-banco-dados-postgresql.md`
    - Documentar escolha do PostgreSQL como banco de dados
    - Incluir seções: Contexto, Decisão, Critérios de Seleção (compatibilidade RDS, ACID, maturidade, suporte JSON), Alternativas Consideradas (MySQL, DynamoDB), Consequências
    - _Requisitos: 12.2_

  - [x] 16.3 Criar `tech-challange/docs/adr/0006-estrategia-autenticacao-lambda-jwt.md`
    - Documentar estratégia de autenticação com Lambda + JWT via API Gateway
    - Incluir seções: Contexto, Decisão, Critérios de Seleção, Fluxo de Autenticação (API Gateway → Lambda Authorizer → JWT), Alternativas Consideradas, Consequências
    - _Requisitos: 12.3_

  - [ ]* 16.4 Escrever teste de propriedade para documentos RFC
    - **Property 4: Documentos RFC contêm seções obrigatórias**
    - Verificar que cada RFC contém: Contexto, Decisão, Critérios de Seleção, Alternativas Consideradas, Consequências
    - **Valida: Requisitos 12.1, 12.2, 12.3**

- [x] 17. Diagramas arquiteturais
  - [x] 17.1 Criar diagrama de sequência de autenticação em `tech-challange/docs/sequence-auth.md`
    - Diagrama Mermaid representando: Cliente → API Gateway → Lambda Auth Issuer → RDS → JWT
    - Incluir todos os participantes e mensagens do fluxo de login
    - _Requisitos: 13.1_

  - [x] 17.2 Criar diagrama de sequência de abertura de OS em `tech-challange/docs/sequence-service-order.md`
    - Diagrama Mermaid representando: Cliente → API Gateway → Lambda Authorizer → EKS → Aplicação → RDS
    - Incluir validação JWT, VPC Link, e persistência no banco
    - _Requisitos: 13.2_

  - [x] 17.3 Criar diagrama de componentes em `tech-challange/docs/component-diagram.md`
    - Diagrama Mermaid representando a arquitetura completa: API Gateway, Lambdas, EKS, RDS, Datadog
    - Incluir conexões entre todos os componentes
    - _Requisitos: 13.3_

- [x] 18. Checkpoint final — Verificar todos os artefatos
  - Garantir que todos os testes passam e todos os 13 requisitos estão cobertos. Perguntar ao usuário se há dúvidas.

## Notas

- Tarefas marcadas com `*` são opcionais e podem ser puladas para um MVP mais rápido
- Cada tarefa referencia os requisitos específicos para rastreabilidade
- Checkpoints garantem validação incremental
- Testes de propriedade validam propriedades universais de corretude
- Requisitos 10 e 11 (branch protection e colaborador) requerem acesso à API do GitHub ou configuração manual via UI
- A ordem de implementação respeita as dependências: build → pipeline → documentação → segurança → artefatos arquiteturais

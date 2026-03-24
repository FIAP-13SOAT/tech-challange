# Documento de Requisitos — Finalização do Projeto de Pós-Graduação

## Introdução

Este documento descreve os requisitos pendentes para a finalização do projeto de pós-graduação em Arquitetura de Software (FIAP). O projeto "Garage" é composto por cinco repositórios: **auth-issuer** (Lambda de autenticação em Go), **garage-cloud-stack** (infraestrutura AWS via Terraform), **garage-database-infra** (infraestrutura RDS via Terraform), **garage-observability-stack** (observabilidade com Datadog via Terraform) e **tech-challange** (aplicação principal em Java/Spring Boot). Os requisitos cobrem a criação de artefatos de build, pipelines CI/CD, documentação técnica, configuração de segurança dos repositórios e validação da stack de observabilidade.

## Glossário

- **Pipeline_CI_CD**: Fluxo automatizado de integração e entrega contínua configurado via GitHub Actions (`.github/workflows`)
- **Branch_Protection**: Regra de proteção de branch no GitHub que exige revisão de código e status checks antes do merge na branch principal
- **ECR**: Amazon Elastic Container Registry, serviço de registro de imagens Docker na AWS
- **EKS**: Amazon Elastic Kubernetes Service, serviço gerenciado de Kubernetes na AWS
- **LB_Listener_ARN**: Amazon Resource Name do listener do Load Balancer interno do EKS, usado para integração com o API Gateway
- **RFC**: Request for Comments, documento de decisão arquitetural que justifica escolhas técnicas do projeto
- **Diagrama_de_Sequência**: Representação visual da interação entre componentes ao longo do tempo
- **Diagrama_de_Componentes**: Representação visual da estrutura de alto nível do sistema, incluindo cloud, APIs, banco de dados e monitoramento
- **Colaborador_soat_architecture**: Usuário do GitHub `soat-architecture` que deve ter acesso de colaborador em todos os repositórios
- **Swagger_Postman**: Documentação interativa de API via Swagger UI ou coleção Postman exportada
- **Dockerfile**: Arquivo de definição para construção de imagem Docker de uma aplicação
- **Go_Mod**: Arquivo `go.mod` que define o módulo Go e suas dependências

## Requisitos

### Requisito 1: Configuração de Build do auth-issuer

**User Story:** Como desenvolvedor, eu quero que o repositório auth-issuer tenha os arquivos de gerenciamento de dependências e containerização, para que a aplicação possa ser compilada e implantada de forma reprodutível.

#### Critérios de Aceitação

1. THE auth-issuer SHALL conter um arquivo `go.mod` na raiz do repositório com o nome do módulo `com.fiapchallenge/tech-challange-auth-issuer` e a versão do Go compatível com o código-fonte
2. THE auth-issuer SHALL conter um arquivo `go.sum` na raiz do repositório com os checksums de todas as dependências declaradas no `go.mod`
3. THE auth-issuer SHALL conter um `Dockerfile` na raiz do repositório que compile o código Go e gere uma imagem otimizada para execução como AWS Lambda

### Requisito 2: Pipeline CI/CD do auth-issuer

**User Story:** Como desenvolvedor, eu quero que o repositório auth-issuer tenha uma pipeline CI/CD automatizada, para que o build, teste e deploy da Lambda sejam executados automaticamente a cada push.

#### Critérios de Aceitação

1. THE auth-issuer SHALL conter um arquivo de workflow em `.github/workflows/` que execute o build da aplicação Go a cada push na branch principal
2. WHEN o build for concluído com sucesso, THE Pipeline_CI_CD SHALL construir a imagem Docker e publicá-la no ECR
3. WHEN a imagem for publicada no ECR, THE Pipeline_CI_CD SHALL atualizar a função Lambda com a nova imagem
4. IF o build falhar, THEN THE Pipeline_CI_CD SHALL reportar o erro no status check do commit

### Requisito 3: Documentação de API do auth-issuer

**User Story:** Como avaliador do projeto, eu quero acessar a documentação da API de autenticação, para que eu possa entender e testar os endpoints disponíveis.

#### Critérios de Aceitação

1. THE auth-issuer SHALL conter no README um link funcional para a documentação da API via Swagger_Postman
2. THE documentação da API SHALL descrever o endpoint POST /login, incluindo o formato do corpo da requisição (CPF/CNPJ), os códigos de resposta e o formato do token JWT retornado

### Requisito 4: Atualização do LB Listener ARN no garage-cloud-stack

**User Story:** Como desenvolvedor, eu quero que o API Gateway esteja integrado ao Load Balancer real do EKS, para que as requisições autenticadas sejam roteadas corretamente para a aplicação.

#### Critérios de Aceitação

1. THE garage-cloud-stack SHALL ter a variável `eks_lb_listener_arn` em `terraform.tfvars` configurada com o ARN real do listener do Load Balancer interno do EKS
2. WHEN a variável `eks_lb_listener_arn` estiver configurada, THE API Gateway SHALL rotear requisições autenticadas para a aplicação via VPC Link e Load Balancer interno

### Requisito 5: Pipeline CI/CD do garage-database-infra

**User Story:** Como desenvolvedor, eu quero que o repositório garage-database-infra tenha uma pipeline CI/CD automatizada, para que alterações na infraestrutura de banco de dados sejam aplicadas de forma controlada.

#### Critérios de Aceitação

1. THE garage-database-infra SHALL conter um arquivo de workflow em `.github/workflows/` que execute `terraform plan` a cada pull request
2. WHEN um merge for realizado na branch principal, THE Pipeline_CI_CD SHALL executar `terraform apply` automaticamente
3. IF o `terraform plan` detectar erros de validação, THEN THE Pipeline_CI_CD SHALL reportar o erro no status check do pull request

### Requisito 6: Documentação do garage-database-infra

**User Story:** Como avaliador do projeto, eu quero acessar a documentação do banco de dados, para que eu possa entender as decisões técnicas e a estrutura de dados.

#### Critérios de Aceitação

1. THE garage-database-infra SHALL conter no diretório `docs/` um documento de justificativa da escolha do banco de dados PostgreSQL, incluindo critérios de seleção e alternativas consideradas
2. THE garage-database-infra SHALL conter no diretório `docs/` um diagrama ER (Entidade-Relacionamento) representando as tabelas e seus relacionamentos
3. THE garage-database-infra SHALL conter no diretório `docs/` uma descrição textual dos relacionamentos entre as entidades do sistema

### Requisito 7: Pipeline CI/CD do garage-observability-stack

**User Story:** Como desenvolvedor, eu quero que o repositório garage-observability-stack tenha uma pipeline CI/CD automatizada, para que alterações na stack de observabilidade sejam aplicadas de forma controlada.

#### Critérios de Aceitação

1. THE garage-observability-stack SHALL conter um arquivo de workflow em `.github/workflows/` que execute `terraform plan` a cada pull request
2. WHEN um merge for realizado na branch principal, THE Pipeline_CI_CD SHALL executar `terraform apply` automaticamente
3. IF o `terraform plan` detectar erros de validação, THEN THE Pipeline_CI_CD SHALL reportar o erro no status check do pull request

### Requisito 8: Validação da Stack de Observabilidade

**User Story:** Como avaliador do projeto, eu quero que a stack de observabilidade cubra todos os requisitos de monitoramento, para que o sistema tenha visibilidade operacional completa.

#### Critérios de Aceitação

1. THE garage-observability-stack SHALL conter monitores para latência P95 das APIs com thresholds de warning (1000ms) e critical (2000ms)
2. THE garage-observability-stack SHALL conter monitores para uso de CPU dos pods com thresholds de warning (80%) e critical (95%)
3. THE garage-observability-stack SHALL conter monitores para uso de memória dos pods com thresholds de warning (80%) e critical (95%)
4. THE garage-observability-stack SHALL conter monitor de health check que alerte após 2 checks consecutivos com falha
5. THE garage-observability-stack SHALL conter monitor de taxa de erros em Ordens de Serviço com thresholds de warning (5%) e critical (15%)
6. THE dashboard operacional SHALL exibir o volume diário de ordens de serviço criadas
7. THE dashboard operacional SHALL exibir o tempo médio por fase de processamento (diagnóstico, execução, finalização)
8. THE dashboard operacional SHALL exibir a contagem de erros por operação
9. THE dashboard operacional SHALL exibir a latência P50, P90 e P99 das APIs REST
10. THE dashboard operacional SHALL exibir o consumo de CPU e memória dos pods
11. THE dashboard operacional SHALL exibir o uptime percentual (24h e 7d)
12. THE garage-observability-stack SHALL configurar logs em formato JSON com correlation ID para rastreamento de requisições

### Requisito 9: Documentação de API do tech-challange

**User Story:** Como avaliador do projeto, eu quero acessar a documentação da API principal, para que eu possa entender e testar os endpoints disponíveis.

#### Critérios de Aceitação

1. THE tech-challange SHALL conter no README um link funcional para a documentação da API via Swagger_Postman
2. THE documentação da API SHALL descrever todos os endpoints REST da aplicação, incluindo parâmetros, códigos de resposta e exemplos

### Requisito 10: Branch Protection em Todos os Repositórios

**User Story:** Como líder técnico, eu quero que todos os repositórios tenham proteção de branch configurada, para que o código na branch principal passe por revisão antes do merge.

#### Critérios de Aceitação

1. THE auth-issuer SHALL ter Branch_Protection configurada na branch principal exigindo ao menos 1 aprovação de revisão
2. THE garage-cloud-stack SHALL ter Branch_Protection configurada na branch principal exigindo ao menos 1 aprovação de revisão
3. THE garage-database-infra SHALL ter Branch_Protection configurada na branch principal exigindo ao menos 1 aprovação de revisão
4. THE garage-observability-stack SHALL ter Branch_Protection configurada na branch principal exigindo ao menos 1 aprovação de revisão
5. THE tech-challange SHALL ter Branch_Protection configurada na branch principal exigindo ao menos 1 aprovação de revisão

### Requisito 11: Acesso do Colaborador soat-architecture

**User Story:** Como líder técnico, eu quero que o usuário soat-architecture tenha acesso a todos os repositórios, para que o avaliador da FIAP possa revisar o código.

#### Critérios de Aceitação

1. THE auth-issuer SHALL ter o Colaborador_soat_architecture adicionado com permissão de leitura ou superior
2. THE garage-cloud-stack SHALL ter o Colaborador_soat_architecture adicionado com permissão de leitura ou superior
3. THE garage-database-infra SHALL ter o Colaborador_soat_architecture adicionado com permissão de leitura ou superior
4. THE garage-observability-stack SHALL ter o Colaborador_soat_architecture adicionado com permissão de leitura ou superior
5. THE tech-challange SHALL ter o Colaborador_soat_architecture adicionado com permissão de leitura ou superior

### Requisito 12: RFCs de Decisões Arquiteturais

**User Story:** Como avaliador do projeto, eu quero acessar os documentos de decisão arquitetural, para que eu possa entender as justificativas técnicas das escolhas do projeto.

#### Critérios de Aceitação

1. THE projeto SHALL conter um RFC documentando a escolha da plataforma cloud (AWS), incluindo critérios de seleção, alternativas avaliadas e justificativa final
2. THE projeto SHALL conter um RFC documentando a escolha do banco de dados (PostgreSQL), incluindo critérios de seleção, alternativas avaliadas e justificativa final
3. THE projeto SHALL conter um RFC documentando a estratégia de autenticação (Lambda + JWT via API Gateway), incluindo o fluxo de autenticação, alternativas avaliadas e justificativa final

### Requisito 13: Diagramas Arquiteturais

**User Story:** Como avaliador do projeto, eu quero acessar diagramas técnicos do sistema, para que eu possa compreender visualmente a arquitetura e os fluxos de interação.

#### Critérios de Aceitação

1. THE projeto SHALL conter um Diagrama_de_Sequência representando o fluxo de autenticação (cliente → API Gateway → Lambda Auth Issuer → RDS → JWT)
2. THE projeto SHALL conter um Diagrama_de_Sequência representando o fluxo de abertura de Ordem de Serviço (cliente → API Gateway → Lambda Authorizer → EKS → aplicação → RDS)
3. THE projeto SHALL conter um Diagrama_de_Componentes representando a arquitetura completa do sistema, incluindo: API Gateway, Lambdas, EKS, RDS, Datadog e as conexões entre os componentes

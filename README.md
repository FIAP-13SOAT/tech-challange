# SOAT Tech-challenge

## Sobre o Projeto

Sistema de gestão para oficinas mecânicas desenvolvido como projeto acadêmico da pós-graduação em Software Architecture da FIAP. A solução digitaliza e centraliza os processos operacionais de uma oficina, desde o atendimento inicial até a conclusão dos serviços, proporcionando maior controle e transparência na gestão.

## Problema que Resolve

Oficinas mecânicas frequentemente enfrentam desafios na gestão de ordens de serviço, controle de estoque de peças, acompanhamento de orçamentos e comunicação com clientes. Este sistema oferece uma plataforma integrada que:

- Centraliza informações de clientes e veículos
- Automatiza a criação e acompanhamento de ordens de serviço
- Facilita o controle de estoque de peças e materiais
- Gera orçamentos de forma ágil
- Permite que clientes acompanhem o status de seus serviços em tempo real

## Funcionalidades Principais

### Para Funcionários (Uso Interno)
- **Gestão de Clientes e Veículos**: Cadastro e manutenção de informações de clientes e seus veículos
- **Controle de Estoque**: Gerenciamento de peças e materiais disponíveis
- **Ordens de Serviço**: Criação, atualização e acompanhamento de OS
- **Geração de Orçamentos**: Criação de orçamentos detalhados para aprovação
- **Relatórios**: Visualização de relatórios de execução das ordens de serviço

### Para Clientes (Acesso Externo)
- **Acompanhamento de OS**: Consulta do status e andamento da ordem de serviço via endpoint público

## Perfis de Usuário

- **Administrativo**: Acesso completo ao sistema, gestão de usuários e configurações
- **Atendente**: Cadastro de clientes, veículos, criação de OS e orçamentos
- **Mecânico**: Atualização de status e execução das ordens de serviço
- **Cliente**: Consulta de status da própria ordem de serviço

## Arquitetura

O projeto implementa **Clean Architecture**, garantindo separação de responsabilidades, testabilidade e manutenibilidade do código. Atualmente desenvolvido como monolito, com estrutura preparada para evolução futura.

# Infraestrutura

A aplicação utiliza orquestração em Kubernetes, com escalonamento automático de pods por meio do **Horizontal Pod Autoscaler (HPA)**.
Toda a infraestrutura é gerenciada via **Infrastructure as Code (IaC)**, responsável pelo provisionamento do cluster Kubernetes na AWS.

## CI/CD

O projeto conta com uma pipeline de **CI/CD** configurada (GitHub Actions, GitLab CI, entre outras), responsável por:

- Build da aplicação
- Execução de testes automatizados
- Criação da imagem Docker
- Publicação da imagem no registry
- Deploy automatizado no cluster Kubernetes

Essa automação garante entregas contínuas, padronizadas e confiáveis em todos os ambientes.

## Diagrama de Arquitetura de Infraestrutura

![Arquitetura da aplicação em nuvem](docs/infra/infra.png)


## Tecnologias
- Java 21
- Maven
- Spring Boot
- PostgreSQL
- Docker
- Terraform (Infraestrutura AWS)
- Kubernetes (EKS)

## Documentação da API

A documentação interativa da API está disponível via Swagger UI. Com a aplicação em execução, acesse:

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

A autenticação é feita via token JWT (Bearer Token). Utilize o endpoint de login para obter o token e configure-o no Swagger UI através do botão "Authorize".

### Endpoints REST Disponíveis

| Recurso | Base Path | Descrição |
|---|---|---|
| Clientes | `/customers` | Cadastro, listagem, atualização e remoção de clientes |
| Veículos | `/vehicles` | Cadastro e gestão de veículos vinculados a clientes |
| Ordens de Serviço | `/service-orders` | Criação, acompanhamento e gestão do ciclo de vida das OS (diagnóstico, execução, conclusão, entrega, cancelamento) |
| Acompanhamento Público | `/public/service-orders` | Consulta pública do status da OS pelo cliente (não requer autenticação) |
| Orçamentos | `/quotes` | Geração, aprovação e rejeição de orçamentos |
| Tipos de Serviço | `/service-types` | Cadastro e gestão dos tipos de serviço oferecidos |
| Estoque | `/stock` | Controle de peças e materiais (cadastro, adição, consumo) |
| Movimentações de Estoque | `/stock-movements` | Histórico de movimentações de entrada e saída |
| Notificações | `/notifications` | Listagem e marcação de notificações como lidas |
| Relatórios | `/reports` | Geração de relatórios de execução das ordens de serviço |
| Usuários | `/users` | Cadastro de usuários e autenticação (login) |

## Documentação Adicional

- **[Arquitetura](docs/ARCHITECTURE.md)** - Regras arquiteturais e organização do código
- **[Guia de Execução](docs/SETUP.md)** - Instruções para executar o projeto
- **[Guia de Testes](docs/TESTING.md)** - Instruções para executar análises de cobertura e segurança

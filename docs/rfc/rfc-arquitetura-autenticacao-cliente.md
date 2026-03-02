# RFC: Arquitetura de Autenticação de Clientes via API Gateway + Lambda

| Campo          | Valor                                      |
|----------------|---------------------------------------------|
| **Status**     | Proposta                                    |
| **Autor**      | Johann Bandelow                             |
| **Data**       | 2026-02-13                                  |
| **Componente** | API Gateway + Lambda Function (Autenticação)|

---

## 1. Situação Atual

```mermaid
graph LR
    subgraph "Serviço Java (Spring Boot)"
        A["/users/login"] -->|email + senha| B["JwtHelper — gera JWT"]
        C["UserSecurityFilter"] -->|valida JWT| D["Endpoints privados"]
        E["/public/service-orders/**"] -->|sem auth| F["PublicServiceOrderController"]
    end

    U["Funcionário"] --> A
    U --> D
    CL["Cliente"] --> E
```

| Tipo de Acesso | Autenticação Atual | Endpoints |
|---|---|---|
| **Funcionários (interno)** | JWT via `/users/login` (email + senha), validado pelo `UserSecurityFilter` | Todos os endpoints exceto `/public/**` |
| **Clientes (externo)** | **Nenhuma** — apenas validação de CPF no nível da OS | `/public/service-orders/{id}/track?cpfCnpj=...` |

---

## 2. Requisito

> Implementar API Gateway + Lambda Serverless para:
> 1. Validar CPF do cliente
> 2. Consultar existência e status do cliente na base de dados
> 3. Gerar e devolver um token JWT válido para consumo das APIs protegidas

---

## 3. Arquitetura Proposta

```mermaid
sequenceDiagram
    participant CL as Cliente
    participant GW as API Gateway
    participant λ as Lambda
    participant DB as Banco de Dados (RDS)
    participant API as Serviço Java (Spring Boot)

    Note over CL,API: Fluxo 1 — Autenticação do Cliente (obter token)
    CL->>GW: POST /auth/customer {cpf: "123.456.789-00"}
    GW->>λ: Invoca Lambda
    λ->>DB: SELECT * FROM customers WHERE cpf = ?
    DB-->>λ: Customer encontrado + status ativo
    λ-->>GW: {token: "eyJhbG...", expiresIn: 3600}
    GW-->>CL: 200 OK + JWT

    Note over CL,API: Fluxo 2 — Requisição autenticada do Cliente
    CL->>GW: GET /service-orders/{id}/track (Authorization: Bearer <token>)
    GW->>λ: Invoca Lambda (como Authorizer)
    λ-->>GW: Allow (policy document) + CPF no contexto
    GW->>API: Forward request + header X-Customer-CPF
    API-->>GW: 200 OK + dados da OS
    GW-->>CL: 200 OK
```

### 3.1 Componentes

#### AWS API Gateway

O API Gateway atua como **ponto de entrada único** para todas as requisições. Ele gerencia dois grupos de rotas:

| Grupo de Rotas | Authorizer | Destino |
|---|---|---|
| **Rotas de funcionários** (`/users/**`, `/customers/**`, `/quotes/**`, etc.) | Nenhum no API Gateway (o Spring Security já faz) | Serviço Java |
| **Rota de autenticação do cliente** (`POST /auth/customer`) | Nenhum (endpoint público) | Lambda |
| **Rotas protegidas do cliente** (`/customer-api/**`) | **Lambda Authorizer** | Serviço Java |

#### Lambda de Autenticação — Dupla Função

A Lambda de autenticação terá **duas responsabilidades**, acionadas de formas diferentes:

##### Função A: Emissão de Token (invocação direta)

```
POST /auth/customer
Body: { "cpf": "12345678900" }
```

1. Recebe o CPF do cliente
2. Consulta a tabela `customers` no banco de dados (RDS)
3. Valida se o cliente existe e está ativo
4. Se válido, gera um JWT contendo:
   - `sub`: CPF do cliente
   - `customer_id`: UUID do cliente
   - `type`: `"customer"` (diferenciador do token de funcionário)
   - `exp`: expiração (ex: 1 hora)
5. Retorna o token ao cliente

##### Função B: Authorizer do API Gateway

Quando configurada como **Lambda Authorizer** no API Gateway, ela:

1. Recebe o token JWT do header `Authorization`
2. Valida a assinatura e expiração
3. Extrai o CPF do `sub` do token
4. Retorna um **policy document** de Allow/Deny ao API Gateway
5. Injeta o CPF como contexto (`context.cpf`) — o API Gateway repassa via header `X-Customer-CPF`

#### Serviço Java — Adaptações Necessárias

O serviço Java precisa de **mudanças mínimas**:

- **Criar novos endpoints para clientes** (ex: `/customer-api/service-orders/{id}/track`) que leem o CPF do header `X-Customer-CPF` em vez de receber como query param
- **Não precisa validar o JWT do cliente** — isso já foi feito pela Lambda no API Gateway
- **Manter a autenticação interna existente** (`UserSecurityFilter` + `JwtHelper`) intacta para funcionários

> [!IMPORTANT]
> Os endpoints internos (funcionários) e externos (clientes) devem ser **completamente separados**. Não reutilize os mesmos endpoints para ambos os fluxos — isso evita confusão de permissões e simplifica a configuração do API Gateway.

---

## 4. Diagrama de Arquitetura Final

```mermaid
graph TB
    subgraph "Clientes"
        C1["Cliente (app/web)"]
    end

    subgraph "Funcionários"
        F1["Funcionário (backoffice)"]
    end

    subgraph "AWS"
        GW["API Gateway"]

        subgraph "Lambda"
            LA["POST /auth/customer — Gera JWT"]
            LB["Lambda Authorizer — Valida JWT"]
        end

        subgraph "EKS / Container"
            API["Serviço Java (Spring Boot)"]
        end

        DB[("RDS — PostgreSQL")]
    end

    C1 -->|"1. POST /auth/customer {cpf}"| GW
    GW -->|"2. Invoca"| LA
    LA -->|"3. Consulta cliente"| DB
    LA -->|"4. Retorna JWT"| GW

    C1 -->|"5. GET /customer-api/... + Bearer token"| GW
    GW -->|"6. Valida token"| LB
    LB -->|"7. Allow + CPF"| GW
    GW -->|"8. Forward + X-Customer-CPF"| API

    F1 -->|"POST /users/login"| GW
    GW -->|"Forward direto"| API
    F1 -->|"Endpoints privados + Bearer token"| GW
    API -->|"Lê/Escreve"| DB
```

---

## 5. Segredo JWT

A Lambda e o serviço Java precisam usar a **mesma chave secreta** para assinar/validar os JWTs? **Não necessariamente**:

| Abordagem | Prós | Contras |
|---|---|---|
| **Chave separada** (recomendada) | Lambda é independente; Java não precisa validar token de cliente | Dois secrets para gerenciar |
| **Chave compartilhada** | Um único secret | Acoplamento; qualquer vazamento compromete ambos |

> [!TIP]
> **Recomendo chave separada**. O serviço Java **nunca precisa validar o JWT do cliente** — quem faz isso é a Lambda Authorizer. O Java só precisa confiar no header `X-Customer-CPF` que chega do API Gateway. Isso simplifica a integração e mantém o desacoplamento.

---

## 6. Resumo de Mudanças Necessárias

| Componente | O que fazer |
|---|---|
| **Lambda** (novo repo) | Criar função com handler duplo: emissão de JWT + authorizer |
| **API Gateway** (Terraform) | Configurar rotas, Lambda Authorizer, e integração com o serviço Java |
| **Serviço Java** | Criar endpoints `/customer-api/**` que leem `X-Customer-CPF` do header; permitir essas rotas sem `UserSecurityFilter` |
| **Terraform** | Criar recursos: Lambda, API Gateway, IAM roles, secrets |

---

## 7. Perguntas para Decisão

1. **A Lambda deve acessar o mesmo banco de dados (RDS) do serviço Java?** Isso é o mais simples, mas cria acoplamento. Alternativa: a Lambda poderia chamar um endpoint do serviço Java para validar o CPF.

2. **Os endpoints de funcionários também devem passar pelo API Gateway?** Sim, é recomendado para ter um ponto de entrada único com rate limiting, logging e monitoramento unificados.

3. **Qual será a expiração do token do cliente?** Sugestão: 1 hora (menor que o token de funcionário, que é 10 horas).

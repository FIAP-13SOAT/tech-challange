# Arquitetura do Projeto

## Visão Geral

Este projeto implementa os princípios da **Clean Architecture** proposta por Robert C. Martin (Uncle Bob), garantindo separação de responsabilidades, independência de frameworks, testabilidade e manutenibilidade do código.

## Estrutura de Camadas

O projeto está organizado em camadas concêntricas, onde as dependências apontam sempre para dentro (das camadas externas para as internas):

```
┌─────────────────────────────────────────┐
│         Adapters (Inbound)              │  ← Controllers, APIs REST
├─────────────────────────────────────────┤
│         Application (Use Cases)         │  ← Regras de aplicação
├─────────────────────────────────────────┤
│         Domain (Entities)               │  ← Regras de negócio
├─────────────────────────────────────────┤
│         Adapters (Outbound)             │  ← Repositories, Entities JPA
└─────────────────────────────────────────┘
```

### 1. Domain (Camada de Domínio)

**Localização**: `domain/`

**Responsabilidade**: Contém as regras de negócio fundamentais e entidades do domínio.

**Características**:
- Não possui dependências de outras camadas
- Contém as entidades de domínio (Customer, Vehicle, ServiceOrder, etc.)
- Define interfaces de repositórios (ports)
- Implementa validações e regras de negócio core
- Contém exceções de domínio

**Exemplos**:
- `domain/customer/Customer.java` - Entidade de domínio
- `domain/customer/CustomerRepository.java` - Interface do repositório (port)
- `domain/serviceorder/ServiceOrderStatus.java` - Enums e value objects

**Regras**:
- ✅ Pode conter lógica de negócio pura
- ✅ Pode definir interfaces (ports)
- ❌ Não pode depender de frameworks
- ❌ Não pode conhecer detalhes de infraestrutura

### 2. Application (Camada de Aplicação)

**Localização**: `application/`

**Responsabilidade**: Orquestra o fluxo de dados entre as camadas e implementa casos de uso.

**Características**:
- Depende apenas da camada de domínio
- Implementa os casos de uso do sistema
- Coordena a execução de regras de negócio
- Define comandos (DTOs de entrada)
- Contém exceções de aplicação

**Estrutura por caso de uso**:
```
application/
├── customer/
│   ├── create/
│   │   ├── CreateCustomerUseCase.java (interface)
│   │   └── CreateCustomerService.java (implementação)
│   ├── update/
│   ├── delete/
│   └── list/
```

**Exemplos**:
- `application/customer/create/CreateCustomerUseCase.java` - Interface do caso de uso
- `application/customer/create/CreateCustomerService.java` - Implementação do caso de uso
- `application/serviceorder/create/CreateServiceOrderCommand.java` - DTO de entrada

**Regras**:
- ✅ Pode depender da camada de domínio
- ✅ Pode definir interfaces de casos de uso
- ✅ Pode orquestrar múltiplas operações de domínio
- ❌ Não pode conhecer detalhes de controllers ou repositórios
- ❌ Não pode depender de frameworks externos

### 3. Adapters (Camada de Adaptadores)

#### 3.1. Inbound Adapters (Entrada)

**Localização**: `adapters/inbound/controller/`

**Responsabilidade**: Recebe requisições externas e as converte para chamadas de casos de uso.

**Características**:
- Controllers REST
- Validação de entrada (DTOs)
- Conversão de DTOs para comandos
- Tratamento de respostas HTTP
- Documentação OpenAPI

**Exemplos**:
- `adapters/inbound/controller/customer/CustomerController.java`
- `adapters/inbound/controller/serviceorder/ServiceOrderController.java`

**Regras**:
- ✅ Pode depender da camada de aplicação
- ✅ Pode usar frameworks web (Spring MVC)
- ✅ Responsável por validações de formato
- ❌ Não pode conter lógica de negócio
- ❌ Não pode acessar repositórios diretamente

#### 3.2. Outbound Adapters (Saída)

**Localização**: `adapters/outbound/`

**Responsabilidade**: Implementa as interfaces de repositório definidas no domínio.

**Estrutura**:
```
adapters/outbound/
├── entities/          # Entidades JPA
│   ├── CustomerEntity.java
│   └── ServiceOrderEntity.java
└── repositories/      # Implementações de repositórios
    ├── customer/
    └── serviceorder/
```

**Características**:
- Implementa interfaces de repositório do domínio
- Contém entidades JPA (mapeamento ORM)
- Realiza conversão entre entidades de domínio e entidades JPA
- Acessa banco de dados

**Regras**:
- ✅ Pode depender da camada de domínio
- ✅ Pode usar frameworks de persistência (JPA, Hibernate)
- ✅ Responsável por mapeamento objeto-relacional
- ❌ Não pode expor detalhes de persistência para outras camadas
- ❌ Não pode conter lógica de negócio

### 4. Config (Configuração)

**Localização**: `config/`

**Responsabilidade**: Configurações de infraestrutura e frameworks.

**Exemplos**:
- `config/SecurityConfig.java` - Configuração de segurança
- `config/OpenApiConfig.java` - Configuração do Swagger
- `config/GlobalExceptionHandler.java` - Tratamento global de exceções

### 5. Infra (Infraestrutura)

**Localização**: `infra/`

**Responsabilidade**: Componentes de infraestrutura transversais.

**Exemplos**:
- `infra/JwtHelper.java` - Utilitário para JWT
- `infra/UserDetailsServiceImpl.java` - Implementação de autenticação

### 6. Shared (Compartilhado)

**Localização**: `shared/`

**Responsabilidade**: Componentes compartilhados entre camadas.

**Exemplos**:
- `shared/exception/` - Exceções base
- `shared/mapper/` - Mapeadores genéricos
- `shared/pagination/` - Utilitários de paginação

## Princípios Aplicados

### 1. Dependency Rule (Regra de Dependência)

As dependências do código-fonte devem apontar apenas para dentro, em direção às políticas de alto nível:

```
Adapters → Application → Domain
```

### 2. Separation of Concerns (Separação de Responsabilidades)

Cada camada tem uma responsabilidade bem definida e não deve conhecer detalhes de implementação de outras camadas.

### 3. Dependency Inversion (Inversão de Dependência)

As camadas internas definem interfaces (ports) que são implementadas pelas camadas externas:

```java
// Domain define a interface
public interface CustomerRepository {
    Customer save(Customer customer);
}

// Adapter implementa a interface
public class CustomerRepositoryImpl implements CustomerRepository {
    // Implementação com JPA
}
```

### 4. Single Responsibility (Responsabilidade Única)

Cada classe tem uma única razão para mudar:
- Use Cases: orquestram fluxo de negócio
- Entities: encapsulam regras de negócio
- Repositories: persistem dados
- Controllers: lidam com HTTP

## Fluxo de Dados

### Exemplo: Criar Cliente

```
1. HTTP Request
   ↓
2. CustomerController (Inbound Adapter)
   ↓
3. CreateCustomerService (Application/Use Case)
   ↓
4. Customer (Domain Entity)
   ↓
5. CustomerRepository (Domain Interface)
   ↓
6. CustomerRepositoryImpl (Outbound Adapter)
   ↓
7. CustomerEntity (JPA Entity)
   ↓
8. Database
```

## Benefícios da Arquitetura

### 1. Testabilidade
- Camadas internas podem ser testadas sem dependências externas
- Use cases podem ser testados com mocks de repositórios
- Regras de negócio isoladas e testáveis

### 2. Independência de Frameworks
- Regras de negócio não dependem de Spring, JPA ou outros frameworks
- Facilita migração ou substituição de tecnologias

### 3. Independência de UI
- Lógica de negócio não conhece detalhes de REST, GraphQL ou outras interfaces

### 4. Independência de Banco de Dados
- Domínio não conhece SQL, NoSQL ou detalhes de persistência
- Facilita mudança de banco de dados

### 5. Manutenibilidade
- Código organizado e com responsabilidades claras
- Facilita localização e correção de bugs
- Reduz acoplamento entre componentes

## Convenções de Nomenclatura

### Use Cases
- Interface: `{Ação}{Entidade}UseCase` (ex: `CreateCustomerUseCase`)
- Implementação: `{Ação}{Entidade}Service` (ex: `CreateCustomerService`)

### Commands
- `{Ação}{Entidade}Command` (ex: `CreateServiceOrderCommand`)

### Repositories
- Interface: `{Entidade}Repository` (ex: `CustomerRepository`)
- Implementação: `{Entidade}RepositoryImpl` (ex: `CustomerRepositoryImpl`)

### Entities
- Domínio: `{Entidade}` (ex: `Customer`)
- JPA: `{Entidade}Entity` (ex: `CustomerEntity`)

### Controllers
- `{Entidade}Controller` (ex: `CustomerController`)

## Regras de Desenvolvimento

### ✅ Permitido

1. **Domain** pode definir interfaces de repositório
2. **Application** pode depender de interfaces do Domain
3. **Adapters** podem implementar interfaces do Domain
4. **Use Cases** podem orquestrar múltiplas operações de domínio
5. **Entities** podem conter validações e regras de negócio

### ❌ Proibido

1. **Domain** não pode depender de Application ou Adapters
2. **Application** não pode conhecer detalhes de Controllers ou Repositories
3. **Entities de Domínio** não podem ter anotações JPA
4. **Use Cases** não podem acessar banco de dados diretamente
5. **Controllers** não podem conter lógica de negócio

## Evolução Futura

A arquitetura atual está preparada para:

- **Migração para Microserviços**: Cada módulo pode se tornar um serviço independente
- **Adição de Novos Adapters**: Novos tipos de entrada (GraphQL, gRPC) ou saída (NoSQL, APIs externas)
- **Implementação de CQRS**: Separação de comandos e consultas
- **Event-Driven Architecture**: Adição de eventos de domínio e mensageria

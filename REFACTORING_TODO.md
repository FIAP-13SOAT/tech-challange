# Refactoring TODO List: Clean Architecture Migration

This document outlines the steps required to refactor the project into a strict Clean Architecture structure, addressing the feedback from recent code reviews.

---

## 1. Feedback do professor — pontos a atender

### 1.1 Controller e separação de responsabilidades

- **Controller (Clean Architecture)**: O `*Controller` da camada de aplicação é responsável por **instanciar** (ou receber por injeção) as interfaces **Gateway** e **Presenter**, e **realizar a injeção dessas dependências nos Use Cases**. Ele orquestra a aplicação, mas **não conhece HTTP nem frameworks externos**.
- **Separação obrigatória**: Manter **duas camadas distintas**:
  1. **Adapter de entrada HTTP (`*Resource`)**  
     → **É aqui que ficam os endpoints REST** (`@RestController`, `@GetMapping`, `@PostMapping`, etc.).  
     Esta classe só sabe de HTTP: recebe o request, faz o parse (body, path, query), chama o **Controller** e devolve a resposta (ex.: `ResponseEntity`).  
     Convenção de nome: `VehicleResource`, `CustomerResource`, `ServiceOrderResource`.
  2. **Controller de aplicação (`*Controller`)**  
     → Classe **sem dependências externas** (sem anotações Spring de web). Recebe dados já em formato de aplicação (DTOs de entrada, IDs, etc.), instancia ou usa Use Case, Gateway e Presenter injetados, e retorna o resultado. Não mapeia URLs nem métodos HTTP.

**Resumo**: Os **endpoints REST** ficam no **`*Resource`**. O **`*Controller`** é o orquestrador puro (UseCase + Gateway + Presenter), chamado pelo resource HTTP.

### 1.2 Gateway — sem vazamento de domínio

- O Gateway **não deve permitir vazamento do domínio**.
- É responsável por **traduzir** a entidade recebida do Use Case para **formatos externos** (DTOs, DAOs, entidades JPA), expondo apenas os atributos necessários.
- **Não** deve carregar **regras de negócio** (validações de domínio, invariantes). Apenas conversão de dados na fronteira com infraestrutura (ex.: persistência).

### 1.3 Presenter — tradução para o exterior

- Implementar **Presenters** de forma explícita.
- O Presenter, assim como o Gateway, atua na **tradução** das entidades (saída dos Use Cases) para os **formatos exigidos externamente** (DTOs de resposta, ViewModels).
- Deve **evitar a exposição de regras de negócio** que pertencem ao núcleo da aplicação. Apenas formatação e seleção de dados para a camada de interface.

### 1.4 Use Cases

- Use Cases devem ser **classes Java puras**, sem dependência de frameworks (evitar anotações como `@Service` ou `@Transactional` onde o objetivo for manter a camada de aplicação limpa). A injeção de dependências (Gateway, etc.) é feita pelo Controller.

---

## 2. Princípios gerais da refatoração

- **Gateways**: Renomear todas as interfaces `*Repository` para `*Gateway` e implementações para `*GatewayImpl`, tratando-as como adapters de saída para sistemas externos.
- **Presenters**: Criar classes `*Presenter` que convertem entidades de domínio em DTOs/ViewModels de resposta.
- **Controllers**: O `*Controller` (aplicação) instancia (ou recebe) Use Cases, Gateways e Presenters e injeta as dependências nos Use Cases; não contém endpoints REST.
- **Resources HTTP**: O `*Resource` contém exclusivamente endpoints REST e delega para o `*Controller`.
- **Mappers em adapters**: Remover os mappers da camada de adapters; a transformação de saída deve ser responsabilidade do Presenter.

---

## 3. Onde ficam os endpoints REST?

| Camada                    | Responsabilidade                                      | Onde ficam os endpoints? |
|---------------------------|--------------------------------------------------------|---------------------------|
| **`*Resource` (HTTP)**    | Falar HTTP: rotas, métodos, request/response, status  | **Sim** — `@GetMapping`, `@PostMapping`, etc. |
| **`*Controller` (app)**   | Orquestrar UseCase + Gateway + Presenter (sem HTTP)   | **Não** — sem anotações de web |

Fluxo: **Cliente HTTP** → **`*Resource`** (mapeia URL/método, parse do body) → **`*Controller`** (chama UseCase, usa Gateway e Presenter) → **`*Resource`** (monta resposta e status).

---

## 4. Roadmap de implementação (por domínio)

Para cada domínio, garantir:

1. Renomear Repository → Gateway (interface e impl).
2. Garantir que o Gateway apenas traduza Entity ↔ DTO/DAO (sem regras de negócio).
3. Criar Presenter que traduz entidade → DTO de resposta (sem regras de negócio).
4. Criar **`*Controller`** (sem anotações HTTP) que instancia/usa UseCase, Gateway e Presenter.
5. Criar ou refatorar **`*Resource`** onde ficam **apenas** os endpoints REST; esse adapter chama o `*Controller`.
6. Remover mappers da camada de adapters; mover a transformação para Presenter (saída) e para o Controller (entrada, quando necessário).
7. Refatorar testes para **não injetar UseCases com `@Autowired`**; os testes devem instanciar UseCases manualmente com dependências reais (Gateways/Repositories), sem mocks.

### [ ] Vehicle Domain

- [x] Rename `VehicleRepository` → `VehicleGateway` (interface em domain ou application).
- [x] Rename `VehicleRepositoryImpl` → `VehicleGatewayImpl`; garantir tradução Entity ↔ DTO/DAO, sem vazamento de domínio.
- [x] Create `VehiclePresenter` (entidade → DTO de resposta).
- [x] Refatorar o atual `VehicleController` para `VehicleResource` (apenas endpoints REST).
- [x] Criar `VehicleController` como orquestrador (UseCase + Gateway + Presenter), sem anotações HTTP.
- [x] Remover `VehicleMapper` da camada de adapters.


### [x] Customer Domain

- [x] Rename `CustomerRepository` → `CustomerGateway`, `CustomerRepositoryImpl` → `CustomerGatewayImpl`.
- [x] Gateway: só tradução Entity ↔ DTO/DAO.
- [x] Create `CustomerPresenter`.
- [x] Refatorar `CustomerController` para `CustomerResource` (endpoints REST).
- [x] Criar `CustomerController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [x] Remover mappers de customer da camada de adapters.

### [x] ServiceOrder Domain

- [x] Rename `ServiceOrderRepository` → `ServiceOrderGateway` e impl.
- [x] Create `ServiceOrderPresenter`.
- [x] Refatorar `ServiceOrderController` para `ServiceOrderResource` (endpoints REST).
- [x] Criar `ServiceOrderController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [x] Remover mappers de service order da camada de adapters.

### [x] User Domain

- [x] Rename `UserRepository` → `UserGateway` e impl.
- [x] Create `UserPresenter`.
- [x] Refatorar `UserController` para `UserResource` (endpoints REST).
- [x] Criar `UserController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [x] Remover mappers de user da camada de adapters.

### [ ] Stock Domain

- [ ] Rename `StockRepository` → `StockGateway` e impl.
- [ ] Create `StockPresenter`.
- [ ] Refatorar `StockController` para `StockResource` (endpoints REST).
- [ ] Criar `StockController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [ ] Remover mappers de stock da camada de adapters.

### [ ] Stock Movement Domain

- [ ] Rename `StockMovementRepository` → `StockMovementGateway`.
- [ ] Create `StockMovementPresenter`.
- [ ] Refatorar `StockMovementController` para `StockMovementResource` (endpoints REST).
- [ ] Criar `StockMovementController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [ ] Remover mappers de stock movement da camada de adapters.

### [ ] Service Type Domain

- [ ] Rename `ServiceTypeRepository` → `ServiceTypeGateway`.
- [ ] Create `ServiceTypePresenter`.
- [ ] Refatorar `ServiceTypeController` para `ServiceTypeResource` (endpoints REST).
- [ ] Criar `ServiceTypeController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [ ] Remover mappers de service type da camada de adapters.

### [ ] Service Order Execution Domain

- [ ] Rename `ServiceOrderExecutionRepository` → `ServiceOrderExecutionGateway`.
- [ ] Create `ServiceOrderExecutionPresenter`.
- [ ] Refatorar controller existente para `ServiceOrderExecutionResource` (endpoints REST).
- [ ] Criar `ServiceOrderExecutionController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [ ] Remover mappers de service order execution da camada de adapters.

### [ ] Quote Domain

- [ ] Rename `QuoteRepository` → `QuoteGateway`.
- [ ] Create `QuotePresenter`.
- [ ] Refatorar `QuoteController` para `QuoteResource` (endpoints REST).
- [ ] Criar `QuoteController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [ ] Remover mappers de quote da camada de adapters.

### [ ] Notification Domain

- [ ] Rename `NotificationRepository` → `NotificationGateway`.
- [ ] Create `NotificationPresenter`.
- [ ] Refatorar `NotificationController` para `NotificationResource` (endpoints REST).
- [ ] Criar `NotificationController` como orquestrador (UseCase + Gateway + Presenter), sem HTTP.
- [ ] Remover mappers de notification da camada de adapters.

### [ ] Report (se aplicável)

- [ ] Identificar Gateways/Presenters envolvidos; aplicar mesma separação `*Resource` (HTTP) vs `*Controller` (orquestração).

---

## 5. Estrutura de pacotes

- [ ] Renomear `adapters/outbound/repositories` → `adapters/gateways` (ou equivalente consistente).
- [ ] Mover os controllers de aplicação (`*Controller`, orquestradores) para `src/main/java/com/fiapchallenge/garage/controllers`.
- [ ] Renomear/mover controllers HTTP atuais para `*Resource` (ex.: `adapters/inbound/http` ou `adapters/inbound/api`).
- [ ] Definir convenção oficial:
  - `*Resource` = endpoint REST.
  - `*Controller` = orquestrador UseCase + Gateway + Presenter (sem HTTP).
- [ ] Criar pacote para Presenters, ex.: `adapters/presenters` ou por domínio em `adapters/inbound/presenters`.
- [ ] Evitar nomenclatura “Ports IN/OUT” se o objetivo for aderir estritamente à Clean Architecture nas camadas principais.

---

## 6. Plano de verificação

- [ ] **Compile**: Projeto compila sem erros após cada refatoração de domínio.
- [ ] **Testes**: `mvn test` sem regressões.
- [ ] **Arquitetura**: Camadas core/domain e application sem dependências de Spring ou outros frameworks externos (exceto onde explicitamente aceito, ex. configuração).
- [ ] **Checklist por domínio**:
  - [ ] Endpoints REST existem apenas em classes `*Resource`.
  - [ ] `*Controller` está em `src/main/java/com/fiapchallenge/garage/controllers`, sem anotações de web, e só orquestra UseCase + Gateway + Presenter.
  - [ ] Não existem mappers na camada de adapters (substituídos por Presenter e montagem de input no Controller).
  - [ ] Gateway não contém regras de negócio; apenas tradução Entity ↔ DTO/DAO.
  - [ ] Presenter não expõe regras de negócio; apenas entidade → DTO/ViewModel de resposta.

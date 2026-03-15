# ADR-003: API REST como superfície de entrada e separação Resource / Controller / Presenter

## Status

Aceito.

## Contexto

A aplicação precisa expor uma API para clientes externos. Era necessário definir:

- Qual estilo de API adotar como principal (REST, GraphQL, gRPC, etc.).
- Como isolar detalhes HTTP da orquestração e das regras de aplicação.
- Quem converte resultados de domínio em DTOs de resposta.

## Decisão

- **REST como único adapter de entrada (por enquanto)**: a superfície pública é REST (JSON), documentada com OpenAPI (SpringDoc). Outros protocolos (GraphQL, gRPC) podem ser adicionados no futuro como **novos adapters inbound**, sem alterar domínio ou casos de uso.
- **Resource (adapter HTTP)**: classes `*Resource` são os únicos pontos com anotações HTTP (`@RestController`, `@GetMapping`, etc.). Elas recebem a requisição, validam/formam o comando e delegam para o **Controller** de aplicação. Não contêm lógica de negócio.
- **Controller de aplicação**: o `*Controller` (não é um controller Spring) orquestra use cases, gateways e **Presenter**. Não conhece HTTP, status codes nem serialização. Pode ser instanciado pelo Resource ou injetado, conforme o contexto.
- **Presenter**: converte entidades e tipos de domínio (incluindo listas paginadas) em DTOs de resposta. O Controller chama o Presenter e devolve o resultado ao Resource, que apenas retorna a resposta HTTP.

Fluxo: **HTTP → Resource → Controller → Use cases + Gateways; Controller → Presenter → DTO → Resource → HTTP**.

## Consequências

### Positivas

- Lógica de negócio e orquestração permanecem livres de detalhes REST.
- Troca ou adição de formato de resposta (ex.: outro schema JSON, XML) pode ficar no Presenter/Resource.
- Novos canais (GraphQL, gRPC) podem ser implementados como novos adapters que reutilizam os mesmos Controllers e casos de uso.
- Testes de orquestração podem exercitar Controller + use cases + presenter sem servidor HTTP.

### Negativas

- Três conceitos (Resource, Controller, Presenter) por contexto; mais uma camada a manter e documentar.
- Desenvolvedores precisam saber onde colocar código (HTTP no Resource, orquestração no Controller, formato no Presenter).

### Irreversibilidade

Unificar Resource e Controller em um único “controller que faz tudo” reintroduziria acoplamento forte à API REST e dificultaria a adição de outros protocolos e testes isolados. A decisão de **REST como adapter e separação Resource / Controller / Presenter** é tratada como **estrutural e difícil de reverter** sem refatoração significativa.

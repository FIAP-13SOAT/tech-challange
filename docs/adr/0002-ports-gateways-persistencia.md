# ADR-002: Persistência via Ports (Gateways) no domínio e implementação em adapters

## Status

Aceito.

## Contexto

O sistema precisa persistir entidades de domínio (Customer, Vehicle, ServiceOrder, etc.) sem que o núcleo de negócio dependa de JPA, SQL ou de um banco específico. Era necessário decidir:

- Onde definir o “contrato” de persistência.
- Como manter entidades de domínio puras, separadas do mapeamento ORM.
- Quem realiza a conversão entre modelo de domínio e modelo de persistência.

## Decisão

- **Portas no Domain**: o domínio define interfaces de persistência (ex.: `CustomerGateway`, `VehicleGateway`) com métodos expressos em **entidades e value objects de domínio**. Nenhuma interface de persistência fica na application ou nos adapters como contrato principal.
- **Implementação nos Adapters**: em `adapters/outbound` existem implementações dessas interfaces (ex.: `CustomerGatewayImpl`) que usam **repositórios JPA** e **entidades JPA** (ex.: `CustomerEntity`). A conversão domínio ↔ entidade JPA é responsabilidade exclusiva do adapter.
- **Entidades duplas**: entidades de domínio (ex.: `Customer`) vivem no Domain; entidades JPA (ex.: `CustomerEntity`) vivem em `adapters/outbound/entities`. O Domain não conhece anotações JPA nem tabelas.

Casos de uso e regras de aplicação dependem apenas das interfaces de gateway; quem injeta as implementações é a infraestrutura (Spring).

## Consequências

### Positivas

- Domínio e aplicação permanecem independentes de JPA e PostgreSQL.
- Troca de banco ou ORM exige apenas novas implementações dos gateways e novas entidades JPA, sem alterar regras de negócio.
- Testes de use cases usam mocks dos gateways, sem necessidade de banco.
- Modelo de domínio pode evoluir (tipos, invariantes) com impacto contido nos adapters.

### Negativas

- Duplicação conceitual (entidade de domínio vs entidade JPA) e mapeamento explícito em cada gateway.
- Possível vazamento se tipos de biblioteca (ex.: `Page`, `Pageable`) forem usados nas interfaces do domínio; recomenda-se tipos próprios de paginação no domínio quando possível.

### Irreversibilidade

Introduzir acesso direto a repositórios JPA a partir da application ou expor entidades JPA no domínio quebraria a independência de persistência e exigiria refatoração ampla. A decisão de **portas no domínio e implementação em adapters** é considerada **estrutural e difícil de reverter**.

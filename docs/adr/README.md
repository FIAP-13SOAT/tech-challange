# Architecture Decision Records (ADRs)

Registro das decisões arquiteturais relevantes e de difícil reversão do projeto.

| ADR | Título | Resumo |
|-----|--------|--------|
| [0001](0001-adocao-clean-architecture.md) | Adoção da Clean Architecture com camadas concêntricas | Estrutura em Domain → Application → Adapters e regra de dependência estrita. |
| [0002](0002-ports-gateways-persistencia.md) | Persistência via Ports (Gateways) no domínio e implementação em adapters | Portas no domain; implementação JPA em adapters; entidades de domínio separadas de entidades JPA. |
| [0003](0003-rest-e-separacao-resource-controller-presenter.md) | API REST como superfície de entrada e separação Resource / Controller / Presenter | REST como adapter inbound; Resource (HTTP), Controller (orquestração), Presenter (DTOs). |

## Convenção

- Número sequencial de 4 dígitos + título em kebab-case.
- Status: Aceito | Supersedido | Depreciado.
- Seções: Contexto, Decisão, Consequências (e opcionalmente Irreversibilidade).

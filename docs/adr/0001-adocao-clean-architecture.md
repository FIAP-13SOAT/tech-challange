# ADR-001: Adoção da Clean Architecture com camadas concêntricas

## Status

Aceito.

## Contexto

O sistema precisa evoluir com regras de negócio complexas, múltiplos pontos de entrada (API, futuros consumers) e persistência que pode mudar ou escalar. Era necessário definir uma arquitetura que:

- Mantenha regras de negócio estáveis e testáveis independentemente de frameworks e UI.
- Permita trocar ou adicionar adaptadores (REST, banco, integrações) sem reescrever o núcleo.
- Garanta direção única de dependências para evitar que detalhes de infraestrutura contaminem o domínio.

## Decisão

Adotamos **Clean Architecture** (Uncle Bob) com camadas concêntricas e **regra de dependência** estrita:

- **Domain**: entidades, value objects e interfaces de portas (ex.: gateways). Sem dependências externas.
- **Application**: casos de uso (interfaces + implementações). Dependem apenas do Domain.
- **Adapters (inbound)**: recebem requisições (ex.: REST), convertem para comandos e invocam casos de uso.
- **Adapters (outbound)**: implementam as portas do domínio (persistência, integrações).

Fluxo de dependência: **Adapters → Application → Domain**. Nada no Domain ou na Application conhece HTTP, JPA ou Spring.

## Consequências

### Positivas

- Domínio e casos de uso são testáveis com mocks, sem subir servidor ou banco.
- Troca de framework web ou de persistência fica restrita aos adapters.
- Código organizado por responsabilidade; onboarding e manutenção facilitados.
- Base preparada para evolução (CQRS, eventos, eventual divisão em serviços).

### Negativas

- Mais arquivos e camadas; mudanças simples podem tocar várias camadas.
- Exige disciplina: vazamento de tipos de framework para o domínio quebra o benefício.
- Curva de aprendizado para quem não conhece Clean/Hexagonal.

### Irreversibilidade

Reverter para uma arquitetura “tudo em controllers/serviços” exigiria refatoração massiva de todo o código, redefinição de testes e reeducação do time. A decisão é tratada como **estrutural e de longo prazo**.

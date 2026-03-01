# RFC: Escolha da Linguagem para Lambda de Autenticação

| Campo          | Valor                                      |
|----------------|---------------------------------------------|
| **Status**     | Proposta                                    |
| **Autor**      | Johann Bandelow                             |
| **Data**       | 2026-02-13                                  |
| **Componente** | Lambda Function — Autenticação (JWT)        |

---

## 1. Contexto

O projeto **tech-challange** utiliza uma arquitetura baseada em microserviços com Java/Spring Boot. Precisamos implementar uma **AWS Lambda Function** dedicada à autenticação de usuários (geração e validação de tokens JWT).

### Fluxo de Autenticação

A Lambda será utilizada como **autorizador (authorizer)** integrada ao **AWS API Gateway**. Para cada requisição que chega ao API Gateway, a Lambda será invocada recebendo o **CPF do cliente** como parâmetro. O fluxo será:

1. O cliente envia uma requisição ao **API Gateway** informando seu **CPF**.
2. O API Gateway invoca a **Lambda de autenticação**, passando o CPF.
3. A Lambda valida o CPF, gera/valida o **token JWT** e retorna a política de autorização ao API Gateway.
4. O API Gateway encaminha (ou rejeita) a requisição ao microserviço de destino.

Como essa função será invocada em **cada requisição autenticada** ao sistema, performance, custo e confiabilidade são fatores críticos. A Lambda precisa ser:

- **Rápida**: o tempo de autenticação impacta diretamente a latência percebida pelo usuário. Cada milissegundo adicional é multiplicado por **todas as requisições**.
- **Leve**: baixo consumo de memória e CPU para manter custos reduzidos, dado o **alto volume de invocações**.
- **Simples**: fácil de manter por um time que já possui contexto em Java.

---

## 2. Linguagens Candidatas

As três linguagens avaliadas são:

| Critério                   | **Golang**          | **Java**               | **Node.js (JS/TS)**   |
|----------------------------|---------------------|------------------------|------------------------|
| Cold Start (médio)         | ~40 ms              | ~500–2000 ms*          | ~130 ms                |
| Warm Execution             | Excelente           | Bom                    | Bom                    |
| Consumo de Memória         | Muito baixo (~20 MB)| Alto (~120–256 MB)     | Moderado (~50–80 MB)   |
| Tamanho do binário/pacote  | ~5–15 MB            | ~30–80 MB (com deps)   | ~10–50 MB (com node_modules) |
| Facilidade da linguagem    | Moderada            | Alta (time já conhece) | Alta                   |
| Documentação AWS           | Extensa             | Extensa                | Extensa                |
| Tipagem                    | Estática            | Estática               | Dinâmica (TS: estática)|
| Concorrência               | Nativa (goroutines) | Threads / Virtual Threads | Event Loop (single-thread) |

> \* Java com **SnapStart** pode reduzir cold start para ~200–400 ms, mas ainda significativamente maior que Go.

---

## 3. Análise Detalhada

### 3.1 Cold Start

O cold start é o fator **mais crítico** para uma Lambda de autenticação. Como ela é chamada pelo **API Gateway a cada requisição** (recebendo o CPF do cliente), um cold start lento resulta em latência adicional perceptível pelo usuário final antes mesmo de a requisição chegar ao microserviço de destino.

**Go é o vencedor claro neste quesito.** Com cold starts na faixa de **~40 ms**, Go é **3x mais rápido que Node.js** (~130 ms) e **até 50x mais rápido que Java** sem otimizações (~2000 ms). Mesmo com SnapStart, Java ainda fica na faixa de centenas de milissegundos.

> [!IMPORTANT]
> A partir de agosto de 2025, a AWS passou a cobrar pela fase de `INIT` (inicialização) das Lambdas. Isso significa que cold starts longos agora impactam diretamente o **custo operacional**, não apenas a latência.

#### 📊 Comparativo de Cold Start

Para um benchmark detalhado e atualizado comparando cold starts entre linguagens no AWS Lambda, consulte:

- **[maxday — AWS Lambda Performance Benchmark](https://maxday.github.io/lambda-perf/)** — Benchmark automatizado e continuamente atualizado com dados reais de cold start para todas as runtimes suportadas pela AWS Lambda.

---

### 3.2 Performance em Execução (CPU, RAM e Tempo)

| Métrica              | Golang                        | Java                          | Node.js (JS/TS)               |
|----------------------|-------------------------------|-------------------------------|-------------------------------|
| **CPU**              | Compilado nativamente, uso mínimo de CPU | JVM com JIT — alto uso inicial, otimiza ao longo do tempo | V8 com JIT — moderado        |
| **RAM (base)**       | ~20 MB                        | ~120–256 MB                   | ~50–80 MB                     |
| **Tempo de execução**| ~1–5 ms (operação JWT típica) | ~5–15 ms                      | ~3–10 ms                      |

Go produz um **binário compilado estático** que não precisa de VM ou runtime pesado. Isso resulta em:
- Menor consumo de memória (permite usar configurações de 128 MB confortavelmente)
- Execução previsível e consistente
- Menor custo por invocação na AWS

Java, por depender da JVM, demanda mais memória base, o que obriga a configurar a Lambda com **256 MB ou mais** — aumentando o custo por invocação.

---

### 3.3 Facilidade da Linguagem

Go tem uma curva de aprendizado **moderada**, mas é conhecida por sua **simplicidade proposital**:

- Sintaxe mínima e sem "mágicas"
- Sem herança, generics simplificados, error handling explícito
- Padrão da comunidade é código direto e legível
- Ferramentas de linting, formatting (`gofmt`) e testing são built-in

Embora o time tenha maior familiaridade com **Java**, Go é uma linguagem **significativamente mais simples**. Um desenvolvedor Java produtivo pode se tornar produtivo em Go em **1–2 semanas**.

Node.js/TypeScript tem baixa barreira de entrada, mas o ecossistema de dependências (npm) pode introduzir complexidade desnecessária para uma função tão pontual.

---

### 3.4 Documentação e Ecossistema

As três linguagens possuem **excelente documentação** para uso com AWS Lambda:

| Recurso                    | Golang | Java | Node.js |
|----------------------------|--------|------|---------|
| AWS SDK oficial            | ✅      | ✅    | ✅       |
| AWS Lambda runtime         | ✅      | ✅    | ✅       |
| Exemplos oficiais AWS      | ✅      | ✅    | ✅       |
| Bibliotecas JWT maduras    | ✅ (`golang-jwt`) | ✅ (`jjwt`, `nimbus-jose`) | ✅ (`jsonwebtoken`) |
| Comunidade ativa           | ✅      | ✅    | ✅       |

Go não fica atrás em ecossistema. A biblioteca [`golang-jwt/jwt`](https://github.com/golang-jwt/jwt) é madura, bem mantida e amplamente adotada.

---

## 4. Quadro Comparativo Final

| Critério                          | Golang 🥇 | Java 🥉  | Node.js/TS 🥈 |
|-----------------------------------|-----------|----------|----------------|
| Cold Start                        | ⭐⭐⭐⭐⭐   | ⭐⭐       | ⭐⭐⭐            |
| Performance (CPU/RAM)             | ⭐⭐⭐⭐⭐   | ⭐⭐⭐      | ⭐⭐⭐⭐           |
| Custo operacional                 | ⭐⭐⭐⭐⭐   | ⭐⭐       | ⭐⭐⭐⭐           |
| Facilidade de aprendizado         | ⭐⭐⭐⭐    | ⭐⭐⭐⭐⭐   | ⭐⭐⭐⭐⭐          |
| Simplicidade do código            | ⭐⭐⭐⭐⭐   | ⭐⭐⭐      | ⭐⭐⭐⭐           |
| Documentação/Ecossistema          | ⭐⭐⭐⭐    | ⭐⭐⭐⭐⭐   | ⭐⭐⭐⭐⭐          |
| Tamanho do pacote de deploy       | ⭐⭐⭐⭐⭐   | ⭐⭐       | ⭐⭐⭐            |
| **Nota geral (para Lambdas)**     | **🟢 Ideal** | **🔴 Não recomendado** | **🟡 Aceitável** |

---

## 5. Recomendação

> [!TIP]
> **Recomendamos fortemente o uso de Golang** para a Lambda de autenticação.

### Justificativa principal

1. **Cold start imbatível (~40 ms)** — essencial para uma função que está no caminho crítico de toda requisição autenticada.
2. **Menor consumo de recursos** — permite usar 128 MB de memória, reduzindo custos em até 50% comparado a Java.
3. **Binário único e autocontido** — deploy simples, sem dependências externas, sem node_modules, sem JVM.
4. **Linguagem simples e manutenível** — código direto, sem abstrações pesadas, ideal para uma função com escopo bem definido.
5. **Custo operacional mais baixo** — combinação de cold start rápido + baixo uso de memória + execução veloz = menor custo por invocação.

### Por que não Java?

Embora o time tenha experiência com Java, usá-lo em Lambda **não aproveita os pontos fortes do Java** (ecossistema Spring, frameworks robustos). Em vez disso, expõe seus pontos fracos: cold start pesado, alto consumo de memória e pacotes de deploy grandes. A Lambda de autenticação é uma **função simples e pontual** — não precisa do poder (e da bagagem) da JVM.

### Por que não Node.js/TypeScript?

Node.js seria uma opção aceitável, com cold start razoável (~130 ms). No entanto, Go ainda o supera em todos os quesitos de performance. Além disso, para uma função serverless isolada, o ecossistema npm introduz complexidade desnecessária em gerenciamento de dependências.

---

## 6. Próximos Passos

Se aprovado:

1. Criar um novo repositório Git dedicado para funções serverless e inicializar o projeto Go da Lambda de autenticação
2. Implementar as funções de geração e validação de JWT
3. Configurar deploy via Terraform (já existente em `infra/`)
4. Adicionar testes unitários e de integração
5. Configurar CI/CD para build e deploy automático

---

## 7. Referências

- [AWS Lambda Performance Benchmark — maxday](https://maxday.github.io/lambda-perf/)
- [AWS Lambda Cold Start Analysis — 2024/2025](https://nebjak.dev/blog/lambda-benchmark-2024)
- [golang-jwt/jwt — Biblioteca JWT para Go](https://github.com/golang-jwt/jwt)
- [AWS Lambda Developer Guide — Go](https://docs.aws.amazon.com/lambda/latest/dg/lambda-golang.html)
- [AWS Lambda SnapStart (Java)](https://docs.aws.amazon.com/lambda/latest/dg/snapstart.html)

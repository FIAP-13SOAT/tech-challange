# Guia de Testes e Análises

## Análise de Cobertura de Testes (SonarQube)

### Primeiro Acesso

#### 1. Subir o container do SonarQube
```bash
docker compose -f docker-compose-sonar.yml up -d
```

#### 2. Acessar a interface do SonarQube
- Abra `http://localhost:9000` no navegador
- Faça login com as credenciais padrão: `admin` / `admin`

#### 3. Configurar nova senha
O SonarQube solicitará que você altere a senha padrão. Faça isso para continuar.

#### 4. Desabilitar autenticação forçada
Este passo simplifica a análise local, permitindo que o Maven crie o projeto automaticamente na primeira vez, **sem a necessidade de gerar tokens** ou configurar o projeto manualmente na UI.

- Navegue até: `Administration` > `Configuration` > `General Settings`
- No menu da esquerda, clique em `Security`
- Encontre a opção **`Force user authentication`** e **DESATIVE-A** (mude o seletor para `false`)
- Navegue até: `Administration` > `Security` > `Global permissions`
- Marque as opções de `Execute Analysis` e `Create projects` para a role `Anyone`

#### 5. Executar a análise
Com o SonarQube rodando e a autenticação desabilitada, execute o comando Maven no diretório do projeto:

```bash
mvn clean verify sonar:sonar
```

Na primeira execução, o scanner do Sonar irá criar automaticamente o projeto `garage-tech-challenge` na sua instância local e enviar o relatório de análise.

#### 6. Verificar resultado
- Abra `http://localhost:9000` no navegador
- Verifique a última análise executada

### Execuções Subsequentes

Para executar análises após a configuração inicial:

```bash
mvn clean verify sonar:sonar
```

---

## Testes de Segurança (OWASP ZAP)

### Execução Completa

#### 1. Subir aplicação e executar testes
```bash
docker compose -f docker-compose-zap.yml up --build
docker compose -f docker-compose-zap.yml --profile security-test up zap
```

### Relatórios Gerados

Os relatórios são salvos no diretório `./zap-reports/`:

- `baseline-report.html` - Relatório baseline
- `full-report.html` - Relatório completo
- Arquivos XML também são gerados para integração CI/CD

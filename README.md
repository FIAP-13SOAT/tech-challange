# tech-challange

## Tecnologias
- Java 21
- Maven
- Spring Boot

## Testes:

### Verificar cobertura de testes:

1. Subir container docker do SonarQube:
```bash
    docker-compose -f docker-compose-sonar.yml up -d
```

2. Acessar o SonarQube em http://localhost:9000 (usuário e senha padrão: admin/admin)
3. Configurar novo usuário e senha
4. Configurar projecto com as seguintes informações:
   - Project Key: garage-tech-challenge
   - Project Name: garage-tech-challenge

5. Configurar token de autenticação (gerar token e copiar)
6. Alterar `pom.xml` adicionando o token gerado na seção `<sonar.login>`:
```xml
    <sonar.login>sqp_57baccf38dce58b0f202e1fe45cb0a81f1f29256</sonar.login>
```

7.Executar maven clean:
```bash
    mvn clean verify sonar:sonar
```

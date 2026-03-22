FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

ADD https://dtdg.co/latest-java-tracer /app/dd-java-agent.jar

COPY ../mvnw .
COPY ../.mvn .mvn
COPY ../pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY ../src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-javaagent:/app/dd-java-agent.jar", "-jar", "target/garage-0.0.1-SNAPSHOT.jar"]
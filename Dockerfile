
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY ./pom.xml ./mvnw ./mvnw.cmd /app/
COPY .mvn /app/.mvn/
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY ./src /app/src/
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/target/me-0.0.1-SNAPSHOT.jar /app/me-0.0.1-SNAPSHOT.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "me-0.0.1-SNAPSHOT.jar"]
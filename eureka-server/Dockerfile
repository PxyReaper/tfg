# Etapa 1: Build
FROM eclipse-temurin:21 AS build
WORKDIR /workspace
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /workspace/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
LABEL authors="Miguel Pareja"
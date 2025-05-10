FROM ubuntu:latest
FROM eclipse-temurin:21
WORKDIR /root
COPY ./pom.xml ./
COPY ./.mvn ./.mvn
COPY ./mvnw ./
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["java","-jar","/root/target/order-service-0.0.1-SNAPSHOT.jar"]
LABEL authors="Miguel Pareja"
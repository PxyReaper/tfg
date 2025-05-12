FROM ubuntu:latest
FROM eclipse-temurin:21
WORKDIR /root
COPY ./pom.xml ./
COPY ./.mvn ./.mvn
COPY ./mvnw ./
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
COPY wait-for-it.sh /wait-for-it.sh
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["/wait-for-it.sh","eureka-server:8761", "--","java","-jar","/root/target/api-gateway-0.0.1-SNAPSHOT.jar"]
LABEL authors="Miguel Pareja"
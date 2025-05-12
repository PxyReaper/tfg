FROM ubuntu:latest
FROM eclipse-temurin:21
WORKDIR /root
COPY ./pom.xml ./
COPY ./.mvn ./.mvn
COPY ./mvnw ./
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests
COPY wait-for-it.sh /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh","config-server:8888", "--","java","-jar","/root/target/user-service-0.0.1-SNAPSHOT.jar"]
LABEL authors="Miguel Pareja"
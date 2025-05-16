FROM eclipse-temurin:21
WORKDIR /root
COPY ./pom.xml ./
COPY ./.mvn ./.mvn
COPY ./mvnw ./
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
COPY wait-for-it.sh /wait-for-it.sh
# Ejecutar update antes de install
RUN apt-get update && apt-get install -y iputils-ping telnet netcat-openbsd && rm -rf /var/lib/apt/lists/*

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["/wait-for-it.sh","eureka-server:8761", "--","java","-jar","/root/target/payment-service-0.0.1-SNAPSHOT.jar"]
LABEL authors="Miguel Pareja"

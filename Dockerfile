FROM openjdk:22-jdk-slim

COPY target/transactions-microservice-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app



ENTRYPOINT ["java", "-jar", "app.jar"]
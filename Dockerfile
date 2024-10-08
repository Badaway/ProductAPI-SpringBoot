FROM openjdk:22-ea-2-jdk-oracle
ARG JAR_FILE=target/*.jar
COPY ./target/ProductAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


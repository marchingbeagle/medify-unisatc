FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/medify-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/.env .env
ENTRYPOINT ["java","-jar","/app.jar"]

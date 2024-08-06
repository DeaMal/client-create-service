FROM alpine:3.20

RUN apk add openjdk17
COPY target/client-create-service-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
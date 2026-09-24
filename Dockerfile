FROM eclipse-temurin:21-jdk

COPY ./target/activemq-messaging-0.0.1-SNAPSHOT.jar app.jar

COPY otel/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

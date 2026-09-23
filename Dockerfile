FROM eclipse-temurin:21-jdk

ARG JAR_FILE=target/*.jar

COPY ./target/activemq-messaging-0.0.1-SNAPSHOT.jar springbootartemisMQ.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "springbootartemisMQ.jar"]
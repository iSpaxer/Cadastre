FROM openjdk:20
ARG JAR_FILE=target/*.jar
COPY  ./target/first-0.0.1-SNAPSHOT.jar rabbit.jar
ENTRYPOINT ["java", "-jar", "/rabbit.jar"]
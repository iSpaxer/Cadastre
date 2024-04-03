FROM openjdk:20
ARG JAR_FILE=target/*.jar
COPY  ./target/Site-0.0.1-SNAPSHOT.jar site.jar
ENTRYPOINT ["java", "-jar", "/site.jar"]
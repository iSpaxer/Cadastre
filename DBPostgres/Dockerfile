FROM openjdk:20
ARG JAR_FILE=target/*.jar
COPY  ./target/DBPostgres-1.0-SNAPSHOT.jar dbp.jar
ENTRYPOINT ["java", "-jar", "/dbp.jar"]

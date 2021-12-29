FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-users-0.0.1-SNAPSHOT.jar userserver.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "userserver.jar"]
FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-stores-0.0.1-SNAPSHOT.jar storeserver.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "storeserver.jar"]
FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-products-0.0.1-SNAPSHOT.jar productserver.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "productserver.jar"]
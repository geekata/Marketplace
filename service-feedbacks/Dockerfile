FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-feedbacks-0.0.1-SNAPSHOT.jar feedbackserver.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "feedbackserver.jar"]
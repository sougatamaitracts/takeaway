FROM openjdk:8-jdk-alpine
MAINTAINER sougatamaitra
VOLUME /tmp
COPY eventService.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
FROM openjdk:8-jdk-alpine
MAINTAINER sougatamaitra
VOLUME /tmp
COPY authorizationServer.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.active.profiles=prod","app.jar"]
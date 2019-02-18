FROM openjdk:8-jdk-alpine
MAINTAINER sougatamaitra
VOLUME /tmp
COPY employeeService.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]

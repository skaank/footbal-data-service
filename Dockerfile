FROM amazoncorretto:17
EXPOSE 8080
ADD target/football-data-service.jar football-data-service.jar
ENTRYPOINT ["java","-jar","/football-data-service.jar"]
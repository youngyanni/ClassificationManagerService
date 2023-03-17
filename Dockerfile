FROM maven:3.6.3-openjdk-17-slim as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-oracle
COPY --from=build /usr/src/app/target/*.jar /usr/app/classification-service.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/usr/app/classification-service.jar"]

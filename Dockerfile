FROM openjdk:17-oracle
COPY target/*.jar /usr/app/classifier-manager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/classifier-manager.jar"]

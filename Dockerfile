FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY *.jar PharmacyBack.jar
ENTRYPOINT ["java","-jar","/PharmacyBack.jar"]
EXPOSE 8080
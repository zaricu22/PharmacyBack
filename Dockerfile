# Build stage: Use Maven image (no wrapper needed)
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy all module pom.xml files and download dependencies (cached layer)
COPY pom.xml ./
COPY core/pom.xml core/
COPY core/application/pom.xml core/application/
COPY core/application-contracts/pom.xml core/application-contracts/
COPY core/domain/pom.xml core/domain/
COPY core/domain-shared/pom.xml core/domain-shared/
COPY external/pom.xml external/
COPY external/app-main/pom.xml external/app-main/
COPY external/persistence/pom.xml external/persistence/
COPY external/security/pom.xml external/security/
COPY external/webapi/pom.xml external/webapi/
RUN mvn dependency:go-offline

# Copy the source code and build the JAR file, skipping tests for speed
COPY core ./core
COPY external ./external
RUN mvn package -DskipTests

#------------------------------------------------------------------

# Run stage: Use a smaller JRE image for the final container
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy only the built JAR (app-main outputs to root target/ via outputDirectory config)
COPY --from=build /app/target/*.jar app.jar

# FIX: Render requires the app to listen on port 10000
ENV SERVER_PORT=10000
EXPOSE 10000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

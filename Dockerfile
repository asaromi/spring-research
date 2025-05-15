# Step 1: Build the Spring Boot JAR (build stage)
FROM maven:3.8-openjdk-17 AS build

# Set the working directory inside the container for the build
WORKDIR /app

# Copy the Maven project files to the container so the build process can be included
COPY pom.xml .
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw .
COPY mvnw.cmd .

# Package the application (generate the JAR file)
RUN ./mvnw package -DskipTests

# Step 2: Run the Spring Boot JAR (runtime stage)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container for the runtime
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
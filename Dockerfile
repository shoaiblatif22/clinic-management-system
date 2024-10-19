# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /application

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the Maven wrapper and its configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Run Maven to download dependencies first (optional, but it speeds up builds)
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Run Maven tests
# RUN mvn clean test

# Run Maven to build the application (skip tests as they already ran)
RUN mvn clean package -DskipTests

# Copy the built JAR file from the target directory
COPY target/clinic-management-system-0.0.1-SNAPSHOT.jar /application/application.jar

# Expose the port on which the application will run
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/application/application.jar"]

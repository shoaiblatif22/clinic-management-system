# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /application

# Copy the Maven wrapper and its configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src ./src

# Install Maven
RUN apt-get update && apt-get install -y maven

# Run Maven to build the application
RUN mvn clean package -DskipTests

# Copy the built JAR file from the target directory
COPY target/clinic-management-system-0.0.1-SNAPSHOT.jar /application/application.jar

# Expose the port on which the application will run
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/application/application.jar"]

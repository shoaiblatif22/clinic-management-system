FROM openjdk:17-jre-slim

# Install PostgreSQL client
RUN apt-get update && apt-get install -y postgresql-client && rm -rf /var/lib/apt/lists/*

# Copy your application JAR file and other necessary files
COPY target/clinic-management-system-0.0.1-SNAPSHOT.jar /application/application.jar

# Set the working directory
WORKDIR /application

# Run the application
CMD ["java", "-jar", "clinic-management-system.jar"]

FROM openjdk:17-jre-slim

# Install PostgreSQL client
RUN apt-get update && apt-get install -y postgresql-client && rm -rf /var/lib/apt/lists/*

# Copy your application JAR file
COPY target/clinic-management-system-0.0.1-SNAPSHOT.jar /application/application.jar

# Copy the initialization script
COPY init-db.sh /application/init-db.sh

# Set the working directory
WORKDIR /application

# Run the initialization script and then the application
CMD ["sh", "-c", "./init-db.sh && java -jar application.jar"]


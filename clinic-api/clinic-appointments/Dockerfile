# Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Copy root POM and module POM
COPY pom.xml .
COPY clinic-appointments/pom.xml clinic-appointments/

# Download dependencies
RUN mvn -f clinic-appointments/pom.xml dependency:go-offline -B

# Copy source code
COPY clinic-appointments/src clinic-appointments/src/

# Build the specific module
RUN mvn -f clinic-appointments/pom.xml package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/clinic-appointments/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]

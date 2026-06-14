# Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Copy root POM and module POM
COPY ../pom.xml .
COPY pom.xml clinic-user/

# Download dependencies
RUN mvn -f clinic-user/pom.xml dependency:go-offline -B

# Copy source code
COPY src clinic-user/src/

# Build the specific module
RUN mvn -f clinic-user/pom.xml package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy built JAR from the module
COPY --from=build /app/clinic-user/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

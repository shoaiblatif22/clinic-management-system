# Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies separately for faster builds
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code 
COPY src ./src

# Build with layer caching
RUN mvn package -DskipTests -Dmaven.test.skip=true

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the JAR file 
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Add non-root user for security
RUN useradd -m myuser && chown -R myuser:myuser /app
USER myuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

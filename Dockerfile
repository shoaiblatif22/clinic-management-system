FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app
COPY .mvn/ ./mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY src ./src


FROM build AS test
RUN ./mvnw test


FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
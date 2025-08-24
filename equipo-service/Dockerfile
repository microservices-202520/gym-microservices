# Multi-stage build for Equipo Service
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/equipo-service-0.0.1-SNAPSHOT.jar equipo-service.jar

EXPOSE 8084

CMD ["java", "-jar", "equipo-service.jar"]

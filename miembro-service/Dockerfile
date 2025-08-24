# Multi-stage build for Miembro Service
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
COPY --from=build /app/target/miembro-service-0.0.1-SNAPSHOT.jar miembro-service.jar

EXPOSE 8081

CMD ["java", "-jar", "miembro-service.jar"]

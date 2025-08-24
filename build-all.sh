#!/bin/bash

echo "Compilando todos los microservicios..."

# Compilar cada microservicio
echo "Compilando miembro-service..."
cd miembro-service
./mvnw clean package -DskipTests
cd ..

echo "Compilando entrenador-service..."
cd entrenador-service
./mvnw clean package -DskipTests
cd ..

echo "Compilando clase-service..."
cd clase-service
./mvnw clean package -DskipTests
cd ..

echo "Compilando equipo-service..."
cd equipo-service
./mvnw clean package -DskipTests
cd ..

echo "Compilando gateway-service..."
cd gateway-service
./mvnw clean package -DskipTests
cd ..

echo "¡Compilación completada!"
echo "Para ejecutar los servicios, ejecuta: docker-compose up --build"

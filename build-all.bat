@echo off

echo Compilando todos los microservicios...

echo Compilando miembro-service...
cd miembro-service
call mvnw.cmd clean package -DskipTests
cd ..

echo Compilando entrenador-service...
cd entrenador-service
call mvnw.cmd clean package -DskipTests
cd ..

echo Compilando clase-service...
cd clase-service
call mvnw.cmd clean package -DskipTests
cd ..

echo Compilando equipo-service...
cd equipo-service
call mvnw.cmd clean package -DskipTests
cd ..

echo Compilando gateway-service...
cd gateway-service
call mvnw.cmd clean package -DskipTests
cd ..

echo Compilacion completada!
echo Para ejecutar los servicios, ejecuta: docker-compose up --build

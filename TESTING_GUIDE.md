# API Gateway - Gimnasio Microservices 🏋️‍♂️

Este documento proporciona las instrucciones completas para configurar, ejecutar y probar el API Gateway del sistema de microservicios del gimnasio.

## 📋 Tabla de Contenidos

1. [Prerrequisitos](#prerrequisitos)
2. [Arquitectura del Sistema](#arquitectura-del-sistema)
3. [Configuración Inicial](#configuración-inicial)
4. [Ejecución del Proyecto](#ejecución-del-proyecto)
5. [Pruebas del API Gateway](#pruebas-del-api-gateway)
6. [Endpoints Disponibles](#endpoints-disponibles)
7. [Verificación de Funcionalidades](#verificación-de-funcionalidades)
8. [Troubleshooting](#troubleshooting)

## 🔧 Prerrequisitos

### Software Requerido

- **Java 17+** 
- **Maven 3.8+**
- **Docker & Docker Compose**
- **Postman** o **curl** (para pruebas)

### Servicios Externos

1. **Eureka Server** (puerto 8761)
2. **Keycloak** (puerto 8080)
3. **Microservicios del gimnasio:**
   - miembro-service (puerto 8081)
   - entrenador-service (puerto 8082)
   - clase-service (puerto 8083)
   - equipo-service (puerto 8084)

## 🏗️ Arquitectura del Sistema

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Cliente/UI    │───▶│   API Gateway    │───▶│ Eureka Server   │
└─────────────────┘    │   (Puerto 8085)  │    │  (Puerto 8761)  │
                       └──────────────────┘    └─────────────────┘
                                │
                                ▼
                       ┌──────────────────┐
                       │    Keycloak      │
                       │   (Puerto 8080)  │
                       └──────────────────┘
                                │
                                ▼
      ┌─────────────────────────┼─────────────────────────┐
      │                         │                         │
      ▼                         ▼                         ▼
┌─────────────┐    ┌─────────────────┐    ┌─────────────────┐
│miembro-srv  │    │entrenador-srv   │    │   clase-srv     │
│(Puerto 8081)│    │(Puerto 8082)    │    │ (Puerto 8083)   │
└─────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │   equipo-srv    │
                       │ (Puerto 8084)   │
                       └─────────────────┘
```

## ⚙️ Configuración Inicial

### 1. Verificar configuración de Eureka

Asegúrate de que el archivo `application.properties` del gateway tenga:

```properties
# Eureka Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
eureka.instance.prefer-ip-address=true
eureka.instance.hostname=localhost
```

### 2. Verificar configuración de Keycloak

```properties
# OAuth2 Resource Server Configuration
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://host.docker.internal:8080/realms/gimnasio
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://host.docker.internal:8080/realms/gimnasio/protocol/openid-connect/certs
```

### 3. Configurar Keycloak (Realm "gimnasio")

1. Accede a Keycloak Admin Console: `http://localhost:8080`
2. Crea realm `gimnasio`
3. Crea cliente `gateway-service`
4. Configura roles: `ROLE_ADMIN`, `ROLE_USER`
5. Crea usuarios de prueba

## 🚀 Ejecución del Proyecto

### Opción 1: Con Docker Compose (Recomendado)

```bash
# Desde el directorio raíz del proyecto
cd gym-microservices

# Construir y ejecutar todos los servicios
docker compose up --build -d

# Verificar que todos los containers estén ejecutándose
docker compose ps
```

### Opción 2: Ejecución Local

```bash
# 1. Iniciar Eureka Server (si no está en Docker)
# Ejecutar eureka-server en puerto 8761

# 2. Iniciar Keycloak (si no está en Docker)
# Ejecutar keycloak en puerto 8080

# 3. Iniciar los microservicios
# Ejecutar cada microservicio en sus puertos respectivos

# 4. Iniciar API Gateway
cd gateway-service
mvn spring-boot:run
```

## 🧪 Pruebas del API Gateway

### 1. Verificación de Estado de Servicios

#### a) Health Check del Gateway
```bash
curl -X GET http://localhost:8085/actuator/health
```

**Respuesta esperada:**
```json
{
  "status": "UP",
  "components": {
    "eureka": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

#### a.1) Verificar Eureka Server
```bash
curl -X GET http://localhost:8761/
```

#### a.2) Verificar Keycloak
```bash
curl -X GET http://localhost:8080/
```

#### b) Verificar Rutas Configuradas
```bash
curl -X GET http://localhost:8085/actuator/gateway/routes
```

#### c) Verificar Servicios Registrados en Eureka
```bash
curl -X GET http://localhost:8761/eureka/apps
```

### 2. Obtener Token JWT de Keycloak

curl -X POST ^
  http://localhost:8080/realms/gimnasio/protocol/openid-connect/token ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "grant_type=password&client_id=gateway-service&client_secret={CLIENTSECRET}&username=admin1&password=admin"


**Guarda el `access_token` de la respuesta para las siguientes pruebas.**

### 3. Pruebas de Enrutamiento

#### a) Servicio de Miembros
```bash
# Listar miembros
curl -X GET \
  http://localhost:8085/api/miembros \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN'

# Obtener miembro específico
curl -X GET \
  http://localhost:8085/api/miembros/1 \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN'
```

#### b) Servicio de Entrenadores
```bash
# Listar entrenadores
curl -X GET \
  http://localhost:8085/api/entrenadores \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN'
```

#### c) Servicio de Clases
```bash
# Listar clases
curl -X GET \
  http://localhost:8085/api/clases \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN'
```

#### d) Servicio de Equipos
```bash
# Listar equipos
curl -X GET \
  http://localhost:8085/api/equipos \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN'
```

### 4. Prueba de Agregación de Respuestas

#### Endpoint Especial de Resumen de Miembro
```bash
curl -X GET \
  http://localhost:8085/api/miembros/1/resumen \
  -H 'Authorization: Bearer YOUR_JWT_TOKEN'
```

**Respuesta esperada:**
```json
{
  "miembro": {
    "id": 1,
    "nombre": "Juan Pérez",
    "email": "juan@email.com"
  },
  "clases": [
    {
      "id": 1,
      "nombre": "Yoga Matutino",
      "instructor": "María García"
    }
  ],
  "timestamp": "2025-10-05T12:30:45"
}
```

## 📍 Endpoints Disponibles

### Microservicios (a través del Gateway)

| Servicio | Ruta | Puerto Directo | A través del Gateway |
|----------|------|----------------|---------------------|
| Miembros | `/api/miembros/**` | :8081 | :8085 |
| Entrenadores | `/api/entrenadores/**` | :8082 | :8085 |
| Clases | `/api/clases/**` | :8083 | :8085 |
| Equipos | `/api/equipos/**` | :8084 | :8085 |

### Endpoints del Gateway

| Endpoint | Método | Descripción | Autenticación |
|----------|--------|-------------|---------------|
| `/actuator/health` | GET | Health check | No |
| `/actuator/info` | GET | Información del gateway | No |
| `/actuator/gateway/routes` | GET | Rutas configuradas | Sí (ADMIN) |
| `/api/miembros/{id}/resumen` | GET | Agregación de datos | Sí |

### Swagger/OpenAPI

- **Gateway Swagger UI**: `http://localhost:8085/swagger-ui.html`
- **API Docs**: `http://localhost:8085/v3/api-docs`

## ✅ Verificación de Funcionalidades

### 1. ✅ Balanceo de Carga

# Ejecutar múltiples requests para verificar load balancing
for ($i = 1; $i -le 5; $i++) {
    curl.exe -X GET http://localhost:8085/api/miembros `
      -H "Authorization: Bearer YOUR_JWT_TOKEN" `
      -w "Response time: %{time_total}s`n"
}


### 2. ✅ Forwarding de JWT Tokens

- Verificar que los microservicios reciben el token JWT
- Revisar logs de los microservicios para confirmar autenticación

### 3. ✅ Manejo de Errores

```bash
# Probar con token inválido
curl -X GET \
  http://localhost:8085/api/miembros \
  -H 'Authorization: Bearer INVALID_TOKEN'

# Probar sin token
curl -X GET http://localhost:8085/api/miembros
```

## 📊 Métricas y Monitoreo

### Actuator Endpoints

```bash
# Métricas generales
curl http://localhost:8085/actuator/metrics

# Métricas específicas del Gateway
curl http://localhost:8085/actuator/metrics/gateway.requests

# Estado de health con detalles
curl http://localhost:8085/actuator/health/eureka
```

## 🎯 Checklist de Verificación

- [ ] **Docker Compose ejecutándose correctamente**
- [ ] **Eureka Server ejecutándose en puerto 8761**
- [ ] **Keycloak ejecutándose en puerto 8080 con realm "gimnasio"**
- [ ] **Los 4 microservicios registrados en Eureka**
- [ ] **API Gateway ejecutándose en puerto 8085**
- [ ] **Health checks respondiendo correctamente**
- [ ] **Gateway se conecta exitosamente a Eureka (sin errores de conexión)**
- [ ] **Autenticación JWT funcionando**
- [ ] **Enrutamiento a todos los microservicios**
- [ ] **Agregación de respuestas funcionando**
- [ ] **CORS configurado correctamente**
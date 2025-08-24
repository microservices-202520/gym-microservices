# 🏋️‍♂️ API Endpoints - Microservicios Gimnasio

## 🚪 Punto de Entrada
**Base URL:** `http://localhost:8080`

---

## 👥 Miembros
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/miembros` | Crear miembro |
| GET | `/api/miembros` | Obtener todos los miembros |
| GET | `/api/miembros/{id}` | Obtener miembro por ID |
| PUT | `/api/miembros/{id}` | Actualizar miembro |
| DELETE | `/api/miembros/{id}` | Eliminar miembro |

**Estructura JSON:**
```json
{
  "nombre": "María González",
  "email": "maria@email.com",
  "fechaInscripcion": "2024-08-25"
}
```

---

## 🏃‍♂️ Entrenadores
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/entrenadores` | Crear entrenador |
| GET | `/api/entrenadores` | Obtener todos los entrenadores |
| GET | `/api/entrenadores/{id}` | Obtener entrenador por ID |
| PUT | `/api/entrenadores/{id}` | Actualizar entrenador |
| DELETE | `/api/entrenadores/{id}` | Eliminar entrenador |

**Estructura JSON:**
```json
{
  "nombre": "Juan Pérez",
  "especialidad": "Musculación"
}
```

---

## 🏃‍♀️ Clases
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/clases` | Crear clase |
| GET | `/api/clases` | Obtener todas las clases |
| GET | `/api/clases/{id}` | Obtener clase por ID |
| PUT | `/api/clases/{id}` | Actualizar clase |
| DELETE | `/api/clases/{id}` | Eliminar clase |

**Estructura JSON:**
```json
{
  "nombre": "Yoga Matutino",
  "horario": "2024-08-25T07:00:00",
  "capacidadMaxima": 20,
  "entrenadorId": 1
}
```

### ⭐ Endpoints Especiales (Comunicación REST)
| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/clases/con-entrenador` | Clases con información del entrenador |
| GET | `/api/clases/{id}/con-entrenador` | Clase específica con entrenador |
| GET | `/api/clases/entrenador/{entrenadorId}` | Clases de un entrenador específico |

---

## 🏋️‍♂️ Equipos
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/equipos` | Crear equipo |
| GET | `/api/equipos` | Obtener todos los equipos |
| GET | `/api/equipos/{id}` | Obtener equipo por ID |
| PUT | `/api/equipos/{id}` | Actualizar equipo |
| DELETE | `/api/equipos/{id}` | Eliminar equipo |

**Estructura JSON:**
```json
{
  "nombre": "Mancuernas",
  "descripcion": "Juego de mancuernas de 5kg a 50kg",
  "cantidad": 20
}
```

---

## �️ Consolas H2 (Bases de Datos)
- **Miembro Service**: http://localhost:8081/h2-console
- **Entrenador Service**: http://localhost:8082/h2-console  
- **Clase Service**: http://localhost:8083/h2-console
- **Equipo Service**: http://localhost:8084/h2-console

**Credenciales:** Usuario: `sa`, Contraseña: (vacía)

---

## 📊 Códigos de Respuesta
| Código | Descripción |
|--------|-------------|
| 200 | Operación exitosa |
| 201 | Recurso creado |
| 204 | Eliminación exitosa |
| 404 | Recurso no encontrado |
| 500 | Error del servidor |

---

## 🔄 Arquitectura
```
Cliente → Gateway (8080) → Microservicios (8081-8084)
                    ↓
            clase-service → entrenador-service
```

**Características:**
- ✅ API Gateway como punto único de entrada
- ✅ Comunicación REST entre microservicios
- ✅ Bases de datos independientes
- ✅ CRUD completo en todos los recursos

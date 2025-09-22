# 🚀 Evidencias de Implementación

Este documento reúne las **pruebas y validaciones técnicas** realizadas sobre los diferentes módulos y servicios que componen la plataforma. Se incluyen capturas que demuestran la correcta integración de **Kafka, RabbitMQ, Keycloak y Swagger** dentro del ecosistema de microservicios.

---

## ⚡ Kafka

Se presenta evidencia del correcto funcionamiento de **Apache Kafka**, validando la comunicación entre **Publisher** y **Subscriber** en diferentes escenarios.

### 🔄 Actualización de horarios de clase

Kafka recibe y transmite cambios en los horarios de clase de forma confiable.

![Kafka horarios 1](https://github.com/user-attachments/assets/453ff187-2056-48da-8d28-d304bdd3176c)<br>
![Kafka horarios 2](https://github.com/user-attachments/assets/f0bf1bd9-9321-4cda-9aa6-3f89e0cc6f56)<br>

### 📊 Datos de entrenamiento

Se evidencia el flujo de datos relacionados con el entrenamiento.

![Kafka entrenamiento](https://github.com/user-attachments/assets/48b13098-4755-4995-bb8c-a000cc664a4f)<br>

### 🏫 Ocupación de clases

Kafka gestiona correctamente la publicación y suscripción de eventos relacionados con la **ocupación de las clases**.

![Kafka ocupación 1](https://github.com/user-attachments/assets/91655590-5c88-457f-b188-620178a9ecc5)<br>
![Kafka ocupación 2](https://github.com/user-attachments/assets/a9c4a00c-5c31-4718-a511-2bf6bfc3aa24)<br>
![Kafka ocupación 3](https://github.com/user-attachments/assets/54565c13-4b53-4418-a9b2-5f91dc52f501)<br>

✅ Con estas pruebas se confirma la **integración y correcto funcionamiento de Kafka** en los diferentes módulos del sistema.

---

## 📨 RabbitMQ

Se valida la implementación de **RabbitMQ**, evidenciando la gestión de colas, enrutamiento y la tolerancia a fallos.

### 📌 Dashboard y colas activas

![RabbitMQ dashboard 1](https://github.com/user-attachments/assets/c66d54e5-874a-494b-8de1-2160fb3434f5)<br>
![RabbitMQ dashboard 2](https://github.com/user-attachments/assets/b8687b5a-1eaf-4411-a75c-f66fcb996f68)<br>
![RabbitMQ dashboard 3](https://github.com/user-attachments/assets/bc58fe2c-aaa7-488c-8d60-0c8eb5a524e2)<br>
![RabbitMQ dashboard 4](https://github.com/user-attachments/assets/1004bc93-40ed-4f8a-8bac-6a12df8dc511)<br>

### ⚠️ Manejo de fallos

Cuando un pago falla, el evento se encola y puede ser inspeccionado:

![RabbitMQ error 1](https://github.com/user-attachments/assets/b3c0251a-bbf9-48ae-95bd-58197f3ffafd)<br>
![RabbitMQ error 2](https://github.com/user-attachments/assets/44b18ea0-ad31-4b06-87f4-12f3edd29572)<br>

### 🔄 Persistencia de mensajes

Si un servicio consumidor está **apagado**, RabbitMQ mantiene los mensajes en cola hasta que pueda procesarlos.

![RabbitMQ persistencia](https://github.com/user-attachments/assets/373125f6-d2dc-4a70-a5b2-c2334000b686)<br>

---

## 🔐 Keycloak

Se implementó **Keycloak** como proveedor de identidad para la gestión de seguridad y autenticación en la plataforma.

La configuración incluye:

* **Realms**
* **Clients**
* **Users & Roles**
* Integración con Postman para pruebas de tokens

![Keycloak realm](https://github.com/user-attachments/assets/3e013372-44b1-4e7f-8dc2-7b12141f5e0e)<br>
![Keycloak clients](https://github.com/user-attachments/assets/fb35144d-b62c-49ac-b63d-ff123e38ff9b)<br>
![Keycloak users](https://github.com/user-attachments/assets/7b4a2fda-9b63-4e79-9666-0d684a1bf687)<br>
![Keycloak roles](https://github.com/user-attachments/assets/cea3453e-6df9-4e5e-ae2d-480dacdfae3c)<br>
![Keycloak postman](https://github.com/user-attachments/assets/0bb35553-1b79-42c1-8932-e0a5701d4c50)<br>

---

## 📖 Swagger

Cada microservicio cuenta con documentación de endpoints mediante **Swagger**, lo cual facilita la exploración, prueba y validación de la API.

![Swagger 1](https://github.com/user-attachments/assets/55dec397-55fd-4a6a-809b-c12a9e0819dc)<br>
![Swagger 2](https://github.com/user-attachments/assets/f1f17ddd-4a9f-49e5-9ae2-30bc829b389f)<br>

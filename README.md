# Sistema de Gestión - Centro de Lavado de Vehículos

## . Introducción

Este proyecto consiste en el desarrollo de una API REST utilizando **Spring Boot** para digitalizar la operación de un centro de lavado de vehículos.

El sistema permite gestionar clientes, vehículos, órdenes de servicio, servicios aplicados y pagos, garantizando control, trazabilidad y escalabilidad del negocio.

---

#  2. Análisis del problema

El centro de lavado actualmente maneja sus procesos de forma manual, lo cual genera:

* Falta de control sobre las órdenes
* Dificultad para gestionar múltiples servicios
* Riesgo de errores en pagos
* Poca trazabilidad de la información

## Necesidades identificadas

A partir del análisis del enunciado se determinó que el sistema debe:

* Registrar clientes y sus vehículos
* Crear órdenes de servicio
* Asociar múltiples servicios a una orden
* Controlar el estado de cada orden
* Registrar pagos
* Consultar información detallada

##  Retos del problema

* Servicios con precios variables
* Validación de reglas de negocio
* Escalabilidad futura (promociones, nuevos servicios)
* Evitar inconsistencias (órdenes sin servicios, pagos inválidos)

---

# 3. Modelo del dominio

Se definieron las siguientes entidades principales:

* **Cliente**
* **Vehiculo**
* **Servicio**
* **OrdenServicio**
* **OrdenDetalle**
* **Pago**

## 🔗 Relaciones clave

* Cliente → Vehículos (1:N)
* Vehículo → Órdenes (1:N)
* Orden → Detalles (1:N)
* Orden → Pagos (1:N)
* Detalle → Servicio (N:1)

## 💡 Decisión importante

Se creó la entidad **OrdenDetalle** para:

* Permitir múltiples servicios por orden
* Guardar precios históricos (snapshot)
* Evitar inconsistencias si cambian los precios

---

#  4. Arquitectura del sistema

Se implementó una arquitectura en capas:

* **Controller** → expone la API REST
* **Service** → contiene la lógica de negocio
* **Repository** → acceso a datos (JPA)
* **Model** → entidades del dominio
* **DTO / Mapper** → desacoplamiento

##  Objetivo de la arquitectura

* Separar responsabilidades
* Facilitar mantenimiento
* Permitir escalabilidad
* Mejorar testeo

---

#  5. Decisiones de diseño (CLAVE DEL PROYECTO)

## ✔ Patrón Strategy (cálculo de precios)

Se implementó `PrecioResolutionStrategy` para resolver el precio de un servicio.

### Prioridad:

1. Precio manual
2. Precio especial por tipo de vehículo
3. Precio base

### Justificación:

Permite agregar nuevas reglas sin modificar el sistema existente.

---

## ✔ Patrón State (estado de órdenes)

Se utiliza `OrdenEstadoMachine` para controlar transiciones de estado.

### Estados:

* REGISTRADA
* EN_PROCESO
* FINALIZADA
* ENTREGADA
* CANCELADA

### Justificación:

Centraliza la lógica y evita transiciones inválidas.

---

## ✔ Composición y Delegación

Los servicios no contienen toda la lógica, sino que delegan:

* Validación de estado
* Cálculo de precios
* Conversión a DTO

### Justificación:

Reduce acoplamiento y mejora mantenibilidad.

---

## ✔ Uso de DTOs

Se utilizan DTOs para:

* No exponer entidades directamente
* Controlar la información enviada
* Facilitar cambios futuros

---

## ✔ Separación Orden - Detalle

Permite:

* Manejar múltiples servicios
* Guardar precios históricos
* Evitar inconsistencias

---

# ⚙️ 6. Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Lombok
* Maven

---

# 🗄️ 7. Configuración de base de datos

```sql
CREATE DATABASE lavadero;
```

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/lavadero
    username: postgres
    password: 1234

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

# 8. Ejecución del proyecto

```bash
git clone <repo>
cd lavadero-api
mvn spring-boot:run
```

---

#  9. API REST (resumen)

## Clientes

* POST /api/clientes
* GET /api/clientes

## Vehículos

* POST /api/vehiculos
* GET /api/vehiculos

## Órdenes

* POST /api/ordenes
* GET /api/ordenes
* PATCH /api/ordenes/{id}/estado

## Pagos

* POST /api/ordenes/{id}/pagos
* GET /api/ordenes/{id}/pagos

---

#  10. Reglas de negocio

* No se puede crear una orden sin vehículo válido
* Una orden debe tener al menos un servicio antes de finalizar
* No se permiten pagos negativos
* El total pagado no puede exceder el total de la orden
* Las transiciones de estado son controladas
* No se puede modificar una orden entregada o cancelada

---

#  11. Escalabilidad

El sistema está preparado para:

* Nuevos tipos de servicios
* Promociones
* Nuevas formas de pago
* Nuevas reglas de negocio

Gracias a:

* Strategy Pattern
* State Pattern
* Bajo acoplamiento

---

#  12. Justificación técnica final

El diseño del sistema prioriza la **extensibilidad y mantenibilidad** sobre la simplicidad inicial.

Se aplicaron principios de ingeniería de software como:

* Separación de responsabilidades
* Bajo acoplamiento
* Alta cohesión
* Uso de patrones de diseño

Esto permite que el sistema pueda crecer sin necesidad de reescrituras importantes, adaptándose fácilmente a nuevas necesidades del negocio.

---

#  Conclusión

El sistema no solo cumple con los requerimientos actuales del negocio, sino que está preparado para evolucionar, demostrando una correcta aplicación de análisis, modelado y diseño de software.

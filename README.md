# HELP.md

## Proyecto Spring Initializr

API REST desarrollada con Spring Boot para consultar y crear mascotas utilizando la API externa Petstore Swagger.

## Características del proyecto

- **Tipo de configuración:** Gradle - Groovy
- **Lenguaje:** Java
- **Versión de Spring Boot:** 3.2.7
- **Packaging:** Jar
- **Versión de Java:** 17
- **Dependencia principal:** Spring Web
- **Documentación de API:** Swagger/OpenAPI

## Estructura del proyecto

```text
src
├── main
│   ├── java/com/example/tsoft/demo
│   │   ├── client
│   │   ├── config
│   │   ├── controller
│   │   ├── exception
│   │   ├── model
│   │   └── service
│   └── resources
│       └── application.properties
└── test
    └── java/com/example/tsoft/demo
        ├── DemoApplicationTests.java
        ├── client/PetClientTest.java
        ├── controller/PetControllerTest.java
        └── service/PetServiceTest.java
```

### Descripción de capas

| Capa | Responsabilidad |
|---|---|
| `controller` | Define los endpoints REST |
| `service` | Contiene la lógica de negocio |
| `client` | Consume la API externa Petstore |
| `model` | Contiene modelos y DTOs |
| `exception` | Maneja errores de forma centralizada |
| `config` | Contiene configuraciones de Spring y Swagger |

## API externa

La aplicación consume:

```text
https://petstore.swagger.io/v2
```

Endpoints externos:

```http
GET /pet/{petId}
POST /pet
```

## Configuración

Archivo `src/main/resources/application.properties`:

```properties
spring.application.name=demo

petstore.base-url=https://petstore.swagger.io/v2

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
```

## Endpoints REST

### Consultar una mascota

```http
GET /api/pet/{petId}
```

Ejemplo:

```bash
curl --location "http://localhost:8080/api/pet/10000023"
```

Respuesta:

```json
{
  "id": 10000023,
  "name": "testingPet1",
  "status": "available"
}
```

Código HTTP: `200 OK`

### Crear una mascota

```http
POST /api/pet
Content-Type: application/json
```

Solicitud:

```json
{
  "id": 10000023,
  "status": "available",
  "name": "testingPet1"
}
```

Ejemplo:

```bash
curl --location "http://localhost:8080/api/pet" \
  --header "Content-Type: application/json" \
  --data '{
    "id": 10000023,
    "status": "available",
    "name": "testingPet1"
  }'
```

Respuesta:

```json
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "dateCreated": "2026-11-03T00:00:00Z",
  "status": "available",
  "name": "testingPet1"
}
```

Código HTTP: `201 Created`

## Reglas de negocio

- `id` es obligatorio y debe ser positivo.
- `status` y `name` son obligatorios.
- `transactionId` se genera en la capa `Service` con UUID versión 4.
- `dateCreated` utiliza la fecha actual del sistema.
- La información se imprime en consola antes de enviarse como respuesta.
- La información se obtiene desde Petstore Swagger.

## Manejo de errores

La aplicación utiliza `@RestControllerAdvice`.

| Situación | Código |
|---|---:|
| Datos de entrada inválidos | `400 Bad Request` |
| Mascota no encontrada | `404 Not Found` |
| Petstore no disponible | `502 Bad Gateway` |

## Swagger / OpenAPI

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

Especificación OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

## Ejecución del proyecto

### Windows

```bash
gradlew.bat clean build
gradlew.bat bootRun
```

### Linux o macOS

```bash
./gradlew clean build
./gradlew bootRun
```

La aplicación estará disponible en:

```text
http://localhost:8080
```

## Ejecución de pruebas

```bash
gradlew.bat clean test
```

Para ejecutar la suite `DemoApplicationTests`:

```bash
gradlew.bat clean test --tests com.example.tsoft.demo.DemoApplicationTests
```

Las pruebas cubren:

- Consulta y creación de mascotas.
- Generación de UUID versión 4.
- Generación de fecha de creación.
- Validación de datos de entrada.
- Respuestas HTTP del controller.
- Mascota no encontrada.
- Indisponibilidad de la API externa.

## Dependencias principales

```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'

testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.junit.platform:junit-platform-suite'
```

## Referencias

- [Documentación oficial de Gradle](https://docs.gradle.org)
- [Spring Boot 3.2.7 Gradle Plugin](https://docs.spring.io/spring-boot/docs/3.2.7/gradle-plugin/reference/html/)
- [Spring Boot 3.2.7 Reference Documentation](https://docs.spring.io/spring-boot/docs/3.2.7/reference/html/)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.7/reference/html/web.html)
- [Springdoc OpenAPI](https://springdoc.org/)
- [Petstore Swagger](https://petstore.swagger.io/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

## Autor

**MANM. Carlos Avelino Soto Gonzalez**

- Teléfono: [+52 6871230269](tel:+526871230269)
- Correo: [carlosmorri@gmail.com](mailto:carlosmorri@gmail.com)
- LinkedIn: [Carlos Avelino Soto Gonzalez](https://www.linkedin.com/in/carlos-avelino-soto-gonzalez-71b532164/)

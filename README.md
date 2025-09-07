
# CHAKRAY - API_REST_Juan

## Description
REST API in Java 11 (11.0.28) with Spring Boot, includes unit tests and a Postman collection for testing the endpoints.

## Technologies used
- **Java 11 - Open JDK 11 by Temurin**
- **Spring Boot 3.12**
- **JUnit 5 y Mockito**
- **Maven**
- **Postman - Request Collection**

## API resources
- GET /users?sortedBy=[email|id|name|phone|tax_id|created_at]
- GET
  /users?filter=[email|id|name|phone|tax_id|created_at]+[co|eq|sw|ew]+[va
  lue]
- POST /users - Create User \n
- PATCH /users/{id} - Update User
- DELETE /users/{id} - Delete User
- POST /login - System Login

### Format Create User
{
"name": "Char_Test_01",
"email": "char01@gmail.com",
"password": "juan",
"phone": "+5211223344",
"tax_id": "GAMJ0501147Z4",
"address": [
{
"id": 1,
"name": "Trabajo",
"street": "Calle siempre viva 123",
"country": "UK"
},
{
"id": 2,
"name": "Trabajo",
"street": "Calle 123",
"country": "MX"
}
]
}

## Project structure
src/main/java/com/juan/apirestjuan \
📂controller \
📂model \
📂service \
📂util \
src/test/java/com/juan/apirestjuan \
📂controller \
📂model \
📂service \

## Unit Test

- **UserControllerTest** - Validate the endpoints.
- **UserServiceTest** - Test the logic of the service.
- **UserModelTest y AddressModelTest** - They assure that the models work with getters/setters.

Ejecutar los tests:
```bash
mvn test
```
## Postman
It includes a Postman collection.
- /postman/UserAPI.postman_collection.json

## Docker
It has a Dockerfile, I wrote this file but I did not test it due to the hardware limitations of my personal equipment; I usually use other machines that I have available for these container tasks.

## Git
Use git as a versioning system, do not use github thinking of the privacy of the test related to CHAKRAY.

## Run project
```bash
mvn spring-boot:run
```

## Dev
Juan Garcia \
soymatudev@gmail.com
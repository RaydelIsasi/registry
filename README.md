# User Registry
Este proyecto expone un API REST con dos endpoints uno de login y uno de registro de usuarios:

El endpoint de login devuelve un token jwt una vez autenticado el usuario como se muestra:


*accessToken*: Token de acceso
Ejemplo.
{
    "accessToken": "ertJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MDgwMDE3MDgsImlzcyI6Imh0dHBzOi8vdXNlcnJlZ2lzdHJ5LyIsInN1YiI6ImFkbWluIiwiZXhwIjoxNjA4ODY1NzA4fQ.cqA_mNBPmvrzJqqUH-P4m0r110MoVtkxP2d-ZB2zaWBCP_IzevTSXfNnsC7U6oYcORQLht7rS8gf8xpAt3h0Mw"
}
*Nota*:
Ese token de acceso se pone en los headers del endpoint de registro para la autenticación el cual tiene la siguiente estructura de request:
{
"name" : "Yunior Isasis" ,
"email" : " pepaqwcom" ,
"password" : "Yaine123",
"phones" : [
{
"number" : "1234567",
"citycode" : "1",
"countrycode" : "57"
}
]
}
y responde el usuario guardado en la bd con otros campos como se muestra en la respuesta:

{
    "id": "875bb659-67cc-4c27-ae7c-e92295b1a0f6",
    "name": "Yunior Isasis",
    "email": " pepa@gmail.com",
    "password": "Yaine12",
    "created": "2020-12-15T03:13:46.965+00:00",
    "modified": "2020-12-15T03:13:46.965+00:00",
    "last_login": "2020-12-15T03:13:46.965+00:00",
    "token": "eyrhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MDgwMDIwMjYsImlzcyI6Imh0dHBzOi8vdXNlcnJlZ2lzdHJ5LyIsInN1YiI6Ill1bmlvciBJc2FzaXMiLCJleHAiOjE2MDg4NjYwMjZ9.ScIjENr2e8AYgo23WrQxhm32gI1u3JKdW0uSuunoStGlu4a5KQpJDmLLnoH-ycGxDNeX6J_JcRVPDZDPhdgB_w",
    "isactive": true,
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "countrycode": "57"
        }
    ]
}

# Detalle de los sistemas


Java 8
Spring-Boot 2.41.RELEASE
Gradle 6


# Compilar y ejecutar el proyecto

Para compilar el proyecto se requiere Java y Gradle instalado.
Ingresar al directorio *registry* ejecutar el siguiente comando *gradle*

```bash
gradle build --scan
```

Luego de compilar el proyecto ingresar al directorio *registry\build\libs\* ejecutar el siguiente comando *java*

```bash
java -jar registry-0.0.1-SNAPSHOT.jar
```
*Nota*:
Debe estar disponible el puerto *8090* en el PC donde se ejecute esta API
# Consumo de la API
 Endpoints: 
 http://127.0.0.1:8090/userregistry/login mediante POST enviando las credenciales
 http://127.0.0.1:8090/userregistry/registeruser mediante post agregando el token de acceso en los headers y los datos del usuario a registrar
 
 Se adjunta la colección postman llamada User Registry Service en el repositorio para el consumo del servicio.




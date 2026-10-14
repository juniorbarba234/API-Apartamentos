# API Apartamentos

API REST para administrar apartamentos y sus unidades de alojamiento. Permite registrar clientes y propietarios, crear propiedades, reservar unidades y llevar el control de pagos, disponibilidades, reseñas, notificaciones e imágenes.

Proyecto académico de Arquitectura orientada a servicios. Se desarrolló a partir de la base de Apartamentos proporcionada para la actividad.

Si es tu primera vez ejecutando una API, comienza con [EMPEZAR.md](EMPEZAR.md). Puedes abrir `CONFIGURAR.cmd` para escribir la conexión de MySQL y `EJECUTAR.cmd` para iniciar el proyecto con doble clic.

## Tecnologías

- Java 21 o superior
- Spring Boot 4.1.1
- Spring Data JPA y Hibernate
- MySQL
- Maven Wrapper
- Thymeleaf para las pantallas incluidas en la base
- JUnit y Mockito para pruebas automáticas
- Postman y Newman para pruebas HTTP

## Ejecutar con MySQL

1. Iniciar MySQL en el puerto 3306.
2. Copiar `application-local.example.properties` como `application-local.properties` en la carpeta del proyecto, junto a `pom.xml`.
3. Escribir el usuario y la contraseña reales de MySQL en ese archivo. El archivo está excluido de Git.
4. Ejecutar desde PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

El usuario de MySQL debe poder crear la base `api_apartamentos`, o la base debe existir previamente. Hibernate crea y actualiza las tablas. La API utiliza `http://localhost:8000/api` y las pantallas se abren en `http://localhost:8000`.

También se pueden configurar las variables `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` y `SERVER_PORT`. La configuración general está en `src/main/resources/application.properties`.

## Recursos de la API

Cada recurso tiene GET para listar, GET `/{id}` para consultar, POST para crear, PUT `/{id}` para actualizar y DELETE `/{id}` para eliminar. Los identificadores los genera la base de datos.

| Ruta | Función |
| --- | --- |
| `/api/clientes` | Clientes, propietarios y empresas |
| `/api/propiedades` | Apartamentos y otras propiedades |
| `/api/unidades` | Unidades que se pueden reservar |
| `/api/reservas` | Reservas y cálculo del importe |
| `/api/pagos` | Registro y estado de pagos |
| `/api/disponibilidades` | Periodos de disponibilidad registrados |
| `/api/resenas` | Calificaciones y comentarios |
| `/api/notificaciones` | Mensajes guardados entre clientes |
| `/api/propiedad-imagenes` | Enlaces de imágenes de propiedades |

Consultas adicionales:

- GET `/api/propiedades/tipo/{tipo}`
- GET `/api/propiedades/propietario/{id}`
- GET `/api/unidades/propiedad/{idPropiedad}`
- GET `/api/disponibilidades/unidad/{idUnidad}`
- GET `/api/pagos/reserva/{idReserva}`
- GET `/api/resenas/reserva/{idReserva}`
- GET `/api/notificaciones/destinatario/{idCliente}`
- GET `/api/propiedad-imagenes/propiedad/{idPropiedad}`
- POST `/api/pagos/{id}/confirmar`
- POST `/api/pagos/{id}/reembolsar`

## Funcionamiento

Un propietario se registra con el tipo `PROPIETARIO` o `EMPRESA`. Una propiedad pertenece a ese propietario y tiene unidades rentables. Una reserva relaciona una unidad con un cliente y un intervalo de fechas.

La reserva requiere al menos una noche. El servidor calcula el importe con el precio de la unidad guardada y la diferencia entre las fechas de entrada y salida. Por ejemplo, del 1 al 4 de noviembre a 500 por noche resulta en 1500. Se rechazan reservas que se crucen con otras reservas activas de la misma unidad.

Las reservas admiten `PENDIENTE`, `CONFIRMADA`, `CANCELADA`, `RECHAZADA` y `COMPLETADA`. Una unidad debe estar `DISPONIBLE` para aceptar una reserva activa. Los pagos pendientes, en proceso y pagados no pueden superar juntos el importe de la reserva. Un pago confirmado se puede reembolsar y no se puede eliminar ni editar.

Las contraseñas se guardan con BCrypt y se excluyen de las respuestas JSON. Se validan campos obligatorios, correos, precios y calificaciones de 1 a 5. Un registro duplicado o relacionado con otros registros devuelve 409; una solicitud inválida devuelve 400; un identificador inexistente en una consulta devuelve 404.

## Pruebas en Postman

1. Iniciar la aplicación.
2. En Postman, pulsar **Import** y seleccionar `postman/API Apartamentos.postman_collection.json`.
3. Verificar que `baseUrl` tenga el valor `http://localhost:8000`.
4. Ejecutar la colección en orden con **Run collection**. Los identificadores se guardan automáticamente para las siguientes solicitudes.

La colección crea datos ficticios con correos distintos en cada ejecución. Contiene 40 solicitudes y 44 comprobaciones: registros de los nueve recursos, consultas, actualizaciones, eliminación, reservas duplicadas, fechas inválidas, correos duplicados y operaciones de pago.

Para ejecutarla con Newman:

```powershell
npx newman run "postman/API Apartamentos.postman_collection.json"
```

Las evidencias de la ejecución están en `docs/pruebas`. Se ejecutó la colección con Newman contra H2 en memoria en el puerto 18080: 40 solicitudes y 44 comprobaciones aprobadas. H2 se utilizó solamente como entorno de prueba; la configuración de ejecución habitual utiliza MySQL. La conexión local con MySQL requiere completar `application-local.properties`.

## Pruebas automáticas

```powershell
.\mvnw.cmd test
```

Las ocho pruebas verifican el inicio de la aplicación, el cálculo de reservas, el rechazo de solapamientos, las unidades con propiedades inexistentes o en mantenimiento, los importes de pagos y las transiciones inválidas. Utilizan H2 y repositorios simulados, sin necesitar la contraseña local de MySQL.

## Organización del código

`models` contiene las entidades y sus relaciones. `repositories` realiza las consultas a la base. `services` aplica las reglas de negocio. `controllers` recibe las solicitudes y devuelve respuestas. `resources/templates` contiene las pantallas de la base.

## Alcance académico

Los pagos representan registros y cambios de estado; no procesan dinero mediante un banco. Las notificaciones se guardan en la base, sin enviar correos. La disponibilidad y el precio especial se administran como registros independientes; las reservas utilizan el precio habitual de la unidad. No se ha implementado autenticación ni autorización de usuarios. Las pruebas cubren solicitudes secuenciales; no certifican reservas y pagos simultáneos.

## Repositorio de esta actividad

Este repositorio corresponde únicamente a **API Apartamentos**. Biblioteca y Alquila YA se entregan mediante sus propios repositorios.

Enlace: https://github.com/juniorbarba234/API-Apartamentos


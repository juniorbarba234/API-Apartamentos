# API Apartamentos

API REST para administrar apartamentos y sus unidades de alojamiento. Permite registrar clientes y propietarios, crear propiedades, reservar unidades y llevar el control de pagos, disponibilidades, reseñas, notificaciones e imágenes.

Proyecto académico de Arquitectura orientada a servicios. Se desarrolló a partir de la base de Apartamentos proporcionada para la actividad.

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

La colección crea datos ficticios con correos distintos en cada ejecución. Contiene 40 solicitudes: registros de los nueve recursos, consultas, actualizaciones, eliminación, reservas duplicadas, fechas inválidas, correos duplicados y operaciones de pago.

## Organización del código

`models` contiene las entidades y sus relaciones. `repositories` realiza las consultas a la base. `services` aplica las reglas de negocio. `controllers` recibe las solicitudes y devuelve respuestas. `resources/templates` contiene las pantallas de la base.

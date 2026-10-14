# API Apartamentos

## Primero inicia el proyecto

1. Verifica que MySQL esté encendido.
2. Abre `CONFIGURAR.cmd` con doble clic. Escribe tu usuario de MySQL y su contraseña. Pulsa **Guardar configuración**. Solo necesitas hacer esto la primera vez.
3. Abre `EJECUTAR.cmd` con doble clic. Espera a que aparezca **Started ApartamentosApplication**. Deja esa ventana abierta.

Si quieres ver las pantallas que trae el proyecto, abre `http://localhost:8000` en el navegador.

## Después prueba en Postman

1. Abre Postman y pulsa **Import**.
2. Selecciona el archivo `API Apartamentos.postman_collection.json` que está dentro de la carpeta `postman`.
3. Abre la colección **API Apartamentos**. Ejecuta las solicitudes en orden, empezando por **01 Crear propietario**. También puedes ejecutar todas con **Run collection**.
4. Verifica el número de la respuesta. Por ejemplo, 200 y 201 significan que la operación salió bien. Algunas pruebas buscan un error a propósito y deben devolver 400, 404 o 409.

Los identificadores se guardan automáticamente. No necesitas cambiarlos a mano.

## Qué decir cuando te pregunten cómo funciona

“Mi proyecto registra propietarios, apartamentos y clientes. Cada apartamento tiene unidades que se pueden reservar. Una reserva relaciona al cliente con la unidad y sus fechas. El sistema calcula el total por las noches y evita que se registre otra reserva en esas mismas fechas. También permite registrar pagos y confirmar o reembolsar un pago.”

“Postman sirve para enviar una solicitud al proyecto y ver su respuesta. GET consulta, POST crea, PUT actualiza y DELETE elimina.”

“El controlador recibe la solicitud. El servicio revisa las reglas. El repositorio guarda o consulta los datos en MySQL. El modelo representa los datos, como un cliente o una reserva.”

## Qué entregar

Entrega el documento **API Apartamentos** con el enlace de este repositorio y sus evidencias. Esta actividad tiene su propio enlace; las otras tareas tienen enlaces distintos.

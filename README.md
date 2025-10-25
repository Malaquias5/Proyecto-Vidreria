Proyecto Vidrios-CGM - Backend (Spring Boot)

Resumen rápido
- Ya implementé y compilé el módulo de ventas: crear ventas (decrementa stock), listar (paginado), obtener por id y cancelar ventas (revierte stock).
- Añadí un `DataLoader` que inserta datos de prueba (categoría, 2 productos, 1 cliente, 1 empleado) cuando la BD está vacía.
- Añadí manejo global de excepciones y la dependencia para Swagger/OpenAPI.

Requisitos
- Java 17
- Maven (o usar el wrapper `mvnw.cmd` incluido)
- PostgreSQL corriendo en localhost:5432 con la base de datos `vidrieria_db` o ajustar `src/main/resources/application.properties`.

Crear la base de datos (si no existe)
En psql o pgAdmin ejecuta:

```sql
CREATE DATABASE vidrieria_db;
-- crea un usuario/postgres y contraseña si hace falta o ajusta application.properties
```

Comandos (Windows)
- Compilar (desde la carpeta del proyecto):

PowerShell:
```powershell
cd C:\CGM-VIDRIOS\Vidrios-CGM\Vidrios-CGM
.\mvnw.cmd -DskipTests package
```

cmd.exe:
```cmd
cd C:\CGM-VIDRIOS\Vidrios-CGM\Vidrios-CGM
mvnw.cmd -DskipTests package
```

- Ejecutar la aplicación:

PowerShell:
```powershell
.\mvnw.cmd spring-boot:run
# o
java -jar target\Vidrios-CGM-0.0.1-SNAPSHOT.jar
```

Comprobaciones iniciales
- Verifica que la app arrancó (logs): busca "Started VidriosCgmApplication".
- Verifica que el DataLoader pobló datos (si BD vacía):
  - GET http://localhost:8080/api/productos
  - GET http://localhost:8080/api/clientes
  - GET http://localhost:8080/api/empleados

Swagger / OpenAPI
- Interfaz Swagger UI disponible en:
  - http://localhost:8080/swagger-ui/index.html
  (muestra los endpoints y permite probar desde la UI).

Endpoints relevantes (ventas)
- POST /api/ventas
  - Crea una venta y decrementa stock.
  - Body (JSON) ejemplo:
    {
      "clienteId": 1,
      "vendedorId": 1,
      "tipoPago": "EFECTIVO",
      "items": [
        { "productoId": 1, "cantidad": 2, "precioUnitario": 30.00, "descuento": 0.0 }
      ]
    }
  - Respuesta: 201 Created con `VentaDTO`.
  - Errores: 400 (petición inválida), 409 (stock insuficiente).

- GET /api/ventas?page=0&size=10&sort=fecha,desc
  - Lista paginada de ventas.

- GET /api/ventas/{id}
  - Obtiene una venta por id.

- PUT /api/ventas/{id}/cancel?motivo=razon
  - Cancela la venta, revierte stock y retorna venta actualizada.
  - Respuesta: 200 OK con `VentaDTO`.

Comportamiento de stock
- Para evitar sobreventa se utiliza una actualización condicional en la BD (`UPDATE ... WHERE stock_actual >= :qty`).
- Si no hay suficiente stock, el endpoint retorna HTTP 409 con JSON:
  {
    "error": "stock_insuficiente",
    "productoId": <id>,
    "disponible": <qty_disponible>,
    "message": "Stock insuficiente..."
  }

Errores comunes y cómo solucionarlos
- ECONNREFUSED 127.0.0.1:8080
  - La app no está corriendo. Arranca con `spring-boot:run` o `java -jar` y revisa logs.
  - El puerto está ocupado: `netstat -ano | findstr :8080` y mata el proceso con `taskkill /PID <pid> /F`.

- 400 Required request body is missing
  - En Postman pon Header `Content-Type: application/json` y Body → raw → JSON.

- 409 stock_insuficiente
  - El front debe capturar 409 y refrescar stock o avisar al usuario.

Siguientes pasos recomendados
1. Añadir validación `@Valid` en `VentaCreateDTO` y en controlador.
2. Añadir endpoint para pagos/abonos si se manejan pagos parciales.
3. Añadir tests de integración (MockMvc) y pruebas concurrentes para asegurar no-overbooking.
4. Docker-compose (app + postgres) para levantar entorno local fácil.
5. (Opcional) Generar ticket/PDF y agregar auditoría (createdBy, timestamps automáticos).

Si quieres que implemente ahora alguno de los pasos recomendados (por ejemplo: validaciones `@Valid` y tests básicos, o cancelar venta con motivo guardado), dime cuál y lo hago.

Contacto
- Si ves un error al arrancar o al ejecutar un endpoint, pega aquí la salida completa del log o el body+headers que enviaste desde Postman y lo reviso.

Fecha de la última acción: 2025-10-24


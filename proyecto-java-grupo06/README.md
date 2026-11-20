# Sistema de Gestión para el Taller Automotriz CarMotors

CarMotors es una aplicación de consola que gestiona un taller automotriz completo: clientes y vehículos,
órdenes de servicio con su ciclo de estados, inventario de repuestos y proveedores, facturación en
archivos `.txt` y reportes gerenciales.

## Tecnologías

Java 17, MySQL 8, JDBC, Maven y consola.

## Módulos

| Módulo | Menú | Qué hace |
|---|---|---|
| **Clientes y vehículos** | 1 | Registro de clientes y vehículos, historial de servicios y recordatorios de mantenimiento preventivo. |
| **Órdenes de servicio** | 2 | Crear, consultar y listar órdenes; registrar y asignar técnicos; agregar repuestos a la orden y avanzar su estado. |
| **Inventario y proveedores** | 3 | Catálogo de repuestos y proveedores, lotes con caducidad, evaluación de proveedores y alertas de stock bajo. |
| **Facturación** | 4 | Vista previa, emisión de la factura en `.txt` (con IVA del 19 %) y regeneración del archivo. |
| **Reportes gerenciales** | 5 | Servicios más solicitados (por nombre, como «Cambio de aceite»), productividad por técnico y consumo de repuestos en un periodo. |

## Ciclo de una orden

```
Pendiente ──► En Proceso ──► Completado ──► Facturado ──► Entregado
   │  agregar repuestos  │        │              │              │
   └── (se reservan) ────┘        │              │              └─ se guarda la fecha de entrega
                                  │              └─ solo desde Facturación: se guarda la factura y el .txt
                                  └─ se descuenta del stock lo reservado (una sola vez)
```

- Una orden solo avanza al estado siguiente (menú de órdenes, opción 9). No salta estados ni retrocede.
- **Reserva:** al agregar un repuesto (opción 7 de órdenes u opción 11 de inventario) se aparta la cantidad,
  pero el stock todavía no baja. Ambos menús usan la misma tabla `reservas_repuesto`, y la reserva guarda el
  precio de venta del momento. Lo disponible es `stock − reservas activas`, así que dos órdenes no pueden
  apartar las mismas unidades.
- **Descuento:** al pasar a `Completado` se resta del stock lo reservado, se anota en `servicios_repuestos`
  y la reserva queda `DESCONTADO`. Como el ciclo no retrocede, el stock se descuenta una sola vez.
- **Facturación:** una orden `Completado` se factura en el menú 4. La factura se guarda en la tabla `facturas`
  y la orden pasa a `Facturado` en la misma transacción. Después se escribe
  `facturas/factura-NNNNNN-orden-N.txt`. Hay un ejemplo en [docs/ejemplo-factura.txt](docs/ejemplo-factura.txt).

Cada paso que modifica varias tablas se hace en una transacción: si algo falla, no queda nada a medias.

## Base de datos

| Script | Tablas |
|---|---|
| `database/schema.sql` | Las 15 tablas: catálogos (`repuesto_tipos`, `servicio_estados`, `mantenimiento_tipos`), clientes, vehículos, proveedores, repuestos, servicios, servicios_repuestos, facturas, técnicos, servicios_tecnicos, lotes, evaluaciones_proveedor y reservas_repuesto |
| `database/data.sql` | Estados, tipos, datos de ejemplo para las 15 tablas y datos de prueba de reportes |

- Los dos scripts se pueden ejecutar varias veces: `schema.sql` usa `CREATE TABLE IF NOT EXISTS` y agrega las
  columnas nuevas (`nombre_servicio`, `precio_unitario_venta` de las reservas) a una base creada con una versión
  anterior sin borrar datos; `data.sql` solo inserta lo que todavía no existe.
- Ambos trabajan sobre la base `carmotors` (`USE carmotors`). Para cargarlos en otra base, por ejemplo una de
  pruebas, cambia ese nombre en los scripts antes de ejecutarlos.
- Al final, `data.sql` muestra las tablas y los datos cargados (clientes, vehículos, proveedores, repuestos, lotes,
  alertas, evaluaciones, órdenes, técnicos, reservas, consumos, facturas y reportes). Los identificadores pueden
  variar entre instalaciones: consúltalos en esos resultados.

![Diagrama entidad-relación del equipo](docs/diagrama-er.png)

Los diagramas actualizados del proyecto integrado (entidad-relación, clases y ciclo de estados) están en
[docs/diagramas.md](docs/diagramas.md). GitHub los dibuja automáticamente. También están como imagen el
[diagrama de clases](docs/diagrama-clases.png) y la [arquitectura por capas](docs/arquitectura.png).

## Instalación y ejecución

Requisitos: JDK 17 o superior, Maven y MySQL Server 8.

1. Clonar el repositorio y entrar en `proyecto-java-grupo06`:
   ```powershell
   git clone <URL-del-repositorio>
   cd proyecto-java-grupo6/proyecto-java-grupo06
   ```
2. Abrir el cliente MySQL desde el directorio del proyecto y ejecutar los scripts, en este orden:
   ```sql
   SOURCE database/schema.sql;
   SOURCE database/data.sql;
   ```
   Crean la base `carmotors` si no existe. Si ya tienes una base de una versión anterior, ejecútalos igual:
   `schema.sql` le agrega las columnas que faltan sin borrar datos.
3. Configurar la conexión en la terminal PowerShell (o en *Run → Edit Configurations → Environment variables* de IntelliJ):
   ```powershell
   $env:CARMOTORS_DB_URL = 'jdbc:mysql://localhost:3306/carmotors'
   $env:CARMOTORS_DB_USER = 'tu_usuario'
   $env:CARMOTORS_DB_PASSWORD = 'tu_clave'
   ```
4. Compilar y ejecutar:
   ```powershell
   mvn clean compile exec:java
   ```
   Los datos de ejemplo incluyen clientes, vehículos, tipos de mantenimiento, cuatro técnicos, tres proveedores,
   siete repuestos (dos de ellos bajo el stock mínimo, para ver las alertas), lotes y órdenes en distintos estados. Las facturas `.txt` se guardan en la carpeta `facturas/` del directorio donde se ejecuta el programa.

## Pruebas

Compila las clases de prueba con `mvn test-compile` y ejecuta sus métodos `main` desde el IDE:

| Clase | Qué prueba | Modos |
|---|---|---|
| `OrdenServicioPrueba` | Validaciones, creación, consulta, asignación de técnicos, rollback y consola de órdenes | sin BD, `--h2`, `--mysql` |
| `RegistroCorreccionPrueba` | Validación y normalización del registro de clientes y vehículos | sin BD |
| `CamposObligatoriosPrueba` | Campos vacíos y reintentos en los formularios de clientes, inventario y todos los menús (incluido «No aplica» en la caducidad) | sin BD |
| `ValidacionesFormularioPrueba` | Reintentos por campo, límites de longitud, nombres, documentos, teléfono y fin de la entrada | sin BD |
| `ReportesDia2Prueba`, `ReportesDia3Prueba` | Reportes con Streams, fechas y datos de factura ([resultados esperados](docs/reportes-resultados-esperados.md)) | sin BD, `--mysql` |
| `CicloFacturaPrueba` | Ciclo de estados, reservas sin sobreventa, descuento único del stock, IVA, factura `.txt` y entrega | sin BD, `--mysql` |
| `IntegracionSistemaPrueba` | Recorrido completo en una base temporal: carga repetida de los SQL, estados, reserva con su precio, descuento único, factura y entrega | solo MySQL |

- **Sin argumentos:** cálculos y validaciones, sin abrir conexiones.
- **Con `--mysql`:** usa la base de `CARMOTORS_DB_URL`, que debe ser una base exclusiva de pruebas cargada con
  `schema.sql` y `data.sql` (que ya trae los datos de reportes). Corre primero las pruebas de reportes y
  después `CicloFacturaPrueba`, porque esta crea órdenes con la fecha actual. El paso a paso está en
  [docs/plan-pruebas-mysql.md](docs/plan-pruebas-mysql.md).
- **Con `--h2`:** ejecuta `OrdenServicioPrueba --h2` desde la carpeta del proyecto con H2 2.3.232 en el classpath.
  Comprueba la creación de órdenes, asignaciones y reintentos de consola con una base en memoria.
- **`IntegracionSistemaPrueba`:** se ejecuta desde la raíz del repositorio (`proyecto-java-grupo6`). Crea una base
  temporal `carmotors_merge_…`, carga los dos scripts dos veces, recorre el flujo y borra la base al terminar.
  Necesita un usuario de MySQL con permiso para crear y eliminar bases.

Las siete pruebas sin base de datos pasan con Java 17. `OrdenServicioPrueba`, `ReportesDia2Prueba`,
`ReportesDia3Prueba`, `CicloFacturaPrueba` e `IntegracionSistemaPrueba` también pasan contra MySQL 8.0.
También se recorrió el flujo completo desde la consola: crear la orden, reservar, completar, revisar el stock,
emitir la factura y obtener el `.txt`.

## Captura de datos

- Los formularios vuelven a pedir un campo si queda vacío o no es válido.
- Teléfono, contacto, marca y observaciones son obligatorios. Si no hay observaciones, escribe `Sin observaciones`.
- La fecha de ingreso del lote es obligatoria. Para un lote sin caducidad escribe `No aplica`.
- Los nombres de personas y de servicios admiten letras. Documentos y teléfonos admiten dígitos y conservan los
  ceros iniciales. Placas, modelos y referencias de repuestos admiten letras y números.

## Limitaciones conocidas

Problemas detectados en la revisión del 19-11-2026 que todavía no están corregidos:

**Inventario**
- La marca del repuesto se pide y es obligatoria, pero no se guarda: la tabla `repuestos` no tiene columna `marca`.
- Registrar un lote guarda el lote y suma el stock en dos conexiones distintas, sin transacción. Si falla la
  segunda operación, el lote queda guardado y el stock no sube.
- `lotes.cantidad_disponible` no baja cuando una orden descuenta stock al pasar a Completado. Por eso la alerta
  de caducidad puede mostrar unidades que ya se usaron.
- La opción 12 («Completar orden y descontar stock») muestra «Stock descontado» aunque no haga nada, por ejemplo
  si la orden ya está en Completado o en un estado posterior.
- Cancelar las reservas de una orden existe en el servicio y en el controlador, pero no está en ningún menú.

**Base de datos**
- Las facturas de ejemplo de `data.sql` se guardan con IVA 0. Las que emite el programa sí llevan el 19 %.
- `facturas.servicio_id` no es `UNIQUE` y `reservas_repuesto.servicio_id` no tiene clave foránea hacia `servicios`.
  El programa evita facturar dos veces la misma orden, pero la base no lo impide.
- Con los datos de ejemplo no aparece ningún recordatorio de mantenimiento preventivo.

**Detalles menores**
- Cuando el límite de un campo es 1, la consola dice «Ingresa entre 1 y 1 caracteres».
- Algunos campos numéricos de inventario muestran la etiqueta y después «Valor:», así que la pregunta sale doble.
- `MenuOrdenes.elegirServicio()` y `MenuReportes.consultarFactura()` no se usan.
- Inventario crea su propio `OrdenServicioServicio` para reservar y descontar, en lugar de recibirlo por el
  constructor como el resto de las clases.

## Patrones de diseño

| Patrón | Dónde | Qué resuelve |
|---|---|---|
| **MVC** | `vista/Menu*` (vista), `controlador/*Controlador` (controlador), `modelo/*` (modelo) | La consola solo lee y muestra. El controlador recibe lo que escribió el usuario y llama al servicio. El modelo representa los datos. Se puede cambiar la consola sin tocar la lógica. |
| **DAO** | `dao/*Dao` (`OrdenServicioDao`, `FacturaDao`, `RepuestoOrdenDao`, …) | Todo el SQL de cada tabla está en una sola clase. El resto del programa no escribe SQL. |
| **Singleton** | `config/ConexionFactory` | Hay una única fábrica de conexiones (`getInstancia()`) con la configuración de la base. |
| **Factory** | `ConexionFactory.abrir()` | Encapsula cómo se crea una conexión JDBC (URL, usuario y clave desde variables de entorno). Quien la pide no sabe cómo se crea. |
| **Capa de servicio** | `servicio/*Servicio` (`OrdenServicioServicio`, `FacturaServicio`, …) | Concentra las reglas del negocio y las transacciones: ciclo de estados, stock disponible, IVA. |

Otras decisiones:
- **Inyección de dependencias:** servicios, controladores y vistas reciben sus colaboradores por el constructor.
  La interfaz funcional `Conexiones` permite probar las validaciones sin una base real.
- **Transacciones:** reservar, completar (descuento de stock) y facturar bloquean las filas con `SELECT … FOR UPDATE`
  y confirman o deshacen todo junto.
- **Lambdas y Streams:** reportes gerenciales (`ReporteServicio`), búsqueda de estados del catálogo y recorrido de órdenes.
- **Responsabilidad única:** presentación, coordinación, negocio y persistencia están separadas.

## Autores

Grupo 6:
- Brenda Nicol: clientes y vehículos, integración.
- Yeimer Torres: inventario y proveedores.
- Andrés: órdenes de servicio.
- Keiler: reportes gerenciales y facturación.

# Sistema de Control de Asistencia — MVP

Aplicación de escritorio en Java para el control de entrada y salida del personal de una empresa de 25 trabajadores. Desarrollada como Etapa I de la asignatura Integración de Competencias II.

Implementa los siete requerimientos funcionales del caso:

| Código | Requerimiento |
|---|---|
| CA-01 | Control de asistencia |
| RE-01 | Reporte de atrasos |
| RE-02 | Reporte de salidas anticipadas |
| RE-03 | Reporte de inasistencias |
| GU-01 | Crear usuarios |
| GU-02 | Modificar usuarios |
| GU-03 | Eliminar usuarios |

## Tecnologías

- Java 17 con interfaz Swing
- MySQL Server Community Edition
- JDBC con MySQL Connector/J 9.4.0
- Maven para gestión de dependencias
- JUnit 5 para pruebas unitarias
- Apache NetBeans

## Requerimientos implementados

### CA-01 Control de asistencia

El usuario ingresa con correo y contraseña. Luego puede registrar entrada, salida o cerrar sesión. Cada marca guarda identificador de usuario, tipo de acción, fecha y hora actual.

El sistema valida que no exista una marca duplicada del mismo tipo para ese usuario en la fecha actual. Sin esa validación un trabajador podría registrar varias entradas el mismo día y distorsionar los reportes.

### RE-01 Reporte de atrasos

Entradas registradas después de las 09:30. El umbral se define en `apoyo/ReglasHorario.java` y se pasa a la consulta como parámetro.

### RE-02 Reporte de salidas anticipadas

Salidas registradas antes de las 17:30. Mismo criterio que el anterior.

### RE-03 Reporte de inasistencias

El administrador ingresa una fecha y el sistema lista los usuarios activos que no tienen ningún registro ese día. Se resuelve con una subconsulta `NOT EXISTS`.

### GU-01 / GU-02 / GU-03 Gestión de usuarios

El administrador puede crear usuarios con nombre, correo, contraseña y rol; modificar nombre, correo, rol y opcionalmente la contraseña; y eliminar usuarios.

La eliminación es **lógica**: se marca `activo = 0` en lugar de borrar el registro. Un borrado físico haría fallar la clave foránea de `asistencias` o eliminaría el historial de marcas, dejando a la empresa sin respaldo frente a una fiscalización laboral.

### Panel de administrador

Al iniciar sesión como administrador se muestran tres botones: Registrar asistencia, Registrar salida y Gestionar usuarios. Los dos primeros registran la marca del propio administrador. El tercero abre la ventana de administración, con la gestión de usuarios y los tres reportes.

## Seguridad

- Las contraseñas se almacenan como hash **SHA-256** en la columna `password_hash CHAR(64)`. La aplicación nunca guarda ni transporta la contraseña en texto plano.
- Todas las consultas usan `PreparedStatement` con parámetros, lo que evita inyección SQL.
- La aplicación se conecta con el usuario de base de datos `asistencia_app`, que tiene permisos `SELECT`, `INSERT`, `UPDATE` y `DELETE` únicamente sobre `asistencia_mvp`. No se conecta con `root`.
- Las credenciales de conexión están externalizadas en `config/db.properties`, fuera del código fuente.

## Pruebas unitarias

El proyecto incluye 19 pruebas en tres clases, ejecutables con `mvn test` o desde NetBeans con `Alt+F6`. Ninguna requiere una base de datos activa.

| Clase | Cubre |
|---|---|
| `entidades/UsuarioTest` | Constructores, getters, estado activo y detección de rol administrador |
| `apoyo/ClavesTest` | Formato del hash, determinismo y coincidencia con `SHA2()` de MySQL |
| `apoyo/ReglasHorarioTest` | Casos límite de las reglas de atraso y salida anticipada |

La prueba `hashCoincideConElGeneradoPorMySql` compara el hash generado en Java contra el que produce la función `SHA2()` del script de base de datos. Si ambos dejan de coincidir, ningún usuario sembrado por el script podría iniciar sesión.

`ReglasHorarioTest` verifica los bordes exactos: una entrada a las 09:30 no es atraso y una salida a las 17:30 no es anticipada, porque el caso define ambos umbrales como estrictos.

## Credenciales de prueba

| Rol | Correo | Contraseña |
|---|---|---|
| Administrador | `admin@empresa.cl` | `admin123` |
| Usuario | `camila@empresa.cl` | `usuario123` |
| Usuario | `diego@empresa.cl` | `usuario123` |
| Usuario | `javiera@empresa.cl` | `usuario123` |

## Preparar la base de datos

1. Abrir MySQL Workbench y conectarse al servidor local **como root**.
2. Abrir una nueva pestaña SQL.
3. Ejecutar `database/schema.sql`.

El script crea la base `asistencia_mvp`, el usuario de aplicación, las tablas `usuarios` y `asistencias`, y carga datos de prueba. Es reejecutable: parte con `DROP TABLE IF EXISTS`, por lo que **borra los datos existentes** cada vez que se corre.

Desde consola:

```powershell
mysql -u root -p < database/schema.sql
```

También se incluye `database/asistencia_mvp_export.sql` como copia exportable de la base.

## Configurar la conexión

El archivo `config/db.properties` contiene:

```properties
db.url=jdbc:mysql://localhost:3306/asistencia_mvp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Santiago
db.user=asistencia_app
db.password=asistencia123
```

El proyecto asume el puerto estándar de MySQL Community: `3306`.

## Abrir en NetBeans

1. `File > Open Project` y elegir la carpeta del proyecto.
2. NetBeans lo reconoce como proyecto Maven por el `pom.xml`.
3. Esperar a que descargue MySQL Connector/J y JUnit.
4. Ejecutar con `F6`. La clase principal es `inicio.Inicio`.

Requiere JDK 17 o superior.

### Si aparece "No suitable driver found"

Significa que el conector de MySQL no está en el classpath. Verificar que el `pom.xml` contenga la dependencia:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.4.0</version>
</dependency>
```

Luego `Reload Project` y ejecutar de nuevo.

### Si aparece "Access denied for user 'asistencia_app'"

Ejecutar en Workbench como root:

```sql
ALTER USER 'asistencia_app'@'localhost' IDENTIFIED BY 'asistencia123';
GRANT SELECT, INSERT, UPDATE, DELETE ON asistencia_mvp.* TO 'asistencia_app'@'localhost';
FLUSH PRIVILEGES;
```

O ejecutar `scripts/reparar_conexion_mysql.ps1`, que no borra datos existentes.

## Ejecutar sin NetBeans

```
ejecutar_app.bat
```

Requiere `dist/asistencia-mvp.jar` y `lib/mysql-connector-j-9.4.0.jar`.

## Estructura del proyecto

```text
asistencia-mvp/
  src/main/java/inicio/         Clase principal
  src/main/java/conexion/       Conexión a MySQL
  src/main/java/datos/          Consultas y operaciones con la base de datos
  src/main/java/entidades/      Clases de dominio
  src/main/java/ventanas/       Ventanas Swing
  src/main/java/apoyo/          Hash de claves, reglas de horario, mensajes, fondos
  src/test/java/                Pruebas unitarias
  src/main/resources/imagenes/  Fondos de las ventanas
  database/                     Scripts SQL
  config/                       Archivo de conexión
  lib/                          Conector MySQL para ejecutar con .bat
  scripts/                      Scripts de ayuda para configurar MySQL
  docs/                         Documentación complementaria
  pom.xml                       Configuración Maven
  ejecutar_app.bat              Ejecuta la app sin abrir NetBeans
```

## Arquitectura

El proyecto separa responsabilidades en paquetes para permitir el trabajo en paralelo:

```text
ventanas  ->  datos  ->  conexion  ->  MySQL
                 |
             entidades
```

Las ventanas nunca abren conexiones directamente: delegan en las clases de `datos`, que a su vez obtienen la conexión desde `ConexionBaseDatos`.

**Limitación conocida:** los métodos de consulta de `ReportesDatos` y `UsuariosDatos` devuelven `DefaultTableModel`, una estructura de Swing. Esto acopla la capa de datos a la interfaz y dificulta reutilizar esas consultas fuera de Swing. Está identificado como deuda técnica para la siguiente etapa.

## Consultas clave

Reporte de atrasos:

```sql
SELECT u.id, u.nombre, u.correo, a.fecha, a.hora
FROM asistencias a
INNER JOIN usuarios u ON u.id = a.usuario_id
WHERE a.tipo = 'ENTRADA' AND a.hora > ?
ORDER BY a.fecha DESC, a.hora DESC;
```

Reporte de salidas anticipadas:

```sql
SELECT u.id, u.nombre, u.correo, a.fecha, a.hora
FROM asistencias a
INNER JOIN usuarios u ON u.id = a.usuario_id
WHERE a.tipo = 'SALIDA' AND a.hora < ?
ORDER BY a.fecha DESC, a.hora DESC;
```

Reporte de inasistencias:

```sql
SELECT u.id, u.nombre, u.correo
FROM usuarios u
WHERE u.rol = 'USUARIO'
  AND u.activo = 1
  AND NOT EXISTS (
      SELECT 1
      FROM asistencias a
      WHERE a.usuario_id = u.id
        AND a.fecha = ?
  )
ORDER BY u.nombre;
```

Registro de una marca:

```sql
INSERT INTO asistencias (usuario_id, tipo, fecha, hora)
VALUES (?, ?, CURDATE(), CURTIME());
```

## Equipo

Maickoll Challapa · Rafael Lorenzo · Gonzalo Fuentes · Ignacia Mamani · Diego Sereño · Danka Tapia

Instituto Profesional Santo Tomás — Ingeniería en Informática

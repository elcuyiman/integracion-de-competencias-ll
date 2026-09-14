# Sistema de Control de Asistencia - MVP

Proyecto Java de escritorio para Apache NetBeans. Implementa los requerimientos funcionales del caso:

- CA-01: Control de asistencia.
- RE-01: Reporte de atrasos.
- RE-02: Reporte de salidas anticipadas.
- RE-03: Reporte de inasistencias.
- GU-01: Crear usuarios.
- GU-02: Modificar usuarios.
- GU-03: Eliminar usuarios.

## Tecnologias

- Java Swing.
- MySQL Server Community Edition.
- JDBC con MySQL Connector/J.
- Apache NetBeans.

## Requerimientos implementados

### CA-01 Control de asistencia

El usuario ingresa con correo y contrasena. Luego puede registrar:

- Entrada.
- Salida.
- Cierre de sesion.

Cada marca guarda identificador de usuario, tipo de accion, fecha y hora actual.

### RE-01 Reporte de atrasos

El administrador puede visualizar las entradas registradas despues de las 09:30 desde el apartado Control de asistencia.

### RE-02 Reporte de salidas anticipadas

El administrador puede visualizar las salidas registradas antes de las 17:30 desde el apartado Control de asistencia.

### RE-03 Reporte de inasistencias

El administrador puede ingresar una fecha y visualizar los usuarios activos que no tienen registros de entrada ni salida ese dia.

### GU-01 Crear usuarios

El administrador puede crear usuarios con nombre, correo, contrasena y rol.

### GU-02 Modificar usuarios

El administrador puede modificar nombre, correo, rol y opcionalmente cambiar la contrasena.

### GU-03 Eliminar usuarios

El administrador puede eliminar usuarios dejandolos inactivos. Se usa eliminacion logica para conservar el historial de asistencias.

### Panel de administrador

Al iniciar sesion como administrador se muestran tres botones:

- Registrar asistencia.
- Registrar salida.
- Gestionar usuarios.

Los dos primeros registran la marca del administrador con fecha y hora actual. El boton Gestionar usuarios abre la ventana de administracion, donde aparece la gestion de usuarios y el apartado Control de asistencia con los reportes.

## Credenciales de prueba

Administrador:

- Correo: `admin@empresa.cl`
- Contrasena: `admin123`

Usuarios:

- Correo: `camila@empresa.cl`
- Contrasena: `usuario123`

- Correo: `diego@empresa.cl`
- Contrasena: `usuario123`

- Correo: `javiera@empresa.cl`
- Contrasena: `usuario123`

## Preparar base de datos en MySQL Server Community

1. Abrir MySQL Workbench.
2. Conectarse al servidor local de MySQL Community.
3. Abrir una nueva pestana SQL.
4. Ejecutar el archivo:

```sql
database/schema.sql
```

Ese script crea la base de datos `asistencia_mvp`, las tablas `usuarios` y `asistencias`, y datos de prueba. Tambien se incluye `database/asistencia_mvp_export.sql` como copia exportable de la base para subir o importar desde MySQL Workbench.

Si usas consola, tambien puedes ejecutar:

```powershell
mysql -u root -p < database/schema.sql
```

## Configurar conexion

El archivo `config/db.properties` contiene la conexion:

```properties
db.url=jdbc:mysql://localhost:3306/asistencia_mvp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Santiago
db.user=asistencia_app
db.password=asistencia123
```

El script `database/schema.sql` crea este usuario de aplicacion. Tu usuario `root` se usa solo para preparar la base de datos.

El proyecto asume el puerto normal de MySQL Community: `3306`.

## Abrir en NetBeans

1. Abrir Apache NetBeans.
2. Seleccionar `File > Open Project`.
3. Elegir la carpeta del proyecto.
4. NetBeans lo reconocera como proyecto Maven por el archivo `pom.xml`.
5. Esperar a que descargue MySQL Connector/J automaticamente.
6. Ejecutar el proyecto o la clase principal:

```text
inicio.Inicio
```

Se recomienda usar JDK 17 o superior.

## Nota sobre MySQL

Este proyecto esta preparado para **MySQL Server Community Edition**, no para Microsoft SQL Server. La conexion usa:

```text
jdbc:mysql://localhost:3306/asistencia_mvp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Santiago
```

## Configuracion automatica en Windows

Tambien puedes ejecutar:

```powershell
scripts/configurar_mysql_community.ps1
```

El script pide la contrasena de `root`, carga `database/schema.sql` y verifica las tablas.

Si la aplicacion muestra `Public Key Retrieval is not allowed` o `Access denied for user 'asistencia_app'`, ejecuta:

```powershell
scripts/reparar_conexion_mysql.ps1
```

Ese script no borra datos existentes; solo repara la URL esperada, el usuario de la app y sus permisos.

## Estructura principal

```text
asistencia-mvp/
  src/main/java/                 Codigo Java de la aplicacion
  src/main/java/inicio            Clase principal
  src/main/java/conexion          Conexion a MySQL
  src/main/java/datos             Consultas y operaciones con la base de datos
  src/main/java/entidades         Clases de datos
  src/main/java/ventanas          Ventanas Swing
  src/main/java/apoyo             Funciones de apoyo
  database/                      Scripts SQL de la base de datos
  config/                        Archivo de conexion
  lib/                           Conector MySQL para ejecutar con .bat
  scripts/                       Scripts de ayuda para configurar MySQL
  docs/                          Informe breve de cumplimiento
  pom.xml                        Configuracion Maven del proyecto
  ejecutar_app.bat               Ejecuta la app sin abrir NetBeans
```

En NetBeans, dentro de **Source Packages**, la organizacion queda asi:

```text
inicio     inicio del programa
conexion   conexion a la base de datos
datos      consultas SQL
entidades  datos del sistema
ventanas   ventanas de la aplicacion
apoyo      funciones de apoyo
```

## Archivos importantes para explicar

```text
src/main/java/inicio/Inicio.java
src/main/java/conexion/ConexionBaseDatos.java
src/main/java/datos/AccesoDatos.java
src/main/java/datos/AsistenciasDatos.java
src/main/java/datos/ReportesDatos.java
src/main/java/datos/UsuariosDatos.java
src/main/java/entidades/Usuario.java
src/main/java/ventanas/VentanaLogin.java
src/main/java/ventanas/VentanaAsistencia.java
src/main/java/ventanas/VentanaAdministrador.java
src/main/java/ventanas/VentanaUsuarios.java
database/schema.sql
config/db.properties
docs/INFORME_CUMPLIMIENTO.md
docs/INSTRUCCIONES_COMPANEROS.md
ejecutar_app.bat
```

## Copia para companeros

Para otra persona, lo mas simple es entregar la carpeta completa o el `.zip`.

Primero debe preparar la base con:

```text
database/schema.sql
```

Despues puede ejecutar con:

```text
ejecutar_app.bat
```

Tambien puede abrirlo desde NetBeans como proyecto Maven.

## Consultas clave

Reporte de atrasos:

```sql
SELECT u.id, u.nombre, u.correo, a.fecha, a.hora
FROM asistencias a
INNER JOIN usuarios u ON u.id = a.usuario_id
WHERE a.tipo = 'ENTRADA' AND a.hora > '09:30:00';
```

Reporte de salidas anticipadas:

```sql
SELECT u.id, u.nombre, u.correo, a.fecha, a.hora
FROM asistencias a
INNER JOIN usuarios u ON u.id = a.usuario_id
WHERE a.tipo = 'SALIDA' AND a.hora < '17:30:00';
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
  );
```

Registro automatico desde administrador:

```sql
INSERT INTO asistencias (usuario_id, tipo, fecha, hora)
VALUES (?, ?, CURDATE(), CURTIME());
```

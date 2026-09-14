# Informe breve de cumplimiento

## Caso

La empresa solicita un MVP de escritorio para registrar asistencia de trabajadores. La aplicacion implementa los requerimientos funcionales indicados en el documento:

1. CA-01 Control de asistencia.
2. RE-01 Reporte de atrasos.
3. RE-02 Reporte de salidas anticipadas.
4. RE-03 Reporte de inasistencias.
5. GU-01 Crear usuarios.
6. GU-02 Modificar usuarios.
7. GU-03 Eliminar usuarios.

## Alcance implementado

La solucion fue desarrollada como aplicacion de escritorio en Java Swing, conectada a **MySQL Server Community Edition** mediante JDBC y MySQL Connector/J.

### CA-01 Control de asistencia

La aplicacion permite que un usuario ingrese mediante correo y contrasena. Una vez autenticado, puede marcar entrada o salida mediante botones. El sistema guarda:

- ID del usuario.
- Tipo de marca: `ENTRADA` o `SALIDA`.
- Fecha actual.
- Hora actual.

### RE-01 Reporte de atrasos

El administrador puede consultar una tabla con los usuarios que registraron entrada despues de las 09:30.

Condicion aplicada:

```sql
a.tipo = 'ENTRADA' AND a.hora > '09:30:00'
```

### RE-02 Reporte de salidas anticipadas

El administrador puede consultar una tabla con los usuarios que registraron salida antes de las 17:30.

Condicion aplicada:

```sql
a.tipo = 'SALIDA' AND a.hora < '17:30:00'
```

### RE-03 Reporte de inasistencias

El administrador ingresa una fecha y el sistema muestra usuarios activos sin registros de asistencia para ese dia.

### GU-01 Crear usuarios

El administrador puede registrar nuevos usuarios con nombre, correo, contrasena y rol.

### GU-02 Modificar usuarios

El administrador puede modificar datos de usuarios existentes y cambiar contrasena si lo requiere.

### GU-03 Eliminar usuarios

El administrador puede eliminar usuarios mediante desactivacion logica (`activo = 0`). Esto conserva el historial y evita eliminar asistencias ya registradas.

### Panel de administrador

Al iniciar sesion como administrador se muestra una ventana simple con tres botones: registrar asistencia, registrar salida y gestionar usuarios.

El boton Gestionar usuarios abre la ventana de administracion. En esa ventana aparece la gestion de usuarios y el apartado Control de asistencia con los reportes.

## Roles

- `ADMIN`: registra asistencia, registra salida y accede a la gestion de usuarios con control de asistencia.
- `USUARIO`: marca entrada y salida.

## Tablas de base de datos

### usuarios

Guarda datos de acceso y rol del usuario.

Campos principales:

- `id`
- `nombre`
- `correo`
- `password_hash`
- `rol`
- `activo`

### asistencias

Guarda cada marca de asistencia.

Campos principales:

- `id`
- `usuario_id`
- `tipo`
- `fecha`
- `hora`

## Validaciones basicas

- No permite login con campos vacios.
- No permite login con credenciales incorrectas.
- No permite que un usuario marque dos entradas el mismo dia.
- No permite que un usuario marque dos salidas el mismo dia.

## Nota de alcance

La aplicacion se limita al control de asistencia, reportes y gestion de usuarios solicitados.

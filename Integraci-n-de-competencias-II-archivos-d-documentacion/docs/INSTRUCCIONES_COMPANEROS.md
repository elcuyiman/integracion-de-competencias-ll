# Instrucciones para ejecutar

## Requisitos

- Tener Java instalado.
- Tener MySQL Server Community instalado y encendido.
- Tener Apache NetBeans si lo van a abrir como proyecto.

## Preparar la base de datos

1. Abrir MySQL Workbench.
2. Entrar a la conexion local.
3. Abrir el archivo:

```text
database/schema.sql
```

Tambien pueden usar:

```text
database/asistencia_mvp_export.sql
```

4. Ejecutar el script con el rayo.

Eso crea:

```text
base: asistencia_mvp
usuario app: asistencia_app
clave app: asistencia123
```

## Ejecutar desde NetBeans

1. Abrir NetBeans.
2. Ir a `File > Open Project`.
3. Seleccionar la carpeta `asistencia-mvp`.
4. Clic derecho en el proyecto.
5. Elegir `Run`.

Si pide clase principal:

```text
inicio.Inicio
```

## Ejecutar sin NetBeans

Si ya esta creada la base de datos, abrir:

```text
ejecutar_app.bat
```

## Usuarios de prueba

Administrador:

```text
admin@empresa.cl
admin123
```

Usuario normal:

```text
camila@empresa.cl
usuario123
```

## Ver datos en MySQL Workbench

```sql
USE asistencia_mvp;
SELECT * FROM usuarios;
SELECT * FROM asistencias;
```

Si marcan entrada o salida desde la aplicacion, deben volver a ejecutar el `SELECT` o actualizar la tabla en Workbench.

# Configuracion con MySQL Server Community Edition

Este proyecto usa MySQL Server Community Edition en `localhost`, puerto `3306`.

## Pasos recomendados

1. Abrir MySQL Workbench.
2. Entrar a la conexion local de MySQL.
3. Abrir el archivo `schema.sql` o `asistencia_mvp_export.sql`.
4. Ejecutar todo el script.
5. Confirmar que existe la base de datos:

```sql
SHOW DATABASES;
USE asistencia_mvp;
SHOW TABLES;
SELECT * FROM usuarios;
SELECT * FROM asistencias;
```

## Conexion usada por Java

```properties
db.url=jdbc:mysql://localhost:3306/asistencia_mvp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Santiago
db.user=asistencia_app
db.password=asistencia123
```

El usuario `asistencia_app` se crea automaticamente al ejecutar `schema.sql` o `asistencia_mvp_export.sql` con `root`.

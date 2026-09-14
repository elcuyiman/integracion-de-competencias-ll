@echo off
cd /d "%~dp0"

if not exist "dist\asistencia-mvp.jar" (
  echo No se encontro dist\asistencia-mvp.jar
  pause
  exit /b 1
)

if not exist "lib\mysql-connector-j-9.4.0.jar" (
  echo No se encontro lib\mysql-connector-j-9.4.0.jar
  echo Tambien puedes abrir el proyecto en NetBeans y ejecutarlo desde ahi.
  pause
  exit /b 1
)

java -cp "dist\asistencia-mvp.jar;lib\mysql-connector-j-9.4.0.jar" inicio.Inicio
pause

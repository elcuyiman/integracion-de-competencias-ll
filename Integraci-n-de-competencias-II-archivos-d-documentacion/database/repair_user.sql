CREATE DATABASE IF NOT EXISTS asistencia_mvp
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_spanish_ci;

CREATE USER IF NOT EXISTS 'asistencia_app'@'localhost' IDENTIFIED BY 'asistencia123';
ALTER USER 'asistencia_app'@'localhost' IDENTIFIED BY 'asistencia123';
GRANT SELECT, INSERT, UPDATE, DELETE ON asistencia_mvp.* TO 'asistencia_app'@'localhost';
FLUSH PRIVILEGES;

USE asistencia_mvp;

CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  correo VARCHAR(120) NOT NULL UNIQUE,
  password_hash CHAR(64) NOT NULL,
  rol VARCHAR(20) NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS asistencias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  tipo VARCHAR(10) NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_asistencias_usuario_repair
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
  CONSTRAINT chk_asistencias_tipo_repair
    CHECK (tipo IN ('ENTRADA', 'SALIDA'))
);

INSERT IGNORE INTO usuarios (id, nombre, correo, password_hash, rol, activo) VALUES
(1, 'Administrador General', 'admin@empresa.cl', SHA2('admin123', 256), 'ADMIN', 1),
(2, 'Camila Rojas', 'camila@empresa.cl', SHA2('usuario123', 256), 'USUARIO', 1),
(3, 'Diego Soto', 'diego@empresa.cl', SHA2('usuario123', 256), 'USUARIO', 1),
(4, 'Javiera Lagos', 'javiera@empresa.cl', SHA2('usuario123', 256), 'USUARIO', 1);

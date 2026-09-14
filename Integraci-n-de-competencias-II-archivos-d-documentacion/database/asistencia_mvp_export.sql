CREATE DATABASE IF NOT EXISTS asistencia_mvp
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_spanish_ci;

CREATE USER IF NOT EXISTS 'asistencia_app'@'localhost' IDENTIFIED BY 'asistencia123';
ALTER USER 'asistencia_app'@'localhost' IDENTIFIED BY 'asistencia123';
GRANT SELECT, INSERT, UPDATE, DELETE ON asistencia_mvp.* TO 'asistencia_app'@'localhost';
FLUSH PRIVILEGES;

USE asistencia_mvp;

DROP TABLE IF EXISTS asistencias;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  correo VARCHAR(120) NOT NULL UNIQUE,
  password_hash CHAR(64) NOT NULL,
  rol VARCHAR(20) NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE asistencias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  tipo VARCHAR(10) NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_asistencias_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
  CONSTRAINT chk_asistencias_tipo
    CHECK (tipo IN ('ENTRADA', 'SALIDA')),
  INDEX idx_asistencias_tipo_hora (tipo, hora),
  INDEX idx_asistencias_fecha_usuario (fecha, usuario_id)
);

INSERT INTO usuarios (id, nombre, correo, password_hash, rol, activo) VALUES
(1, 'Administrador General', 'admin@empresa.cl', SHA2('admin123', 256), 'ADMIN', 1),
(2, 'Camila Rojas', 'camila@empresa.cl', SHA2('usuario123', 256), 'USUARIO', 1),
(3, 'Diego Soto', 'diego@empresa.cl', SHA2('usuario123', 256), 'USUARIO', 1),
(4, 'Javiera Lagos', 'javiera@empresa.cl', SHA2('usuario123', 256), 'USUARIO', 1);

INSERT INTO asistencias (usuario_id, tipo, fecha, hora) VALUES
(2, 'ENTRADA', CURDATE(), '09:10:00'),
(2, 'SALIDA', CURDATE(), '17:45:00'),
(3, 'ENTRADA', CURDATE(), '09:45:00'),
(3, 'SALIDA', CURDATE(), '17:20:00'),
(4, 'ENTRADA', CURDATE(), '10:05:00'),
(4, 'SALIDA', CURDATE(), '16:55:00');

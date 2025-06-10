-- Script de creación de tablas para la práctica
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasenya VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL  -- 'admin' o 'user'
);

CREATE TABLE espacios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    espacio_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'activa', -- 'activa', 'cancelada'
    FOREIGN KEY (espacio_id) REFERENCES espacios(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    INDEX idx_espacio_fecha (espacio_id, fecha_hora_inicio, fecha_hora_fin),
    INDEX idx_usuario (usuario_id),
    INDEX idx_estado (estado)
);
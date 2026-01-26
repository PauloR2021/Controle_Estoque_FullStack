ALTER TABLE usuarios
ADD CONSTRAINT uk_usuario_username UNIQUE (username);
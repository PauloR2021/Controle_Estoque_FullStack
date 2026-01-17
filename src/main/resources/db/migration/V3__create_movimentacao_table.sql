CREATE TABLE movimentacoes_estoque (
    id SERIAL PRIMARY KEY,
    produto_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    quantidade INTEGER NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_mov_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos (id),

    CONSTRAINT fk_mov_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
);
-- Esquema PostgreSQL inferido das consultas SQL dos repositories.

CREATE TABLE IF NOT EXISTS clientes (
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS produtos (
    id                 SERIAL PRIMARY KEY,
    nome               VARCHAR(100)   NOT NULL,
    preco              NUMERIC(10, 2) NOT NULL CHECK (preco > 0),
    estoque_disponivel INTEGER        NOT NULL DEFAULT 0
                       CHECK (estoque_disponivel >= 0)
);

CREATE TABLE IF NOT EXISTS pedidos (
    id               SERIAL PRIMARY KEY,
    cliente_id       INTEGER        NOT NULL REFERENCES clientes (id),
    produto_id       INTEGER        NOT NULL REFERENCES produtos (id),
    preco_registrado NUMERIC(10, 2) NOT NULL,
    data_registro    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao         VARCHAR(20)    NOT NULL DEFAULT 'ATIVO'
                     CHECK (situacao IN ('ATIVO', 'CANCELADO'))
);

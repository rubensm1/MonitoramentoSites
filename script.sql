CREATE SCHEMA monitorador;

CREATE TABLE monitorador.LocalMaquina (
	id SERIAL PRIMARY KEY,
	nome VARCHAR (32) NOT NULL,
	url VARCHAR(128) NOT NULL
);

CREATE TABLE monitorador.Maquina (
    id SERIAL PRIMARY KEY,
    nome VARCHAR (32) NOT NULL,
    endereco VARCHAR (64) NOT NULL,
    ip VARCHAR (16)
);
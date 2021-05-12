-- SCRIPTS INICIAIS
CREATE DATABASE projetoc;
USE projetoc;

-- SCRIPT CRIAÇÃO DA TABELA, HOMOLOG
CREATE TABLE labirinto(
  nome          VARCHAR(25)                          NOT NULL,
  identificador VARCHAR(15)                          NOT NULL,
  dataCriacao   TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  dataEdicao    TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  conteudo      LONGTEXT                             NOT NULL,
  CONSTRAINT PK_lab PRIMARY KEY (nome,identificador)
);

-- SCRIPT CRIAÇÃO DA TABELA EM UMA LINHA, PRODUÇÃO
CREATE TABLE labirinto( nome VARCHAR(25) NOT NULL, identificador VARCHAR(15) NOT NULL, dataCriacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, dataEdicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP, conteudo LONGTEXT NOT NULL, CONSTRAINT PK_lab PRIMARY KEY (nome,identificador) );

-- COMANDO DE INSERCAO
INSERT INTO labirinto(nome, identificador, conteudo) VALUES ('Labirinto Facil','192.168.0.1','#######');

-- COMANDO DE SELEÇÃO
SELECT * FROM labirinto;
CREATE TABLE ruoli (
	id SERIAL PRIMARY KEY,
	nome_ruolo VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE utente_ruoli (
	utente_id BIGINT NOT NULL,
	ruolo_id INT NOT NULL,
	PRIMARY KEY (utente_id, ruolo_id),
	CONSTRAINT fk_utente_id FOREIGN KEY (utente_id) REFERENCES utenti(id),
	CONSTRAINT fk_ruolo_id FOREIGN KEY (ruolo_id) REFERENCES ruoli(id)
);

INSERT INTO ruoli(nome_ruolo) VALUES('ROLE_UTENTE');
INSERT INTO ruoli(nome_ruolo) VALUES('ROLE_AMMINISTRATORE');

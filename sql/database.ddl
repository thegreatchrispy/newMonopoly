DROP TABLE IF EXISTS accounts CASCADE;

CREATE TABLE accounts (
	id SERIAL NOT NULL PRIMARY KEY,
	username TEXT NOT NULL,
	password TEXT NOT NULL,
	email TEXT NOT NULL,
	is_admin BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS sessions;

CREATE TABLE sessions (
	id SERIAL NOT NULL PRIMARY KEY,
	board_config JSON NOT NULL,
	players JSON NOT NULL,
	current_player INTEGER NOT NULL REFERENCES players (id),
	current_season TEXT NOT NULL,
	difficulty TEXT
);
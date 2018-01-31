DROP TABLE IF EXISTS player CASCADE;

CREATE TABLE player (
	id SERIAL PRIMARY KEY,
	username VARCHAR() NOT NULL,
	password VARCHAR() NOT NULL,
	email VARCHAR(),
	icon BYTEA,
	bio VARCHAR(),
	isAdmin BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS games;

CREATE TABLE games (
	number_players INTEGER NOT NULL,
	in_progress BOOLEAN NOT NULL,
	turn_number INTEGER NOT NULL
);
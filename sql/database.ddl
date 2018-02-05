CREATE SCHEMA IF NOT EXISTS monopoly;

DROP TABLE IF EXISTS monopoly.player CASCADE;

CREATE TABLE monopoly.player (
	id SERIAL PRIMARY KEY,
	username TEXT NOT NULL,
	password TEXT NOT NULL,
	email TEXT,
	icon BYTEA,
	bio TEXT,
	isAdmin BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS monopoly.games;

CREATE TABLE monopoly.games (
	number_players INTEGER NOT NULL,
	in_progress BOOLEAN NOT NULL,
	turn_number INTEGER NOT NULL
);
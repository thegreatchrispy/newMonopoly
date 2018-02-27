CREATE SCHEMA IF NOT EXISTS monopoly;

DROP TABLE IF EXISTS monopoly.players CASCADE;

CREATE TABLE monopoly.players (
	id SERIAL NOT NULL PRIMARY KEY,
	username TEXT NOT NULL,
	password TEXT NOT NULL,
	email TEXT,
	isAdmin BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS monopoly.sessions;

CREATE TABLE monopoly.sessions (
	id SERIAL NOT NULL PRIMARY KEY,
	board_config JSON NOT NULL,
	players JSON NOT NULL,
	currentPlayer INTEGER NOT NULL REFERENCES monopoly.player (id),
	currentSeason TEXT NOT NULL,
	difficulty TEXT
);
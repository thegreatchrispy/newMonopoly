DROP TABLE IF EXISTS accounts CASCADE;

CREATE TABLE accounts (
	id SERIAL NOT NULL PRIMARY KEY,
	username TEXT NOT NULL,
	password TEXT NOT NULL,
	email TEXT NOT NULL,
	is_admin BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS new_monopolies CASCADE;

CREATE TABLE new_monopolies (
	id SERIAL NOT NULL PRIMARY KEY,
	players JSON NOT NULL,
	die1 INTEGER NOT NULL,
	die2 INTEGER NOT NULL,
	board_id INTEGER NOT NULL REFERENCES boards (id),
	max_turns INTEGER NOT NULL,
	game_over BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS boards CASCADE;

CREATE TABLE boards (
	board_id NOT NULL PRIMARY KEY,
	new_monopoly_id NOT NULL REFERENCES new_monopolies (id),
	players JSON NOT NULL,
	current_turn INTEGER NOT NULL,
	total_player INTEGER NOT NULL,
	turn_over BOOLEAN NOT NULL,
	spaces JSON NOT NULL,
	chance JSON NOT NULL,
	community JSON NOT NULL,
	player_index INTEGER NOT NULL,
	houses_available INTEGER NOT NULL,
	hotels_available INTEGER NOT NULL,
	die_value INTEGER NOT NULL
)
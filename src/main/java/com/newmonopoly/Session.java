package com.newmonopoly;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gson.Gson;

import java.io.Serializable;

@Entity
@Table(name="sessions")
public class Session implements Serializable {
	private Gson gson = new Gson();
	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "board_config")
	private String boardConfig;

	@Column(name = "players")
	private String players;

	@Column(name = "current_player")
	private Player currentPlayer;

	@Column(name = "current_season")
	private String currentSeason;

	@Column(name = "difficulty")
	private String difficulty;

	protected Session() {
	}

	public Session(String boardConfig, String players, Player currentPlayer, String currentSeason, String difficulty) {
		this.boardConfig = boardConfig;
		this.players = players;
		this.currentPlayer = currentPlayer;
		this.currentSeason = currentSeason;
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return String.format("Session:\n{\ngameId=%d\nboardConfig=%s,\nplayers=%s,\ncurrentPlayer=%s,\ncurrentSeason=%s,\ndifficulty=%s\n}", id, boardConfig, players, currentPlayer.toString(), currentSeason, difficulty);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBoardConfig() {
		return boardConfig;
	}

	public void setBoardConfig(String boardConfig) {
		this.boardConfig = boardConfig;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
}
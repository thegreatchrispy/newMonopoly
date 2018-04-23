package com.newmonopoly.model;

import java.util.Set;
import java.util.List;
import java.util.Vector;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "board_id")
	private int id;
	@Column(name = "players")
	private List<Player> players;
	@Column(name = "current_turn")
	private int currentTurn;
	@Column(name = "total_player")
	private int totalPlayer;
	@Column(name = "turn_over")
	private boolean turnOver;
	@Column(name = "spaces")
	private List<Space> spaces;
	@Column(name = "chance")
	private List<Card> chance;
	@Column(name = "community")
	private List<Card> community;
	@Column(name = "player_index")
	private int playerIndex;
	@Column(name = "houses_available")
	private int housesAvailable;
	@Column(name = "hotels_available")
	private int hotelsAvailable;
	@Column(name = "die_value")
	private int dieValue;
	@Column(name = "player_choice_char")
	private char playerChoiceChar;
	@Column(name = "player_choice_int")
	private int playerChoiceInt;
	@Column(name = "trade_choice_char")
	private char tradeChoiceChar;

	public Board() {

	}

	public Board(List<Player> players, boolean randomizeSet) {
		this.players = players;
		currentTurn = 0;
		totalPlayer = 6;// Set to 6 for testing, will get from constructor in future.
		turnOver = false;
		spaces = new Vector<Space>();
		chance = new Vector<Card>();
		community = new Vector<Card>();
		dieValue = 0;
		playerIndex = 0;
		housesAvailable = 32;
		hotelsAvailable = 12;
		Collections.shuffle(chance);
		Collections.shuffle(community);
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public int getTotalPlayer() {
		return totalPlayer;
	}

	public void setTotalPlayer(int totalPlayer) {
		this.totalPlayer = totalPlayer;
	}

	public boolean getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(boolean turnOver) {
		this.turnOver = turnOver;
	} 

	public List<Space> getSpaces() {
		return spaces;
	}

	public void setSpaces(List<Space> spaces) {
		this.spaces = spaces;
	}

	public List<Card> getChance() {
		return chance;
	}

	public void setChance(List<Card> chance) {
		this.chance = chance;
	}

	public List<Card> getCommunity() {
		return community;
	}

	public void setCommunity(List<Card> community) {
		this.community = community;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getHousesAvailable() {
		return housesAvailable;
	}

	public void setHousesAvailable(int housesAvailable) {
		this.housesAvailable = housesAvailable;
	}

	public int getHotelsAvailable() {
		return hotelsAvailable;
	}

	public void setHotelsAvailable(int hotelsAvailable) {
		this.hotelsAvailable = hotelsAvailable;
	}

	public int getDieValue() {
		return dieValue;
	}

	public void setDieValue(int dieValue) {
		this.dieValue = dieValue;
	}

	public char getPlayerChoiceChar() {
		return playerChoiceChar;
	}

	public void setPlayerChoiceChar(char playerChoiceChar) {
		this.playerChoiceChar = playerChoiceChar;
	}

	public int getPlayerChoiceInt() {
		return playerChoiceInt;
	}

	public void setPlayerChoiceInt(int playerChoiceInt) {
		this.playerChoiceInt = playerChoiceInt;
	}

	public char getTradeChoiceChar() {
		return tradeChoiceChar;
	}

	public void setTradeChoiceChar(char tradeChoiceChar) {
		this.tradeChoiceChar = tradeChoiceChar;
	}
}
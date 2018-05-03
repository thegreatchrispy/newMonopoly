package com.newmonopoly.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity
@Table(name = "boards")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "board_id")
	private int id;
	@Column(name = "players")
	private String players;
	@Column(name = "current_turn")
	private int currentTurn;
	@Column(name = "total_player")
	private int totalPlayer;
	@Column(name = "turn_over")
	private boolean turnOver;
	@Column(name = "spaces")
	private String spaces;
	@Column(name = "chance")
	private String chance;
	@Column(name = "community")
	private String community;
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
	@Column(name = "randomize_set")
	private boolean randomizeSet;
	@Column(name = "swapped")
	private String swapped;

	public Board() {

	}

	public Board(List<Player> players, boolean randomizeSet) {
		Gson gson = new Gson();
		this.players = gson.toJson(players);
		currentTurn = 0;
		totalPlayer = 6;// Set to 6 for testing, will get from constructor in future.
		turnOver = false;
		spaces = "";
		chance = "";
		community = "";
		swapped = "";
		dieValue = 0;
		playerIndex = 0;
		housesAvailable = 32;
		hotelsAvailable = 12;
		this.randomizeSet = randomizeSet;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		Gson gson = new Gson();
		return gson.fromJson(this.players, new TypeToken<List<Player>>(){}.getType());
	}

	public void setPlayers(List<Player> players) {
		Gson gson = new Gson();
		this.players = gson.toJson(players);
	}

	public void addPlayer(Player player) {
		Gson gson = new Gson();
		List<Player> currentPlayers = gson.fromJson(this.players, new TypeToken<List<Player>>(){}.getType());
		currentPlayers.add(player);
		this.players = gson.toJson(currentPlayers);
	}

	public void removePlayer(Player player) {
		Gson gson = new Gson();
		List<Player> currentPlayers = gson.fromJson(this.players, new TypeToken<List<Player>>(){}.getType());
		currentPlayers.remove(player);
		this.players = gson.toJson(currentPlayers);
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
		Gson gson = new Gson();
		return gson.fromJson(this.spaces, new TypeToken<List<Space>>(){}.getType());
	}

	public void setSpaces(List<Space> spaces) {
		Gson gson = new Gson();
		this.spaces = gson.toJson(spaces);
	}

	public List<Card> getChance() {
		Gson gson = new Gson();
		return gson.fromJson(this.chance, new TypeToken<List<Card>>(){}.getType());
	}

	public void setChance(List<Card> chance) {
		Gson gson = new Gson();
		this.chance = gson.toJson(chance);
	}

	public List<Card> getCommunity() {
		Gson gson = new Gson();
		return gson.fromJson(this.community, new TypeToken<List<Card>>(){}.getType());
	}

	public void setCommunity(List<Card> community) {
		Gson gson = new Gson();
		this.community = gson.toJson(community);
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

	public boolean isRandomized() {
		return randomizeSet;
	}

	public void setRandomize(boolean randomizeSet) {
		this.randomizeSet = randomizeSet;
	}

	public String getSwappedString() {
		return swapped;
	}

	public void setSwappedString(String swapped) {
		this.swapped = swapped;
	}
}
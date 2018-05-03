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

/**
 * Board model object.
 * 
 * <p>Model for the columns stored in the <tt>boards</tt> table.
 * See {@link #Board(List<Space>, boolean)} for more information.
 */
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

	/**
	 * Default constructor.
	 */
	public Board() {

	}

	/**
	 * Constructor given parameters.
	 * 
	 * @param players The list of players that the game is starting with.
	 * @param randomizeSet The option to either randomize the set of spaces, or leave them in original order.
	 */
	public Board(List<Player> players, boolean randomizeSet) {
		Gson gson = new Gson();
		this.players = gson.toJson(players);
		currentTurn = 0;
		totalPlayer = 6;
		turnOver = false;
		spaces = "";
		chance = "";
		community = "";
		dieValue = 0;
		playerIndex = 0;
		housesAvailable = 32;
		hotelsAvailable = 12;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
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

	/**
	 * Returns an integer indicating how many turns have passed in the <tt>Board</tt> object from the database.
	 * This is used to end the game if the player that creates the game decides to set a turn limit.
	 * 
	 * @return the current turn for the <tt>Board</tt> in the database
	 */
	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public int getTotalPlayer() {
		return totalPlayer;
	}

	public void setTotalPlayer(int totalPlayer) {
		this.totalPlayer = totalPlayer;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public boolean getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(boolean turnOver) {
		this.turnOver = turnOver;
	} 

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public List<Space> getSpaces() {
		Gson gson = new Gson();
		return gson.fromJson(this.spaces, new TypeToken<List<Space>>(){}.getType());
	}

	public void setSpaces(List<Space> spaces) {
		Gson gson = new Gson();
		this.spaces = gson.toJson(spaces);
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public List<Card> getChance() {
		Gson gson = new Gson();
		return gson.fromJson(this.chance, new TypeToken<List<Card>>(){}.getType());
	}

	public void setChance(List<Card> chance) {
		Gson gson = new Gson();
		this.chance = gson.toJson(chance);
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public List<Card> getCommunity() {
		Gson gson = new Gson();
		return gson.fromJson(this.community, new TypeToken<List<Card>>(){}.getType());
	}

	public void setCommunity(List<Card> community) {
		Gson gson = new Gson();
		this.community = gson.toJson(community);
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public int getHousesAvailable() {
		return housesAvailable;
	}

	public void setHousesAvailable(int housesAvailable) {
		this.housesAvailable = housesAvailable;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public int getHotelsAvailable() {
		return hotelsAvailable;
	}

	public void setHotelsAvailable(int hotelsAvailable) {
		this.hotelsAvailable = hotelsAvailable;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public int getDieValue() {
		return dieValue;
	}

	public void setDieValue(int dieValue) {
		this.dieValue = dieValue;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public char getPlayerChoiceChar() {
		return playerChoiceChar;
	}

	public void setPlayerChoiceChar(char playerChoiceChar) {
		this.playerChoiceChar = playerChoiceChar;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public int getPlayerChoiceInt() {
		return playerChoiceInt;
	}

	public void setPlayerChoiceInt(int playerChoiceInt) {
		this.playerChoiceInt = playerChoiceInt;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Board</tt> object from the database.
	 * 
	 * @return the id for the <tt>Board</tt> in the database
	 */
	public char getTradeChoiceChar() {
		return tradeChoiceChar;
	}

	public void setTradeChoiceChar(char tradeChoiceChar) {
		this.tradeChoiceChar = tradeChoiceChar;
	}
}
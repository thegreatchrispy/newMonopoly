package com.newmonopoly.service;

import java.util.List;

import com.newmonopoly.model.Board;
import com.newmonopoly.model.Card;
import com.newmonopoly.model.Player;
import com.newmonopoly.model.Space;

public interface BoardService {
	// Initial functions - need to be run at start of game
	public List<Space> decideSeasonsAndOrder(Board board, boolean randomizeSet);

	// User input functions
	public int getUserInput(Board board);
	public char getCharInput(Board board);

	// Player movement functions
	public String movePlayer(Board board, Player player, int value);
	public String movePlayer(Board board, Player player, String spaceName);
	public String movePlayerToJail(Board board, Player player);
	public String moveToNearest(Board board, Player player, String type);
	public String moveToPosition(Board board, Player player, int position);

	// Player funds increase functions
	public void addFunds(Board board, Player player, int payment);
	public void takeFromPlayers(Board board, Player player, int payment);

	// Player funds decrease functions
	public void removeFunds(Board board, Player player, int payment);
	public void repairs(Board board, Player player, int housePrice, int hotelPrice);
	public void giveToPlayers(Board board, Player player, int payment);
	
	// Monopoly logic functions - DO NOT require user input
	public String performSpaceAction(Board board, Player player);
	public void transaction(Board board, Player player);
	//public void performSpaceAction(Board board, Player player, Space space);
	public String drawCard(Board board, Player player, String type);
	public void build(Board board, Player player, Space space);
	public void trade(Board board, Player player);
	public String addMonopolyAfterPurchase(Board board, Player player);
	public void findMonopolies(Board board, Player player);
	public void bankrupt(Board board, Player player);
	public int getPlayerIndex(Board boared, Player player);

	// Monopoly logic functions - DO require user input
	public String askToBuy(Board board, Player player);
	public String buy(Board board, Player player, Space space);
	public void chooseToBuy(Board board, Player player, Space space);
	public void auction(Board board, Space space);
	public void mortgage(Board board, Player player, int debt);
	public void unmortgage(Board board, Player player);
	public void playerDecision(Board board, Player player);
	public void displayOwnedProperties(Board board, Player player);
	public void displayPlayersWithProperties(Board board);

	// Game progression functions
	public Player getCurrentPlayer(Board board);
	public boolean hasWinner(Board board);
	public Player getWinner(Board board);
	public void nextTurn(Board board);

	// Space payment functions
	public String payRailroad(Board board, Player player, Player owner);
	public String payRent(Board board, Player player, Player owner);
	public String payTax(Board board, Player player, Space space);
	public String payUtility(Board board, Player player, Player owner);

	// Repository functions
	public void saveBoard(Board board);
	public Board findByGameId(int id);
	public void deleteBoard(Board board);
}
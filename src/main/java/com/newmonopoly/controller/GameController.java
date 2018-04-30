package com.newmonopoly.controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import com.newmonopoly.model.Board;
import com.newmonopoly.model.Card;
import com.newmonopoly.model.Player;
import com.newmonopoly.model.Space;
import com.newmonopoly.service.BoardService;

@RestController
public class GameController {
	@Autowired
	private BoardService boardService;

	/*
	 * hasWinner()
	 * getCurrentPlayer()
	 * getCharInput() -> getButtonInput()
	 * removeFunds()
	 * movePlayer(Player p, int v)
	 * playerDecision()
	 * nextTurn()
	 * movePlayerToJail()
	 */

	@RequestMapping("/creategame")
	public String save(@RequestParam("players") String jsonPlayers, @RequestParam("randomize") boolean randomize) {
		Gson gson = new Gson();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		Board board;

		try {
			players = gson.fromJson(jsonPlayers, new TypeToken<List<Player>>(){}.getType());

			if (players.isEmpty()) {
				throw new RuntimeException("save(): List of players is empty.");
			}

			board = new Board(players, randomize);
			spaces = boardService.decideSeasonsAndOrder(board, randomize);
			board.setSpaces(spaces);
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(players);
	}

	@RequestMapping("/joingame")
	public String join(@RequestParam("player") String jsonPlayer, @RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		Board board = new Board();

		try {
			player = gson.fromJson(jsonPlayer, Player.class);
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p == null) {
					p = player;
				}
			}

			board.setPlayers(players);
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Joining game.";
	}

	@RequestMapping("/retrievegame")
	public String retrieve(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();

		try {
			board = boardService.findByGameId(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(board);
	}

	@RequestMapping("/updategame")
	public void update(@RequestParam("board") String jsonBoard) {
		Gson gson = new Gson();
		Board board = new Board();

		try {
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deletegame")
	public String delete(@RequestParam("gameid") int id) {
		Board board = new Board();

		try {
			board = boardService.findByGameId(id);
			boardService.deleteBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Game " + id + " deleted.";
	}

	@RequestMapping("/removefunds")
	public String removeFunds(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("payment") int payment) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			boardService.removeFunds(board, player, payment);
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Funds removed from " + player.getName() + ".";
	}

	@RequestMapping("/addfunds")
	public String addFunds(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("payment") int payment) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			boardService.addFunds(board, player, payment);
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Funds received by " + player.getName() + ".";
	}

	@RequestMapping("/nextturn")
	public String nextTurn(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();

		try {
			board = boardService.findByGameId(id);
			boardService.nextTurn(board);
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return boardService.getCurrentPlayer(board) + "'s turn is beginning.";
	}

	@RequestMapping("/getnumberofplayers")
	public int getNumberOfPlayers(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		List<Player> players = new Vector<Player>();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return players.size();
	}

	@RequestMapping("/getnamesofplayers")
	public String getNamesOfPlayers(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		List<Player> players = new Vector<Player>();
		String names = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player player : players) {
				names += player.getName() + " ";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return names;
	}

	@RequestMapping("/moveplayer")
	public String movePlayer(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("value") int value) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			} 
			boardService.movePlayer(board, player, value);
			boardService.saveBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return player.getName() + " moved " + value + " spaces.";
	}

	/*
	 * NEEDS TO BE REWRITTEN
	 */
	// @RequestMapping("/moveplayertospacename")
	// public void movePlayer(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("space") String spaceName) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In movePlayer: Player player is null.");
	// 	}

	// 	for (Space space : board.getSpaces()) {
	// 		if (space.getName().equals(spaceName)) {
	// 			if (board.getSpaces().indexOf(space) < player.getCurrentPosition()) {
	// 				addFunds(board, player, 200);
	// 				System.out.println(player.getName() + " collected $200 for passing GO.");
	// 			}

	// 			player.setCurrentPosition(board.getSpaces().indexOf(space));
	// 			// Update: player
	// 			System.out.println(player.getName() + " moved to " + space.getName());
	// 		}
	// 	}

	// 	performSpaceAction(board, player, board.getSpaces().get(player.getCurrentPosition()));
	// }

	// @RequestMapping("/moveplayertojail")
	// public String movePlayerToJail(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
	// 	Gson gson = new Gson();
	// 	Board board = new Board();
	// 	Player player = new Player();
	// 	List<Player> players = new Vector<Player>();

	// 	try {
	// 		board = boardService.findByGameId(id);
	// 		players = board.getPlayers();

	// 		for (Player p : players) {
	// 			if (p.getName().equals(playerName)) {
	// 				player = p;
	// 				break;
	// 			}
	// 		}
	
	// 		boardService.movePlayerToJail(board, player);
	// 	}
	// 	catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

	// 	return player.getName() + " was moved to jail.";
	// }

	// /*
	//  * I DUNNO IF WE NEED THIS HERE OR NOT
	//  */
	// @RequestMapping("/movetonearest")
	// public void moveToNearest(Board board, Player player, String type) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In moveToNearest: Player player is null.");
	// 	}

	// 	if (type.isEmpty()) {
	// 		throw new IllegalArgumentException("In moveToNearest: String type is empty.");
	// 	}

	// 	boolean nearestFound = false;

	// 	for (int i = 1; i < 40 && !nearestFound; i++) {
	// 		if ((board.getSpaces().get((player.getCurrentPosition() + i) % 40).getType().equals(type))) {
	// 			if (player.getCurrentPosition() > (player.getCurrentPosition() + i) % 40) {
	// 				addFunds(board, player, 200);
	// 				System.out.println(player.getName() + " collected $200 for passing GO.");
	// 			}

	// 			player.setCurrentPosition((player.getCurrentPosition() + i) % 40);
	// 			// Update player
	// 			System.out.println(player.getName() + " landed on " + board.getSpaces().get(player.getCurrentPosition()).getName());
	// 			nearestFound = true;
	// 		}
	// 	}

	// 	performSpaceAction(board, player, board.getSpaces().get(player.getCurrentPosition()));
	// }

	// /*
	//  * I DUNNO IF WE NEED THIS HERE OR NOT
	//  */
	// @RequestMapping("/movetoposition")
	// public void moveToPosition(Board board, Player player, int position) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In moveToPosition: Player player is null.");
	// 	}

	// 	if (position < 0) {
	// 		player.setCurrentPosition(player.getCurrentPosition() + position);
	// 	}
	// 	else {
	// 		if (position < player.getCurrentPosition()) {
	// 			addFunds(board, player, 200);
	// 			System.out.println(player.getName() + " collected $200 for passing GO.");
	// 		}

	// 		player.setCurrentPosition(position);
	// 	}
	// 	// Update player
	// 	System.out.println(player.getName() + " moved to " + board.getSpaces().get(player.getCurrentPosition()).getName());
	// 	performSpaceAction(board, player, board.getSpaces().get(player.getCurrentPosition()));
	// }

	// @RequestMapping("/takefromplayers")
	// public void takeFromPlayers(Board board, Player player, int payment) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In takeFromPlayers: Player player is null.");
	// 	}

	// 	for (Player otherPlayer : board.getPlayers()) {
	// 		if (otherPlayer != null) {
	// 			removeFunds(board, otherPlayer, payment);
	// 			addFunds(board, player, payment);
	// 		}
	// 	}
	// }

	// @RequestMapping("/repairs")
	// public void repairs(Board board, Player player, int housePrice, int hotelPrice) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In repairs: Player player is null.");
	// 	}

	// 	for (Space space : board.getSpaces()){
	// 		if(space.getOwnedBy() == board.getPlayers().indexOf(player)){
	// 			if(space.getBuildings() == 5){
	// 				removeFunds(board, player, ((housePrice * 4) - hotelPrice));
	// 			}
	// 			else {
	// 				removeFunds(board, player, (housePrice * space.getBuildings()));
	// 			}
	// 		}
	// 	}
	// }

	// @RequestMapping("/givetoplayers")
	// public void giveToPlayers(Board board, Player player, int payment) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In giveToPlayers: Player player is null.");
	// 	}

	// 	for (Player otherPlayer : board.getPlayers()) {
	// 		if (otherPlayer != null) {
	// 			removeFunds(board, player, payment);
	// 			addFunds(board, otherPlayer, payment);
	// 		}
	// 	}
	// }

	// @RequestMapping("/transaction")
	// public void transaction(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In transaction: Player player is null.");
	// 	}

	// 	performSpaceAction(board, player, board.getSpaces().get(player.getCurrentPosition()));

	// 	if (player.getMoney() <= 0) {
	// 		board.setTurnOver(true);
	// 		// Update turnOver
	// 		// End turn called here.
	// 	}

	// 	while (!board.getTurnOver()) {
	// 		playerDecision(board, player);
	// 	}
	// }

	// @RequestMapping("/performspaceaction")
	// public void performSpaceAction(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In performSpaceAction: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In performSpaceAction: Space space is null.");
	// 	}
		
	// 	switch (space.getType()) {
	// 		case "gotojail":
	// 			movePlayerToJail(board, player);
	// 			break;
	// 		case "property":
	// 			if ((space.getOwnedBy() != (board.getPlayerIndex())) && space.getOwnedBy() > -1) {
	// 				if (!space.isMortgaged()) {
	// 					payRent(board, player, space);					
	// 				}
	// 				else {
	// 					System.out.println(space.getName() + " is currently mortgaged.");
	// 				}
	// 			}
	// 			else if (space.getOwnedBy() == board.getPlayerIndex()) {
	// 				break;
	// 			}
	// 			else {
	// 				if((player.getMoney() - space.getPrice()) >= 0) {
	// 					chooseToBuy(board, player, space);
	// 				}
	// 				else {
	// 					System.out.println("You cannot buy this property!");
	// 					break;
	// 				}
	// 			}
	// 			break;
	// 		case "railroad":
	// 			if ((space.getOwnedBy() != (board.getPlayerIndex())) && space.getOwnedBy() > -1) {
	// 				if (!space.isMortgaged()) {
	// 					payRailroad(board, player, space);
	// 				}
	// 				else {
	// 					System.out.println(space.getName() + " is currently mortgaged.");
	// 				}
	// 			}
	// 			else if (space.getOwnedBy() == board.getPlayerIndex()) {
	// 				break;
	// 			} 
	// 			else {
	// 				chooseToBuy(board, player, space);
	// 			}
	// 			break;
	// 		case "utility":
	// 			if ((space.getOwnedBy() != (board.getPlayerIndex())) && space.getOwnedBy() > -1) {
	// 				if(!space.isMortgaged()) {	
	// 					payUtility(board, player, space);
	// 				}
	// 				else {
	// 					System.out.println(space.getName() + " is currently mortgaged.");
	// 				}
	// 			}
	// 			else if (space.getOwnedBy() == board.getPlayerIndex()) {
	// 				break;
	// 			}
	// 			else {
	// 				chooseToBuy(board, player, space);
	// 			}
	// 			break;
	// 		case "tax":
	// 			payTax(board, player, space);
	// 			break;
	// 		case "comchest":
	// 			drawCard(board, player, board.getCommunity());
	// 			break;
	// 		case "chance":
	// 			drawCard(board, player, board.getChance());
	// 			break;
	// 		default:
	// 			break;
	// 	}
	// }

	// @RequestMapping("/drawcard")
	// public void drawCard(Board board, Player player, List<Card> cards) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In drawCard: Player player is null.");
	// 	}

	// 	if (cards.isEmpty()) {
	// 		throw new IllegalArgumentException("In drawCard: List<Card> cards is empty.");
	// 	}

	// 	Card card = cards.get(0);
	// 	System.out.println(player.getName() + " got the " + card.getTitle() + " card.");
	// 	cards.add(cards.remove(0));
	// 	switch (card.getAction()) {
	// 		case "move":
	// 			if(card.getPosition() != 0){
	// 				moveToPosition(board, player, card.getPosition());
	// 			} else{
	// 				movePlayer(board, player, card.getSpaceName());
	// 			}
	// 			break;
	// 		case "movenearest":
	// 			moveToNearest(board, player, card.getType());
	// 			break;
	// 		case "addfunds":
	// 			addFunds(board, player, card.getPayment());
	// 			break;
	// 		case "removefunds":
	// 			removeFunds(board, player, card.getPayment());
	// 			break;
	// 		case "repairs":
	// 			repairs(board, player, card.getHouses(), card.getHotels());
	// 			break;
	// 		case "givetoplayers":
	// 			giveToPlayers(board, player, card.getPayment());
	// 			break;
	// 		case "takefromplayers":
	// 			takeFromPlayers(board, player, card.getPayment());
	// 			break;
	// 		case "gotojail":
	// 			movePlayerToJail(board, player);
	// 			break;
	// 		case "outjail":
	// 			player.setJailCard(true);
	// 			break;
	// 		default:
	// 			break;
	// 	}
	// 	// Call a new Card object dependent on the type.
	// }

	// @RequestMapping("/build")
	// public void build(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In build: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In build: Space space is null.");
	// 	}

	// 	System.out.println(space.getName() + " currently has " + space.getBuildings() + " houses.");
	// 	boolean valid = true;
	// 	int maxHouses = 0;

	// 	for (Space otherSpace : player.getOwnedProperties()) {
	// 		if (otherSpace.getGroup() == space.getGroup() && otherSpace != space) {
	// 			if (otherSpace.getBuildings() > maxHouses) {
	// 				maxHouses = otherSpace.getBuildings();
	// 			}
	// 		}
	// 	}
	// 	do {
	// 		System.out.print("How many more houses would you like to add? ");
	// 		int additions = getUserInput(board);
	// 		if (additions + space.getBuildings() > maxHouses + 1) {
	// 			valid = false;
	// 			System.out.println("You need to even build! Please try again.");
	// 		}
	// 		else if (additions + space.getBuildings() > 5) {
	// 			valid = false;
	// 			System.out.println("You entered an invalid amount of houses! Please try again.");
	// 		}
	// 		else if (player.getMoney() - additions*space.getHouseCost() < 0) {
	// 			valid = false;
	// 			System.out.println("You cannot afford to purchase these houses! Please try again.");
	// 		}
	// 		else if (additions == -1) {
	// 			break;
	// 		}
	// 		else {
	// 			valid = true;
	// 			removeFunds(board, player, additions*space.getHouseCost());
	// 			space.setBuildings(space.getBuildings() + additions);
	// 			if (space.getBuildings() == 5) {
	// 				board.setHousesAvailable(board.getHousesAvailable() + 4);
	// 				board.setHotelsAvailable(board.getHotelsAvailable() - 1);
	// 			}
	// 			else {
	// 				board.setHousesAvailable(board.getHousesAvailable() - additions);
	// 			}
	// 		}
	// 	} while (!valid);

	// 	// Update player, space, housesAvailable, hotelsAvailable
	// }

	// @RequestMapping("/trade")
	// public void trade(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In trade: Player player is null.");
	// 	}

	// 	// Currently a player may only trade one property for one other property
	// 	displayPlayersWithProperties(board);
	// 	int choice;
	// 	int myChoice;    // Represents which property to give away
	// 	int tradeChoice; // Represents which property to take
	// 	Player tradePlayer;
	// 	char approvalChoice;
	// 	boolean approved = false;
	// 	boolean ended = false;

	// 	do {
	// 		choice = getUserInput(board);
	// 	} while (choice == board.getPlayerIndex() + 1 || board.getPlayers().get(choice - 1) == null);

	// 	tradePlayer = board.getPlayers().get(choice  - 1);

	// 	while (!approved && !ended) {
	// 		displayOwnedProperties(board, player);
	// 		System.out.println("Select your property to offer:");
			
	// 		do {
	// 			myChoice = getUserInput(board);				
	// 		} while (player.getOwnedProperties().get(myChoice -1).isMortgaged());
			 
	// 		displayOwnedProperties(board, tradePlayer);
	// 		System.out.println("Select the property you want:");

	// 		do {
	// 			tradeChoice = getUserInput(board);			
	// 		} while (tradePlayer.getOwnedProperties().get(tradeChoice -1).isMortgaged());

	// 		System.out.println("Does other player approve? Y/N, X to exit ");
	// 		approvalChoice = getCharInput(board);

	// 		if (approvalChoice == 'Y' || approvalChoice == 'y') {
	// 			player.addOwnedProperties(tradePlayer.getOwnedProperties().get(tradeChoice - 1));
	// 			tradePlayer.addOwnedProperties(player.getOwnedProperties().get(myChoice - 1));
	// 			player.removeOwnedProperties(myChoice - 1);
	// 			tradePlayer.removeOwnedProperties(tradeChoice - 1);
	// 			player.getOwnedProperties().get(player.getOwnedProperties().size()-1).setOwnedBy(board.getPlayers().indexOf(player));
	// 			tradePlayer.getOwnedProperties().get(tradePlayer.getOwnedProperties().size()-1).setOwnedBy(board.getPlayers().indexOf(tradePlayer));
	// 			addMonopoly(board, player, player.getOwnedProperties().get(player.getOwnedProperties().size()-1));
	// 			addMonopoly(board, tradePlayer, tradePlayer.getOwnedProperties().get(tradePlayer.getOwnedProperties().size()-1));
	// 			approved = true;
	// 			// Update player, space
	// 		}
	// 		else if (approvalChoice == 'X' || approvalChoice == 'x') {
	// 			ended = true;
	// 		}
	// 	}
	// }

	// // Checks if player has new monopolies.
	// @RequestMapping("/addmonopoly")
	// public void addMonopoly(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In addMonopoly: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In addMonopoly: Space space is null.");
	// 	}

	// 	boolean newMonopoly = true;

	// 	if (space.getType().equals("railroad") || space.getType().equals("utility")) {
	// 		newMonopoly = false;
	// 	}
	// 	else {
	// 		for (Space s : board.getSpaces()) {
	// 			if (s.getGroup() == space.getGroup()) {
	// 				if(s.getType().equals("property")) {
	// 					if (s.getOwnedBy() != board.getPlayers().indexOf(player)) {
	// 						newMonopoly = false;
	// 						break;
	// 					}
	// 				}
	// 			}
	// 		}
	// 	}

	// 	if (newMonopoly) {
	// 		System.out.println(player.getName() + " has a monopoly on group " + space.getGroup());
	// 		player.addMonopolyGroup(space.getGroup()-1);

	// 		for (Space s : board.getSpaces()) {
	// 			if (s.getGroup() == space.getGroup()) {
	// 				if (s.getType().equals("property")) {
	// 					s.setCurrentRent(s.getRent() * 2);
	// 					player.addMonopolyProperties(s);
	// 				}
	// 			}
	// 		}
	// 	}
	// }

	// // Finds and prints the list of player's monopolies.
	// // Display only
	// @RequestMapping("/findmonopolies")
	// public void findMonopolies(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In findMonopolies: Player player is null.");
	// 	}

	// 	int total = 0;
	// 	int i = 0;
	// 	Collections.sort(player.getMonopolyProperties());
	// 	System.out.println("#    Property Name           Group    Bldg No.    Bldg Val    Mortgage    Total    isMortgaged");

	// 	for (Space space : player.getMonopolyProperties()) {
	// 		total = (space.getPrice() / 2) + (space.getBuildings() * space.getHouseCost() / 2);
	// 		System.out.printf("%d    %-20s      %d         %d         $%-5d      $%-5d     $%-5d        %b\n",++i ,space.getName(), space.getGroup(), space.getBuildings(), (space.getHouseCost() / 2), (space.getPrice() / 2), total, space.isMortgaged());
	// 	}
	// }

	// @RequestMapping("/bankrupt")
	// public void bankrupt(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In bankrupt: Player player is null.");
	// 	}

	// 	int i = 0;
	// 	System.out.println(player.getName() + " declared bankruptcy. Their owned properties will be auctioned.");
	// 	displayOwnedProperties(board, player);
	// 	System.out.println("Beginning auctions...");
	// 	player.setMoney(0);
	// 	// Update player
	// 	for (Space space : player.getOwnedProperties()) {
	// 		space.setMortgaged(false);
	// 		// Update space
	// 		auction(board, space);
	// 	}

	// 	board.getPlayers().set(board.getPlayerIndex(), null);
	// 	// Update players
	// 	System.out.println("\nPlayers remaining:");

	// 	for (Player p : board.getPlayers()) {
	// 		if (p != null) {
	// 			System.out.println("\t" + ++i + ". " + p.getName());
	// 		}
	// 	}
	// }

	// @RequestMapping("/choosetobuy")
	// public void chooseToBuy(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In chooseToBuy: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In chooseToBuy: Space space is null.");
	// 	}

	// 	System.out.print("Would you like to purchase " + space.getName() + " for $" + space.getPrice() + "? Y/N ");
	// 	char choice = getCharInput(board);
		
	// 	if (choice == 'Y' || choice == 'y') {
	// 		if (player.getMoney() - space.getPrice() > 0) {
	// 			System.out.println(player.getName() + " purchased " + space.getName());
	// 			removeFunds(board, player, space.getPrice()); // Player buys property.
	// 			player.addOwnedProperties(space); // Add property to board.getPlayers() Owned Properties list.
	// 			space.setOwnedBy(board.getPlayers().indexOf(player));
	// 			addMonopoly(board, player, space);
	// 		}
	// 		else {
	// 			System.out.println("You can't afford " + space.getName());
	// 		}
	// 	}
	// 	// Player does not want property.
	// 	else {
	// 		auction(board, space); // All players eligible for auction.
	// 	}
	// }

	// @RequestMapping("/auction")
	// public void auction(Board board, Space space) {
	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In payUtility: Space space is null.");
	// 	}
	// 	System.out.println(space.getName() + " original sale price is $" + space.getPrice());
	// 	List<Integer> offers = new Vector<Integer>(Collections.nCopies(board.getPlayers().size(), 0));
	// 	List<Boolean> stillInAuction = new Vector<Boolean>(Collections.nCopies(board.getPlayers().size(), true));
	// 	int offer = 0;
	// 	int count = 0;
	// 	int max = 0;
	// 	int index = -1;

	// 	for (int i = 0; i < board.getPlayers().size(); i++){
	// 		do {
	// 			if (board.getPlayers().get(i).getMoney() > 0) {
	// 				System.out.println(board.getPlayers().get(i).getName() + ", enter your bid or -1 to give up:");
	// 				offer = getUserInput(board);
	// 			}
	// 			else {
	// 				offer = -1;
	// 				continue;
	// 			}
	// 		} while (board.getPlayers().get(i).getMoney() - offer <= 0 || offers.contains(offer) || (offer <= Collections.max(offers) && offer > -1));

	// 		if (offer == -1) {
	// 			stillInAuction.set(i, false);
	// 		} else {
	// 			offers.set(i, offer);
	// 		}
	// 	}

	// 	do {
	// 		for (int i = 0; i < board.getPlayers().size(); i++) {
	// 			if (stillInAuction.get(i) && (Collections.frequency(stillInAuction, false) != (board.getPlayers().size()-1))) {
	// 				do {
	// 					System.out.println(board.getPlayers().get(i).getName() + ", enter your bid or -1 to give up:");
	// 					offer = getUserInput(board);

	// 				} while (board.getPlayers().get(i).getMoney() - offer <= 0 || offers.contains(offer) || (offer <= Collections.max(offers) && offer > -1));

	// 				if (offer == -1) {
	// 					stillInAuction.set(i, false);
	// 				} else {
	// 					offers.set(i, offer);
	// 				}
	// 			}

	// 			offer = 0;
	// 		}

	// 		count++;
	// 	} while ( count < 2 );

	// 	max = Collections.max(offers);
	// 	index = offers.indexOf(max);
	// 	removeFunds(board, board.getPlayers().get(index), max); // Player buys property.
	// 	board.getPlayers().get(index).addOwnedProperties(space); // Add property to players Owned Properties list.
	// 	space.setOwnedBy(index);
	// 	System.out.println(board.getPlayers().get(index).getName() + " has won the auction.");
	// 	addMonopoly(board, board.getPlayers().get(index), space);
	// 	// Update: player who won auction
	// }

	// @RequestMapping("/mortgage")
	// public void mortgage(Board board, Player player, int debt) {
	// 	// NOTE: ADD WAY TO IDENTIFY WHICH ONE IS MORTGAGE
	// 	// Currently, selling a property includes selling all of the 
	// 	// houses and hotels on that property. This will need to be
	// 	// fixed in the future.
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In mortgage: Player player is null.");
	// 	}

	// 	Space property;
	// 	boolean allMortgage = false;
	// 	int choice = -1;

	// 	while (debt <= 0 && !allMortgage) {
	// 		for (Space space : player.getOwnedProperties()) {
	// 			if (!space.isMortgaged()) {
	// 				allMortgage = false;
	// 				break;
	// 			}
	// 			else {
	// 				allMortgage = true;
	// 			}
	// 		}

	// 		if (allMortgage) {
	// 			break;
	// 		}

	// 		System.out.println("Debt is currently at: $" + debt);
	// 		displayOwnedProperties(board, player);
	// 		System.out.println("Which property would you like to sell?");

	// 		try {
	// 			do {
	// 				choice = getUserInput(board);
	// 			} while(player.getOwnedProperties().get(choice -1 ).isMortgaged());
	// 		}
	// 		catch (IllegalArgumentException e) {
	// 			e.printStackTrace();
	// 		}

	// 		if (choice > 0) {
	// 			property = player.getOwnedProperties().get(choice-1);
	// 			addFunds(board, player, (debt + (property.getPrice() / 2) + (property.getBuildings() * property.getHouseCost() / 2)));
	// 			debt += (property.getPrice() / 2) + (property.getBuildings() * property.getHouseCost() / 2);

	// 			if (property.getBuildings() == 5) {
	// 				board.setHotelsAvailable(board.getHotelsAvailable() + 1);
	// 			}
	// 			else {
	// 				board.setHousesAvailable(board.getHousesAvailable() + property.getBuildings());
	// 			}

	// 			player.getOwnedProperties().get(choice-1).setMortgaged(false);
	// 			// Updates spaces, player, hotelsAvailable, housesAvailable
	// 		}
	// 	}

	// 	if (allMortgage && debt <= 0) {
	// 		bankrupt(board, player);
	// 	}
	// }

	// @RequestMapping("/unmortgage")
	// public void unmortgage(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In unmortgage: Player player is null.");
	// 	}

	// 	displayOwnedProperties(board, player);
	// 	System.out.println("Which property would you like to pay off?");
	// 	int choice = 0;
	// 	Space property;

	// 		try {
	// 			do {
	// 				choice = getUserInput(board);
	// 			} while(!player.getOwnedProperties().get(choice -1 ).isMortgaged());
	// 		}
	// 		catch (IllegalArgumentException e) {
	// 			e.printStackTrace();
	// 		}

	// 		if (choice > 0) {
	// 			property = player.getOwnedProperties().get(choice-1);
	// 			removeFunds(board, player, (int) ((property.getPrice() / 2) * 1.1));
	// 			player.getOwnedProperties().get(choice-1).setMortgaged(true);
	// 			System.out.println(property.getName() + " is now paid off!");
	// 			// Update player, space
	// 		}
	// }

	// @RequestMapping("/playerdecision")
	// public void playerDecision(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In playerDecision: Player player is null.");
	// 	}

	// 	// Build improvements, trade, or end turn.
	// 	System.out.println("\n/---------------------------------------\\");
	// 	System.out.println("| Commands:                               |");
	// 	System.out.println("| B: Build        T: Trade    D: Display  |");
	// 	System.out.println("| P: Pay Mortgage Q: Quit     E: End Turn |");
	// 	System.out.println("\\-----------------------------------------/\n");
	// 	char ans = getCharInput(board);
	// 	boolean hasMortgaged = false;

	// 	if(ans == 'B') { // Build Improvements
	// 		if (player.getMonopolyGroups().contains(1)) {
	// 			findMonopolies(board, player);
	// 			System.out.print("What property from the above list would you like to improve? ");
	// 			int choice = getUserInput(board);

	// 			if (player.getMonopolyProperties().get(choice - 1).getBuildings() < 5 && !player.getMonopolyProperties().get(choice -1).isMortgaged()) {
	// 				build(board, player, player.getMonopolyProperties().get(choice - 1));
	// 			} 
	// 			else {
	// 				System.out.println("You cannot build anymore on this property!");
	// 			}
	// 		}
	// 		else {
	// 			System.out.println("You have no monopolies to build on.");
	// 		}
	// 	}
	// 	else if(ans == 'T') {	// Trade
	// 		if (player.getOwnedProperties().size() > 0) {
	// 			trade(board, player);
	// 		}
	// 		else {
	// 			System.out.println("You have no properties to trade.");
	// 		}
	// 	}
	// 	else if (ans == 'D') {
	// 		System.out.println(player.toString());
	// 		displayOwnedProperties(board, player);
	// 	}
	// 	else if (ans == 'P') {
	// 		for (Space space : player.getOwnedProperties()) {
	// 			if (space.isMortgaged()) {
	// 				hasMortgaged = true;
	// 				unmortgage(board, player);
	// 				break;
	// 			}
	// 		}
	// 		if (!hasMortgaged) {
	// 			System.out.println("You have no mortgaged properties..");
	// 		}
	// 	}
	// 	else if (ans == 'Q') {
	// 		bankrupt(board, player);
	// 		board.setTurnOver(true);
	// 		// update turn over
	// 	}
	// 	else {	// End turn.
	// 		board.setTurnOver(true);
	// 		// update turn over
	// 	}
	// }

	// // Display only
	// @RequestMapping("/displayownedproperties")
	// public void displayOwnedProperties(Board board, Player player) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In displayOwnedProperties: Player player is null.");
	// 	}

	// 	int total;
	// 	int i = 0;

	// 	if (player.getOwnedProperties().size() > 0) {
	// 		Collections.sort(player.getOwnedProperties());
	// 		System.out.println(player.getName() + " owns these properties:");
	// 		System.out.println("#    Property Name           Group    Bldg No.    Bldg Val    Mortgage    Total    isMortgaged");

	// 		for (Space space : player.getOwnedProperties()) {
	// 			total = (space.getPrice() / 2) + (space.getBuildings() * space.getHouseCost() / 2);
	// 			System.out.printf("%d    %-20s      %d         %d         $%-5d      $%-5d     $%-5d        %b\n",++i ,space.getName(), space.getGroup(), space.getBuildings(), (space.getHouseCost() / 2), (space.getPrice() / 2), total, space.isMortgaged());
	// 		}
	// 	}
	// 	else {
	// 		System.out.println(player.getName() + " owns no properties.");
	// 	}
	// }

	// // Display only
	// @RequestMapping("/displayplayerswithproperties")
	// public void displayPlayersWithProperties(Board board) {
	// 	int i = 0;
	// 	System.out.println("The following players own properties:");
	// 	System.out.println("#    Player Name         No. of Properties");

	// 	for (Player player : board.getPlayers()) {
	// 		i++;
	// 		if (player != null) {
	// 			if (player == getCurrentPlayer(board)) {
	// 				continue;
	// 			}
	// 			else if (player.getOwnedProperties().size() > 0) {
	// 				System.out.printf("%d    %-16s            %d\n", i, player.getName(), player.getOwnedProperties().size());
	// 			}
	// 		}
	// 	}
	// }

	// @RequestMapping("/haswinner")
	// public String hasWinner(@RequestParam("gameid") int id) {
	// 	Gson gson = new Gson();
	// 	Board board = new Board();
	// 	boolean winner = false;

	// 	try {
	// 		board = boardService.findByGameId(id);
	// 		winner = boardService.hasWinner(board);
	// 	}
	// 	catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

	// 	return gson.toJson(winner);
	// }

	// @RequestMapping("/getcurrentplayer")
	// public String getCurrentPlayer(@RequestParam("gameid") int id) {
	// 	Gson gson = new Gson();
	// 	Board board = new Board();
	// 	Player currentPlayer = new Player();

	// 	try {
	// 		board = boardService.findByGameId(id);
	// 		currentPlayer = boardService.getCurrentPlayer(board);
	// 	}
	// 	catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

	// 	return gson.toJson(currentPlayer);
	// }
	
	// // Return the winner of the game.
	// @RequestMapping("/getwinner")
	// public Player getWinner(Board board) {
	// 	return getCurrentPlayer(board);
	// }

	// @RequestMapping("/payrailroad")
	// public void payRailroad(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In payRailroad: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In payRailroad: Space space is null.");
	// 	}

	// 	int payment = 25;
	// 	int index = space.getOwnedBy();

	// 	for (int i = 0; i < 4; i++) {
	// 		if (index == board.getSpaces().get(10*i + 5).getOwnedBy()) {
	// 			payment *= 2;
	// 		}
	// 	}

	// 	removeFunds(board, player, payment);
	// 	addFunds(board, board.getPlayers().get(index), payment);
	// }

	// @RequestMapping("/payrent")
	// public void payRent(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In payRent: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In payRent: Space space is null.");
	// 	}

	// 	// Rent will be changed in database in the build() method
	// 	int rent = space.getCurrentRent();
	// 	int index = space.getOwnedBy();
	// 	removeFunds(board, player, rent);
	// 	addFunds(board, board.getPlayers().get(index), rent);
	// }

	// @RequestMapping("/paytax")
	// public void payTax(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In payTax: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In payTax: Space space is null.");
	// 	}

	// 	if(space.getName().equals("Luxury Tax")){
	// 		addFunds(board, player, space.getPrice());
	// 	} else {
	// 		removeFunds(board, player, Math.min(200,(player.getMoney()/10)));
	// 	}
	// }

	// @RequestMapping("/payutility")
	// public void payUtility(Board board, Player player, Space space) {
	// 	if (player == null) {
	// 		throw new IllegalArgumentException("In payUtility: Player player is null.");
	// 	}

	// 	if (space == null) {
	// 		throw new IllegalArgumentException("In payUtility: Space space is null.");
	// 	}

	// 	int initialPayment;
	// 	boolean waterWorks = false;
	// 	boolean electricCompany = false;
	// 	List<Space> properties = board.getPlayers().get(space.getOwnedBy()).getOwnedProperties();

	// 	for (Space utility : board.getSpaces()) {
	// 		if (utility.getName().equals("Water Works")) {
	// 			if(properties.contains(utility))
	// 				waterWorks = true;
	// 		}
	// 		if (utility.getName().equals("Electric Company")) {
	// 			if(properties.contains(utility))
	// 				electricCompany = true;
	// 		}
	// 	}

	// 	if(waterWorks && electricCompany){
	// 		initialPayment=10;
	// 	}else {
	// 		initialPayment=4;
	// 	}
	// 	removeFunds(board, player, (initialPayment * board.getDieValue()));
	// 	addFunds(board, board.getPlayers().get(space.getOwnedBy()), (initialPayment * board.getDieValue()));
	// }
}
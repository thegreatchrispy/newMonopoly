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

	 /* Create, join, retrieve, update, and delete game. */
	@RequestMapping("/creategame")
	public long save(@RequestParam("players") String jsonPlayers, @RequestParam("randomize") boolean randomize) {
		Gson gson = new Gson();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		List<Card> community = new Vector<Card>();
		List<Card> chance = new Vector<Card>();
		Board board;
		long id = -1;

		try {
			players = gson.fromJson(jsonPlayers, new TypeToken<List<Player>>(){}.getType());

			if (players.isEmpty()) {
				throw new RuntimeException("save(): List of players is empty.");
			}

			board = new Board(players, false);
			spaces = boardService.decideSeasonsAndOrder(board, false);
			community = board.getCommunity();
			chance = board.getChance();

			board.setSpaces(spaces);

			boardService.saveBoard(board);
			id = board.getId();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return id;
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

	/* Accessor Methods. */

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

	@RequestMapping("/getrandomized")
	public boolean isRandomized(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		List<Player> players = new Vector<Player>();
		boolean status = false;

		try {
			board = boardService.findByGameId(id);
			status = board.isRandomized();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping("/getswappedstring")
	public String getSwappedString(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		List<Player> players = new Vector<Player>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			string = board.getSwappedString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return string;
	}

	@RequestMapping("/getdoublescount")
	public int getDoubles(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		int count = 0;

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			count = player.getDoublesCount();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	@RequestMapping("/getmoney")
	public int getMoney(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		int money = 0;

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			money = player.getMoney();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return money;
	}

	@RequestMapping("/getposition")
	public int getPosition(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();

		int position = -1;

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}
			position = player.getCurrentPosition(); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return position;
	}

	@RequestMapping("/getinjail")
	public String getInJail(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
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

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return player.getInJail() + ";" + player.getJailCard() + ";" + player.getJailTime();
	}

	@RequestMapping("/getbuildstatus")
	public boolean getBuildStatus(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
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
			//
			player.addMonopolyGroup(7);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return player.getMonopolyGroups().contains(1);
	}

	@RequestMapping("/getmonopolygroups")
	public String getMonopolyGroups(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}
			for (int i : player.getMonopolyGroups()) {
				string += i + ";";
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return string;
	}

	@RequestMapping("/getbuildinginfo")
	public String getBuildingInfo(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("group") int group) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();

		int numberOfProperties = 0;
		String string = "";
		String space_string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			for (Space space : player.getMonopolyProperties()) {
				if(space.getGroup() == group) {
					spaces.add(space);
					numberOfProperties++;
				}
			}
			
			string += numberOfProperties + ":" + board.getHotelsAvailable() + ":" + board.getHousesAvailable() + ":";

			for (Space space : spaces) {
				space_string = space.getName() + "," + space.getBuildings() + "," + space.getHouseCost() + "," + (space.getHouseCost() / 2) + ";";
				string += space_string;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return string;
	}

	@RequestMapping("/gettradestatus")
	public boolean getTradeStatus(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		boolean status = false;

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getOwnedProperties().size() > 0) {
					status = true;
					break;
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping("/getmortgagestatus")
	public boolean getMortgageStatus(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		boolean status = false;

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			status = (player.getOwnedProperties().size() > 0);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping("/getmortgageinfo")
	public String getMortgageInfo(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();

		String string = "";
		String temp = "";
		boolean groupHasBuildings = false;
		int mortgageValue = 0;
		int loanAmount = 0;
		int groupHasNoBuildings[] = {1, 1, 1, 1, 1, 1, 1, 1};

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			int player_index = 0;
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
				player_index++;
			}

			// Find groups that have buildings.
			for (int i = 1; i < 9; i++) {
				for (Space space : player.getOwnedProperties()) {
					if (space.getGroup() == i) {
						if (space.getBuildings() != 0) {
							groupHasNoBuildings[i-1] = 0;
						}
					}
				}
			}

			// Unmortgaged.
			for (int i = 1; i < 9; i++) {
				for (Space space : player.getOwnedProperties()) {
					if (space.getGroup() == i) {
						if (groupHasNoBuildings[i-1] == 1) {
							if (!space.isMortgaged()) {
								mortgageValue = space.getPrice() / 2;
								loanAmount = (int)(mortgageValue * 1.1);
								temp += space.getName() + "," + mortgageValue + "," + loanAmount + ";";
							}
						}
					}
				}
			}

			string += temp + ":";

			// // Unmortgaged
			// int property_index = 0;
			// for (int i = 1; i < 9; i++) {
			// 	for (Space space : spaces) {
			// 		if (space.getOwnedBy() == player_index) {
			// 			if (space.getGroup() == i) {
			// 				if (!groupHasBuildings) {
			// 					if (!space.isMortgaged()) {
			// 						if (space.getBuildings() == 0) {
			// 							mortgageValue = space.getPrice() / 2;
			// 							loanAmount = (int)(mortgageValue * 1.1);
			// 							temp += space.getName() + "," + mortgageValue + "," + loanAmount + "," + property_index + ";";
			// 							property_index++;
			// 						} else {
			// 							groupHasBuildings = true;
			// 							temp = "";
			// 							break;
			// 						}
			// 					}
			// 				}
			// 			}
			// 		}
			// 	}
			// 	string += temp;
			// 	groupHasBuildings = false;
			// }

			// string += ":";

			for (int i = 1; i < 9; i++) {
				for (Space space : player.getOwnedProperties()) {
					if (space.getGroup() == i) {
						if (space.isMortgaged()) {
							mortgageValue = space.getPrice() / 2;
							loanAmount = (int)(mortgageValue * 1.1);
							string += space.getName() + "," + mortgageValue + "," + loanAmount + ";";
						}
					}
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return string + ":" + player.getMoney();
	}
	

	@RequestMapping("/getownedproperties")
	public String getOwnedProperties(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			for (Space space : player.getOwnedProperties()) {
				string += space.getName() + " " + space.getPrice() + ";";
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return string;
	}

	/* Mutator Methods. */

	@RequestMapping("/moveplayertojail")
	public String movePlayerToJail(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			} 
			string = boardService.movePlayerToJail(board, player);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		board.setPlayers(players);
		boardService.saveBoard(board);
		return string;
	}

	@RequestMapping("/incrementdoubles")
	public String incrementDoubles(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			} 
			player.setDoublesCount(player.getDoublesCount() + 1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		board.setPlayers(players);
		boardService.saveBoard(board);
		return player.getName() + " rolled doubles!";
	}

	@RequestMapping("/decrementjailtime")
	public String decrementJailTime(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		player.setJailTime(player.getJailTime() - 1);
		board.setPlayers(players);
		boardService.saveBoard(board);

		return player.getName() + " has " + player.getJailTime() + " more turn(s) left in jail.";
	}

	@RequestMapping("/setdoubles")
	public String setDoubles(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			} 
			player.setDoublesCount(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		board.setPlayers(players);
		boardService.saveBoard(board);
		return player.getName() + " rolled doubles!";
	}

	@RequestMapping("/getoutofjailfree")
	public String getOutOfJailFree(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
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

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		player.setInJail(false);
		player.setJailCard(false);
		player.setJailTime(0);
		board.setPlayers(players);
		boardService.saveBoard(board);

		return player.getName() + " has been busted out of jail!";
	}

	@RequestMapping("/getoutofjail")
	public String getOutOfJail(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
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
			boardService.removeFunds(board, player, 50);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		player.setInJail(false);
		player.setJailCard(false);
		player.setJailTime(0);
		board.setPlayers(players);
		boardService.saveBoard(board);

		return player.getName() + " has been busted out of jail for $50!";
	}

	/* Logical Methods. */
	@RequestMapping("/performspaceaction")
	public String performSpaceAction(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		List<Card> community = new Vector<Card>();
		List<Card> chance = new Vector<Card>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();
			community = board.getCommunity();
			chance = board.getChance();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			string = boardService.performSpaceAction(board, player);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);

		return string;
	}

	@RequestMapping("/moveplayer")
	public String movePlayer(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("value") int value) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			} 
			string = boardService.movePlayer(board, player, value);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		board.setPlayers(players);
		boardService.saveBoard(board);
		return string;
	}

	@RequestMapping("/acceptpurchase")
	public String buy(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		Space space = new Space();
		List<Space> spaces = new Vector<Space>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					space = spaces.get(player.getCurrentPosition());
					break;
				}
			}
			string = boardService.buy(board, player, space);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		board.setSpaces(spaces);
		board.setPlayers(players);
		boardService.saveBoard(board);
		return string;
	}

	@RequestMapping("/auctionpurchase")
	public String buyAuction(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("winner") String winnerName, @RequestParam("amount") int bid) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		Player winner = new Player();
		List<Player> players = new Vector<Player>();
		Space space = new Space();
		List<Space> spaces = new Vector<Space>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					space = spaces.get(player.getCurrentPosition());
				}
				if (p.getName().equals(winnerName)) {
					winner = p;
				}
			}
			string = boardService.buyAuction(board, winner, space, bid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		board.setSpaces(spaces);
		board.setPlayers(players);
		boardService.saveBoard(board);
		return string;
	}

	@RequestMapping("/addmonopolyafterpurchase")
	public String addMonopolyAfterPurchase(@RequestParam("gameid") int id, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			string = boardService.addMonopolyAfterPurchase(board, player);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);

		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);

		return string;
	}

	@RequestMapping("/addmonopolyafterauction")
	public String addMonopolyAfterAuction(@RequestParam("gameid") int id, @RequestParam("indexPlayer") String indexName, @RequestParam("player") String playerName) {
		Gson gson = new Gson();
		Board board = new Board();
		Player index = new Player();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		String string = "";

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();

			for (Player p : players) {
				if (p.getName().equals(indexName)) {
					index = p;
				}
				if (p.getName().equals(playerName)) {
					player = p;
				}
			}

			string = boardService.addMonopolyAfterAuction(board, index, player);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);

		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);

		return string;
	}

	@RequestMapping("/purchasebuildings")
	public void purchaseBuildings(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("space") String spaceName, @RequestParam("buildings") int buildings) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		Space space = new Space();
		int balance = 0;

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}
			int monopoly_index = 0;
			for (Space s : player.getMonopolyProperties()) {
				if (s.getName().equals(spaceName)) {
					space = s;
					break;
				}
				monopoly_index++;
			}

			int space_index = 0;
			for (Space s : player.getOwnedProperties()) {
				if (s.getName().equals(spaceName)) {
					break;
				}
				space_index++;
			}

			if (buildings > space.getBuildings()) {
				balance = (buildings - space.getBuildings()) * space.getHouseCost();
				boardService.removeFunds(board, player, balance); 
				if (buildings == 5) {
					board.setHousesAvailable(board.getHousesAvailable() + 4);
					board.setHotelsAvailable(board.getHotelsAvailable() - 1);
				} else {
					board.setHousesAvailable(board.getHousesAvailable() - (buildings - space.getBuildings()));
				}
			} else if (buildings < space.getBuildings()) {
				balance = (space.getBuildings() - buildings) * (space.getHouseCost() / 2);
				boardService.addFunds(board, player, balance);
				if (space.getBuildings() == 5) {
					board.setHousesAvailable((board.getHousesAvailable() - buildings));
					board.setHotelsAvailable(board.getHotelsAvailable() + 1);
				} else {
					board.setHousesAvailable(board.getHousesAvailable() + (space.getBuildings() - buildings));
				}
			}
			
			space.setBuildings(buildings);
			if (space.getBuildings() > 0) {
				space.setCurrentRent(space.getMultipliedRent()[ space.getBuildings() - 1]);
			} else {
				space.setCurrentRent(space.getRent() * 2);
			}

			player.getMonopolyProperties().set(monopoly_index, space);
			player.getOwnedProperties().set(space_index, space);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		board.setPlayers(players);
		boardService.saveBoard(board);
	}

	@RequestMapping("/addmortgage")
	public void addMortgage(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("space") String spaceName, @RequestParam("value") int mortgageValue) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		Space space = new Space();


		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();
			
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			int owned_index = 0;
			for (Space s : player.getOwnedProperties()) {
				if (s.getName().equals(spaceName)) {
					space = s;
					break;
				}
				owned_index++;
			}

			int monopoly_index = -1;
			int index = 0;
			for (Space s : player.getMonopolyProperties()) {
				if (s.getName().equals(spaceName)) {
					monopoly_index = index;
					break;
				}
				index++;
			}

			int space_index = 0;
			for (Space s : spaces) {
				if (s.getName().equals(spaceName)) {
					break;
				}
				space_index++;
			}


			boardService.addFunds(board, player, mortgageValue);
			space.setMortgaged(true);
			spaces.set(space_index, space);
			player.getOwnedProperties().set(owned_index, space);
			if (monopoly_index != -1) {
				player.getMonopolyProperties().set(monopoly_index, space);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);
	}

	@RequestMapping("/paymortgage")
	public void payMortgage(@RequestParam("gameid") int id, @RequestParam("player") String playerName, @RequestParam("space") String spaceName, @RequestParam("value") int loanValue) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		Space space = new Space();

		try {
			board = boardService.findByGameId(id);
			players = board.getPlayers();
			spaces = board.getSpaces();
			
			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					player = p;
					break;
				}
			}

			int owned_index = 0;
			for (Space s : player.getOwnedProperties()) {
				if (s.getName().equals(spaceName)) {
					space = s;
					break;
				}
				owned_index++;
			}

			int monopoly_index = -1;
			int index = 0;
			for (Space s : player.getMonopolyProperties()) {
				if (s.getName().equals(spaceName)) {
					monopoly_index = index;
					break;
				}
				index++;
			}

			int space_index = 0;
			for (Space s : spaces) {
				if (s.getName().equals(spaceName)) {
					break;
				}
				space_index++;
			}


			boardService.removeFunds(board, player, loanValue);
			space.setMortgaged(false);
			spaces.set(space_index, space);
			player.getOwnedProperties().set(owned_index, space);
			if (monopoly_index != -1) {
				player.getMonopolyProperties().set(monopoly_index, space);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		board.setPlayers(players);
		board.setSpaces(spaces);
		boardService.saveBoard(board);
	}
}
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

	 /* Creating, join, retrieve, update, and delete game. */

	@RequestMapping("/creategame")
	public String save(@RequestParam("players") String jsonPlayers, @RequestParam("randomize") boolean randomize) {
		Gson gson = new Gson();
		List<Player> players = new Vector<Player>();
		List<Space> spaces = new Vector<Space>();
		List<Card> community = new Vector<Card>();
		List<Card> chance = new Vector<Card>();
		Board board;

		try {
			players = gson.fromJson(jsonPlayers, new TypeToken<List<Player>>(){}.getType());

			if (players.isEmpty()) {
				throw new RuntimeException("save(): List of players is empty.");
			}

			board = new Board(players, randomize);
			spaces = boardService.decideSeasonsAndOrder(board, randomize);
			community = board.getCommunity();
			chance = board.getChance();

			board.setSpaces(spaces);
			// board.setCommunity(community);
			//board.setChance(chance);

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

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return player.getMonopolyGroups().contains(1);
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

		return string;
	}
}
package com.newmonopoly.controller;

import java.util.List;
import java.util.Vector;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Joining game."
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
	public void update(@RequestParam("gameid") int id) {
		// Function not currently used
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

	@RequestMapping("/haswinner")
	public String hasWinner(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		boolean winner = false;

		try {
			board = boardService.findByGameId(id);
			winner = boardService.hasWinner(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(winner);
	}

	@RequestMapping("/getcurrentplayer")
	public String getCurrentPlayer(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();
		Player currentPlayer = new Player();

		try {
			board = boardService.findByGameId(id);
			currentPlayer = boardService.getCurrentPlayer(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(currentPlayer);
	}

	@RequestMapping("/removefunds")
	public String removeFunds(@RequestParam("gameid") int id, @RequestParam("player") String jsonPlayer, @RequestParam("payment") int payment) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();

		try {
			board = boardService.findByGameId(id);
			player = gson.fromJson(jsonPlayer, Player.class);
			boardService.removeFunds(board, player, payment);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Funds removed from " + player.getName() + ".";
	}

	@RequestMapping("/addfunds")
	public String addFunds(@RequestParam("gameid") int id, @RequestParam("player") String jsonPlayer, @RequestParam("payment") int payment) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();

		try {
			board = boardService.findByGameId(id);
			player = gson.fromJson(jsonPlayer, Player.class);
			boardService.addFunds(board, player, payment);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "Funds received by " + player.getName() + ".";
	}

	@RequestMapping("/moveplayer")
	public String movePlayer(@RequestParam("gameid") int id, @RequestParam("player") String jsonPlayer, @RequestParam("value") int value) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();

		try {
			board = boardService.findByGameId(id);
			player = gson.fromJson(jsonPlayer, Player.class);
			boardService.movePlayer(board, player, value);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return player.getName() + " moved " + value + " spaces.";
	}

	@RequestMapping("/moveplayertojail")
	public String movePlayerToJail(@RequestParam("gameid") int id, @RequestParam("player") String jsonPlayer) {
		Gson gson = new Gson();
		Board board = new Board();
		Player player = new Player();

		try {
			board = boardService.findByGameId(id);
			player = gson.fromJson(jsonPlayer, Player.class);
			boardService.movePlayerToJail(board, player);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return player.getName() + " was moved to jail.";
	}

	@RequestMapping("/nextturn")
	public String nextTurn(@RequestParam("gameid") int id) {
		Gson gson = new Gson();
		Board board = new Board();

		try {
			board = boardService.findByGameId(id);
			boardService.nextTurn(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return boardService.getCurrentPlayer(board) + "'s turn is beginning.";
	}
}
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
		Board board;

		try {
			players = gson.fromJson(jsonPlayers, new TypeToken<List<Player>>(){}.getType());

			if (players.isEmpty()) {
				throw new RuntimeException("save(): List of players is empty.");
			}

			board.setSpaces(decideSeasonsAndOrder(spaces, randomizeSet));

			bdao.save(new Board(players, randomize));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(players);
	}

	// @RequestMapping("/retrievegame")

	// @RequestMapping("/updategame")

	// @RequestMapping("/deletegame")

	@RequestMapping("/haswinner")
	public String hasWinner(@RequestParam("gameid") long id) {
		Gson gson = new Gson();
		BoardLogic bl = null;
		boolean winner = false;

		try {
			bl = new BoardLogic(bdao.findById(id).get());
			winner = bl.hasWinner();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(winner);
	}

	@RequestMapping("/getcurrentplayer")
	public String getCurrentPlayer(@RequestParam("gameid") long id) {
		Gson gson = new Gson();
		return new String();
	}

	@RequestMapping("/removefunds")
	public String removeFunds(@RequestParam("gameid") long id, @RequestParam("player") String jsonPlayer, @RequestParam("payment") int payment) {
		Gson gson = new Gson();
		return new String();
	}

	@RequestMapping("/addfunds")
	public String addFunds(@RequestParam("gameid") long id, @RequestParam("player") String jsonPlayer, @RequestParam("payment") int payment) {
		Gson gson = new Gson();
		return new String();
	}

	@RequestMapping("/moveplayer")
	public String movePlayer(@RequestParam("gameid") long id, @RequestParam("player") String jsonPlayer, @RequestParam("value") int value) {
		Gson gson = new Gson();
		return new String();
	}

	@RequestMapping("/moveplayertojail")
	public String movePlayerToJail(@RequestParam("gameid") long id) {
		Gson gson = new Gson();
		return new String();
	}

	@RequestMapping("/nextturn")
	public String nextTurn(@RequestParam("gameid") long id) {
		Gson gson = new Gson();
		return new String();
	}
}
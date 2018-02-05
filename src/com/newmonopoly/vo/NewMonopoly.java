package com.newmonopoly.vo;

import java.util.Scanner;

public class NewMonopoly {
	Die die = new Die();
	Board board;
	Timer timer = new Timer();
	boolean game_card = false;
	
	public NewMonopoly(int totalPlayer) {
		board = new Board(totalPlayer);
	}
	
	public static void main(String[] args) {
		// Main method will need to grab information from Create Game.
		System.out.println("\tMonopoly\n");
		int numPlayers = 0;
		while (numPlayers < 2 || numPlayers > 6/* Grab max num of players from Create Game */) {
			try {
				// Grab how many players will be in current game.
			}
			catch(Exception e) {
				// Use this to catch any possible errors.
				continue;
			}
			if(totalPlayer > maxNum) {
				// Send message back saying that max number of players have been entered.
				System.err.println("Error: Invalid player count.");
			}
		}
		NewMonopoly session = new NewMonopoly(numPlayers);
		game.startGame();
	}
	
	public void startGame() {
		System.out.println("Start Game.");

		// Continuous loop until the game is over and the board has a winner.
		while (!gameOver() && !board.hasWinner()) {
			if(!board.getCurrentPlayer().isBankrupt()) {
				int die_roll = board.getCurrentPlayer().tossDie(die);
				board.movePlayer(board.getCurrentPlayer(), die_roll);
			}
			board.nextTurn();
		}

		if(board.hasWinner()) {
			// Display winner to view.
			System.out.println(board.getWinner().getName());
		}
		else {
			System.out.println(board.getMaxMoneyPlayer().getName());
		}
		System.out.println("Game over!");
	}
	
	public boolean gameOver() {
		for(Player player:board.getPlayers()){
			if(player.getTotalWalk() < 20){ return false; }
		}
		return true;
	}
}

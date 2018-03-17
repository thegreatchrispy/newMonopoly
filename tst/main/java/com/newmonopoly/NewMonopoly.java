package com.newmonopoly;

import java.util.List;
import java.util.Vector;

public class NewMonopoly {
	Board board;
	Die die_1;
	Die die_2;
	List<Player> players;
	boolean gameOver;
	int maxTurns;
	//Timer timer;

	
//	public NewMonopoly(int totalPlayer) {
//		board = new Board(totalPlayer);
//	}

	// CONSTRUCTOR FOR CUSTOM GAMES
	
	// public static void main(String[] args) {
	// 	// Main method will need to grab information from Create Game.
	// 	System.out.println("\tMonopoly\n");
	// 	int numPlayers = 0;
	// 	while (numPlayers < 2 || numPlayers > 6/* Grab max num of players from Create Game */) {
	// 		try {
	// 			// Grab how many players will be in current game.
	// 		}
	// 		catch(Exception e) {
	// 			// Use this to catch any possible errors.
	// 			continue;
	// 		}
	// 		if(totalPlayer > maxNum) {
	// 			// Send message back saying that max number of players have been entered.
	// 			System.err.println("Error: Invalid player count.");
	// 		}
	// 	}/
	// }

		
		// Loop to place players in array.
//		NewMonopoly session = new NewMonopoly(numPlayers);
//		session.startGame();

	/*default*/
	public NewMonopoly(List<Player> players) {
		try {
			board = new Board(players, false);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		die_1 = new Die();
		die_2 = new Die();
		maxTurns=120;
		gameOver=false;
		//timer = new Timer();
	}
	/*custom game*/
	//public NewMonopoly(){
		//something
	//}

	public void startGame() {
		System.out.println("Game Has Begun!!!");
		choosePieces();
		playerOrder();
		// Continuous loop until the game is over and the board has a winner.
		while (!gameOver && !board.hasWinner()) {
			if(board.getCurrentPlayer().getMoney()>0) {
				board.getCurrentPlayer().setDoublesCount(0);
				do{
					die_1.roll();
					die_2.roll();
					int die_roll = die_1.getValue() + die_2.getValue();

					if(die_1.getValue() == die_2.getValue()){
						board.getCurrentPlayer().incrementDoubles();
					}

					if(board.getCurrentPlayer().getDoublesCount()<3){
						board.movePlayer(board.getCurrentPlayer(), die_roll);
					}else {
						board.movePlayerToJail(board.getCurrentPlayer());
						break;
					}
				}while(die_1.getValue() == die_2.getValue());
			}

			board.nextTurn();
		}

		if(board.hasWinner()) {
			// Display winner to view.
			System.out.println(board.getWinner().getName());
		}
		else {
			System.out.println("Maximum turns reached."); // For now...
			//System.out.println(board.getMaxMoneyPlayer().getName());
		}
		System.out.println("Game over!");
	}
	
	public void choosePieces() {
		
	}

	public void playerOrder() {
		
	}
}
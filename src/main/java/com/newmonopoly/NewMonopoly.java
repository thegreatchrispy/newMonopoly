package com.newmonopoly;

import java.util.List;
import java.util.Vector;
import java.util.Scanner;

public class NewMonopoly {
	Board board;
	Die die1;
	Die die2;
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

		die1 = new Die();
		die2 = new Die();
		maxTurns=120;
		gameOver=false;
		//timer = new Timer();
	}
	/*custom game*/
	//public NewMonopoly(){
		//something
	//}

	public void startGame() {
		choosePieces();
		playerOrder();
		// Continuous loop until the game is over and the board has a winner.
		while (!gameOver && !board.hasWinner()) {
			if(board.getCurrentPlayer().getMoney()>0) {
				if(board.getCurrentPlayer().getInJail()) { 	// If player starts turn in jail.
					while(true) {
						Scanner input = new Scanner(System.in);
						String ans = "";
						if(board.getCurrentPlayer().getJailCard()) { // If player wants to use jailCard.
							System.out.print("You have a \"Get Out of Jail Free\" card! Would you like to use it? Y/N ");
							ans = input.nextLine().toUpperCase();
							if(ans.equals("Y")) {
								board.getCurrentPlayer().setJailCard(false);
								board.getCurrentPlayer().setInJail(false);
								beginTurn();
								break;
							}
						}
						if(board.getCurrentPlayer().getJailTime() > 1) { // If player has more than one turn left in jail.
							System.out.print("Would you like to pay $50 to be released? Y/N ");
							ans = input.nextLine().toUpperCase();
							if(ans.equals("Y")) {	// If player wants to pay to get out of jail.
								board.getCurrentPlayer().setMoney(board.getCurrentPlayer().getMoney() - 50);
								board.getCurrentPlayer().setInJail(false);
								beginTurn();
								break;
							} else {	// If player wants to roll, then roll dice.
								die1.roll();
								die2.roll();
								int die_roll = die1.getValue() + die2.getValue();
								if (die1.getValue() == die2.getValue()) {	// If player rolls doubles, then they will be free.
									board.getCurrentPlayer().setInJail(false);
									board.movePlayer(board.getCurrentPlayer(), die_roll);
									break;
								}
								else {	// If player fails, then decrement jailTime by one.
									board.getCurrentPlayer().setJailTime(board.getCurrentPlayer().getJailTime() - 1);
									board.playerDecision(board.getCurrentPlayer());
									break;
								}
							}
						}
						else {	// If player has no more turns left in jail.
							board.getCurrentPlayer().setMoney(board.getCurrentPlayer().getMoney() - 50);
							board.getCurrentPlayer().setInJail(false);
							die1.roll();
							die2.roll();
							int die_roll = die1.getValue() + die2.getValue();
							board.movePlayer(board.getCurrentPlayer(), die_roll);
							break;
						}
					}
				}
				else{	// If player does not start turn in jail.
					beginTurn();
				}
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

	public void beginTurn() {	// Function to begin player's turn.
		board.getCurrentPlayer().setDoublesCount(0);
			do{
				die1.roll();
				die2.roll();
				int die_roll = die1.getValue() + die2.getValue();

				if(die1.getValue() == die2.getValue()){
					board.getCurrentPlayer().incrementDoubles();
				}

				if(board.getCurrentPlayer().getDoublesCount()<3){
					board.movePlayer(board.getCurrentPlayer(), die_roll);
				}else {
					board.movePlayerToJail(board.getCurrentPlayer());
					break;
				}
			}while(die1.getValue() == die2.getValue());
	}
}
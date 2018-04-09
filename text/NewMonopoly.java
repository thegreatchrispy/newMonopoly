import java.util.List;
import java.util.Vector;

import java.util.Scanner;
import java.util.Collections;

public class NewMonopoly {
	private Board board;
	private Die die1;
	private Die die2;
	private List<Player> players;
	private boolean gameOver;
	private int maxTurns;
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
	public NewMonopoly(List<Player> players, boolean randomize) {
		if (players.isEmpty()) {
			throw new IllegalArgumentException("In NewMonopoly constructor: List<Player> players is empty.");
		}

		this.players = players;
		die1 = new Die();
		die2 = new Die();
		playerOrder();
		//choosePieces();
		
		try {
			board = new Board(players, randomize);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		maxTurns=120;
		gameOver=false;
		//timer = new Timer();
		// Create newMonopoly table
		// Table: newMonopoly_id, players, die1, die2, board_id, maxTurns, gameOver
	}
	/*custom game*/
	//public NewMonopoly(){
		//something
	//}

	public void startGame() {
		// Continuous loop until the game is over and the board has a winner.
		while (!gameOver && !board.hasWinner()) {
			System.out.println("\nBeginning " + board.getCurrentPlayer().getName() + "'s turn with $" + board.getCurrentPlayer().getMoney());
			if(board.getCurrentPlayer().getMoney()>0) {
				if(board.getCurrentPlayer().getInJail()) { 	// If player starts turn in jail.
					System.out.println(board.getCurrentPlayer().getName() + " has " + board.getCurrentPlayer().getJailTime() + " more turn(s) in jail.");

					while(true) {
						char ans;

						if(board.getCurrentPlayer().getJailCard()) { // If player wants to use jailCard.
							System.out.print("\nYou have a \"Get Out of Jail Free\" card! Would you like to use it? Y/N ");
							ans = getCharInput();

							if(ans == 'Y') {
								board.getCurrentPlayer().setJailCard(false);
								System.out.println(board.getCurrentPlayer().getName() + " used the Get Out of Jail Free card.");
								board.getCurrentPlayer().setInJail(false);
								System.out.println(board.getCurrentPlayer().getName() + " was released from jail.");
								// update board
								beginTurn();
								break;
							}
						}

						if(board.getCurrentPlayer().getJailTime() > 1) { // If player has more than one turn left in jail.
							System.out.print("Would you like to pay $50 to be released? Y/N ");
							ans = getCharInput();

							if(ans == 'Y') {	// If player wants to pay to get out of jail.
								board.removeFunds(board.getCurrentPlayer(), 50);
								System.out.println(board.getCurrentPlayer().getName() + " paid $50 to get out of jail.");
								board.getCurrentPlayer().setInJail(false);
								System.out.println(board.getCurrentPlayer().getName() + " was released from jail.");
								// Update board
								beginTurn();
								break;
							}
							else {	// If player wants to roll, then roll dice.
								System.out.println("\nRolling dice...");
								die1.roll();
								die2.roll();
								int dieRoll = die1.getValue() + die2.getValue();
								// Updates die1, die2
								System.out.println("Value: " + dieRoll + "\n");

								if (die1.getValue() == die2.getValue()) {	// If player rolls doubles, then they will be free.
									System.out.println(board.getCurrentPlayer().getName() + " rolled doubles.");
									board.getCurrentPlayer().setInJail(false);
									System.out.println(board.getCurrentPlayer().getName() + " was released from jail.");
									// update board
									board.movePlayer(board.getCurrentPlayer(), dieRoll);
									break;
								}
								else {	// If player fails, then decrement jailTime by one.
									board.getCurrentPlayer().setJailTime(board.getCurrentPlayer().getJailTime() - 1);
									System.out.println(board.getCurrentPlayer().getName() + " has " + board.getCurrentPlayer().getJailTime() + " turn(s) left in jail.");
									// Update board
									board.playerDecision(board.getCurrentPlayer());
									break;
								}
							}
						}
						else {	// If player has no more turns left in jail.
							board.removeFunds(board.getCurrentPlayer(), 50);
							System.out.println(board.getCurrentPlayer().getName() + " paid $50 to get out of jail.");
							board.getCurrentPlayer().setInJail(false);
							System.out.println(board.getCurrentPlayer().getName() + " was released from jail.");
							// update board
							System.out.println("\nRolling dice...");
							die1.roll();
							die2.roll();
							int dieRoll = die1.getValue() + die2.getValue();
							// update die1, die2
							System.out.println("Value: " + dieRoll + "\n");
							board.movePlayer(board.getCurrentPlayer(), dieRoll);
							break;
						}
					}
				}
				else {	// If player does not start turn in jail.
					beginTurn();
				}
			}

			board.nextTurn();
			// update board
		}

		if(board.hasWinner()) {
			// Display winner to view.
			System.out.print("THE WINNER IS:\t");
			System.out.println(board.getWinner().getName());
		}
		else {
			System.out.println("Maximum turns reached."); // For now...
			//System.out.println(board.getMaxMoneyPlayer().getName());
		}
		System.out.println("Game over!");
	}
	
	public void playerOrder() {
		System.out.println("Beginning playerOrder()...\n");
		
		for (Player player : players){
			System.out.println("\nRolling dice...");
			die1.roll();
			die2.roll();
			int dieRoll = die1.getValue() + die2.getValue();
			// update die1, die2
			System.out.println("Value: " + dieRoll + "\n");
			player.setTurnOrder(dieRoll);
			// update player
		}

		Collections.sort(players);
		System.out.println("Player order:");

		int i = 0;
		for (Player player : players) {
			System.out.println("\t" + ++i + ". " + player.getName());
		}

		System.out.println("\nEnding playerOrder()...");
	}

	public void choosePieces() {
		for (Player player : players) {
			displayAvailablePieces();
			int choice = board.getUserInput();
			player.setTokenNumber(choice);
		}
	}

	public void displayAvailablePieces() {
		
	}

	public void beginTurn() {	// Function to begin player's turn.
		board.getCurrentPlayer().setDoublesCount(0);

		do{
			System.out.println("\nRolling dice...");
			die1.roll();
			die2.roll();
			int dieRoll = die1.getValue() + die2.getValue();
			// update die1, die2
			System.out.println("Value: " + dieRoll + "\n");

			if(die1.getValue() == die2.getValue()){
				System.out.println(board.getCurrentPlayer().getName() + " rolled doubles!?");
				board.getCurrentPlayer().incrementDoubles();
				// update board
			}

			if(board.getCurrentPlayer().getDoublesCount()<3){
				board.movePlayer(board.getCurrentPlayer(), dieRoll);
			}
			else {
				board.movePlayerToJail(board.getCurrentPlayer());
				break;
			}
		} while (die1.getValue() == die2.getValue());
	}

	public char getCharInput() {
		Scanner sc = new Scanner(System.in);
		// retrieve char input
		return sc.next().charAt(0);
	}

}

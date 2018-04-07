import java.util.List;
import java.util.Vector;
import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		// Prepare the game
		List<Player> players = new Vector<Player>();
		Scanner sc = new Scanner(System.in);
		int numPlayers = 0;
		// System.out.print("Enter the number of players: ");
		//numPlayers = sc.nextInt();

		do {
			System.out.print("Enter the number of players: ");
			numPlayers = sc.nextInt();
		} while(numPlayers < 1 || numPlayers > 6);
		
		for (int i = 0; i < numPlayers; i++) {
			System.out.println("Enter player #" + (i+1) + "'s name: ");
			String playerName = sc.next();
			players.add(new Player(playerName));
			System.out.println("Player added!");
		}

		// Start the game
		NewMonopoly newMonopoly = new NewMonopoly(players);
		// for (Player player : players) {
		// 	System.out.println(player.toString());
		// }

		// System.out.println("Rolling die...");
		// die.roll();
		// System.out.println("Value = " + die.getValue());
		newMonopoly.startGame();
	}
}
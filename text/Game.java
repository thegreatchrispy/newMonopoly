import java.util.List;
import java.util.Vector;
import java.util.Scanner;

public class Game {
	public static boolean isNumber(String s) {
		boolean result = true;

        for (int i = 0; i < s.length(); i++) {
        	if (Character.isDigit(s.charAt(i)) == false) {
				result =  false;
			}
		}

        return result;
	}
	
	public static void main(String[] args) {
		// Prepare the game
		List<Player> players = new Vector<Player>();
		Scanner sc = new Scanner(System.in);
		int numPlayers = 0;
		boolean randomize = false;
		char answer;
		String playerName;

		do {
			System.out.print("Enter the number of players: ");

			while(!sc.hasNextInt()) {
				sc.next();
				System.out.print("Enter a number: ");
			}

			numPlayers = sc.nextInt();

		} while (numPlayers < 2 || numPlayers > 6);

		for (int i = 0; i < numPlayers; i++) {
			do {
				System.out.println("Enter player #" + (i+1) + "'s name: ");
				playerName = sc.next();
			} while ((playerName.length() < 4 || playerName.length() > 15) || isNumber(playerName) || !Character.isLetter(playerName.charAt(0)));

			players.add(new Player(playerName));
			System.out.println("Player added!\n");
		}
		
		do {
			System.out.print("Would you like to randomize the spaces? Y/N ");
			answer = sc.next().charAt(0);
		} while (answer != 'Y' && answer != 'y' && answer != 'N' && answer != 'n');

		if (answer == 'Y' || answer == 'y') {
			randomize = true;
		}

		// Start the game
		NewMonopoly newMonopoly = new NewMonopoly(players, randomize);
		newMonopoly.startGame();
	}
}
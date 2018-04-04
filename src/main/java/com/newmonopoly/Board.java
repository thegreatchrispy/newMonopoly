package com.newmonopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.Random;
import java.util.Scanner;
import java.util.Iterator;
import java.io.FileReader;
import java.lang.Math;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Board {
	private List<Player> players;  // Players are in turn order
	boolean randomizeSet;
	int currentTurn; // Current turn number
	int totalPlayer; // Total number of players allowed to join
	private List<Space> spaces;
	private List<Card> chance;
	private List<Card> community;
	String[] seasons;
	String currentSeason;
	Random rand;
	Gson gson;
	boolean turnOver;
	int dieValue;
	
	// Constructor
	public Board(List<Player> players, boolean randomizeSet) throws Exception {
		gson = new Gson();
		this.players = players;
		this.randomizeSet = randomizeSet;
		currentTurn = 0;
		totalPlayer = 6;// Set to 6 for testing, will get from constructor in future.
		turnOver = false;
		spaces = new Vector<Space>();
		chance = new Vector<Card>();
		community = new Vector<Card>();
		dieValue=0;

		spaces = retrieveSpaceInfo();
		spaces = decideSeasonsAndOrder(spaces, randomizeSet);
		chance = retrieveChanceInfo();
		community = retrieveCommunityInfo();

		Collections.shuffle(chance);
		Collections.shuffle(community);
	}

	public List<Space> decideSeasonsAndOrder(List<Space> originalSpaces, boolean randomizeSet) {
		// Updates all the seasons in the database.
		List<Space> tempSpaces = new Vector<Space>();
		List<Space> newSpaces = new Vector<Space>();

        // List for seasons.
        List<Integer> strongSeasons = new ArrayList<Integer>();
        List<Integer> weakSeasons = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++) {
            strongSeasons.add(i);
        }
        for (int i = 0; i < 4; i++) {
            weakSeasons.add(i);
        }
        do {
            Collections.shuffle(strongSeasons);
            Collections.shuffle(weakSeasons);
        } while(strongSeasons.equals(weakSeasons));

        // List for groups.
        List<Integer> groups = new ArrayList<Integer>();
        for (int i = 1; i < 9; i++) {
            groups.add(i);
        }
        Collections.shuffle(groups);

        // Variables
        int strongRand;
        int weakRand;
        int group1;
        int group2;

        int groupsIndex = 0;
        Space space;
        Iterator<Space> itr = originalSpaces.iterator();

        for (int i = 0; i < 4; i++) {
            // Determining seasons.
            strongRand = strongSeasons.get(i);
            weakRand = weakSeasons.get(i);
            group1 = groups.get(groupsIndex++);
            group2 = groups.get(groupsIndex++);

            while(itr.hasNext()) {
                space = (Space)itr.next();
                    if (space.getGroup() == group1) {
                        space.setStrongSeason(strongRand);
                        space.setWeakSeason(weakRand);
                        if (randomizeSet) {
                            space.setGroup(group2);
                            tempSpaces.add(space);
                            itr.remove();
                        }
                    }
            }
            itr = originalSpaces.iterator();

            while(itr.hasNext()) {
                space = (Space)itr.next();
                    if (space.getGroup() == group2) {
                        space.setStrongSeason(strongRand);
                        space.setWeakSeason(weakRand);
                        if (randomizeSet) {
                            space.setGroup(group1);
                            tempSpaces.add(space);
                            itr.remove();
                        }
                    }
            }
			itr = originalSpaces.iterator();
		}

		// Sort tempSpaces by group in ascending order.
		Collections.sort(tempSpaces);

		if (randomizeSet) {
			for (int i = 0; i < 40; i++) {
				// Add Corner.
				if (i%10 == 0) {
					newSpaces.add(originalSpaces.remove(0));
				}
				// Add Railroad
				else if (i%5 == 0 && !(i%10 == 0)) {
					newSpaces.add(originalSpaces.remove(0));
				}
				// Add Space
				else {
					newSpaces.add(tempSpaces.remove(0));
				}
			}
		} 
		else {
			newSpaces = originalSpaces;
		}
		return newSpaces;
	}
	// Move player.
	public void movePlayer(Player player, int value) {
		dieValue = value;
		turnOver = false;
		if ( ((player.getCurrentPosition() + value) % 40) < (player.getCurrentPosition())) {
			addFunds(player, 200);
		}

		player.setCurrentPosition((player.getCurrentPosition() + value) % 40);
		transaction(player);
	}

	public void movePlayer(Player player, String spaceName) {
		for (Space space : spaces) {
			if (space.getName().equals(spaceName)) {
				player.setCurrentPosition(spaces.indexOf(space));
			}
		}

		performSpaceAction(player, spaces.get(player.getCurrentPosition()));
	}

	public void moveToPosition(Player player, int position) {
		player.setCurrentPosition(position);
		performSpaceAction(player, spaces.get(player.getCurrentPosition()));
	}

	public void moveToNearest(Player player, String type) {
		boolean nearestFound = false;

		for (int i = 1; i < 40 && !nearestFound; i++) {
			if (spaces.get(player.getCurrentPosition() + i).getType() == type) {
				player.setCurrentPosition(player.getCurrentPosition() + i);
				nearestFound = true;
			}
		}
		performSpaceAction(player, spaces.get(player.getCurrentPosition()));
	}

	public void addFunds(Player player, int payment) {
		player.setMoney(player.getMoney() + payment);
	}

	public void removeFunds(Player player, int payment) {
		if((player.getMoney() - payment) < 0){
			mortgage(player, (player.getMoney() - payment));
		}
		player.setMoney(player.getMoney() - payment);
	}

	public void repairs(Player player, int housePrice, int hotelPrice) {
		for(Space space : spaces){
			if(space.getOwnedBy() == players.indexOf(player)){
				if(space.getBuildings() == 5){
					removeFunds(player, ((housePrice * 4) - hotelPrice));
				}
				else {
					removeFunds(player, (housePrice * space.getBuildings()));
				}
			}
		}
	}

	public void giveToPlayers(Player player, int payment) {
		for (Player otherPlayer : players) {
			removeFunds(player, payment);
			addFunds(otherPlayer, payment);
		}
	}

	public void takeFromPlayers(Player player, int payment) {
		for (Player otherPlayer : players) {
			removeFunds(otherPlayer, payment);
			addFunds(player, payment);
		}
	}

	public void movePlayerToJail(Player player) {
		player.setCurrentPosition(10);
		player.setInJail(true);
		player.setJailTime(3);
	}

	public void transaction(Player player) {
		performSpaceAction(player, spaces.get(player.getCurrentPosition()));
		if (player.getMoney() <= 0) {
			turnOver = true;
			// End turn called here.
		}
		while (!turnOver) {
			playerDecision(player);
		}
	}

	public void performSpaceAction(Player player, Space space) {
		switch (space.getType()) {
			case "corner":
				movePlayerToJail(player);
				break;
			case "property":
				if ((space.getOwnedBy() != (currentTurn % players.size())) && space.getOwnedBy() > -1) {
					payRent(player, space);
				}
				else {
					chooseToBuy(player, space);
				}
				break;
			case "railroad":
				if ((space.getOwnedBy() != (currentTurn % players.size())) && space.getOwnedBy() > -1) {
					payRailroad(player, space);
				}
				else {
					chooseToBuy(player, space);
				}
				break;
			case "utility":
				if ((space.getOwnedBy() != (currentTurn % players.size())) && space.getOwnedBy() > -1) {
					payUtility(player, space);
				}
				else {
					chooseToBuy(player, space);
				}
				break;
			case "tax":
				payTax(player, space);
				break;
			case "comchest":
				drawCard(player, community);
				break;
			case "chance":
				drawCard(player, chance);
				break;
			default:
				break;
		}
	}

	public void payRent(Player player, Space space) {
		// Rent will be changed in database in the build() method
		int rent = space.getCurrentRent();
		int index = space.getOwnedBy();
		removeFunds(player, rent);
		addFunds(players.get(index), rent);
	}

	public void payRailroad(Player player, Space space) {
		int payment = 25;
		int index = space.getOwnedBy();

		for (int i = 0; i < 4; i++) {
			if (index == spaces.get(10*i + 5).getOwnedBy()) {
				payment *= 2;
			}
		}

		removeFunds(player, payment);
		addFunds(players.get(index), payment);
	}

	public void payUtility(Player player, Space space) {
		int initialPayment;
		boolean waterWorks = false;
		boolean electricCompany = false;
		List<Space> properties = players.get(space.getOwnedBy()).getOwnedProperties();

		for (Space utility : spaces) {
			if (utility.getName().equals("Water Works")) {
				if(properties.contains(utility))
					waterWorks = true;
			}
			if (utility.getName().equals("Electric Company")) {
				if(properties.contains(utility))
					electricCompany = true;
			}
		}

		if(waterWorks && electricCompany){
			initialPayment=10;
		}else {
			initialPayment=4;
		}
		removeFunds(player, (initialPayment*dieValue));
		addFunds(players.get(space.getOwnedBy()), (initialPayment*dieValue));
	}

	public void payTax(Player player, Space space) {
		if(space.getName().equals("Luxury Tax")){
			addFunds(player, space.getPrice());
		} else {
			removeFunds(player, Math.min(200,(player.getMoney()/10)));
		}
	}

	public void chooseToBuy(Player player, Space space) {
		Scanner input = new Scanner(System.in);
		System.out.print("Would you like to purchase " + space.getName() + " for $" + space.getPrice() + "? Y/N");
		String choice = input.nextLine();
		
		if (choice.equals("Y") || choice.equals("y")) {
			removeFunds(player, space.getPrice()); // Player buys property.
			player.addOwnedProperties(space); // Add property to players Owned Properties list.
			space.setOwnedBy(players.indexOf(player));
			addMonopoly(player, space);
		}
		// Player does not want property.
		else {
			auction(players, space); // All players eligible for auction.
		}
	}

	public void auction(List<Player> players, Space space) {

	}

	public void drawCard(Player player, List<Card> cards) {
		Card card = cards.get(0);
		cards.add(cards.remove(0));
		switch (card.getAction()) {
			case "move":
				if(card.getSpaceName() != null){
					movePlayer(player, card.getSpaceName());
				} else if(card.getPosition() != 0){
					moveToPosition(player, card.getPosition());
				}
				break;
			case "movenearest":
				moveToNearest(player, card.getType());
				break;
			case "addfunds":
				addFunds(player, card.getPayment());
				break;
			case "removefunds":
				removeFunds(player, card.getPayment());
				break;
			case "repairs":
				repairs(player, card.getHouses(), card.getHotels());
				break;
			case "givetoplayers":
				giveToPlayers(player, card.getPayment());
				break;
			case "takefromplayers":
				takeFromPlayers(player, card.getPayment());
				break;
			case "gotojail":
				movePlayerToJail(player);
				break;
			case "outjail":
				player.setJailCard(true);
				break;
			default:
				break;
		}
		// Call a new Card object dependent on the type.
	}

	public void playerDecision(Player player) {
		// Build improvements, trade, or end turn.
		System.out.print("Your turn is almost to an end. Would you like to build improvements, trade, or end your turn? B/T/E ");
		Scanner input = new Scanner(System.in);
		String ans = input.nextLine().toUpperCase();
		if(ans.equals("B")) { // Build Improvements
			findMonopolies(player);
			System.out.print("What property from the above list would you like to improve? ");
			ans = input.nextLine();
			int index = 0;
			for (Space space : spaces) {
				if (space.getName().equals(ans)) {
					index = spaces.indexOf(space);
					break;
				}
			}
			if (spaces.get(index).getBuildings() < 5) {
				build(player, spaces.get(index));
			} else {
				System.out.println("You cannot build anymore on this property!");
			}
		}
		else if(ans.equals("T")) {	// Trade
			for (Player other_player : players) {
				System.out.println(other_player.getName());
			}
			System.out.print("What player from the above list would you like to trade with? ");
			ans = input.nextLine();
			int index = 0;
			for (Player otherPlayer : players) {
				if (otherPlayer.getName().equals(ans)) {
					index = players.indexOf(otherPlayer);
				}
			}
			trade(player, players.get(index));
		}
		else {	// End turn.
			turnOver = true;
		}
	}

	public void mortgage(Player player, int debt) {
		// Scanner input = new Scanner(System.in);
		// displayOwnedProperties(player);
		// while (debt <= 0) {
		// 	System.out.println("Which properties would you like to sell? ");
		// }
	}

	public void displayOwnedProperties(Player player) {

	} 

	public void build(Player player, Space space) {
		System.out.println(space.getName() + " currently has " + space.getBuildings() + " houses.");
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		do {
			System.out.print("How many more houses would you like to add? ");
			int additions = input.nextInt();
			if (additions + space.getBuildings() > 5) {
				valid = false;
				System.out.println("You entered an invalid amount of houses! Please try again.");
			}
			else {
				removeFunds(player, additions*space.getHouseCost());
				space.setBuildings(space.getBuildings() + additions);
			}
		} while (!valid);
	}

	public void trade(Player playerSending, Player playerReceiving) {
		Scanner input = new Scanner(System.in);
		System.out.print("Trade or sell? T/S ");
		char ans = Character.toUpperCase(input.next().charAt(0));
		switch (ans) {
			case 'T':
				//trade
				System.out.println("you can't yet.. :/");
				System.out.println("Have you tried playing Resident Evil 4?");
				// don't forget addMonopoly() boi
				break;
			case 'S':
				//sell
				System.out.println("you can't yet.. :/");
				System.out.println("Have you tried playing Resident Evil 4?");
				break;
			default:
				System.out.println("incorrect input");
				break;
		}
	}

	public void endTurn() {
		turnOver = true;
	}

	// Checks if player has new monopolies.
	public void addMonopoly(Player player, Space space) {
		boolean newMonopoly = true;
		for (Space s : spaces) {
			if (s.getGroup() == space.getGroup()) {
				if(s.getType().equals("property")) {
					if (s.getOwnedBy() != players.indexOf(player)) {
						newMonopoly = false;
						break;
					}
				}
			}
		}
		if (newMonopoly) {
			player.addMonopolyGroup(space.getGroup()-1);
			for (Space s : spaces) {
				if (s.getGroup() == space.getGroup()) {
					if (s.getType().equals("property")) {
						s.setCurrentRent(s.getRent() * 2);
					}
				}
			}
		}
	}

	// Finds and prints the list of player's monopolies.
	// This will print the group and each space in that group.
	public void findMonopolies(Player player) {
		for(int i = 0; i < 8; i++) {
			if(player.getMonopolyGroups()[i] == 1) {
				System.out.println("Group " + (i+1) + ":");
				for(Space space : spaces) {
					if(space.getGroup() == (i+1)) {
						System.out.println(space.getName());
					}
				}
				System.out.println();
			}
		}
	}
	// Return a boolean if the game has ended.
	// If there is only one player that is not bankrupt, then the game will terminate.
	public boolean hasWinner() {
		int playersInGame = 0;
		for(Player player:players){
			if(player.getMoney() > 0){
				playersInGame++;
			}
		}
		return playersInGame <= 1;
	}
	
	// Return the winner of the game.
	public Player getWinner() {
		return getCurrentPlayer();
	}
//	
//	// Return top player for statistics.
//	public Player getTopPlayer() {
//		Player maxplayer = null;
//		for(Player player:players){
//			if(maxplayer == null || maxplayer.getMoney().getMoney() < player.getMoney().getMoney()){
//				maxplayer = player;
//			}
//		}
//		return maxplayer;
//	}
//	
//	public int normalizePosition(int position) {
//		return position % spaces.length;
//	}
//	
	public Player getCurrentPlayer() {
		return players.get(currentTurn % players.size());
	}
//	
//	public Player[] getPlayers() {
//		return players;
//	}
//	
	public void nextTurn() {
		// if(++currentTurn >= players.length){
		// 	currentTurn = 0;
		// }
	}
//	
//	public Player getPlayer(int id) {
//		return players[id];
//	}
//	
//	public int getTotalSquare() {
//		return spaces.length;
//	}
	public List<Space> retrieveSpaceInfo() throws Exception {
		List<Space> spaces = gson.fromJson(new FileReader("spaces.json"), new TypeToken<List<Space>>(){}.getType());
		return spaces;
	}
	public List<Card> retrieveChanceInfo() throws Exception {
		List<Card> chance = gson.fromJson(new FileReader("chance.json"), new TypeToken<List<Card>>(){}.getType());
		return chance;
	}

	public List<Card> retrieveCommunityInfo() throws Exception {
		List<Card> community = gson.fromJson(new FileReader("community.json"), new TypeToken<List<Card>>(){}.getType());
		return community;
	}

}

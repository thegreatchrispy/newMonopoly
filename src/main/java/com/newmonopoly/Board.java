package com.newmonopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.lang.Math;
import java.lang.reflect.Type;

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
		chance = retrieveChanceInfo();
		community = retrieveCommunityInfo();

		Collections.shuffle(chance);
		Collections.shuffle(community);
		//rand = new Random(4);
		//seasons = {"Spring", "Summer", "Fall", "Winter"};
		//currentSeason = seasons[rand];


		// for(int i = 0;i < players.length;i++){
		// 	// Fix constructor.
		// 	players[i] = new Player(i, "Player " + (i + 1));
		// }

		// THIS IS WHERE THE RANDOM ALGORITHM WILL GO.
		// THIS IS WHERE STUFF SHOULD GO.

		//decideSeasons(randomizeSet);

		// Grab JSON file
		// SET 8 Static Spaces
//		for (int i = 0; i < 8; i++) {
//			getGroup(spaces, i);
//		}

//		for(int i = 0;i < spaces.length;i++){
//			if(i == 0){
//				spaces[i] = new SpaceNode("GO");
//			}else if(i == 10){
//				spaces[i] = new JailSquare("Prison");
//			}else if(i == 20){
//				spaces[i] = new HouseSquare("House");
//			}else if(i == 30){
//				spaces[i] = new GoToJailSquare("Locked Up!");
//			}else{
//				spaces[i] = new HouseSquare(names[rand.nextInt(names.length)] + " " + names[rand.nextInt(names.length)], 400 + rand.nextInt(300));
//			}
//		}
	}

	public void decideSeasons(boolean randomizeSet) {
		// Updates all the seasons in the database.

		// Lists for flags to ensure that there are two of every strong and weak season.
		List<Boolean> strongSeasons_flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[4]));
		Collections.fill(strongSeasons_flag, Boolean.FALSE);

		List<Boolean> weakSeasons_flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[4]));
		Collections.fill(weakSeasons_flag, Boolean.FALSE);

		// List for flags to ensure all eight groups of properties are cycled through.
		List<Boolean> groups_flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[8]));
		Collections.fill(groups_flag, Boolean.FALSE);

		// String array to pull seasons from.
		String[] strong_season = {"Spring", "Summer", "Fall", "Winter"};
		String[] weak_season = {"Spring", "Summer", "Fall", "Winter"};

		// Initializing Random object and integer variables.
		rand = new Random();
		int strong_rand;
		int weak_rand;
		int group1;
		int group2;
		
		while (strongSeasons_flag.contains(false) && weakSeasons_flag.contains(false)) {
			// Generate Random Number to randomly pick seasons.
			strong_rand = rand.nextInt(4);
			weak_rand = rand.nextInt(4);
			// Ensure that the two random numbers are not equal and that the season has not been picked yet. (A strong season cannot be a weak seaon).
			while ((strong_rand == weak_rand) || (strongSeasons_flag.get(strong_rand) && weakSeasons_flag.get(weak_rand)) ) {
				strong_rand = rand.nextInt(4);
				weak_rand = rand.nextInt(4);
			}

			// Generate Random Number to randomly pick groups.
			group1 = rand.nextInt(8);
			group2 = rand.nextInt(8);
			// Ensure that the two random numbers are not equal and that the season has not been picked yet. (A strong season cannot be a weak seaon).
			while ((group1 == group2) || (groups_flag.get(group1) && groups_flag.get(group2)) ) {
				group1 = rand.nextInt(8);
				group2 = rand.nextInt(8);
			}



		}
		//while (strongSeasons_flag.contains(false) && weakSeasons_flag)
		// 	strong rand
		// 	weak rand
		//	group1 rand
		//  group2 rand
		// 	if (randomizeSet)
		// 		swap(group1 rand, group2 rand)
	}

//	public void getGroup(SpaceNode[] spaces, int i) {
//		// Call Database to get each group and position spaces correctly.
//		//i*5 + group_pos
//	}
//	
	// Move player.
	public void movePlayer(Player player, int value) {
		dieValue = value;
		turnOver = false;
		if ( ((player.getCurrentPosition() + value) % 40) < (player.getCurrentPosition())) {
			player.setMoney(player.getMoney() + 200);
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
		// JUAN
	}
	public void removeFunds(Player player, int payment) {
		// JUAN
	}
	public void repairs(Player player, int houses, int hotels) {
		// JUAN
	}
	public void giveToPlayers(Player player, int payment) {
		
	}
	public void takeFromPlayers(Player player, int payment) {
		
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
		int rent = space.getRent();
		int index = space.getOwnedBy();
		player.setMoney(player.getMoney() - rent);
		players.get(index).setMoney(players.get(index).getMoney() + rent);
	}

	public void payRailroad(Player player, Space space) {
		int payment = 25;
		int index = space.getOwnedBy();

		for (int i = 0; i < 4; i++) {
			if (index == spaces.get(10*i + 5).getOwnedBy()) {
				payment *= 2;
			}
		}

		player.setMoney(player.getMoney() - payment);
		players.get(index).setMoney(players.get(index).getMoney() + payment);
	}

	public void payUtility(Player player, Space space) {
		int initialPayment;
		List<String> properties = players.get(space.getOwnedBy()).getOwnedProperties();

		if(properties.contains("Water Works")&&properties.contains("Electric Company")){
			initialPayment=10;
		}else {
			initialPayment=4;
		}
		player.setMoney(player.getMoney() - (initialPayment*dieValue));
		players.get(space.getOwnedBy()).setMoney(players.get(space.getOwnedBy()).getMoney() + (initialPayment*dieValue));
	}

	public void payTax(Player player, Space space) {
		if(space.getName().equals("Luxury Tax")){
			player.setMoney(player.getMoney() - space.getPrice());
		} else {
			player.setMoney(player.getMoney() - Math.min(200,(player.getMoney()/10)));
		}
	}

	public void chooseToBuy(Player player, Space space) {
		Scanner input = new Scanner(System.in);
		System.out.print("Would you like to purchase " + space.getName() + " for $" + space.getPrice() + "? Y/N");
		String choice = input.nextLine();
		
		if (choice.equals("Y") || choice.equals("y")) {
			player.setMoney(player.getMoney() - space.getPrice()); // Player buys property.
			player.addOwnedProperties(space.getName()); // Add property to players Owned Properties list.
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
		// If end turn: turnOver = true;
	}

	public void build(Player player, Space space) {

	}

	public void trade(Player playerSending, Player playerReceiving) {
		
	}

	public void endTurn() {
		turnOver = true;
	}  

//	
//	public Square movePlayer(Player player, int face, boolean count) {
//		if(player.isBrokeOut()){ return spaces[player.getCurrentPosition()]; }
//		int newPosition = normalizePosition(player.getCurrentPosition() + face);
//		player.setPosition(newPosition);
//		Util.print(player, player.getName() + " goes to " + spaces[player.getCurrentPosition()].getName());
//		
//		// Space Action - Transaction Start
//		spaces[newPosition].doAction(player, this);
//		if(player.getMoney().isBrokeOut()){
//			Util.print(player, player.getName() + " has been broke out!");
//			player.setBrokeOut(true);
//		}else{
//			if(count){
//				player.nextTurn();
//			}
//		}
//		return spaces[newPosition];
//	}
	
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

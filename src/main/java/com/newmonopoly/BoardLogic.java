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

public class BoardLogic {
	String currentSeason;
	Random rand;
	Gson gson;
	BoardModel bm;
	
	// Constructor
	public BoardLogic(BoardModel bm) throws Exception {
		gson = new Gson();
		this.bm = bm;
		// Create Board table
		// Table include: board_id, newMonopoly_id, player list, currentTurn, totalPlayer, turnOver, spaces lists, chance list, community list, playerIndex, housesAvailable, hotelsAvailable, dieValue
	}

	public List<Space> decideSeasonsAndOrder(List<Space> originalSpaces, boolean randomizeSet) {
		if (originalSpaces.isEmpty()) {
			throw new IllegalArgumentException("In decideSeasonsAndOrder: List<Space> originalSpaces is empty.");
		}

		// Updates all the seasons in the database.
		List<Space> tempSpaces = new Vector<Space>();
		List<Space> newSpaces = new Vector<Space>();
        List<Integer> strongSeasons = new ArrayList<Integer>();
		List<Integer> weakSeasons = new ArrayList<Integer>();
		List<Integer> groups = new ArrayList<Integer>();
		int strongRand;
        int weakRand;
        int group1;
        int group2;
        int groupsIndex;
        Space space;
        Iterator<Space> itr;

        for (int i = 0; i < 4; i++) {
            strongSeasons.add(i);
		}

        for (int i = 0; i < 4; i++) {
            weakSeasons.add(i);
		}

        do {
            Collections.shuffle(strongSeasons);
            Collections.shuffle(weakSeasons);
        } while (strongSeasons.equals(weakSeasons));
		
        for (int i = 1; i < 9; i++) {
            groups.add(i);
		}

		Collections.shuffle(groups);
		groupsIndex = 0;
		itr = originalSpaces.iterator();

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

	public void movePlayer(Player player, int value) {
		if (player == null) {
			throw new IllegalArgumentException("In movePlayer: Player player is null.");
		}

		bm.setDieValue(value);
		bm.setTurnOver(false);

		if ( ((player.getCurrentPosition() + value) % 40) < (player.getCurrentPosition())) {
			addFunds(player, 200);
			System.out.println(player.getName() + " collected $200 for passing GO.");
		}

		player.setCurrentPosition((player.getCurrentPosition() + value) % 40);
		// Update player
		System.out.println(player.getName() + " landed on " + bm.getSpaces().get(player.getCurrentPosition()).getName());
		transaction(player);
	}

	public void movePlayer(Player player, String spaceName) {
		if (player == null) {
			throw new IllegalArgumentException("In movePlayer: Player player is null.");
		}

		for (Space space : bm.getSpaces()) {
			if (space.getName().equals(spaceName)) {
				if (bm.getSpaces().indexOf(space) < player.getCurrentPosition()) {
					addFunds(player, 200);
					System.out.println(player.getName() + " collected $200 for passing GO.");
				}

				player.setCurrentPosition(bm.getSpaces().indexOf(space));
				// Update: player
				System.out.println(player.getName() + " moved to " + space.getName());
			}
		}

		performSpaceAction(player, bm.getSpaces().get(player.getCurrentPosition()));
	}

	public void moveToPosition(Player player, int position) {
		if (player == null) {
			throw new IllegalArgumentException("In moveToPosition: Player player is null.");
		}

		if (position < 0) {
			player.setCurrentPosition(player.getCurrentPosition() + position);
		}
		else {
			if (position < player.getCurrentPosition()) {
				addFunds(player, 200);
				System.out.println(player.getName() + " collected $200 for passing GO.");
			}

			player.setCurrentPosition(position);
		}
		// Update player
		System.out.println(player.getName() + " moved to " + bm.getSpaces().get(player.getCurrentPosition()).getName());
		performSpaceAction(player, bm.getSpaces().get(player.getCurrentPosition()));
	}

	public void moveToNearest(Player player, String type) {
		if (player == null) {
			throw new IllegalArgumentException("In moveToNearest: Player player is null.");
		}

		if (type.isEmpty()) {
			throw new IllegalArgumentException("In moveToNearest: String type is empty.");
		}

		boolean nearestFound = false;

		for (int i = 1; i < 40 && !nearestFound; i++) {
			if ((bm.getSpaces().get((player.getCurrentPosition() + i) % 40).getType().equals(type))) {
				if (player.getCurrentPosition() > (player.getCurrentPosition() + i) % 40) {
					addFunds(player, 200);
					System.out.println(player.getName() + " collected $200 for passing GO.");
				}

				player.setCurrentPosition((player.getCurrentPosition() + i) % 40);
				// Update player
				System.out.println(player.getName() + " landed on " + bm.getSpaces().get(player.getCurrentPosition()).getName());
				nearestFound = true;
			}
		}

		performSpaceAction(player, bm.getSpaces().get(player.getCurrentPosition()));
	}

	public void addFunds(Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In addFunds: Player player is null.");
		}

		player.setMoney(player.getMoney() + payment);
		// Update player
		System.out.println(player.getName() + "'s new money total: " + player.getMoney() + "\n");
	}

	public void removeFunds(Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In removeFunds: Player player is null.");
		}

		if((player.getMoney() - payment) < 0){
			mortgage(player, (player.getMoney() - payment));
		}

		player.setMoney(player.getMoney() - payment);
		// Update player
		System.out.println(player.getName() + "'s new money total: " + player.getMoney() + "\n");
	}

	public void repairs(Player player, int housePrice, int hotelPrice) {
		if (player == null) {
			throw new IllegalArgumentException("In repairs: Player player is null.");
		}

		for (Space space : bm.getSpaces()){
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
		if (player == null) {
			throw new IllegalArgumentException("In giveToPlayers: Player player is null.");
		}

		for (Player otherPlayer : players) {
			if (otherPlayer != null) {
				removeFunds(player, payment);
				addFunds(otherPlayer, payment);
			}
		}
	}

	public void takeFromPlayers(Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In takeFromPlayers: Player player is null.");
		}

		for (Player otherPlayer : players) {
			if (otherPlayer != null) {
				removeFunds(otherPlayer, payment);
				addFunds(player, payment);
			}
		}
	}

	public void movePlayerToJail(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In movePlayerToJail: Player player is null.");
		}

		player.setCurrentPosition(10);
		player.setInJail(true);
		player.setJailTime(3);
		// Update player
		System.out.println(player.getName() + " has been moved to jail.");
	}

	public void transaction(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In transaction: Player player is null.");
		}

		performSpaceAction(player, bm.getSpaces().get(player.getCurrentPosition()));
		if (player.getMoney() <= 0) {
			turnOver = true;
			// Update turnOver
			// End turn called here.
		}
		while (!turnOver) {
			playerDecision(player);
		}
	}

	public void performSpaceAction(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In performSpaceAction: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In performSpaceAction: Space space is null.");
		}
		
		switch (space.getType()) {
			case "gotojail":
				movePlayerToJail(player);
				break;
			case "property":
				if ((space.getOwnedBy() != (playerIndex)) && space.getOwnedBy() > -1) {
					if (!space.isMortgaged()) {
						payRent(player, space);					
					}
					else {
						System.out.println(space.getName() + " is currently mortgaged.");
					}
				}
				else if (space.getOwnedBy() == playerIndex) {
					break;
				}
				else {
					if((player.getMoney() - space.getPrice()) >= 0) {
						chooseToBuy(player, space);
					}
					else {
						System.out.println("You cannot buy this property!");
						break;
					}
				}
				break;
			case "railroad":
				if ((space.getOwnedBy() != (playerIndex)) && space.getOwnedBy() > -1) {
					if (!space.isMortgaged()) {
						payRailroad(player, space);
					}
					else {
						System.out.println(space.getName() + " is currently mortgaged.");
					}
				}
				else if (space.getOwnedBy() == playerIndex) {
					break;
				} 
				else {
					chooseToBuy(player, space);
				}
				break;
			case "utility":
				if ((space.getOwnedBy() != (playerIndex)) && space.getOwnedBy() > -1) {
					if(!space.isMortgaged()) {	
						payUtility(player, space);
					}
					else {
						System.out.println(space.getName() + " is currently mortgaged.");
					}
				}
				else if (space.getOwnedBy() == playerIndex) {
					break;
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

		// Update: player, space, chance, community
	}

	public void payRent(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In payRent: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In payRent: Space space is null.");
		}

		// Rent will be changed in database in the build() method
		int rent = space.getCurrentRent();
		int index = space.getOwnedBy();
		removeFunds(player, rent);
		addFunds(players.get(index), rent);
	}

	public void payRailroad(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In payRailroad: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In payRailroad: Space space is null.");
		}

		int payment = 25;
		int index = space.getOwnedBy();

		for (int i = 0; i < 4; i++) {
			if (index == bm.getSpaces().get(10*i + 5).getOwnedBy()) {
				payment *= 2;
			}
		}

		removeFunds(player, payment);
		addFunds(players.get(index), payment);
	}

	public void payUtility(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In payUtility: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In payUtility: Space space is null.");
		}

		int initialPayment;
		boolean waterWorks = false;
		boolean electricCompany = false;
		List<Space> properties = players.get(space.getOwnedBy()).getOwnedProperties();

		for (Space utility : bm.getSpaces()) {
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
		if (player == null) {
			throw new IllegalArgumentException("In payTax: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In payTax: Space space is null.");
		}

		if(space.getName().equals("Luxury Tax")){
			addFunds(player, space.getPrice());
		} else {
			removeFunds(player, Math.min(200,(player.getMoney()/10)));
		}
	}

	public void chooseToBuy(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In chooseToBuy: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In chooseToBuy: Space space is null.");
		}

		System.out.print("Would you like to purchase " + space.getName() + " for $" + space.getPrice() + "? Y/N ");
		char choice = getCharInput();
		
		if (choice == 'Y' || choice == 'y') {
			if (player.getMoney() - space.getPrice() > 0) {
				System.out.println(player.getName() + " purchased " + space.getName());
				removeFunds(player, space.getPrice()); // Player buys property.
				player.addOwnedProperties(space); // Add property to players Owned Properties list.
				space.setOwnedBy(players.indexOf(player));
				addMonopoly(player, space);
			}
			else {
				System.out.println("You can't afford " + space.getName());
			}
		}
		// Player does not want property.
		else {
			auction(space); // All players eligible for auction.
		}
	}

	public void auction(Space space) {
		if (space == null) {
			throw new IllegalArgumentException("In payUtility: Space space is null.");
		}
		System.out.println(space.getName() + " original sale price is $" + space.getPrice());
		List<Integer> offers = new Vector<Integer>(Collections.nCopies(players.size(), 0));
		List<Boolean> stillInAuction = new Vector<Boolean>(Collections.nCopies(players.size(), true));
		int offer = 0;
		int count = 0;
		int max = 0;
		int index = -1;

		for (int i = 0; i < players.size(); i++){
			do {
				if (players.get(i).getMoney() > 0) {
					System.out.println(players.get(i).getName() + ", enter your bid or -1 to give up:");
					offer = getUserInput();
				}
				else {
					offer = -1;
					continue;
				}
			} while (players.get(i).getMoney() - offer <= 0 || offers.contains(offer) || (offer <= Collections.max(offers) && offer > -1));

			if (offer == -1) {
				stillInAuction.set(i, false);
			} else {
				offers.set(i, offer);
			}
		}

		do {
			for (int i = 0; i < players.size(); i++) {
				if (stillInAuction.get(i) && (Collections.frequency(stillInAuction, false) != (players.size()-1))) {
					do {
						System.out.println(players.get(i).getName() + ", enter your bid or -1 to give up:");
						offer = getUserInput();

					} while (players.get(i).getMoney() - offer <= 0 || offers.contains(offer) || (offer <= Collections.max(offers) && offer > -1));

					if (offer == -1) {
						stillInAuction.set(i, false);
					} else {
						offers.set(i, offer);
					}
				}

				offer = 0;
			}

			count++;
		} while ( count < 2 );

		max = Collections.max(offers);
		index = offers.indexOf(max);
		removeFunds(players.get(index), max); // Player buys property.
		players.get(index).addOwnedProperties(space); // Add property to players Owned Properties list.
		space.setOwnedBy(index);
		System.out.println(players.get(index).getName() + " has won the auction.");
		addMonopoly(players.get(index), space);
		// Update: player who won auction
	}

	public void drawCard(Player player, List<Card> cards) {
		if (player == null) {
			throw new IllegalArgumentException("In drawCard: Player player is null.");
		}

		if (cards.isEmpty()) {
			throw new IllegalArgumentException("In drawCard: List<Card> cards is empty.");
		}

		Card card = cards.get(0);
		System.out.println(player.getName() + " got the " + card.getTitle() + " card.");
		cards.add(cards.remove(0));
		switch (card.getAction()) {
			case "move":
				if(card.getPosition() != 0){
					moveToPosition(player, card.getPosition());
				} else{
					movePlayer(player, card.getSpaceName());
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
		if (player == null) {
			throw new IllegalArgumentException("In playerDecision: Player player is null.");
		}

		// Build improvements, trade, or end turn.
		System.out.println("\n/---------------------------------------\\");
		System.out.println("| Commands:                               |");
		System.out.println("| B: Build        T: Trade    D: Display  |");
		System.out.println("| P: Pay Mortgage Q: Quit     E: End Turn |");
		System.out.println("\\-----------------------------------------/\n");
		char ans = getCharInput();
		boolean hasMortgaged = false;

		if(ans == 'B') { // Build Improvements
			if (player.getMonopolyGroups().contains(1)) {
				findMonopolies(player);
				System.out.print("What property from the above list would you like to improve? ");
				int choice = getUserInput();

				if (player.getMonopolyProperties().get(choice - 1).getBuildings() < 5 && !player.getMonopolyProperties().get(choice -1).isMortgaged()) {
					build(player, player.getMonopolyProperties().get(choice - 1));
				} 
				else {
					System.out.println("You cannot build anymore on this property!");
				}
			}
			else {
				System.out.println("You have no monopolies to build on.");
			}
		}
		else if(ans == 'T') {	// Trade
			if (player.getOwnedProperties().size() > 0) {
				trade(player);
			}
			else {
				System.out.println("You have no properties to trade.");
			}
		}
		else if (ans == 'D') {
			System.out.println(player.toString());
			displayOwnedProperties(player);
		}
		else if (ans == 'P') {
			for (Space space : player.getOwnedProperties()) {
				if (space.isMortgaged()) {
					hasMortgaged = true;
					unmortgage(player);
					break;
				}
			}
			if (!hasMortgaged) {
				System.out.println("You have no mortgaged properties..");
			}
		}
		else if (ans == 'Q') {
			bankrupt(player);
			turnOver = true;
			// update turn over
		}
		else {	// End turn.
			turnOver = true;
			// update turn over
		}
	}

	public void mortgage(Player player, int debt) {
		// NOTE: ADD WAY TO IDENTIFY WHICH ONE IS MORTGAGE
		// Currently, selling a property includes selling all of the 
		// houses and hotels on that property. This will need to be
		// fixed in the future.
		if (player == null) {
			throw new IllegalArgumentException("In mortgage: Player player is null.");
		}

		Space property;
		boolean allMortgage = false;
		int choice = -1;

		while (debt <= 0 && !allMortgage) {
			for (Space space : player.getOwnedProperties()) {
				if (!space.isMortgaged()) {
					allMortgage = false;
					break;
				}
				else {
					allMortgage = true;
				}
			}

			if (allMortgage) {
				break;
			}

			System.out.println("Debt is currently at: $" + debt);
			displayOwnedProperties(player);
			System.out.println("Which property would you like to sell?");

			try {
				do {
					choice = getUserInput();
				} while(player.getOwnedProperties().get(choice -1 ).isMortgaged());
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			if (choice > 0) {
				property = player.getOwnedProperties().get(choice-1);
				addFunds(player, (debt + (property.getPrice() / 2) + (property.getBuildings() * property.getHouseCost() / 2)));
				debt += (property.getPrice() / 2) + (property.getBuildings() * property.getHouseCost() / 2);

				if (property.getBuildings() == 5) {
					hotelsAvailable += 1;
				}
				else {
					housesAvailable += property.getBuildings();
				}

				player.getOwnedProperties().get(choice-1).setMortgaged(false);
				// Updates spaces, player, hotelsAvailable, housesAvailable
			}
		}

		if (allMortgage && debt <= 0) {
			bankrupt(player);
		}
	}

	public void unmortgage(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In unmortgage: Player player is null.");
		}

		displayOwnedProperties(player);
		System.out.println("Which property would you like to pay off?");
		int choice = 0;
		Space property;

			try {
				do {
					choice = getUserInput();
				} while(!player.getOwnedProperties().get(choice -1 ).isMortgaged());
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			if (choice > 0) {
				property = player.getOwnedProperties().get(choice-1);
				removeFunds(player, (int) ((property.getPrice() / 2) * 1.1));
				player.getOwnedProperties().get(choice-1).setMortgaged(true);
				System.out.println(property.getName() + " is now paid off!");
				// Update player, space
			}
	}

	public void bankrupt(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In bankrupt: Player player is null.");
		}

		int i = 0;
		System.out.println(player.getName() + " declared bankruptcy. Their owned properties will be auctioned.");
		displayOwnedProperties(player);
		System.out.println("Beginning auctions...");
		player.setMoney(0);
		// Update player
		for (Space space : player.getOwnedProperties()) {
			space.setMortgaged(false);
			// Update space
			auction(space);
		}

		players.set(playerIndex, null);
		// Update players
		System.out.println("\nPlayers remaining:");

		for (Player p : players) {
			if (p != null) {
				System.out.println("\t" + ++i + ". " + p.getName());
			}
		}
	}

	// Display only
	public void displayOwnedProperties(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In displayOwnedProperties: Player player is null.");
		}

		int total;
		int i = 0;

		if (player.getOwnedProperties().size() > 0) {
			Collections.sort(player.getOwnedProperties());
			System.out.println(player.getName() + " owns these properties:");
			System.out.println("#    Property Name           Group    Bldg No.    Bldg Val    Mortgage    Total    isMortgaged");

			for (Space space : player.getOwnedProperties()) {
				total = (space.getPrice() / 2) + (space.getBuildings() * space.getHouseCost() / 2);
				System.out.printf("%d    %-20s      %d         %d         $%-5d      $%-5d     $%-5d        %b\n",++i ,space.getName(), space.getGroup(), space.getBuildings(), (space.getHouseCost() / 2), (space.getPrice() / 2), total, space.isMortgaged());
			}
		}
		else {
			System.out.println(player.getName() + " owns no properties.");
		}
	}

	// Display only
	public void displayPlayersWithProperties() {
		int i = 0;
		System.out.println("The following players own properties:");
		System.out.println("#    Player Name         No. of Properties");

		for (Player player : players) {
			i++;
			if (player != null) {
				if (player == getCurrentPlayer()) {
					continue;
				}
				else if (player.getOwnedProperties().size() > 0) {
					System.out.printf("%d    %-16s            %d\n", i, player.getName(), player.getOwnedProperties().size());
				}
			}
		}
	}

	public int getUserInput() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the value of your choice: ");
		// retrieve int input
		return sc.nextInt();
	}

	public char getCharInput() {
		Scanner sc = new Scanner(System.in);
		// retrieve char input
		return sc.next().charAt(0);
	}

	public void build(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In build: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In build: Space space is null.");
		}

		System.out.println(space.getName() + " currently has " + space.getBuildings() + " houses.");
		boolean valid = true;
		int maxHouses = 0;

		for (Space otherSpace : player.getOwnedProperties()) {
			if (otherSpace.getGroup() == space.getGroup() && otherSpace != space) {
				if (otherSpace.getBuildings() > maxHouses) {
					maxHouses = otherSpace.getBuildings();
				}
			}
		}
		do {
			System.out.print("How many more houses would you like to add? ");
			int additions = getUserInput();
			if (additions + space.getBuildings() > maxHouses + 1) {
				valid = false;
				System.out.println("You need to even build! Please try again.");
			}
			else if (additions + space.getBuildings() > 5) {
				valid = false;
				System.out.println("You entered an invalid amount of houses! Please try again.");
			}
			else if (player.getMoney() - additions*space.getHouseCost() < 0) {
				valid = false;
				System.out.println("You cannot afford to purchase these houses! Please try again.");
			}
			else if (additions == -1) {
				break;
			}
			else {
				valid = true;
				removeFunds(player, additions*space.getHouseCost());
				space.setBuildings(space.getBuildings() + additions);
				if (space.getBuildings() == 5) {
					housesAvailable += 4;
					hotelsAvailable--;
				}
				else {
					housesAvailable -= additions;
				}
			}
		} while (!valid);

		// Update player, space, housesAvailable, hotelsAvailable
	}

	public void trade(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In trade: Player player is null.");
		}

		// Currently a player may only trade one property for one other property
		displayPlayersWithProperties();
		int choice;
		int myChoice;    // Represents which property to give away
		int tradeChoice; // Represents which property to take
		Player tradePlayer;
		char approvalChoice;
		boolean approved = false;
		boolean ended = false;

		do {
			choice = getUserInput();
		} while (choice == playerIndex + 1 || players.get(choice - 1) == null);

		tradePlayer = players.get(choice  - 1);

		while (!approved && !ended) {
			displayOwnedProperties(player);
			System.out.println("Select your property to offer:");
			
			do {
				myChoice = getUserInput();				
			} while (player.getOwnedProperties().get(myChoice -1).isMortgaged());
			 
			displayOwnedProperties(tradePlayer);
			System.out.println("Select the property you want:");

			do {
				tradeChoice = getUserInput();			
			} while (tradePlayer.getOwnedProperties().get(tradeChoice -1).isMortgaged());

			System.out.println("Does other player approve? Y/N, X to exit ");
			approvalChoice = getCharInput();

			if (approvalChoice == 'Y' || approvalChoice == 'y') {
				player.addOwnedProperties(tradePlayer.getOwnedProperties().get(tradeChoice - 1));
				tradePlayer.addOwnedProperties(player.getOwnedProperties().get(myChoice - 1));
				player.removeOwnedProperties(myChoice - 1);
				tradePlayer.removeOwnedProperties(tradeChoice - 1);
				player.getOwnedProperties().get(player.getOwnedProperties().size()-1).setOwnedBy(players.indexOf(player));
				tradePlayer.getOwnedProperties().get(tradePlayer.getOwnedProperties().size()-1).setOwnedBy(players.indexOf(tradePlayer));
				addMonopoly(player, player.getOwnedProperties().get(player.getOwnedProperties().size()-1));
				addMonopoly(tradePlayer, tradePlayer.getOwnedProperties().get(tradePlayer.getOwnedProperties().size()-1));
				approved = true;
				// Update player, space
			}
			else if (approvalChoice == 'X' || approvalChoice == 'x') {
				ended = true;
			}
		}
	}

	// Checks if player has new monopolies.
	public void addMonopoly(Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In addMonopoly: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In addMonopoly: Space space is null.");
		}

		boolean newMonopoly = true;

		if (space.getType().equals("railroad") || space.getType().equals("utility")) {
			newMonopoly = false;
		}
		else {
			for (Space s : bm.getSpaces()) {
				if (s.getGroup() == space.getGroup()) {
					if(s.getType().equals("property")) {
						if (s.getOwnedBy() != players.indexOf(player)) {
							newMonopoly = false;
							break;
						}
					}
				}
			}
		}

		if (newMonopoly) {
			System.out.println(player.getName() + " has a monopoly on group " + space.getGroup());
			player.addMonopolyGroup(space.getGroup()-1);

			for (Space s : bm.getSpaces()) {
				if (s.getGroup() == space.getGroup()) {
					if (s.getType().equals("property")) {
						s.setCurrentRent(s.getRent() * 2);
						player.addMonopolyProperties(s);
					}
				}
			}
		}
	}

	// Finds and prints the list of player's monopolies.
	// Display only
	public void findMonopolies(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In findMonopolies: Player player is null.");
		}

		int total = 0;
		int i = 0;
		Collections.sort(player.getMonopolyProperties());
		System.out.println("#    Property Name           Group    Bldg No.    Bldg Val    Mortgage    Total    isMortgaged");

		for (Space space : player.getMonopolyProperties()) {
			total = (space.getPrice() / 2) + (space.getBuildings() * space.getHouseCost() / 2);
			System.out.printf("%d    %-20s      %d         %d         $%-5d      $%-5d     $%-5d        %b\n",++i ,space.getName(), space.getGroup(), space.getBuildings(), (space.getHouseCost() / 2), (space.getPrice() / 2), total, space.isMortgaged());
		}
	}

	// Return a boolean if the game has ended.
	// If there is only one player that is not bankrupt, then the game will terminate.
	public boolean hasWinner() {
		int playersInGame = 0;

		for (Player player : players) {
			if (player != null) {
				if(player.getMoney() > 0) {
					playersInGame++;
				}
			}
		}

		return playersInGame <= 1;
	}
	
	// Return the winner of the game.
	public Player getWinner() {
		return getCurrentPlayer();
	}

	public Player getCurrentPlayer() {
		return players.get(playerIndex);
	}

	public void nextTurn() {
		do {
			playerIndex++;
			if (playerIndex == players.size()) { // If playerIndex has reached the end of the list, set playerIndex back to zero.
				playerIndex = 0;
			}
		} while(players.get(playerIndex) == null);
	}

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

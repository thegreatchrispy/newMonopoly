package com.newmonopoly.service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.lang.Math;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.hibernate.mapping.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newmonopoly.model.Board;
import com.newmonopoly.model.Card;
import com.newmonopoly.model.Player;
import com.newmonopoly.model.Space;
import com.newmonopoly.repository.BoardRepository;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public List<Space> decideSeasonsAndOrder(Board board, boolean randomizeSet) {
		Gson gson = new Gson();
		List<Space> originalSpaces = new Vector<Space>();
		List<Card> chance = new Vector<Card>();
		List<Card> community = new Vector<Card>();

		try {
			originalSpaces = gson.fromJson(new FileReader("/Users/isaacperalez/newMonopoly/src/main/java/com/newmonopoly/service/spaces.json"), new TypeToken<List<Space>>(){}.getType());
			chance = gson.fromJson(new FileReader("/Users/isaacperalez/newMonopoly/src/main/java/com/newmonopoly/service/chance.json"), new TypeToken<List<Card>>(){}.getType());
			community = gson.fromJson(new FileReader("/Users/isaacperalez/newMonopoly/src/main/java/com/newmonopoly/service/community.json"), new TypeToken<List<Card>>(){}.getType());
			Collections.shuffle(chance);
			Collections.shuffle(community);
			board.setChance(chance);
			board.setCommunity(community);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Updates all the seasons in the database.
		List<Space> tempSpaces = new Vector<Space>();
		List<Space> newSpaces = new Vector<Space>();
		List<Integer> strongSeasons = new Vector<Integer>();
		List<Integer> weakSeasons = new Vector<Integer>();
		List<Integer> groups = new Vector<Integer>();
		int strongRand;
		int weakRand;
		int group1;
		int group2;
		int groupsIndex;
		Space space;
		Iterator<Space> itr;
		String swapped = "";

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
			swapped += group1 + " " + group2 + ";";
			
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
		board.setSwappedString(swapped);
		return newSpaces;
	}

	@Override
	public int getUserInput(Board board) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the value of your choice: ");
		// retrieve int input
		return sc.nextInt();
	}

	@Override
	public char getCharInput(Board board) {
		Scanner sc = new Scanner(System.in);
		// retrieve char input
		return sc.next().charAt(0);
	}

	@Override
	public String movePlayer(Board board, Player player, int value) {
		if (player == null) {
			throw new IllegalArgumentException("In movePlayer: Player player is null.");
		}

		board.setDieValue(value);
		board.setTurnOver(false);
		String string = "";

		if ( ((player.getCurrentPosition() + value) % 40) < (player.getCurrentPosition())) {
			addFunds(board, player, 200);
			string += player.getName() + " has collected $200 for passing GO.<br>";
			System.out.println(player.getName() + " collected $200 for passing GO.");
		}

		player.setCurrentPosition((player.getCurrentPosition() + value) % 40);
		// Update player
		string += player.getName() + " landed on " + board.getSpaces().get(player.getCurrentPosition()).getName() + ".";
		return string;
		//transaction(board, player);
	}

	@Override
	public String movePlayer(Board board, Player player, String spaceName) {
		if (player == null) {
			throw new IllegalArgumentException("In movePlayer: Player player is null.");
		}

		int newIndex = 0;
		for (Space space : board.getSpaces()) {
			if (space.getName().equals(spaceName)) {
				break;
			}
			newIndex++;
		}

		String string = "";

		if (newIndex < player.getCurrentPosition()) {
			addFunds(board, player, 200);
			string += player.getName() + " has collected $200 for passing GO.<br>";
			System.out.println(player.getName() + " collected $200 for passing GO.");
		}
		player.setCurrentPosition(newIndex);
		// Update: player
		string += player.getName() + " moved to " + spaceName + ".";
	
		return string;
	}

	@Override
	public String movePlayerToJail(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In movePlayerToJail: Player player is null.");
		}

		player.setCurrentPosition(10);
		player.setInJail(true);
		player.setJailTime(3);
		// Update player
		return player.getName() + " has been locked up!";
	}

	@Override
	public String moveToNearest(Board board, Player player, String type) {
		if (player == null) {
			throw new IllegalArgumentException("In moveToNearest: Player player is null.");
		}

		if (type.isEmpty()) {
			throw new IllegalArgumentException("In moveToNearest: String type is empty.");
		}

		boolean nearestFound = false;
		String string = "";

		for (int i = 1; i < 40 && !nearestFound; i++) {
			if ((board.getSpaces().get((player.getCurrentPosition() + i) % 40).getType().equals(type))) {
				if (player.getCurrentPosition() > (player.getCurrentPosition() + i) % 40) {
					addFunds(board, player, 200);
					System.out.println(player.getName() + " collected $200 for passing GO.");
				}

				player.setCurrentPosition((player.getCurrentPosition() + i) % 40);
				// Update player
				string = player.getName() + " landed on " + board.getSpaces().get(player.getCurrentPosition()).getName() + ".";
				nearestFound = true;
			}
		}

		return string;
	}

	@Override
	public String moveToPosition(Board board, Player player, int position) {
		if (player == null) {
			throw new IllegalArgumentException("In moveToPosition: Player player is null.");
		}

		if (position < 0) {
			player.setCurrentPosition(player.getCurrentPosition() + position);
		}
		else {
			if (position < player.getCurrentPosition()) {
				addFunds(board, player, 200);
				System.out.println(player.getName() + " collected $200 for passing GO.");
			}

			player.setCurrentPosition(position);
		}
		// Update player
		return player.getName() + " moved to " + board.getSpaces().get(player.getCurrentPosition()).getName() + ".";
	}

	@Override
	public void addFunds(Board board, Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In addFunds: Player player is null.");
		}

		player.setMoney(player.getMoney() + payment);
		// Update player
		System.out.println(player.getName() + "'s new money total: " + player.getMoney() + "\n");
	}

	@Override
	public void takeFromPlayers(Board board, Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In takeFromPlayers: Player player is null.");
		}

		for (Player otherPlayer : board.getPlayers()) {
			if (otherPlayer != null) {
				removeFunds(board, otherPlayer, payment);
				addFunds(board, player, payment);
			}
		}
	}

	@Override
	public void removeFunds(Board board, Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In removeFunds: Player player is null.");
		}

		if((player.getMoney() - payment) < 0){
			mortgage(board, player, (player.getMoney() - payment));
		}

		player.setMoney(player.getMoney() - payment);
		// Update player
		System.out.println(player.getName() + "'s new money total: " + player.getMoney() + "\n");
	}

	@Override
	public void repairs(Board board, Player player, int housePrice, int hotelPrice) {
		if (player == null) {
			throw new IllegalArgumentException("In repairs: Player player is null.");
		}

		for (Space space : board.getSpaces()){
			if(space.getOwnedBy() == getPlayerIndex(board, player)){
				if(space.getBuildings() == 5){
					removeFunds(board, player, ((housePrice * 4) - hotelPrice));
				}
				else {
					removeFunds(board, player, (housePrice * space.getBuildings()));
				}
			}
		}
	}

	@Override
	public void giveToPlayers(Board board, Player player, int payment) {
		if (player == null) {
			throw new IllegalArgumentException("In giveToPlayers: Player player is null.");
		}

		for (Player otherPlayer : board.getPlayers()) {
			if (otherPlayer != null) {
				removeFunds(board, player, payment);
				addFunds(board, otherPlayer, payment);
			}
		}
	}

	@Override
	public void transaction(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In transaction: Player player is null.");
		}

		if (player.getMoney() <= 0) {
			board.setTurnOver(true);
			// Update turnOver
			// End turn called here.
		}

		while (!board.getTurnOver()) {
			playerDecision(board, player);
		}
	}

	// @Override
	// public String getSpaceAction(Board board, Player player) {
	// 	Space space = board.getSpaces().get(player.getCurrentPosition());
	// 	System.out.println(space.getType());
	// 	return space.getType();
	// }

	@Override
	public String performSpaceAction(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In performSpaceAction: Player player is null.");
		}
		Space space = board.getSpaces().get(player.getCurrentPosition());
		if (space == null) {
			throw new IllegalArgumentException("In performSpaceAction: Space space is null.");
		}

		String string = "";
		
		switch (space.getType()) {
			case "gotojail":
				string = movePlayerToJail(board, player) + ";movingToJail";
				break;
			case "property":
				if ((space.getOwnedBy() != (getPlayerIndex(board, player))) && space.getOwnedBy() > -1) {
					if (!space.isMortgaged()) {
						//payRent(board, player, space);
						string = payRent(board, player, board.getPlayers().get(space.getOwnedBy())) + ";moneyChange";					
					}
					else {
						//System.out.println(space.getName() + " is currently mortgaged.");
						string = "Property is currently mortgaged!;mortgaged";
					}
				}
				else if (space.getOwnedBy() == getPlayerIndex(board, player)) {
					string = "You already own this space, " + player.getName() + ".;nothing";
					break;
				}
				else {
					if((player.getMoney() - space.getPrice()) >= 0) {
						string = askToBuy(board, player) + ";chooseToBuy";
						//chooseToBuy(board, player, space);
					}
					else {
						//System.out.println("You cannot buy this property!");
						string = "You cannot afford this right now!;nothing";
						break;
					}
				}
				break;
			case "railroad":
				if ((space.getOwnedBy() != (getPlayerIndex(board, player))) && space.getOwnedBy() > -1) {
					if (!space.isMortgaged()) {
						//payRailroad(board, player, space);
						string = payRailroad(board, player, board.getPlayers().get(space.getOwnedBy())) + ";moneyChange";
					}
					else {
						//System.out.println(space.getName() + " is currently mortgaged.");
						string = "Property is currently mortgaged!;mortgaged";
					}
				}
				else if (space.getOwnedBy() == getPlayerIndex(board, player)) {
					string = "You already own this space, " + player.getName() + ".;nothing";
					break;
				} 
				else {
					if((player.getMoney() - space.getPrice()) >= 0) {
						string = askToBuy(board, player) + ";chooseToBuy";
						//chooseToBuy(board, player, space);
					}
					else {
						//System.out.println("You cannot buy this property!");
						string = "You cannot afford this right now!;nothing";
						break;
					}
				}
				break;
			case "utility":
				if ((space.getOwnedBy() != (getPlayerIndex(board, player))) && space.getOwnedBy() > -1) {
					if(!space.isMortgaged()) {	
						//payUtility(board, player, space);
						string = payUtility(board, player, board.getPlayers().get(space.getOwnedBy())) + ";moneyChange";
					}
					else {
						string = "Property is currently mortgaged!;mortgaged";
						//System.out.println(space.getName() + " is currently mortgaged.");
					}
				}
				else if (space.getOwnedBy() == getPlayerIndex(board, player)) {
					string = "You already own this space, " + player.getName() + ".;nothing";
					break;
				}
				else {
					if((player.getMoney() - space.getPrice()) >= 0) {
						string = askToBuy(board, player) + ";chooseToBuy";
						//chooseToBuy(board, player, space);
					}
					else {
						//System.out.println("You cannot buy this property!");
						string = "You cannot afford this right now!;nothing";
						break;
					}
				}
				break;
			case "tax":
				string = payTax(board, player, space) + ";moneyChange";
				break;
			case "comchest":
				string = drawCard(board, player, "comchest");
				break;
			case "chance":
				string = drawCard(board, player, "chance");
				break;
			default:
				string = ";nothing";
				break;
		}
		System.out.println(string);
		return string;
		// Update: player, space, chance, community
	}

	@Override
	public String drawCard(Board board, Player player, String type) {
		if (player == null) {
			throw new IllegalArgumentException("In drawCard: Player player is null.");
		}

		List<Card> cards = new Vector<Card>();
		if (type.equals("comchest")) {
			cards = board.getCommunity();
		} else {
			cards = board.getChance();
		}

		if (cards == null) {
			throw new IllegalArgumentException("In drawCard: List<Card> cards is empty.");
		}

		String string = "";
		Card card = cards.get(0);
		string = player.getName() + " got the " + card.getTitle() + " card.";
		cards.add(cards.remove(0));

		if (type.equals("comchest")) {
			board.setCommunity(cards);
		} else {
			board.setChance(cards);
		}

		switch (card.getAction()) {
			case "move":
				if(card.getPosition() != 0){
					string += "<br>" + moveToPosition(board, player, card.getPosition()) + ";move";
				} else{
					string += "<br>" + movePlayer(board, player, card.getSpaceName()) + ";move";
				}
				break;
			case "movenearest":
				string += "<br>" + moveToNearest(board, player, card.getType()) + ";move";
				break;
			case "addfunds":
				addFunds(board, player, card.getPayment());
				string += ";moneyChange";
				break;
			case "removefunds":
				removeFunds(board, player, card.getPayment());
				string += ";moneyChange";
				break;
			case "repairs":
				repairs(board, player, card.getHouses(), card.getHotels());
				string += ";moneyChange";
				break;
			case "givetoplayers":
				giveToPlayers(board, player, card.getPayment());
				string += ";moneyChange";
				break;
			case "takefromplayers":
				takeFromPlayers(board, player, card.getPayment());
				string += ";moneyChange";
				break;
			case "gotojail":
				string += "<br>" + movePlayerToJail(board, player) + ";movingToJail";
				break;
			case "outjail":
				player.setJailCard(true);
				string += ";nothing";
				break;
			default:
				break;
		}
		System.out.println(string);
		return string;
		// Call a new Card object dependent on the type.
	}

	@Override
	public void build(Board board, Player player, Space space) {
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
			int additions = getUserInput(board);
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
				removeFunds(board, player, additions*space.getHouseCost());
				space.setBuildings(space.getBuildings() + additions);
				if (space.getBuildings() == 5) {
					board.setHousesAvailable(board.getHousesAvailable() + 4);
					board.setHotelsAvailable(board.getHotelsAvailable() - 1);
				}
				else {
					board.setHousesAvailable(board.getHousesAvailable() - additions);
				}
			}
		} while (!valid);

		// Update player, space, housesAvailable, hotelsAvailable
	}

	@Override
	public void trade(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In trade: Player player is null.");
		}

		// Currently a player may only trade one property for one other property
		displayPlayersWithProperties(board);
		int choice;
		int myChoice;    // Represents which property to give away
		int tradeChoice; // Represents which property to take
		Player tradePlayer;
		char approvalChoice;
		boolean approved = false;
		boolean ended = false;

		do {
			choice = getUserInput(board);
		} while (choice == getPlayerIndex(board, player) + 1 || board.getPlayers().get(choice - 1) == null);

		tradePlayer = board.getPlayers().get(choice  - 1);

		while (!approved && !ended) {
			displayOwnedProperties(board, player);
			System.out.println("Select your property to offer:");
			
			do {
				myChoice = getUserInput(board);				
			} while (player.getOwnedProperties().get(myChoice -1).isMortgaged());
			 
			displayOwnedProperties(board, tradePlayer);
			System.out.println("Select the property you want:");

			do {
				tradeChoice = getUserInput(board);			
			} while (tradePlayer.getOwnedProperties().get(tradeChoice -1).isMortgaged());

			System.out.println("Does other player approve? Y/N, X to exit ");
			approvalChoice = getCharInput(board);

			if (approvalChoice == 'Y' || approvalChoice == 'y') {
				player.addOwnedProperties(tradePlayer.getOwnedProperties().get(tradeChoice - 1));
				tradePlayer.addOwnedProperties(player.getOwnedProperties().get(myChoice - 1));
				player.removeOwnedProperties(myChoice - 1);
				tradePlayer.removeOwnedProperties(tradeChoice - 1);
				player.getOwnedProperties().get(player.getOwnedProperties().size()-1).setOwnedBy(getPlayerIndex(board, player));
				tradePlayer.getOwnedProperties().get(tradePlayer.getOwnedProperties().size()-1).setOwnedBy(getPlayerIndex(board, tradePlayer));
				//addMonopoly(board, player, player.getOwnedProperties().get(player.getOwnedProperties().size()-1));
				//addMonopoly(board, tradePlayer, tradePlayer.getOwnedProperties().get(tradePlayer.getOwnedProperties().size()-1));
				approved = true;
				// Update player, space
			}
			else if (approvalChoice == 'X' || approvalChoice == 'x') {
				ended = true;
			}
		}
	}

	// Checks if player has new monopolies.
	@Override
	public String addMonopolyAfterPurchase(Board board, Player player) {
		System.out.println("In addMonopoly");
		if (player == null) {
			throw new IllegalArgumentException("In addMonopoly: Player player is null.");
		}

		Space space = board.getSpaces().get(player.getCurrentPosition());

		if (space == null) {
			throw new IllegalArgumentException("In addMonopoly: Space space is null.");
		}

		boolean newMonopoly = true;
		String string = "";

		if (space.getType().equals("railroad") || space.getType().equals("utility")) {
			newMonopoly = false;
		}
		else {
			for (Space s : board.getSpaces()) {
				if (s.getGroup() == space.getGroup()) {
					if(s.getType().equals("property")) {
						if (s.getOwnedBy() != getPlayerIndex(board, player)) {
							newMonopoly = false;
							break;
						}
					}
				}
			}
		}

		if (newMonopoly) {
			string = player.getName() + " has a monopoly on group " + space.getGroup() + ".";
			player.addMonopolyGroup(space.getGroup()-1);

			for (Space s : board.getSpaces()) {
				if (s.getGroup() == space.getGroup()) {
					if (s.getType().equals("property")) {
						s.setCurrentRent(s.getRent() * 2);
						player.addMonopolyProperties(s);
						player.updateOwnedProperties(s);
					}
				}
			}
		}
		return string;
	}

	// Checks if player has new monopolies.
	@Override
	public String addMonopolyAfterAuction(Board board, Player index, Player player) {
		System.out.println("In addMonopoly");
		if (player == null) {
			throw new IllegalArgumentException("In addMonopoly: Player player is null.");
		}

		Space space = board.getSpaces().get(index.getCurrentPosition());

		if (space == null) {
			throw new IllegalArgumentException("In addMonopoly: Space space is null.");
		}

		boolean newMonopoly = true;
		String string = "";

		if (space.getType().equals("railroad") || space.getType().equals("utility")) {
			newMonopoly = false;
		}
		else {
			for (Space s : board.getSpaces()) {
				if (s.getGroup() == space.getGroup()) {
					if(s.getType().equals("property")) {
						if (s.getOwnedBy() != getPlayerIndex(board, player)) {
							newMonopoly = false;
							break;
						}
					}
				}
			}
		}

		if (newMonopoly) {
			string = player.getName() + " has a monopoly on group " + space.getGroup() + ".";
			player.addMonopolyGroup(space.getGroup()-1);

			for (Space s : board.getSpaces()) {
				if (s.getGroup() == space.getGroup()) {
					if (s.getType().equals("property")) {
						s.setCurrentRent(s.getRent() * 2);
						player.addMonopolyProperties(s);
						player.updateOwnedProperties(s);
					}
				}
			}
		}
		return string;
	}

	// Finds and prints the list of player's monopolies.
	// Display only
	@Override
	public void findMonopolies(Board board, Player player) {
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

	@Override
	public void bankrupt(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In bankrupt: Player player is null.");
		}

		int i = 0;
		System.out.println(player.getName() + " declared bankruptcy. Their owned properties will be auctioned.");
		displayOwnedProperties(board, player);
		System.out.println("Beginning auctions...");
		player.setMoney(0);
		// Update player
		for (Space space : player.getOwnedProperties()) {
			space.setMortgaged(false);
			// Update space
			auction(board, space);
		}

		board.getPlayers().set(getPlayerIndex(board, player), null);
		// Update players
		System.out.println("\nPlayers remaining:");

		for (Player p : board.getPlayers()) {
			if (p != null) {
				System.out.println("\t" + ++i + ". " + p.getName());
			}
		}
	}
	
	@Override
	public String askToBuy(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In chooseToBuy: Player player is null.");
		}

		Space space = board.getSpaces().get(player.getCurrentPosition());

		if (space == null) {
			throw new IllegalArgumentException("In chooseToBuy: Space space is null.");
		}

		return "Would you like to buy " + space.getName() + " for $" + space.getPrice() + ", " + player.getName() + "?";
	}

	@Override
	public String buy(Board board, Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In chooseToBuy: Player player is null.");
		}
		if (space == null) {
			throw new IllegalArgumentException("In chooseToBuy: Space space is null.");
		}
		String string = "";

		if (player.getMoney() - space.getPrice() > 0) {
			string = player.getName() + " purchased " + space.getName() + ".";
			removeFunds(board, player, space.getPrice()); // Player buys property.
			player.addOwnedProperties(space); // Add property to board.getPlayers() Owned Properties list.
			space.setOwnedBy(getPlayerIndex(board, player));
		}
		else {
			string = "You can't afford " + space.getName();
		}
		return string;
	}

	@Override
	public String buyAuction(Board board, Player player, Space space, int value) {
		if (player == null) {
			throw new IllegalArgumentException("In chooseToBuy: Player player is null.");
		}
		if (space == null) {
			throw new IllegalArgumentException("In chooseToBuy: Space space is null.");
		}
		String string = "";

		if (player.getMoney() - value > 0) {
			string = player.getName() + " purchased " + space.getName() + ".";
			removeFunds(board, player, value); // Player buys property.
			player.addOwnedProperties(space); // Add property to board.getPlayers() Owned Properties list.
			space.setOwnedBy(getPlayerIndex(board, player));
		}
		else {
			string = "You can't afford " + space.getName();
		}
		return string;
	}


	@Override
	public void chooseToBuy(Board board, Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In chooseToBuy: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In chooseToBuy: Space space is null.");
		}

		System.out.print("Would you like to purchase " + space.getName() + " for $" + space.getPrice() + "? Y/N ");
		char choice = getCharInput(board);
		
		if (choice == 'Y' || choice == 'y') {
			if (player.getMoney() - space.getPrice() > 0) {
				System.out.println(player.getName() + " purchased " + space.getName());
				removeFunds(board, player, space.getPrice()); // Player buys property.
				player.addOwnedProperties(space); // Add property to board.getPlayers() Owned Properties list.
				space.setOwnedBy(getPlayerIndex(board, player));
				//addMonopoly(board, player, space);
			}
			else {
				System.out.println("You can't afford " + space.getName());
			}
		}
		// Player does not want property.
		else {
			auction(board, space); // All players eligible for auction.
		}
	}

	@Override
	public void auction(Board board, Space space) {
		if (space == null) {
			throw new IllegalArgumentException("In payUtility: Space space is null.");
		}
		System.out.println(space.getName() + " original sale price is $" + space.getPrice());
		List<Integer> offers = new Vector<Integer>(Collections.nCopies(board.getPlayers().size(), 0));
		List<Boolean> stillInAuction = new Vector<Boolean>(Collections.nCopies(board.getPlayers().size(), true));
		int offer = 0;
		int count = 0;
		int max = 0;
		int index = -1;

		for (int i = 0; i < board.getPlayers().size(); i++){
			do {
				if (board.getPlayers().get(i).getMoney() > 0) {
					System.out.println(board.getPlayers().get(i).getName() + ", enter your bid or -1 to give up:");
					offer = getUserInput(board);
				}
				else {
					offer = -1;
					continue;
				}
			} while (board.getPlayers().get(i).getMoney() - offer <= 0 || offers.contains(offer) || (offer <= Collections.max(offers) && offer > -1));

			if (offer == -1) {
				stillInAuction.set(i, false);
			} else {
				offers.set(i, offer);
			}
		}

		do {
			for (int i = 0; i < board.getPlayers().size(); i++) {
				if (stillInAuction.get(i) && (Collections.frequency(stillInAuction, false) != (board.getPlayers().size()-1))) {
					do {
						System.out.println(board.getPlayers().get(i).getName() + ", enter your bid or -1 to give up:");
						offer = getUserInput(board);

					} while (board.getPlayers().get(i).getMoney() - offer <= 0 || offers.contains(offer) || (offer <= Collections.max(offers) && offer > -1));

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
		removeFunds(board, board.getPlayers().get(index), max); // Player buys property.
		board.getPlayers().get(index).addOwnedProperties(space); // Add property to players Owned Properties list.
		space.setOwnedBy(index);
		System.out.println(board.getPlayers().get(index).getName() + " has won the auction.");
		//addMonopoly(board, board.getPlayers().get(index), space);
		// Update: player who won auction
	}

	@Override
	public void mortgage(Board board, Player player, int debt) {
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
			displayOwnedProperties(board, player);
			System.out.println("Which property would you like to sell?");

			try {
				do {
					choice = getUserInput(board);
				} while(player.getOwnedProperties().get(choice -1 ).isMortgaged());
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			if (choice > 0) {
				property = player.getOwnedProperties().get(choice-1);
				addFunds(board, player, (debt + (property.getPrice() / 2) + (property.getBuildings() * property.getHouseCost() / 2)));
				debt += (property.getPrice() / 2) + (property.getBuildings() * property.getHouseCost() / 2);

				if (property.getBuildings() == 5) {
					board.setHotelsAvailable(board.getHotelsAvailable() + 1);
				}
				else {
					board.setHousesAvailable(board.getHousesAvailable() + property.getBuildings());
				}

				player.getOwnedProperties().get(choice-1).setMortgaged(false);
				// Updates spaces, player, hotelsAvailable, housesAvailable
			}
		}

		if (allMortgage && debt <= 0) {
			bankrupt(board, player);
		}
	}

	@Override
	public void unmortgage(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In unmortgage: Player player is null.");
		}

		displayOwnedProperties(board, player);
		System.out.println("Which property would you like to pay off?");
		int choice = 0;
		Space property;

			try {
				do {
					choice = getUserInput(board);
				} while(!player.getOwnedProperties().get(choice -1 ).isMortgaged());
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			if (choice > 0) {
				property = player.getOwnedProperties().get(choice-1);
				removeFunds(board, player, (int) ((property.getPrice() / 2) * 1.1));
				player.getOwnedProperties().get(choice-1).setMortgaged(true);
				System.out.println(property.getName() + " is now paid off!");
				// Update player, space
			}
	}

	@Override
	public void playerDecision(Board board, Player player) {
		if (player == null) {
			throw new IllegalArgumentException("In playerDecision: Player player is null.");
		}

		// Build improvements, trade, or end turn.
		System.out.println("\n/---------------------------------------\\");
		System.out.println("| Commands:                               |");
		System.out.println("| B: Build        T: Trade    D: Display  |");
		System.out.println("| P: Pay Mortgage Q: Quit     E: End Turn |");
		System.out.println("\\-----------------------------------------/\n");
		char ans = getCharInput(board);
		boolean hasMortgaged = false;

		if(ans == 'B') { // Build Improvements
			if (player.getMonopolyGroups().contains(1)) {
				findMonopolies(board, player);
				System.out.print("What property from the above list would you like to improve? ");
				int choice = getUserInput(board);

				if (player.getMonopolyProperties().get(choice - 1).getBuildings() < 5 && !player.getMonopolyProperties().get(choice -1).isMortgaged()) {
					build(board, player, player.getMonopolyProperties().get(choice - 1));
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
				trade(board, player);
			}
			else {
				System.out.println("You have no properties to trade.");
			}
		}
		else if (ans == 'D') {
			System.out.println(player.toString());
			displayOwnedProperties(board, player);
		}
		else if (ans == 'P') {
			for (Space space : player.getOwnedProperties()) {
				if (space.isMortgaged()) {
					hasMortgaged = true;
					unmortgage(board, player);
					break;
				}
			}
			if (!hasMortgaged) {
				System.out.println("You have no mortgaged properties..");
			}
		}
		else if (ans == 'Q') {
			bankrupt(board, player);
			board.setTurnOver(true);
			// update turn over
		}
		else {	// End turn.
			board.setTurnOver(true);
			// update turn over
		}
	}

	// Display only
	@Override
	public void displayOwnedProperties(Board board, Player player) {
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
	@Override
	public void displayPlayersWithProperties(Board board) {
		int i = 0;
		System.out.println("The following players own properties:");
		System.out.println("#    Player Name         No. of Properties");

		for (Player player : board.getPlayers()) {
			i++;
			if (player != null) {
				if (player == getCurrentPlayer(board)) {
					continue;
				}
				else if (player.getOwnedProperties().size() > 0) {
					System.out.printf("%d    %-16s            %d\n", i, player.getName(), player.getOwnedProperties().size());
				}
			}
		}
	}

	@Override
	public Player getCurrentPlayer(Board board) {
		return board.getPlayers().get(0);
	}

	// Return a boolean if the game has ended.
	// If there is only one player that is not bankrupt, then the game will terminate.
	@Override
	public boolean hasWinner(Board board) {
		int playersInGame = 0;

		for (Player player : board.getPlayers()) {
			if (player != null) {
				if(player.getMoney() > 0) {
					playersInGame++;
				}
			}
		}

		return playersInGame <= 1;
	}
	
	// Return the winner of the game.
	@Override
	public Player getWinner(Board board) {
		return getCurrentPlayer(board);
	}

	@Override
	public void nextTurn(Board board) {
		do {
			board.setPlayerIndex(0);

			if (0 == board.getPlayers().size()) { // If playerIndex has reached the end of the list, set playerIndex back to zero.
				board.setPlayerIndex(0);
			}
		} while(board.getPlayers().get(0) == null);
	}

	@Override
	public String payRailroad(Board board, Player player, Player owner) {
		if (player == null) {
			throw new IllegalArgumentException("In payRailroad: Player player is null.");
		}
		Space space = board.getSpaces().get(player.getCurrentPosition());
		if (space == null) {
			throw new IllegalArgumentException("In payRailroad: Space space is null.");
		}

		int payment = 25;
		int index = space.getOwnedBy();

		for (int i = 0; i < 4; i++) {
			if (index == board.getSpaces().get(10*i + 5).getOwnedBy()) {
				payment *= 2;
			}
		}

		removeFunds(board, player, payment);
		addFunds(board, owner, payment);

		return player.getName() + " has paid $" + payment + " to " + owner.getName() + ".";  
	}

	@Override
	public String payRent(Board board, Player player, Player owner) {
		if (player == null) {
			throw new IllegalArgumentException("In payRent: Player player is null.");
		}
		Space space = new Space();
		for (Space s : owner.getOwnedProperties()) {
			if (s.getName().equals(board.getSpaces().get(player.getCurrentPosition()).getName())) {
				space = s;
				break;
			}
		}

		// Rent will be changed in database in the build() method
		int rent = space.getCurrentRent();
		removeFunds(board, player, rent);
		addFunds(board, owner, rent);

		return player.getName() + " has paid $" + rent + " to " + owner.getName() + ".";  
	}

	@Override
	public String payTax(Board board, Player player, Space space) {
		if (player == null) {
			throw new IllegalArgumentException("In payTax: Player player is null.");
		}

		if (space == null) {
			throw new IllegalArgumentException("In payTax: Space space is null.");
		}

		int payment;

		if(space.getName().equals("Luxury Tax")){
			payment = space.getPrice();
		} else {
			payment = Math.min(200, (player.getMoney()/10));
		}
		removeFunds(board, player, payment);
		return player.getName() + " paid $" + payment + " for " + space.getName() + ".";
	}

	@Override
	public String payUtility(Board board, Player player, Player owner) {
		if (player == null) {
			throw new IllegalArgumentException("In payUtility: Player player is null.");
		}
		Space space = board.getSpaces().get(player.getCurrentPosition());
		if (space == null) {
			throw new IllegalArgumentException("In payUtility: Space space is null.");
		}

		int initialPayment;
		int finalPayment;
		boolean waterWorks = false;
		boolean electricCompany = false;
		List<Space> properties = board.getPlayers().get(space.getOwnedBy()).getOwnedProperties();

		for (Space utility : board.getSpaces()) {
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
		
		finalPayment = initialPayment * board.getDieValue();
		removeFunds(board, player, finalPayment);
		addFunds(board, board.getPlayers().get(space.getOwnedBy()), finalPayment) ;

		return player.getName() + " has paid $" + finalPayment + " to " + owner.getName() + ".";
	}

	@Override
	public int getPlayerIndex(Board board, Player player) {
		int index = -1;
		for (Player p : board.getPlayers()) {
			index++;
			if (p.getName().equals(player.getName())) {
				break;
			}
		}
		return index;
	}

	@Override
	public void saveBoard(Board board) {
		boardRepository.save(board);
	}

	@Override
	public Board findByGameId(int id) {
		return boardRepository.findById(id);
	}

	@Override
	public void deleteBoard(Board board) {
		boardRepository.delete(board);
	}
}
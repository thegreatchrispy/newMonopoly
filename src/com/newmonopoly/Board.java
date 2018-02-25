package com.newmonopoly.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
	int turn_num = 0;
	int totalPlayer = 0;

	Player[] players;
	boolean randomizeSet;

	SpaceNode[] spaces = new SpaceNode[40];
	String[] seasons = {"Spring", "Summer", "Fall", "Winter"};
	String currentSeason = "";
	
	// Constructor
	public Board(Player[] players, boolean randomizeSet) {
		this.players = players;
		this.randomizeSet = randomizeSet;
		// for(int i = 0;i < players.length;i++){
		// 	// Fix constructor.
		// 	players[i] = new Player(i, "Player " + (i + 1));
		// }

		// THIS IS WHERE THE RANDOM ALGORITHM WILL GO.
		// THIS IS WHERE STUFF SHOULD GO.

		decideSeasons(randomizeSet);

		// Grab JSON file
		// SET 8 Static Spaces
		for (int i = 0; i < 8; i++) {
			getGroup(spaces, i);
		}

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
		String[] strong_season = {"Spring", "Summer", "Fall", "Winter"}
		String[] weak_season = {"Spring", "Summer", "Fall", "Winter"}

		// Initializing Random object and integer variables.
		Random rand = new Random();
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

	public void getGroup(SpaceNode[] spaces, int i) {
		// Call Database to get each group and position spaces correctly.
		//i*5 + group_pos
	}
	
	// Move player.
	public Space movePlayer(Player player, int face) {
		return movePlayer(player, face, true);
	}
	
	public Square movePlayer(Player player, int face, boolean count) {
		if(player.isBrokeOut()){ return spaces[player.getCurrentPosition()]; }
		int newPosition = normalizePosition(player.getCurrentPosition() + face);
		player.setPosition(newPosition);
		Util.print(player, player.getName() + " goes to " + spaces[player.getCurrentPosition()].getName());
		
		// Space Action - Transaction Start
		spaces[newPosition].doAction(player, this);
		if(player.getMoney().isBrokeOut()){
			Util.print(player, player.getName() + " has been broke out!");
			player.setBrokeOut(true);
		}else{
			if(count){
				player.nextTurn();
			}
		}
		return spaces[newPosition];
	}
	
	// Return a boolean if the game has ended.
	// If there is only one player that is not bankrupt, then the game will terminate.
	public boolean hasWinner() {
		int players_inGame = 0;
		for(Player player:players){
			if(!player.isBrokeOut()){
				inGame++;
			}
		}
		return ingame <= 1;
	}
	
	// Return the winner of the game.
	public Player getWinner() {
		if(!hasWinner()){ return null; }
		for(Player player:players){
			if(!player.isBrokeOut()){ return player; }
		}
		return null;
	}
	
	// Return top player for statistics.
	public Player getTopPlayer() {
		Player maxplayer = null;
		for(Player player:players){
			if(maxplayer == null || maxplayer.getMoney().getMoney() < player.getMoney().getMoney()){
				maxplayer = player;
			}
		}
		return maxplayer;
	}
	
	public int normalizePosition(int position) {
		return position % spaces.length;
	}
	
	public Player getCurrentPlayer() {
		return players[currentTurn];
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public void nextTurn() {
		if(++currentTurn >= players.length){
			currentTurn = 0;
		}
	}
	
	public Player getPlayer(int id) {
		return players[id];
	}
	
	public int getTotalSquare() {
		return spaces.length;
	}
}

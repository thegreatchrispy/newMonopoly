package com.newmonopoly.vo;

import java.util.Random;

public class Board {
	int turn_num = 0;
	int totalPlayer = 0;
	Player[] players;
	Space[] spaces = new Space[40];
	String season = "";
	
	public Board(int totalPlayer) {
		players = new Player[totalPlayer];
		this.totalPlayer = totalPlayer;
		for(int i = 0;i < players.length;i++){
			players[i] = new Player(i, "Player " + (i + 1));
		}
		Random rand = new Random();
		for(int i = 0;i < spaces.length;i++){
			if(i == 0){
				spaces[i] = new GoSquare("GO");
			}else if(i == 9){
				spaces[i] = new JailSquare("Prison");
			}else if(i == 19){
				spaces[i] = new HouseSquare("House");
			}else if(i == 29){
				spaces[i] = new GoToJailSquare("Locked Up!");
			}else{
				spaces[i] = new HouseSquare(names[rand.nextInt(names.length)] + " " + names[rand.nextInt(names.length)], 400 + rand.nextInt(300));
			}
		}
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

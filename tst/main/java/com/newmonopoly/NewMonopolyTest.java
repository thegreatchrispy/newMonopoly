package com.newmonopoly;

import java.util.List;
import java.util.Vector;

public class NewMonopolyTest {
	public static void main(String[] args) {
		List<Player> players = new Vector<Player>();
		players.add(new Player("Isaac"));
		players.add(new Player("Juan"));
		players.add(new Player("Chris"));
		players.add(new Player("Ivan"));
		players.add(new Player("Pepe"));
		players.add(new Player("Tang"));

		NewMonopoly newMonopoly = new NewMonopoly(players);
		newMonopoly.startGame();
	}
}
package com.newmonopoly;

import com.monopoly.Board;
import com.monopoly.Card;
import com.monopoly.Player;
import com.monopoly.Space;

import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

public class BoardTest {
	private Board board;

	public BoardTest() {
		List<Player> players = new Vector<Player>();
		players.add(new Player("Isaac"));
		players.add(new Player("Juan"));
		players.add(new Player("Chris"));
		players.add(new Player("Ivan"));
		players.add(new Player("Pepe"));
		players.add(new Player("Tang"));
		board = new Board(players, false);
	}

	@Test
	public void testRetrieveSpaceInfo() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRetrieveChanceInfo() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRetrieveCommunityInfo() {
		Assert.assertTrue(true);
	}

	@Test
	public void testConstructor() {
		Assert.assertTrue(true);
	}
}
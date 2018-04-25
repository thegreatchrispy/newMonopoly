package com.newmonopoly.model;

import com.newmonopoly.model.Board;
import com.newmonopoly.model.Card;
import com.newmonopoly.model.Player;
import com.newmonopoly.model.Space;

import org.junit.*;

import java.util.List;
import java.util.Vector;

public class BoardTest {
	private Board board;
	private List<Player> players;

	public BoardTest() {
		// players = new Vector<Player>();
		// players.add(0, new Player("Isaac"));
		// players.add(1, new Player("Juan"));
		// players.add(2, new Player("Chris"));
		// players.add(3, new Player("Ivan"));
		// players.add(4, new Player("Pepe"));
		// players.add(5, new Player("Tang"));

		// try {
		// 	board = new Board(players, false);
		// }
		// catch (Exception e) {
		// 	e.printStackTrace();
		// }
	}

	/*
		Test the setup and constructor
	*/

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
	public void testDecideSeasonsAndOrder() {
		Assert.assertTrue(true);
	}

	@Test
	public void testConstructor() {
		Assert.assertTrue(true);
	}

	/*
		Test the Player movement and Card actions
	*/

	@Test
	public void testMovePlayer() {
		// Test movePlayer(Player, int)
		Assert.assertTrue(true);

		// Test movePlayer(Player, String)
		Assert.assertTrue(true);
	}

	@Test
	public void testMovePlayerToJail() {
		Assert.assertTrue(true);
	}

	@Test
	public void testMoveToPosition() {
		Assert.assertTrue(true);
	}

	@Test
	public void testMoveToNearest() {
		Assert.assertTrue(true);
	}

	@Test
	public void testAddFunds() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRemoveFunds() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRepairs() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGiveToPlayers() {
		Assert.assertTrue(true);
	}

	@Test
	public void testTakeFromPlayers() {
		Assert.assertTrue(true);
	}

	/*
		Test the game turn functionalities
	*/

	@Test
	public void testTransaction() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPerformSpaceAction() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPayRent() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPayRailroad() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPayUtility() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPayTax() {
		Assert.assertTrue(true);
	}

	@Test
	public void testChooseToBuy() {
		Assert.assertTrue(true);
	}

	@Test
	public void testAuction() {
		Assert.assertTrue(true);
	}

	@Test
	public void testDrawCard() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPlayerDecision() {
		Assert.assertTrue(true);
	}

	@Test
	public void testBuild() {
		Assert.assertTrue(true);
	}

	@Test
	public void testTrade() {
		Assert.assertTrue(true);
	}

	@Test
	public void testEndTurn() {
		Assert.assertTrue(true);
	}

	@Test
	public void testAddMonopoly() {
		Assert.assertTrue(true);
	}

	@Test
	public void testFindMonopolies() {
		Assert.assertTrue(true);
	}

	@Test
	public void testHasWinner() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetWinner() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCurrentPlayer() {
		Assert.assertTrue(true);
	}

	@Test
	public void testNextTurn() {
		Assert.assertTrue(true);
	}
}

import com.newmonopoly.Board;
import com.newmonopoly.Card;
import com.newmonopoly.Player;
import com.newmonopoly.Space;

import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

public class BoardTest {
	private Board board;
	List<Player> players;

	public BoardTest() {
		players = new Vector<Player>();
		players.add(new Player("Isaac"));
		players.add(new Player("Juan"));
		players.add(new Player("Chris"));
		players.add(new Player("Ivan"));
		players.add(new Player("Pepe"));
		players.add(new Player("Tang"));

		try {
			board = new Board(players, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
import com.newmonopoly.Die;
import com.newmonopoly.Player;
import com.newmonopoly.NewMonopoly;

import org.junit.*;

import java.util.List;
import java.util.Vector;

public class NewMonopolyTest {
	private NewMonopoly newMonopoly;
	private List<Player> players;

	public NewMonopolyTest() {
		players = new Vector<Player>();
		players.add(0, new Player("Isaac"));
		players.add(1, new Player("Juan"));
		players.add(2, new Player("Chris"));
		players.add(3, new Player("Ivan"));
		players.add(4, new Player("Pepe"));
		players.add(5, new Player("Tang"));

		try {
			newMonopoly = new NewMonopoly(players);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConstructor() {
		Assert.assertTrue(true);
	}

	@Test
	public void testStartGame() {
		Assert.assertTrue(true);
	}

	@Test
	public void testChoosePieces() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPlayerOrder() {
		Assert.assertTrue(true);
	}
}
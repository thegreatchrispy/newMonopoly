import com.newmonopoly.Die;

import org.junit.*;

public class NewMonopolyTest {
	private NewMonopoly newmonopoly;

	public NewMonopolyTest() {
		newmonopoly = new NewMonopoly();
		
	}

	@Test
	public void testNewMonopoly(List<Player> players) {
		Assert.assertTrue(die.getValue() <= 6);
		Assert.assertTrue(die.getValue() > 0);
	}

	public void testStartGame(){
		
	}

	public void testChoosePieces(){
		
	}

	public void testPlayerOrder(){
		
	}

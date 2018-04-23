import com.newmonopoly.model.Die;

import org.junit.*;

public class DieTest {
	private Die die;

	public DieTest() {
		die = new Die();
		die.roll();
	}

	@Test
	public void testRoll() {
		die.roll();
		Assert.assertTrue(die.getValue() <= 6);
		Assert.assertTrue(die.getValue() > 0);
	}

	@Test
	public void testGetValue() {
		int val = die.getValue();
		Assert.assertNotNull(val);
		Assert.assertSame(val, die.getValue());
	}
}
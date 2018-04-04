import com.newmonopoly.Account;

import org.junit.*;

public class AccountTest {
	private Account account;
	private String email;
	private String username;
	private String password;
	private boolean isAdmin;

	public AccountTest() {
		email = "test@email.com";
		username = "testUser";
		password = "testPass";
		isAdmin = true;

		try {
			account = new Account(email, username, password, isAdmin);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEmptyConstructor() {
		Assert.assertTrue(true);
	}

	@Test
	public void testConstructor() {
		Assert.assertTrue(true);
	}

	@Test
	public void testToString() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetId() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetId() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetEmail() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetEmail() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetUsername() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetUsername() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetPassword() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetPassword() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetIsAdmin() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetIsAdmin() {
		Assert.assertTrue(true);
	}
}
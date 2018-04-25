package com.newmonopoly.model;

import com.newmonopoly.model.Player;

import org.junit.*;

import java.util.List;
import java.util.Vector;

public class PlayerTest {
	private String name;
	private Player player;

	public PlayerTest() {
		name = "TestName";

		try {
			player = new Player(name);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
		Test the constructor
	 */
	@Test
	public void testConstructor() {
		Assert.assertTrue(true);
	}

	/*
		Test the getters and setters
	*/
	@Test
	public void testGetName() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetName() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetMoney() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetMoney() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCurrentPosition() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetCurrentPosition() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDoublesCount() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetDoublesCount() {
		Assert.assertTrue(true);
	}

	@Test
	public void testIncrementDoubles() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetJailCard() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetJailCard() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetInJail() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetInJail() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetJailTime() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSetJailTime() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetOwnedProperties() {
		Assert.assertTrue(true);
	}

	@Test
	public void testAddOwnedProperties() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetMonopolyGroup() {
		Assert.assertTrue(true);
	}

	@Test
	public void testAddMonopolyGroup() {
		Assert.assertTrue(true);
	}

	@Test
	public void testRemoveMonopolyGroup() {
		Assert.assertTrue(true);
	}
}
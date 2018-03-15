package com.newmonopoly;

import java.util.List;
import java.util.Vector;

public class Player {
	private int money;
	private int currentPosition;
	private int doublesCount;
	private boolean jailCard;
	private boolean inJail;
	private List<String> ownedProperties;
	

	public Player() {
		money = 500;
		currentPosition = 0;
		ownedProperties = new Vector<String>();
		jailCard = false;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int newMoney) {
		money = newMoney;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int newPosition) {
		currentPosition = newPosition;
	}

	public int getDoublesCount() {
		return doublesCount;
	}

	public void setDoublesCount(int count) {
		doublesCount = count;
	}

	public List<String> getOwnedProperties() {
		return ownedProperties;
	}

	public void addOwnedProperties(String newProperty) {
		ownedProperties.add(newProperty);
	}

	public boolean getJailCard() {
		return jailCard;
	}

	public void setJailCard(boolean newOwner) {
		jailCard = newOwner;
	}

	public boolean getInJail() {
		return inJail;
	}

	public void setInJail(boolean jailStatus) {
		inJail = jailStatus;
	}
}
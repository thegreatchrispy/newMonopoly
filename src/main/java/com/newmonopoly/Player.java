package com.newmonopoly;

import java.util.List;
import java.util.Vector;

public class Player {
	private String name;
	private int money;
	private int currentPosition;
	private int doublesCount;
	private boolean jailCard;
	private boolean inJail;
	private int jailTime;
	private List<Space> ownedProperties;
	private int[] monopolyGroup;
	

	public Player(String name) {
		this.name = name;
		money = 1500;
		currentPosition = 0;
		doublesCount = 0;
		jailCard = false;
		inJail = false;
		jailTime = 0;
		ownedProperties = new Vector<Space>();
		monopolyGroup = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void incrementDoubles() {
		doublesCount++;
	}

	public List<Space> getOwnedProperties() {
		return ownedProperties;
	}

	public void addOwnedProperties(Space property) {
		ownedProperties.add(property);
	}

	public void removeOwnedProperties(int index) {
		ownedProperties.remove(index);
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
	
	public int getJailTime() {
		return jailTime;
	}

	public void setJailTime(int jailTime) {
		this.jailTime = jailTime;
	}

	public int[] getMonopolyGroups() {
		return monopolyGroup;
	}

	public void addMonopolyGroup(int group) {
		monopolyGroup[group] = 1;
	}

	public void removeMonopolyGroup(int group) {
		monopolyGroup[group] = 0;
	}
}

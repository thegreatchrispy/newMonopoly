package com.newmonopoly;

public class Space implements Comparable<Space> {
  	private String name;
  	private int group;
	private int position;
  	private String type;
  	private int price;
	private int currentRent;
  	private int rent;
  	private int[] multipliedRent;
  	private int mortgageVal;
  	private int houseCost;
  	private int ownedBy;
  	private int buildings;
  	private boolean mortgaged;
  	private int strongSeason;
  	private int weakSeason;

  	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	public int getGroup() {
		return group;
	}

  	public void setPosition(int pos) {
	 	position = pos;
	}

  	public int getPosition() {
  		return position;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setPrice(int price) {
		this.price = price;
  	}

  	public int getPrice() {
  		return price;
  	}

    public void setCurrentRent(int currentRent) {
		this.currentRent = currentRent;
	}

    public int getCurrentRent() {
		return currentRent;
	}

  	public void setRent(int rent) {
		this.rent = rent;
	}

  	public int getRent() {
		return rent;
	}

  	public void setMultipliedRent(int[] multipliedRent) {
	  	this.multipliedRent = multipliedRent;
  	}

  	public int[] getMultipliedRent() {
  		return multipliedRent;
  	}

  	public void setMortgageVal(int mortgage) {
	  	mortgageVal = mortgage;
  	}

  	public int getMortgageVal() {
  		return mortgageVal;
  	}

   	public void setHouseCost(int houseCost) {
	  	this.houseCost = houseCost;
  	}

  	public int getHouseCost() {
  		return houseCost;
  	}

  	public void setOwnedBy(int ownedBy) {
		this.ownedBy = ownedBy;
	}

  	public int getOwnedBy() {
  		return ownedBy;
	}

    public void addBuildings(int additions) {
      buildings += additions;
      currentRent = multipliedRent[buildings-1];
    }

    public void removeBuildings(int removals) {
      buildings -= removals;
      currentRent = multipliedRent[buildings-1];
    }

  	public void setBuildings(int buildings) {
		this.buildings = buildings;
	}

  	public int getBuildings() {
  		return buildings;
	}

  	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}

  	public boolean isMortgaged() {
  		return mortgaged;
  	}

  	public void setStrongSeason(int strong) {
		strongSeason = strong;
	}

  	public int getStrongSeason() {
  		return strongSeason;
  	}

  	public void setWeakSeason(int weak) {
		weakSeason = weak;
	}

  	public int getWeakSeason() {
		return weakSeason;
	}

	@Override
	public int compareTo(Space compareSpace) {
		int compareGroup = ((Space)compareSpace).getGroup();
		return this.group - compareGroup;
	}
}
package com.newmonopoly.vo;

public class UtilitySpace extends Property{

	//For the sake of expandabilty/inherited organization
	public UtilitySpace(char type, String name, String color, String price,
			String buildCost, String rent, String oneHouseRent, int pos) {
		super(type, name, color, price, buildCost, rent, oneHouseRent, pos);
	}
}
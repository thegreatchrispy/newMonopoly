package com.newmonopoly;

public class Card{
	private String title;
	private String action;
	private String spaceName;
	private String type;
	private int rentMultiplier;
	private int payment;
	private int position;
	private int houses;
	private int hotels;

	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getAction(){
		return action;
	}
	public void setAction(String action){
		this.action = action;
	}
	public String getSpaceName(){
		return spaceName;
	}
	public void setSpaceName(String spaceName){
		this.spaceName = spaceName;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public int getRentMultiplier(){
		return rentMultiplier;
	}
	public void setRentMultiplier(int rentMultiplier){
		this.rentMultiplier = rentMultiplier;
	}
	public int getPayment(){
		return payment;
	}
	public void setPayment(int payment){
		this.payment = payment;
	}
	public int getPosition(){
		return position;
	}
	public void setPosition(int position){
		this.position = position;
	}
	public int getHouses(){
		return houses;
	}
	public void setHouses(int houses){
		this.houses = houses;
	}
	public int getHotels(){
		return hotels;
	}
	public void setHotels(int hotels){
		this.hotels = hotels;
	}
}
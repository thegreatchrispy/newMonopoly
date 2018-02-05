package com.newmonopoly.vo;

public class TaxSpace extends SpaceNode{

	private String tax;

	//Extends tax value, from SpaceNode
	public TaxSpace(char type, String name, String tax, int pos){
  		super(type, name, pos);
  		this.tax = tax;
  	}
	
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	
}
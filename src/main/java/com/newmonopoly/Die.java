package com.newmonopoly;

import java.util.Random;

public class Die {
	private int value;

    public void roll() {
        Random rand = new Random();
        value = 1 + rand.nextInt(6);
    }

    public int getValue(){
    	return value;
    }
}

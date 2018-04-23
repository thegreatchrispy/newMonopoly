package com.newmonopoly.model;

import java.util.Random;

public class Die {
    private Random rand;
    private int value;
    
    public Die() {
        rand = new Random();
        value = 1 + rand.nextInt(6);
    }

    public void roll() {
        value = 1 + rand.nextInt(6);
    }

    public int getValue(){
    	return value;
    }
}

import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;

public class GSONTest {
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
    	Space[] spaces = gson.fromJson(new FileReader("spaces.json"), Space[].class);
    	System.out.println(spaces[1].housecost);
	}


	class Space {
  		String name;
  		int group;
  		int fposition;
  		String type;
  		int price;
  		int rent;
  		int[] multipliedRent;
  		int mortgageVal;
  		int housecost;
  		int ownedBy;
  		int buildings;
  		boolean mortgaged;
  		int strongSeason;
  		int weakseason;
  	}
}
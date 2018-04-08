import java.util.List;
import java.util.Vector;
import java.util.Collections;

public class Player implements Comparable<Player> {
	private String name;
	private int turnOrder;
	private int money;
	private int currentPosition;
	private int doublesCount;
	private boolean jailCard;
	private boolean inJail;
	private int jailTime;
	private List<Space> ownedProperties;
	private List<Integer> monopolyGroup;
	private List<Space> monopolyProperties;
	private int tokenNumber;
	

	public Player(String name) {
		this.name = name;
		turnOrder = 0;
		money = 150;
		currentPosition = 0;
		doublesCount = 0;
		jailCard = false;
		inJail = false;
		jailTime = 0;
		ownedProperties = new Vector<Space>();
		monopolyGroup = new Vector<Integer>(Collections.nCopies(8, 0));
		monopolyProperties = new Vector<Space>();
		tokenNumber = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTurnOrder() {
		return turnOrder;
	}

	public void setTurnOrder(int newTurn) {
		turnOrder = newTurn;
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

	public List<Integer> getMonopolyGroups() {
		return monopolyGroup;
	}

	public void addMonopolyGroup(int group) {
		monopolyGroup.set(group, 1);
	}

	public void removeMonopolyGroup(int group) {
		monopolyGroup.set(group, 0);
	}

	public List<Space> getMonopolyProperties() {
		return monopolyProperties;
	}

	public void addMonopolyProperties(Space property) {
		monopolyProperties.add(property);
	}

	public void removeMonopolyProperties(int index) {
		monopolyProperties.remove(index);
	}

	public int getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(int tokenN) {
		tokenNumber = tokenN;
	}

	@Override
	public int compareTo(Player comparePlayer) {
		int compareOrder = ((Player)comparePlayer).getTurnOrder();
		return compareOrder - this.turnOrder;
	}

	@Override
	public String toString() {
		String result = "Player:\n{\n";
		result += "\tname: " + this.name + ",\n";
		result += "\tturnOrder: " + this.turnOrder + ",\n";
		result += "\tmoney: " + this.money + ",\n";
		result += "\tcurrentPosition: " + this.currentPosition + ",\n";
		result += "\tinJail: " + this.inJail + ",\n}";
		return result;
	}
}

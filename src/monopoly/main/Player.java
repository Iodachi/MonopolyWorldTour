package monopoly.main;

import java.util.ArrayList;
import java.util.List;

import monopoly.entities.Country;
import monopoly.entities.Entity;
import monopoly.entities.Property;

public class Player {
	private String color;
	private Monopoly game;
	private boolean inJail;
	private int funds;
	private List<Property> properties; //a list of properties owned by the player
	
	private int currentLocation;
	
	public Player(String color, Monopoly game) {
		this.color = color;
		this.game = game;
		properties = new ArrayList<>();
		//start from the first entity - "Start"
		currentLocation = 0;
		inJail = false;
	}
	
	/**
	 * player move given steps on board
	 * @param steps
	 * @return
	 * 			true if player moved pass the start
	 */
	public boolean move(int steps) {
		currentLocation += steps;
		if(currentLocation >= 30) {
			currentLocation -= 30;
			if(currentLocation != 0)	return true;
		}
		return false;
	}
	
	public void buyProperty(Property property) {
		deductFunds(property.getPrice());
		properties.add(property);
		property.setOwner(this);
	}
	
	public boolean buildHouse(Country country) { //can only build house on countries
		deductFunds(country.getConstructionPrice());
		return country.addHouses();
	}
	
	public void addFunds(int amount) {
		funds += amount;
	}
	
	public void deductFunds(int amount) {
		funds -= amount;
	}
	
	//================= getters and setters ===============
	/**
	 * how much money player has left
	 * @return
	 */
	public int getFunds() {
		return funds;
	}
	
	public int getCurrentLocation() {
		return currentLocation;
	}
	
	public String getColor() {
		return color;
	}
	
	public boolean isInJail() {
		return inJail;
	}
	
	public void goToJail(boolean inJail) {
		this.inJail = inJail;
	}
	
	/**
	 * get the entity which the player is at.
	 * @return
	 */
	public Entity getCurrentEntity() {
		return game.getBoard().getEntities().get(currentLocation);
	}
	
	/**
	 * if the player's fund is less than 0, he is considered to be broke
	 * @return
	 */
	//TODO: mortgage property to avoid broke? When is this checked??
	public boolean isBroke() {
		return funds <= 0;
	}
}

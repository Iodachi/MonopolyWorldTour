package monopoly.entities;

import java.awt.Color;

import monopoly.main.Monopoly;
import monopoly.main.Player;

/**
 * The properties on the map that players can buy or sell, and build houses on.
 * When another player steps into an owned property, he will need to pay a corresponding rent.
 * @author stella
 *
 */
public class Country extends Entity implements Property{
	private Color color;
	//the price player needs to pay to buy this property
	private int price;
	//the price player needs to pay to build a house
	private int constructionPrice;
	//the price player needs to pay when passing this property, 
	//note if the property has two hotels, rent will be hotelRent * 2
	private int baseRent, oneHouseRent, twoHouseRent, hotelRent;
	private int mortgagePrice;
	//the houses being constructed in this property, the more houses, the higher rent
	private int houses;
	//when there are already two houses, adding another one will form into one hotel, max two hotels in one property
	private int hotels;
	private Player owner = null;
	private String name;
	private ColourGroup colourGroup;
	
	public Country(Monopoly game, Color color, String name, int price, int constructionPrice, 
			int baseRent, int oneHouseRent, int twoHouseRent, int hotelRent, int mortgagePrice) {
		super(game);
		this.color = color;
		this.name = name;
		this.price = price;
		this.constructionPrice = constructionPrice;
		this.baseRent = baseRent;
		this.oneHouseRent = oneHouseRent;
		this.twoHouseRent = twoHouseRent;
		this.hotelRent = hotelRent;
		this.mortgagePrice = mortgagePrice;
	}
	
	//============== getters and setters =============
	@Override
	public int getRent() {
		//FIXME: same colour not taken into consideration
		if(hotels == 2)
			return hotelRent * 2;
		else if(hotels == 1 && houses == 0)
			return hotelRent;
		else if(hotels == 1 && houses == 1)
			return hotelRent + oneHouseRent;
		else if(hotels == 1 && houses == 2)
			return hotelRent + twoHouseRent;
		else if(hotels == 0 && houses == 0)
			return baseRent;
		else if(hotels == 0 && houses == 1)
			return oneHouseRent;
		else if(hotels == 0 && houses == 2)
			return twoHouseRent;
		
		return 0;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getPrice() {
		return price;
	}
	
	public int getConstructionPrice() {
		return constructionPrice;
	}
	
	public int geMortgagePrice() {
		return mortgagePrice;
	}
	
	public int getHouses() {
		return houses;
	}
	
	/**
	 * build a house on current country
	 * @return
	 * 			- true if the houses reaches 3 and turns into a hotel
	 */
	public boolean addHouses() {
		if(houses < 2) {
			houses++;
		} else if (houses == 2) {
			hotels++;
			houses = 0;
			return true;
		}
		return false;
	}
	
	public int getHotels() {
		return hotels;
	}
	
	@Override
	public void setOwner(Player player) {
		this.owner = player;
	}
	
	@Override
	public Player getOwner() {
		return owner;
	}

	public void setColourGroup(ColourGroup colourGroup) {
		this.colourGroup = colourGroup;
	}
	
	public String toString() {
		return "<COUNTRY - " + name + ">";
	}
}

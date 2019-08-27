package monopoly.entities;

import monopoly.main.Monopoly;
import monopoly.main.Player;

public class Airport extends Entity implements Property{
	private String name;
	private Player owner = null;
	private int oneAirportRent;
	private int twoAirportRent;
	private int mortgagePrice;
	
	public Airport(Monopoly game, String name) {
		super(game);
		this.name = name;
		oneAirportRent = 300;
		twoAirportRent = 500;
		mortgagePrice = 1000;
	}
	
	@Override
	public int getPrice() {
		//FIXME
		return 0;
	}
	
	//FIXME: check whether the other airport belongs to the same person
	public int getOneAirportRent() {
		return oneAirportRent;
	}
	
	public int getTwoAirportRent() {
		return twoAirportRent;
	}
	
	public int getMortgagePrice() {
		return mortgagePrice;
	}
	
	@Override
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	@Override
	public Player getOwner() {
		return owner;
	}
	
	@Override
	public int getRent() {
		//FIXME
		return 0;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "<AIRPORT>";
	}
}

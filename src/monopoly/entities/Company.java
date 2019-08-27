package monopoly.entities;

import monopoly.main.Monopoly;
import monopoly.main.Player;

public class Company extends Entity implements Property{
	private String name;
	private Player owner = null;
	private int price;
	private int mortgagePrice;
	
	public Company(Monopoly game, String name) {
		super(game);
		this.name = name;
		this.price = 1200;
		this.mortgagePrice = 600;
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
	
	@Override
	public int getPrice() {
		return price;
	}
	
	public String toString() {
		return "<COMPANY - " + name + ">";
	}
}

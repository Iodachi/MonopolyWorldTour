package monopoly.entities;

import monopoly.main.Player;

public interface Property {
	public int getPrice();
	
	public int getRent();
	
	public void setOwner(Player owner);
	
	public Player getOwner();
	
	public String getName();
}

package monopoly.entities;

import monopoly.main.Monopoly;

public class GoToJail extends Entity{
	public GoToJail(Monopoly game) {
		super(game);
	}
	
	public String toString() {
		return "<GO-TO-JAIL>";
	}
}

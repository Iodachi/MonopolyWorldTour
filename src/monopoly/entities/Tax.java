package monopoly.entities;

import monopoly.main.Monopoly;

public class Tax extends Entity{
	private String name;
	
	public Tax(Monopoly game, String name) {
		super(game);
		this.name = name;
	}
}

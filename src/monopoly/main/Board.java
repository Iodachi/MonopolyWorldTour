package monopoly.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import monopoly.entities.*;

public class Board {
	private List<Entity> entities;
	private Monopoly game;
	private List<String> propertyNames;
	
	public Board(Monopoly game) {
		entities = new ArrayList<Entity>();
		this.game = game;
		
		Country china = new Country(game, Color.PINK, "China", 600, 500, 100, 200, 500, 900, 300);
		Country korea = new Country(game, Color.PINK, "Korea", 1000, 500, 100, 300, 900, 2700, 500);
		Country australia = new Country(game, Color.GREEN, "Australia", 1600, 1000, 200, 600, 1800, 5000, 1000);
		Country egypt = new Country(game, Color.GREEN, "Egypt", 2000, 1000, 200, 800, 2200, 6000, 1000);
		Country southAfrica = new Country(game, Color.BLUE, "SouthAfrica", 2200, 1500, 200, 900, 2500, 7000, 1100);
		Country argentina = new Country(game, Color.BLUE, "Argentina", 2400, 1500, 200, 1000, 3000, 7500, 1200);
		Country france = new Country(game, Color.BLUE, "France", 2600, 1500, 300, 1100, 3300, 8000, 1300);
		Country spain = new Country(game, Color.RED, "Spain", 2200, 1500, 200, 900, 2500, 7000, 1100);
		Country england = new Country(game, Color.RED, "England", 2400, 1500, 200, 1000, 3000, 7500, 1200);
		Country italy = new Country(game, Color.YELLOW, "Italy", 3000, 2000, 300, 1300, 3900, 9000, 1500);
		Country switzerland = new Country(game, Color.YELLOW, "Switzerland", 3600, 2000, 400, 1800, 5000, 11000, 1800);
		Country russia = new Country(game, Color.ORANGE, "Russia", 3200, 2000, 300, 1500, 4500, 10000, 1600);
		Country america = new Country(game, Color.ORANGE, "America", 2200, 1500, 200, 900, 2500, 7000, 1100);
		Country japan = new Country(game, Color.ORANGE, "Japan", 1400, 1000, 100, 500, 1500, 4500, 700);
		
		new ColourGroup(Color.PINK, china, korea);
		new ColourGroup(Color.GREEN, australia, egypt);
		new ColourGroup(Color.BLUE, southAfrica, argentina, france);
		new ColourGroup(Color.RED, spain, england);
		new ColourGroup(Color.YELLOW, italy, switzerland);
		new ColourGroup(Color.ORANGE, russia, america, japan);
		
		entities.add(new Start(game));
		entities.add(china);
		entities.add(korea);
		entities.add(new Chance(game));
		entities.add(australia);
		entities.add(new Airport(game, "Hawaii Airport"));
		entities.add(new Fate(game));
		entities.add(new Tax(game, "Income Tax"));
		entities.add(egypt);
		entities.add(new Jail(game));
		entities.add(southAfrica);
		entities.add(new Company(game, "Electricity Company"));
		entities.add(argentina);
		entities.add(new Chance(game));
		entities.add(france);
		entities.add(new CarPark(game));
		entities.add(spain);
		entities.add(england);
		entities.add(new Fate(game));
		entities.add(italy);
		entities.add(new Tax(game, "Property Tax"));
		entities.add(new Airport(game, "London Airport"));
		entities.add(new Chance(game));
		entities.add(switzerland);
		entities.add(new GoToJail(game));
		entities.add(russia);
		entities.add(new Company(game, "Water Company"));
		entities.add(america);
		entities.add(new Fate(game));
		entities.add(japan);
		
		//System.out.println(entities.size());
		
		propertyNames = new ArrayList<String>();
		propertyNames.add("China");			propertyNames.add("Korea");		propertyNames.add("Australia");	propertyNames.add("Egypt");
		propertyNames.add("SouthAfrica");	propertyNames.add("Argentina");	propertyNames.add("France");	propertyNames.add("Spain");
		propertyNames.add("England");		propertyNames.add("Italy");		propertyNames.add("Switzerland");propertyNames.add("Russia");
		propertyNames.add("America");		propertyNames.add("Japan");
		
		//set the position of entities on board
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(i == 0 || i == 9 || i == 15 || i == 24)
				e.setCorner(true);
			else {
				if((i > 0 && i < 9) || (i > 15 && i < 24))
					e.setTopBottom(true);
				else
					e.setLeftRight(true);
			}
		}
	}
	
	/**
	 * get the index of a property given its name
	 * @param name
	 * @return
	 * 			- -1 if not found
	 */
	public int findPropertyByName(String name) {
		for(Entity e: entities) {
			if(e instanceof Country) {
				if(((Country) e).getName().equals(name))
					return entities.indexOf(e);
			}
		}
		return -1;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public List<String> getPropertyNames() {
		return propertyNames;
	}
}

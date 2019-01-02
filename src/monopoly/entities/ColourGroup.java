package monopoly.entities;

import java.awt.Color;
import java.util.*;

import monopoly.main.Player;

public class ColourGroup implements Iterable<Country>{
	private Country[] properties;
	private Color colour;

	/**
	 * Create colour group made up of Streets supplied as arguments
	 */
	public ColourGroup(Color colour, Country... properties) {
		this.properties = Arrays.copyOf(properties, properties.length);
		for (Country property : properties) {
			property.setColourGroup(this);
		}
		this.colour = colour;
	}

	public Color getColour() {
		return colour;
	}
	
	/**
	 * Check whether all properties in this group are owned by the given player.
	 * 
	 * @param player
	 * @return
	 */
	public boolean allPropertiesOwnedBy(Player player) {
		// Check whether all properties in colour group owned by same player
		for(Country p : properties) {
			if(p.getOwner() != player) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Get an iterator to iterate through all the streets in this group.
	 * 
	 * @return
	 */
	public Iterator<Country> iterator() {
		return Arrays.asList(properties).iterator();
	}
}

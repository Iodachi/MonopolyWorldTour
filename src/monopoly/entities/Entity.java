package monopoly.entities;

import java.util.ArrayList;
import java.util.List;

import monopoly.main.Monopoly;
import monopoly.main.Player;
import monopoly.view.BoardPanel;

public abstract class Entity {
	private Monopoly game;
	//the position of this entity on board
	private boolean corner = false;
	private boolean top_bottom = false;
	private boolean left_right = false;
	
	public Entity(Monopoly game) {
		this.game = game;
	}
	
	/**
	 * return the players at this entity, useful for drawing the tokens
	 * @return
	 */
	public List<Player> playerAtEntity() {
		List<Player> players = new ArrayList<Player>();
		for(Player p: game.getPlayers()) {
			if(p.getCurrentEntity() == this)
				players.add(p);
		}
		
		return players;
	}
	
	public int getXPos() {
		int index = game.getBoard().getEntities().indexOf(this);
		if(index >= 0 && index < 9)
			return (int)(100 + BoardPanel.ENTITY_HEIGHT + BoardPanel.ENTITY_WIDTH * (8 - index));
		else if(index >= 9 && index < 16)
			return 100;
		else if(index >= 16 && index < 25)
			return (int)(100 + BoardPanel.ENTITY_HEIGHT + BoardPanel.ENTITY_WIDTH * (index - 16));
		else
			return (int)(100 + BoardPanel.ENTITY_HEIGHT + BoardPanel.ENTITY_WIDTH * 8);
	}
	
	public int getYPos() {
		int index = game.getBoard().getEntities().indexOf(this);
		if(index >= 0 && index <= 9)
			return (int)(100 + BoardPanel.ENTITY_HEIGHT + BoardPanel.ENTITY_WIDTH * 5);
		else if(index > 9 && index < 15)
			return (int)(100 + BoardPanel.ENTITY_HEIGHT + BoardPanel.ENTITY_WIDTH * (14 - index));
		else if(index >= 15 && index < 25)
			return 100;
		else
			return (int)(100 + BoardPanel.ENTITY_HEIGHT + BoardPanel.ENTITY_WIDTH * (index - 25));
	}
	
	public boolean isCorner() {
		return corner;
	}
	
	public void setCorner(boolean corner) {
		this.corner = corner;
	}
	
	public boolean isLeftRight() {
		return left_right;
	}
	
	public void setLeftRight(boolean left_right) {
		this.left_right = left_right;
	}
	
	public boolean isTopBottom() {
		return top_bottom;
	}
	
	public void setTopBottom(boolean top_bottom) {
		this.top_bottom = top_bottom;
	}
}

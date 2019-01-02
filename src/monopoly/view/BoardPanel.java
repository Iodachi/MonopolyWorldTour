package monopoly.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import monopoly.controller.Controller;
import monopoly.entities.Entity;
import monopoly.entities.Property;
import monopoly.main.Monopoly;
import monopoly.main.Player;

public class BoardPanel extends JPanel{
	public static final double ENTITY_WIDTH = 308 / 4;
	public static final double ENTITY_HEIGHT = 453 / 4;
	public static final int TOKEN_SIZE = 30;
	
	private Monopoly game;
	private Controller controller;
	private Map<String, Image> images;
	
	public BoardPanel(Monopoly game, Controller controller, GameView view) {
		this.game = game;
		this.controller = controller;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.YELLOW);
		loadImages();
	}
	
	public void loadImages() {
		images = new HashMap<String, Image>();

		try {
			for(String name: game.getBoard().getPropertyNames()) 
				images.put(name, ImageIO.read(this.getClass().getResource(name + ".png")));
			
			images.put("Blue", ImageIO.read(this.getClass().getResource("Blue.png")));
			images.put("Red", ImageIO.read(this.getClass().getResource("Red.png")));
			images.put("Yellow", ImageIO.read(this.getClass().getResource("Yellow.png")));
			images.put("Green", ImageIO.read(this.getClass().getResource("Green.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//draw the pictures of entities
		for(String name: game.getBoard().getPropertyNames()) {
			int index = game.getBoard().findPropertyByName(name);
			Entity property = game.getBoard().getEntities().get(index);
			if((index > 0 && index < 9) || (index > 15 && index < 24)){
				g.drawImage(images.get(name), property.getXPos(), property.getYPos(), (int)ENTITY_WIDTH, (int)ENTITY_HEIGHT, null);
			} else {
				g.drawImage(images.get(name), property.getXPos(), property.getYPos(), (int)ENTITY_HEIGHT, (int)ENTITY_WIDTH, null);
			}
		}

		//draw the board frame
		g.drawLine(100, 100, (int) (ENTITY_WIDTH * 8 + ENTITY_HEIGHT * 2 + 100), 100);
		g.drawLine(100, (int) (100 + ENTITY_HEIGHT), (int) (ENTITY_WIDTH * 8 + ENTITY_HEIGHT * 2 + 100), (int) (100 + ENTITY_HEIGHT));
		for(int i = 1; i < 8; i++) {
			g.drawLine((int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100), 100, (int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100), (int) (100 + ENTITY_HEIGHT));
		}
		
		g.drawLine(100, 100, 100, (int)(ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100)); 
		g.drawLine((int) (100 + ENTITY_HEIGHT), 100, (int) (100 + ENTITY_HEIGHT), (int)(ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100));
		for(int i = 1; i < 5; i++) {
			g.drawLine(100, (int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100), (int) (100 + ENTITY_HEIGHT), (int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100)); 
		}
		
		g.drawLine(100, (int) (ENTITY_HEIGHT + ENTITY_WIDTH * 5 + 100), (int) (ENTITY_WIDTH * 8 + ENTITY_HEIGHT * 2 + 100), (int) (ENTITY_HEIGHT + ENTITY_WIDTH * 5+ 100));
		g.drawLine(100, (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100), (int) (ENTITY_WIDTH * 8 + ENTITY_HEIGHT * 2 + 100), (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100));
		for(int i = 1; i < 8; i++) {
			g.drawLine((int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100), (int) (ENTITY_HEIGHT + ENTITY_WIDTH * 5 + 100), (int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100), (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100));
		}
		
		g.drawLine((int) (ENTITY_HEIGHT + ENTITY_WIDTH * 8 + 100), 100, (int) (ENTITY_HEIGHT + ENTITY_WIDTH * 8 + 100), (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100));
		g.drawLine((int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 8 + 100), 100, (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 8 + 100), (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 5 + 100));
		for(int i = 1; i < 5; i++) {
			g.drawLine((int) (ENTITY_HEIGHT + ENTITY_WIDTH * 8 + 100), (int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100), (int) (ENTITY_HEIGHT * 2 + ENTITY_WIDTH * 8 + 100), (int) (ENTITY_HEIGHT + i * ENTITY_WIDTH + 100)); 
		}
		
		//draw the player token
		for(Entity e: game.getBoard().getEntities()) {
			List<Player> players = e.playerAtEntity();
			
			if(players.size() == 1) {
				String color = players.get(0).getColor();
				if(e.isLeftRight())
					g.drawImage(images.get(color), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), (int)(e.getYPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				else if(e.isTopBottom())
					g.drawImage(images.get(color), (int)(e.getXPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				else if(e.isCorner())
					g.drawImage(images.get(color), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
			} else if(players.size() == 2) {
				if(e.isLeftRight()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				} else if(e.isTopBottom()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				} else if(e.isCorner()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				}
			} else if(players.size() == 3) {
				if(e.isLeftRight()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), (int)(e.getYPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_WIDTH / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(2).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_WIDTH / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				} else if(e.isTopBottom()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(2).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				} else if(e.isCorner()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(2).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				}
			} else if(players.size() == 4) {
				if(e.isLeftRight()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(2).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_WIDTH / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(3).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_WIDTH / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				} else if(e.isTopBottom()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(2).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(3).getColor()), (int)(e.getXPos() + ENTITY_WIDTH / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				} else if(e.isCorner()) {
					g.drawImage(images.get(players.get(0).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(1).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(2).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2 - TOKEN_SIZE), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
					g.drawImage(images.get(players.get(3).getColor()), (int)(e.getXPos() + ENTITY_HEIGHT / 2), (int)(e.getYPos() + ENTITY_HEIGHT / 2), TOKEN_SIZE, TOKEN_SIZE, null);
				}
			}
		}
		
		//cover the property with the color of its owner
		for(Entity e: game.getBoard().getEntities()) {
			if(e instanceof Property) {
				if(((Property) e).getOwner() != null) {
					String color = ((Property) e).getOwner().getColor();
					Color c = null;
					if(color.equals("Blue"))		c = new Color(0, 0, 255, 80);
					else if(color.equals("Red"))	c = new Color(255, 0, 0, 80);
					else if(color.equals("Yellow"))	c = new Color(255, 255, 0, 80);
					else if(color.equals("Green"))	c = new Color(0, 255, 0, 80);
					g.setColor(c);
					
					if(e.isTopBottom())
						g.fillRect(e.getXPos(), e.getYPos(), (int)ENTITY_WIDTH, (int)ENTITY_HEIGHT);
					else if(e.isLeftRight())
						g.fillRect(e.getXPos(), e.getYPos(), (int)ENTITY_HEIGHT, (int)ENTITY_WIDTH);
				}
			}
		}
	}
}

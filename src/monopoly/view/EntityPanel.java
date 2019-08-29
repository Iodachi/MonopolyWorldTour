package monopoly.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import monopoly.entities.Entity;
import monopoly.entities.Property;
import monopoly.main.Monopoly;
import monopoly.main.Player;

public class EntityPanel extends JLabel{
	public static final double ENTITY_WIDTH = 308 / 4;
	public static final double ENTITY_HEIGHT = 453 / 4;
	private Monopoly game;
	private String name;
	private Entity entity;
	private BufferedImage image;
	private ImageIcon entityImage;
	
	public EntityPanel(Monopoly game, String name) throws IOException {
		image = ImageIO.read(this.getClass().getResource(name + ".png"));
		int index = game.getBoard().findPropertyByName(name);
		Image scaledImage = image;
		this.name = name;
		this.game = game;
		
		entity = game.getBoard().getEntities().get(index);
		boolean isTopBottom = (index > 0 && index < 9) || (index > 15 && index < 24);
		
		if(isTopBottom) {
			scaledImage = image.getScaledInstance((int)ENTITY_WIDTH, (int)ENTITY_HEIGHT, Image.SCALE_SMOOTH);
			this.setBounds(entity.getXPos(), entity.getYPos(), (int)ENTITY_WIDTH, (int)ENTITY_HEIGHT);
		} else {
			scaledImage = image.getScaledInstance((int)ENTITY_HEIGHT, (int)ENTITY_WIDTH, Image.SCALE_SMOOTH);
			this.setBounds(entity.getXPos(), entity.getYPos(), (int)ENTITY_HEIGHT, (int)ENTITY_WIDTH);
		}
		entityImage = new ImageIcon(scaledImage);
		this.setIcon(entityImage);
	}
	
	/**
	 * Rotates a given image by a given angle
	 * @param src
	 * @param angle
	 * @return
	 */
	public BufferedImage rotateImage(BufferedImage src, double angle) {
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.rotate(Math.toRadians(angle), width/2, height/2);		
		g.drawImage(src,0,0,width,height,null);
		g.dispose();
		return img;
	}
	
	public void showEntityInfoPanel() {
		String ownerInfo = "";
		if(entity instanceof Property) {
			ownerInfo = "<br>Owned by: ";
			Player owner = ((Property) entity).getOwner();
			ownerInfo = owner == null ? "None" : owner.getColor();
		}
		String labelText ="<html>Name: " + name + ownerInfo + "<br>Property price: " + "</html>";
		this.setText(labelText);
		this.setIcon(null);
	}
	
	public void hideEntityInfoPanel() {
		this.setText("");
		this.setIcon(entityImage);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}

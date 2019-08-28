package monopoly.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import monopoly.entities.Entity;
import monopoly.main.Monopoly;

public class EntityPanel extends JLabel{
	public static final double ENTITY_WIDTH = 308 / 4;
	public static final double ENTITY_HEIGHT = 453 / 4;
	private Monopoly game;
	private String name;
	private BufferedImage image;
	
	public EntityPanel(Monopoly game, String name) throws IOException {
		image = ImageIO.read(this.getClass().getResource(name + ".png"));
		int index = game.getBoard().findPropertyByName(name);
		Image scaledImage = image;
		if((index > 0 && index < 9) || (index > 15 && index < 24)){
			scaledImage = image.getScaledInstance((int)ENTITY_WIDTH, (int)ENTITY_HEIGHT, Image.SCALE_SMOOTH);
		}else {
			scaledImage = image.getScaledInstance((int)ENTITY_HEIGHT, (int)ENTITY_WIDTH, Image.SCALE_SMOOTH);
		}
		this.setIcon(new ImageIcon(scaledImage));
		this.name = name;
		this.game = game;
		Entity property = game.getBoard().getEntities().get(index);
		if((index > 0 && index < 9) || (index > 15 && index < 24)){
			this.setBounds(property.getXPos(), property.getYPos(), (int)ENTITY_WIDTH, (int)ENTITY_HEIGHT);
		}else {
			this.setBounds(property.getXPos(), property.getYPos(), (int)ENTITY_HEIGHT, (int)ENTITY_WIDTH);
		}
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}

package monopoly.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EntityPanel extends JLabel{
	private String imageName;
	private BufferedImage image;
	
	public EntityPanel(String imageName) throws IOException {
		image = ImageIO.read(this.getClass().getResource(imageName));
		this.setIcon(new ImageIcon(image));
		this.imageName = imageName;
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
}

package monopoly.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import monopoly.controller.Controller;
import monopoly.main.Monopoly;
import monopoly.main.Player;

public class PlayerInfoPanel extends JPanel{
	private Monopoly game;
	private Controller controller;
	private JTextPane paneBlue, paneRed, paneYellow, paneGreen;
	private boolean hasYellow, hasGreen;
	private static final int LAYOUT_GAP = 5;
	
	public PlayerInfoPanel(Monopoly game, Controller controller, GameView view) {
		this.game = game;
		this.controller = controller;
		List<Player> players = game.getPlayers();
		hasYellow = players.size() > 2;
		hasGreen = players.size() > 3;

        paneBlue = new JTextPane();
        paneRed = new JTextPane();
        paneYellow = new JTextPane();
        paneGreen = new JTextPane();
        
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();  
		StyleConstants.setBold(attributeSet, true);  
		
		StyleConstants.setForeground(attributeSet, Color.blue);  
		paneBlue.setCharacterAttributes(attributeSet, true);  
		
		attributeSet = new SimpleAttributeSet();  
		StyleConstants.setForeground(attributeSet, Color.red);  
		paneRed.setCharacterAttributes(attributeSet, true);  
		
        attributeSet = new SimpleAttributeSet();  
		StyleConstants.setForeground(attributeSet, Color.yellow.darker());  
		if(!hasYellow) {
			StyleConstants.setStrikeThrough(attributeSet, true);
		}
		paneYellow.setCharacterAttributes(attributeSet, true);  
		
        attributeSet = new SimpleAttributeSet();  
		StyleConstants.setForeground(attributeSet, Color.green);
		if(!hasGreen) {
			StyleConstants.setStrikeThrough(attributeSet, true);
		}
		paneGreen.setCharacterAttributes(attributeSet, true);  
		
		this.setPlayerInfoText();
		
		this.add(paneBlue);
		this.add(paneRed);
		this.add(paneYellow);
		this.add(paneGreen);
		this.setLayout(new GridLayout(2, 2));
		

		Border title = BorderFactory.createTitledBorder("Player info");
		Border edge = BorderFactory.createEmptyBorder(LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP);
		Border border = BorderFactory.createCompoundBorder(title, edge);
		this.setBorder(border);
	}
	
	public void setPlayerInfoText() {
		paneBlue.setText("Blue\n $" + game.getPlayerFunds("Blue"));  
		paneRed.setText("Red\n $" + game.getPlayerFunds("Red")); 
		paneYellow.setText("Yellow" + (hasYellow ? "\n $" + game.getPlayerFunds("Yellow") : ""));   
		paneGreen.setText("Green" + (hasGreen ? "\n $" + game.getPlayerFunds("Green") : ""));  
	}
}

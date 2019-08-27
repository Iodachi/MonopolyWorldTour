package monopoly.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

import monopoly.controller.Controller;
import monopoly.main.Monopoly;

public class UtilityPanel extends JPanel{
	private JTextArea textArea;
	private JScrollPane scroll;
	private JToolBar button;
	private JPanel playerInfo;
	
	public UtilityPanel(Monopoly game, Controller controller, GameView view) {
		textArea = new JTextArea(10, 0);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true); 
		textArea.setEditable(false);
		
		scroll = new ScrollPanel(textArea);
		button = new ButtonPanel(game, controller, view);
		playerInfo = new PlayerInfoPanel(game, controller, view);
		
		this.setLayout(new BorderLayout());
		this.add(button, BorderLayout.NORTH);
		this.add(playerInfo, BorderLayout.CENTER);
		this.add(scroll, BorderLayout.SOUTH); 
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, button, playerInfo); 
		this.add(splitPane);
	}
	
	public JTextArea getTextOutputArea() {
		return textArea;
	}
	
	public JPanel getPlayerInfoPanel() {
		return playerInfo;
	}
}

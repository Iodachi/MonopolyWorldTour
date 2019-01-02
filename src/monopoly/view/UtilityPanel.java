package monopoly.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import monopoly.controller.Controller;
import monopoly.main.Monopoly;

public class UtilityPanel extends JPanel{
	private JTextArea textArea;
	private JScrollPane scroll;
	private JToolBar button;
	
	public UtilityPanel(Monopoly game, Controller controller, GameView view) {
		textArea = new JTextArea(10, 0);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true); 
		textArea.setEditable(false);
		
		scroll = new ScrollPanel(textArea);
		button = new ButtonPanel(game, controller, view);
		
		this.setLayout(new BorderLayout());
		this.add(button, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.SOUTH);
	}
}

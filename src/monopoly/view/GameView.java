package monopoly.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import monopoly.controller.Controller;
import monopoly.main.Monopoly;

public class GameView extends JComponent implements Observer{
	private Monopoly game;
	private JFrame f;
	private Controller controller;
	private JPanel board, utility;
	
	public GameView(Monopoly game) {
		this.game = game;
		game.addObserver(this);
		f = new JFrame("The Monopoly - World Tour");
		f.setLayout(new BorderLayout());
		f.pack();
		f.setVisible(true);
		f.setFocusable(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		controller = new Controller(game, this);
		board = new BoardPanel(game, controller, this);
		utility = new UtilityPanel(game, controller, this);
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.setDividerSize(2);
		split.setContinuousLayout(true); 	
		split.setResizeWeight(1); 
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setLeftComponent(board);
		split.setRightComponent(utility);
		
		f.add(split, BorderLayout.CENTER);
	}
	
	public JPanel getBoardPanel() {
		return board;
	}

	public JPanel getPlayerInfoPanel() {
		return ((UtilityPanel) utility).getPlayerInfoPanel();
	}
	
	public JTextArea getTextOutputArea() {
		return ((UtilityPanel) utility).getTextOutputArea();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		f.repaint();
	}
}

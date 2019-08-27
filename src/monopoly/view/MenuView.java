package monopoly.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import monopoly.main.Monopoly;

public class MenuView {
	private JFrame f;
	private JPanel text; 
	private JTextArea infoText;
	
	public MenuView(){
		f = new JFrame("The Monopoly - China Tour");
		
		//prints the game title
		infoText = new JTextArea("	****** WELCOME TO THE MONOPOLY GAME! ******	");
		text = new JPanel();
		text.setLayout(new BorderLayout());
		text.add(infoText, BorderLayout.CENTER);
		text.setMinimumSize(new Dimension(600, 400));
		
		//the panel that contains the three buttons
		JPanel startquit = new JPanel();
		startquit.setLayout(new GridLayout(3, 1));
		
		JButton newGame = new JButton("Begin new game");        
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) { 
				//TODO: ask how many players
				//Monopoly game = new Monopoly();
				f.setVisible(false);
				//SwingUtilities.invokeLater(()->new GameView(game));
			}
		});
		
		JButton quit = new JButton("Quit"); 
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0); //end the program.
			}
		});
		
		startquit.add(newGame);
		startquit.add(quit);
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setPreferredSize(new Dimension(600, 600));
		split.setDividerSize(2);
		split.setContinuousLayout(true); 	
		split.setResizeWeight(1);			 
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(startquit);
		split.setBottomComponent(text);
		
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(split, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}
}

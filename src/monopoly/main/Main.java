package monopoly.main;

import javax.swing.SwingUtilities;

import monopoly.view.GameView;
import monopoly.view.MenuView;

public class Main {
	public static void main(String[] arg) {
		Monopoly game = new Monopoly(4, 10000);
		SwingUtilities.invokeLater(()->new GameView(game));
	}
}

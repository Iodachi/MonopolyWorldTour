package monopoly.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import monopoly.controller.Controller;
import monopoly.main.InvalidMove;
import monopoly.main.Monopoly;
import monopoly.main.Player;
import monopoly.entities.*;

public class ButtonPanel extends JToolBar {
	private int step = 0;
	public ButtonPanel(Monopoly game, Controller controller, GameView view) {
		JButton roll = new JButton("ROLL");
		roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Player currentPlayer = game.getCurrentPlayer();
				try {
					step = game.roll(game.getCurrentPlayer());
					game.move(game.getCurrentPlayer(), step);
					view.getBoardPanel().repaint();
					game.setMovingStage(false);

					int status = game.checkCurrentEntity(currentPlayer);
					Entity e = currentPlayer.getCurrentEntity();
					String name = "";
					if(e instanceof Property) {
						name = ((Property) e).getName();

						if(status == 1) {
							int option = JOptionPane.showConfirmDialog(null, name + " is not occupied, $" + ((Property) e).getPrice() + ".\nDo you wish to buy it?", "Waiting player buy property...", JOptionPane.YES_NO_OPTION);
							if(option == JOptionPane.YES_OPTION)
								game.buyProperty(currentPlayer, (Property)e);
						} else if(status == 2) {

						} else if(status == 3) {
							Player owner = ((Property) e).getOwner();
							int amount = ((Property) e).getRent();
							JOptionPane.showMessageDialog(null, name + " is occupied by " + owner.getColor() + "\nYou need to pay $" + amount, "Waiting player pay rent...", JOptionPane.PLAIN_MESSAGE);
							game.payRent(currentPlayer, owner, amount);
						}
					}
					
					view.getBoardPanel().repaint();
					game.nextPlayer();
					game.setMovingStage(true);
				} catch (InvalidMove e) {
					System.out.println(e.getMessage());
				}
			}

		}); 
		this.add(roll, BorderLayout.CENTER);
		roll.setFocusable(false);
		this.setLayout(new GridLayout(1, 1));
	}

}

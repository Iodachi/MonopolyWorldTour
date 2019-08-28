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
				String playerName = currentPlayer.getColor();
				String playerLocation = currentPlayer.getCurrentEntity().toString();
				try {
					step = game.roll(game.getCurrentPlayer());
					view.getTextOutputArea().append("Player <" + playerName + "> rolled a dice of <" + step + ">.\n");
					game.move(game.getCurrentPlayer(), step);
					view.getTextOutputArea().append("Player <" + playerName + "> moved from location" + playerLocation + " to location" + currentPlayer.getCurrentEntity().toString() + ".\n");
					
					view.getBoardPanel().repaint();
					game.setMovingStage(false);

					int status = game.checkCurrentEntity(currentPlayer);
					Entity e = currentPlayer.getCurrentEntity();
					String name = "";
					if(e instanceof Property) {
						name = ((Property) e).getName();

						if(status == 1) {	//buy property
							int option = JOptionPane.showConfirmDialog(null, name + " is not occupied, $" + ((Property) e).getPrice() + ".\nDo you wish to buy it?", "Waiting player buy property...", JOptionPane.YES_NO_OPTION);
							if(option == JOptionPane.YES_OPTION) {
								try {
									game.buyProperty(currentPlayer, (Property)e);
									view.getTextOutputArea().append("Player <" + playerName + "> bought the property <" + name + ">.\n");
								}catch(InvalidMove i) {
									view.getTextOutputArea().append("Player <" + playerName + "> failed to buy the property <" + name +">: " + i.getMessage() + ".\n");
								}
							}
						} else if(status == 2) {	//build house on owned property
							int option = JOptionPane.showConfirmDialog(null, name + " is occupied by yourself, $" + ((Country) e).getConstructionPrice() + ".\nDo you wish to build house?", "Waiting player build house...", JOptionPane.YES_NO_OPTION);
							if(option == JOptionPane.YES_OPTION) {
								try {
									game.buildHouses(currentPlayer, (Country)e);
									view.getTextOutputArea().append("Player <" + playerName + "> built a house on property <" + name + ">.\n");
									//view.getTextOutputArea().append("The number of houses on property <" + name + "> reaches 3 and it turns into a hotel!\n");
								}catch(InvalidMove i) {
									view.getTextOutputArea().append("Player <" + playerName + "> failed to build a house on property <" + name + ">: " + i.getMessage() + ".\n");
								}
							}
						} else if(status == 3) {	//pay rent when passes others' property
							Player owner = ((Property) e).getOwner();
							int amount = ((Property) e).getRent();
							JOptionPane.showMessageDialog(null, name + " is occupied by " + owner.getColor() + "\nYou need to pay $" + amount, "Waiting player pay rent...", JOptionPane.PLAIN_MESSAGE);
							try {
								game.payRent(currentPlayer, owner, amount);
								view.getTextOutputArea().append("Player <" + playerName + "> passed player <" + owner.getColor() + ">'s property <"
									+ name + "> and paid <" + amount + ">.\n");
							}catch(InvalidMove i) {
								view.getTextOutputArea().append("Player <" + playerName + "> failed to pay rent <" + amount +"> to <" + owner.getColor() + ">: " + i.getMessage() + ".\n");
							}
						}
					}
					
					//repaint player info panel
					PlayerInfoPanel playerInfo = (PlayerInfoPanel) view.getPlayerInfoPanel();
					playerInfo.setPlayerInfoText();
					
					view.getBoardPanel().repaint();
					game.nextPlayer();
					Player nextPlayer = game.getCurrentPlayer();
					view.getTextOutputArea().append("========== Player <" + nextPlayer.getColor() + ">'s turn ==========");
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

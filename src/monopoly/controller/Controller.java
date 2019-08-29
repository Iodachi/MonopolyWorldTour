package monopoly.controller;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import monopoly.main.Monopoly;
import monopoly.view.EntityPanel;
import monopoly.view.GameView;

public class Controller implements MouseListener, KeyListener, ComponentListener{
	private Monopoly game;
	private GameView view;
	
	public Controller(Monopoly game, GameView view) {
		this.game = game;
		this.view = view;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Component component = e.getComponent();
		if(component != null) {
			if(component instanceof EntityPanel) {
				System.out.println("Entered entity");
				((EntityPanel) component).showEntityInfoPanel();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Component component = e.getComponent();
		if(component != null) {
			if(component instanceof EntityPanel) {
				System.out.println("Exited entity");
				((EntityPanel) component).hideEntityInfoPanel();
			}
		}
	}

}

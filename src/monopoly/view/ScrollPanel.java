package monopoly.view;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * A panel that display text description of the overall status of game.
 * @author stella
 *
 */
public class ScrollPanel extends JScrollPane{
	
	public ScrollPanel(JTextArea text) {
		super(text);
		
		//this auto scroll to bottom doesn't seem to work...
		DefaultCaret caret = (DefaultCaret) text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
}

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Extends KeyAdapter to catch keys pressed
 * @author Anton
 *
 */
public class KeySeer extends KeyAdapter  {
	GamePanel panel;
	/**
	 * KeySeer constructor 
	 * @param panel - Game panel - GamePanel class
	 * @see GamePanel
	 */
	public KeySeer(GamePanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		panel.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		panel.keyReleased(e);
	}
}

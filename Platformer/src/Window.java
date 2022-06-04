import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
/**
 * This class creates a window.
 * Is extended after JFrame  
 * @author Anton
 */
@SuppressWarnings("serial")
public class Window extends JFrame {

	/**
	 * Window constructor, initializes windows settings, creates GamePanel and adds
	 * it to the window, also adds key listener.
	 * 
	 * @param opt - Game options
	 * @param mapManager - Map Manager
	 * @see KeySeer
	 * @see Options
	 * @see MapManager
	 */
	public Window(Options opt, MapManager mapManager) {
		
		this.setSize(900, 900);

		// centering the window to the middle of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2),
				(int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2)); 
		
		// setting up window
		this.setResizable(false);
		this.setTitle("Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// creating panel
		GamePanel panel = new GamePanel(opt, mapManager, this);
		panel.setVisible(true);
		this.add(panel);
		
		// adding a key listeners
		this.addKeyListener(new KeySeer(panel));
	}

}

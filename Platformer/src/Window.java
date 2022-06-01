import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {
	Dimension screenSize;
	GamePanel panel;

	public Window(Options opt, MapManager mapManager) {
		
		this.setSize(900, 900);

		// centering the window to the middle of screen
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2),
				(int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2)); 
		
		// setting up window
		this.setResizable(false);
		this.setTitle("Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// creating panel
		panel = new GamePanel(opt, mapManager);
		panel.setVisible(true);
		this.add(panel);
		
		// adding a key listeners
		this.addKeyListener(new KeySeer(panel));
	}

}

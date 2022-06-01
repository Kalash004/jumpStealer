import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	ArrayList<Wall> walls = new ArrayList<Wall>(); // holds all walls in game
	Player player;
	Timer gameTimer;
	Options opt;

	public GamePanel(Options opt) {
		// setting up game panel
		this.setLocation(0, 0);
		this.setSize(this.getSize());
		this.setBackground(opt.backGround);
		// setting up player, walls and game loop
		player = new Player(400, 00, this, opt);
		this.opt = opt; 
		makeWalls();

		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {
			// abstract class task
			@Override
			public void run() { // game loop
				try {					
					player.set();
					repaint();
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}

			}
		}, 0, 17); // (.. , timer starts right away, 17 milisec to wait for 60fps)

	}

	public void makeWalls() {
		for (int i = 50;i < 650; i+=50) {
			walls.add(new Wall(i, 600, 50, 50,opt));
		}
		walls.add(new Wall(50, 550, 50,50,opt));
		walls.add(new Wall(100, 550, 50,50,opt));
		walls.add(new Wall(50, 500, 50,50,opt));
		walls.add(new Wall(50, 300, 50,50,opt));
		walls.add(new Wall(150, 400, 100,5,opt));

	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D gtd = (Graphics2D) g;
		player.draw(gtd);
		for (Wall wall : walls)
			wall.draw(gtd);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'a')
			player.kLeft = true;
		if (e.getKeyChar() == 'd')
			player.kRight = true;
		if (e.getKeyChar() == 'w')
			player.kUp = true;
		if (e.getKeyChar() == 's')
			player.kDown = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a')
			player.kLeft = false;
		if (e.getKeyChar() == 'd')
			player.kRight = false;
		if (e.getKeyChar() == 'w')
			player.kUp = false;
		if (e.getKeyChar() == 's')
			player.kDown = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}

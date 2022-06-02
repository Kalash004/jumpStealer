import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	ArrayList<Wall> walls = new ArrayList<Wall>(); // holds all walls in game
	Player player;
	Timer gameTimer;
	Options opt;
	MapManager mapManager;
	Window wind;
	public int cameraX, cameraY;
	public boolean redrawMap = true;

	public GamePanel(Options opt, MapManager mapManager, Window wind) {
		// setting up game panel
		this.setLocation(0, 0);
		this.setSize(this.getSize());
		this.setBackground(opt.backGround);
		// setting up player, walls and game loop
		this.wind = wind;
		player = new Player(400, 0, this, opt);
		this.mapManager = mapManager;
		mapManager.gamePanel = this;
		this.opt = opt; 
		reset();
		
		
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {
			// abstract class task
			@Override
			public void run() { // game loop
				try {					
					player.set();
					for(Wall wall : walls) {
						wall.set(cameraX,cameraY);
					}
					repaint();
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}

			}
		}, 0, 17); // (.. , timer starts right away, 17 milisec to wait for 60fps)

	}
// load auto test map
	public void makeWalls(int offset) {
		int s = 50;
		Random rand = new Random();
		int index = rand.nextInt(1);
		
		
		
		for (int i = 50;i < 650; i+=50) {
			walls.add(new Wall(i, 600, 50, 50,opt));
		}//                 x, y, width, height
		walls.add(new Wall(50, 550, 50,50,opt));
		walls.add(new Wall(100, 550, 50,50,opt));
		walls.add(new Wall(50, 500, 50,50,opt));
		walls.add(new Wall(50, 300, 50,50,opt));
		walls.add(new Wall(150, 400, 100,5,opt));
		walls.add(new Wall(0,50,50, 8000,opt));
		walls.add(new Wall(this.getWidth(),50,50, 8000,opt));
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D gtd = (Graphics2D) g;
		player.draw(gtd); 
		Map map;
		for (Wall wall : walls) {	
			wall.draw(gtd);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			player.kLeft = true;
		if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			player.kRight = true;
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			player.kUp = true;
		if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			player.kDown = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			player.kLeft = false;
		if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			player.kRight = false;
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			player.kUp = false;
		if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			player.kDown = false;
	}
	
	public void reset() {

		
		player.x = wind.getWidth()/2;
		cameraY = 50;
		player.y = cameraY*8; //FIXME: Needs clean new way
		player.xSpeed = 0;
		player.ySpeed = 0;
		walls.clear();
		int offset = 50;
		makeWalls(offset);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}

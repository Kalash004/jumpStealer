import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 * Hosting game loop and drawing out the game.
 * @author Anton 
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	/**
	 * CAMERA_Y_OFFSET - used to offset y 50 blocks down in order to compensate for
	 * the window and other ui elements so that camera in in center of player.
	 */
	private static final int CAMERA_Y_OFFSET = 50;
	/**
	 * walls - holds all walls, from this array walls get drawn together with their
	 * hit boxes.
	 */
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private Player player;
	private MapManager mapManager;
	private Window wind;
	private int cameraX, cameraY;
	boolean redrawMap = true;

	/**
	 * 
	 * @param opt        - options of the game
	 * @param mapManager - map manager for the game
	 * @param wind       - window class, which wields this class
	 */
	public GamePanel(Options opt, MapManager mapManager, Window wind) {
		// setting up game panel
		this.setLocation(0, 0);
		this.setSize(this.getSize());
		this.setBackground(opt.getBackGround());
		// setting up player, walls and game loop
		this.wind = wind;
		player = new Player(400, 0, this, opt);
		this.mapManager = mapManager;
		mapManager.gamePanel = this;
		reset();

		Timer gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {
			@Override
			public void run() { // game loop
				try {
					player.set();
					for (Wall wall : walls) {
						wall.set(cameraX, cameraY);
					}
					repaint();
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}

			}
		}, 0, 17); // (.. , timer starts right away, 17 milisec to wait for 60fps)

	}

	/**
	 * This method adds walls from map manager current map
	 * 
	 * @see MapManager
	 */
	public void makeWalls() {
		Map map = mapManager.currentMap;
		for (Wall wall : map.map) {
			walls.add(wall);
		}

	}

	/**
	 * Used to paint all the game graphics.
	 * 
	 * @param g - Graphics
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D gtd = (Graphics2D) g;
		player.draw(gtd);
		for (Wall wall : walls) {
			wall.draw(gtd);
		}
	}

	/**
	 * This methods checks for "gamer keys (w,a,s,d + with holding shift)" being
	 * pressed and then sends the results to player booleans
	 * 
	 * @see Player
	 * @see KeySeer
	 * @param e - key event dropped from KeySeer
	 */
	public void keyPressed(KeyEvent e) {
		// need to check all possible combinations because we want to store all pressed
		// keys values
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			player.kLeft = true;
		if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			player.kRight = true;
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			player.kUp = true;
		if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			player.kDown = true;
	}

	/**
	 * This methods checks for "gamer keys (w,a,s,d + with holding shift)" being
	 * released and then sends the results to player booleans
	 * 
	 * @see Player
	 * @see KeySeer
	 * @param e - key event dropped from KeySeer
	 */
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

	/**
	 * This method is used at start of the game to respawn player and reset camera
	 * position. Also used to tp player back from the "Void"
	 */
	public void reset() {
		player.setX(wind.getWidth() / 2);
		cameraY = CAMERA_Y_OFFSET;
		player.setY(cameraY * 8);
		player.setxSpeed(0);
		player.setySpeed(0);
		walls.clear();
		makeWalls();
	}

	@Override 
	public void actionPerformed(ActionEvent e) {
		// not used
	}
	
	
	// not checking getters and setter, maybe in future patches
	public int getCameraX() {
		return cameraX;
	}

	public void setCameraX(int cameraX) {
		this.cameraX = cameraX;
	}

	public int getCameraY() {
		return cameraY;
	}

	public void setCameraY(int cameraY) {
		this.cameraY = cameraY;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}

}

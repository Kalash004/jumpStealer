import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * This class is made to create a block on the map
 * 
 * @author Anton
 */
public class Wall {
	/*
	 * TODO: add type to wall, if boundary make it follow player
	 */

	private Options opt;

	private int x, y, width, height, startX, startY;

	public Rectangle hitBox;

	/**
	 * Constructor for Wall class, creates hitbox and sets position and size.
	 * 
	 * @param x      - x position of the block
	 * @param y      - y position of the block
	 * @param width  - Width of the block
	 * @param height - Height of the block
	 * @param opt    - Game options
	 */
	public Wall(int x, int y, int width, int height, Options opt) {
		this.opt = opt;
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		this.width = width;
		this.height = height;

		hitBox = new Rectangle(x, y, width, height);

	}

	/**
	 * This method draws the block into the map, also creates a grid like pattern
	 * 
	 * @param gtd - Graphics 2D
	 */
	public void draw(Graphics2D gtd) {
		gtd.setColor(opt.getWallGridColor());
		gtd.drawRect(x, y, width, height);
		gtd.setColor(opt.getWallColor());
		gtd.fillRect(x + 1, y + 1, width - 2, height - 2);
	}

	/**
	 * This method makes walls move in order to simulate player movements so that
	 * player can move on the map.
	 * 
	 * @param cameraX - Camera x position
	 * @param cameraY - Camera y position
	 * @return - coordinates of the wall after the movement
	 */
	public Point2D set(int cameraX, int cameraY) {
		y = startY - cameraY;
		hitBox.x = x;
		hitBox.y = y;
		Point2D coords = new Point(1, 2);
		return coords;
	}

	
	//not handling getters and setters, might be implemented in future patches 
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
}

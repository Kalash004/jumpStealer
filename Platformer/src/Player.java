import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 *  TODO: 
 *  	fix down nohold jump
 */

/**
 * Player class, all logic for movement and player drawing is held here
 * 
 * @author Anton
 */
public class Player {
	/**
	 * Maximum speed of player
	 */
	private static final double MAX_SPEEED = 8;
	/**
	 * This constant is created to manage sliding of player
	 */
	private static final double SLIDING_THRESHOLD = 0.8;
	private GamePanel panel;
	private Options opt;
	private int x, y;
	private int width, height;
	private double xSpeed, ySpeed;
	private Rectangle hitBox;
	public boolean kLeft, kRight, kUp, kDown;

	/**
	 * Player constructor
	 * 
	 * @param x     - Player x position
	 * @param y     - Player y position
	 * @param panel - Game panel
	 * @param opt   - Game options
	 */
	public Player(int x, int y, GamePanel panel, Options opt) {
		this.panel = panel;
		this.x = x;
		this.y = y;
		this.opt = opt;
		width = opt.getPlayerWidth();
		height = opt.getPlayerHeight();
		hitBox = new Rectangle(x, y, width, height);
	}

	/**
	 * This variable is used to determine how high player will jump after charging.
	 */
	private double charge = 1;

	/**
	 * This variable is used to determine how many FPS has passed.
	 */
	private int chargeTimes = 1;
	/**
	 * This variable is used to determine how many time the player has charged. Max
	 * 2.
	 */
	private int charges = 0;
	/**
	 * This variable is used to change height when charging and jumping.
	 */
	private int heightDifference = 0;

	/**
	 * This method sets the player. Checks where he moves, how high he jumps and
	 * also creates gravity for the player. Main logic for player.
	 * 
	 * @throws InterruptedException
	 */
	public void set() throws InterruptedException {
		// left and right movement
		if (kLeft && kRight || !kLeft && !kRight) {
			xSpeed *= 0.3;
		} else if (kLeft && !kRight) {
			if (!kDown) {
				xSpeed--;
			}
		} else if (kRight && !kLeft) {
			if (!kDown) {
				xSpeed++;
			}
		} else if (kDown && onGround()) {
			xSpeed = 0;
		}

		if (xSpeed > 0 && xSpeed < SLIDING_THRESHOLD || xSpeed < 0 && xSpeed > -SLIDING_THRESHOLD)
			xSpeed = 0; // prevent sliding

		if (xSpeed > MAX_SPEEED)
			xSpeed = MAX_SPEEED; // max speed to the right
		if (xSpeed < -MAX_SPEEED)
			xSpeed = -MAX_SPEEED; // max speed to the left

		// small jumps up
		if (kUp && !kDown) {
			hitBox.y++;
			for (Wall walls : panel.getWalls()) {
				if (this.hitBox.intersects(walls.hitBox)) {
					ySpeed = -(6 * (1 + opt.getPlayerGravitation()));
				}
			}
			hitBox.y--;

		}
		// charging big jump
		if (kDown) {
			if (onGround()) {
				xSpeed = 0;
				if (chargeTimes <= 30) {
					if (chargeTimes == 10) { // first charge
						charges = 1; // 1 charges
						heightDifference = height / 4;
						hitBox.y += heightDifference;
						y += heightDifference;
						height -= heightDifference;
						hitBox.height = height;
						charge = charge + opt.getPlayerCharge() + opt.getPlayerGravitation() + 1;
					} else if (chargeTimes == 30) { // max charge
						charges = 2;
						heightDifference = height / 3;
						hitBox.y = y + heightDifference;
						y = y + heightDifference;
						height -= heightDifference;
						hitBox.height = height;
						charge = charge + opt.getPlayerCharge() + opt.getPlayerGravitation();
					}
					chargeTimes++;
				}
			}
		}
		// jumping
		if (!kDown && chargeTimes != 1) {
			if (charges == 1) {
				hitBox.y = y - heightDifference * charges;
				y = y - heightDifference * charges;
			} else if (charges == 2) {
				hitBox.y = y - heightDifference;
				y = y - heightDifference;
				heightDifference = opt.getPlayerHeight() / 4;
				hitBox.y = y - heightDifference;
				y = y - heightDifference;
			}
			heightDifference = 0;
			if (chargeTimes != 0) {
				ySpeed = -(6 * charge);
			}
			chargeTimes = 1;
			charge = 1;
			charges = 1;
			height = opt.getPlayerHeight();
			hitBox.height = height;
		}

		ySpeed += opt.getPlayerGravitation(); // gravity

		// Horizontal collisions
		if (collisionX()) {
			// TODO: add bounce
			if (Math.signum(ySpeed) + Math.signum(xSpeed) > 4) {
				xSpeed = -xSpeed;
			} else {
				xSpeed = 0;
				x = hitBox.x;
			}
		}

		// Vertical collisions
		if (collisionY()) {
			if (10 <= Math.signum(ySpeed)) {
				ySpeed = ySpeed * 0.3;
			} else {
				ySpeed = 0;
				hitBox.y = y;
			}
		}

		x += xSpeed;
		panel.setCameraY((int) (panel.getCameraY() + ySpeed));

		hitBox.x = x;
		hitBox.y = y;

		// checking for death
		if (panel.getCameraY() > 900) {
			panel.reset();
		}
	}

	/**
	 * This method determines whether player is colliding with the wall on x axis
	 * (sides).
	 * 
	 * @return - returns true if collides, false if not.
	 */
	public boolean collisionX() {
		hitBox.x += xSpeed;
		for (Wall wall : panel.getWalls()) {
			if (this.hitBox.intersects(wall.hitBox)) {
				hitBox.x -= xSpeed;
				while (!wall.hitBox.intersects(this.hitBox)) {
					hitBox.x += Math.signum(xSpeed); // ability to get right to the
				}
				hitBox.x -= Math.signum(xSpeed); // backstep from a wall 1 px
				return true;
			}
		}
		return false;
	}

	/**
	 * This method determines whether player is colliding with the wall on y axis
	 * (floor or ceeling).
	 * 
	 * @return - returns true if collides, false if not.
	 */
	public boolean collisionY() {
		hitBox.y += ySpeed;
		for (Wall wall : panel.getWalls()) {
			if (this.hitBox.intersects(wall.hitBox)) {
				hitBox.y -= ySpeed;
				while (!wall.hitBox.intersects(this.hitBox)) {
					hitBox.y += Math.signum(ySpeed);
				}
				hitBox.y -= Math.signum(ySpeed); // backstep from a wall 1 px
				return true;
			}
		}
		return false;
	}

	/**
	 * Less complicated than collisionY. But cant be used to make collision logic.
	 * Mainly used to determine whether player is standing on floor.
	 * 
	 * @return
	 */
	public boolean onGround() {
		hitBox.y++;
		for (Wall wall : panel.getWalls()) {
			if (this.hitBox.intersects(wall.hitBox)) {
				hitBox.y--;
				return true;
			}
		}
		hitBox.y--;
		return false;
	}

	/**
	 * This method draws out the player and players y position
	 * 
	 * @param gtd - Graphics 2D
	 */
	public void draw(Graphics2D gtd) {
		gtd.setColor(opt.getPlayerColor());
		gtd.fillRect(x, y, width, height);
		Font f = new Font("Arial", Font.BOLD, 40);
		gtd.setFont(f);
		gtd.drawString("Height: " + Integer.toString((100 - panel.getCameraY()) / 10), 300, 100);
	}

	// not checking getters and setter, maybe in future patches
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

	public double getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}

	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
}

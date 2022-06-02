import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 *  TODO: 
 *  	fix down nohold jump
 *  	
 */

public class Player {
	GamePanel panel;
	Options opt;

	public int x, y;

	private int width, height;

	double xSpeed, ySpeed;

	private Rectangle hitBox;

	public boolean kLeft, kRight, kUp, kDown;

	public Player(int x, int y, GamePanel panel, Options opt) {
		this.panel = panel;
		this.x = x;
		this.y = y;
		this.opt = opt;

		width = opt.playerWidth;
		height = opt.playerHeight;
		hitBox = new Rectangle(x, y, width, height);

	}

	double charge = 1;
	int chargeTimes = 1;
	int charges = 0;
	int heightDifference = 0;

	public void set() throws InterruptedException {
		// left and right movement
		if (kLeft && kRight || !kLeft && !kRight) {
			xSpeed *= 0.3;
		} else if (kLeft && !kRight) {
			if (!kDown) {
				xSpeed--;
			} else if (!kDown && !onGround()) {
				xSpeed--;
			}
		} else if (kRight && !kLeft) {
			if (!kDown) {
				xSpeed++;
			} else if (!kDown && !onGround()) {
				xSpeed--;
			}
		} else if (kDown && onGround()) {
			xSpeed = 0;
		}

		if (xSpeed > 0 && xSpeed < 0.8 || xSpeed < 0 && xSpeed > -0.8)
			xSpeed = 0; // prevent sliding

		if (xSpeed > 8)
			xSpeed = 8; // max speed to the right
		if (xSpeed < -8)
			xSpeed = -8; // max speed to the left

		// small jumps up
		if (kUp && !kDown) {
			hitBox.y++;
			for (Wall walls : panel.walls) {
				if (this.hitBox.intersects(walls.hitBox)) {
					ySpeed = -(6 * (1 + opt.playerGravitation));
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
						charge += opt.playerCharge + opt.playerGravitation + 1;
					} else if (chargeTimes == 30) { // max charge
						charges = 2;
						heightDifference = height / 3;
						hitBox.y = y + heightDifference;
						y = y + heightDifference;
						height -= heightDifference;
						hitBox.height = height;
						charge += opt.playerCharge + opt.playerGravitation;
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
				heightDifference = opt.playerHeight / 4;
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
			height = opt.playerHeight;
			hitBox.height = height;
		}

		ySpeed += opt.playerGravitation; // gravity

		// Horizontal collisions
		if (collisionX()) {
			// TODO: add bounce
			xSpeed = 0;
			x = hitBox.x;
		}

		// Vertical collisions
		if (collisionY()) {
			// panel.cameraY += y - hitBox.y;
			if (10 <= Math.signum(ySpeed)) {
				ySpeed = ySpeed * 0.3;
				System.out.println("Collission");
			} else {
				ySpeed = 0;
				hitBox.y = y;
			}
		}

		x += xSpeed;
		panel.cameraY += ySpeed;

		hitBox.x = x;
		hitBox.y = y;

		// checking for death
		if (panel.cameraY > 900) {
			panel.reset();
		}
	}

	public boolean collisionX() {
		hitBox.x += xSpeed;
		for (Wall wall : panel.walls) {
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
	// FIXME: jump stops when you have too high speed
	public boolean collisionY() {
		hitBox.y += ySpeed;
		for (Wall wall : panel.walls) {
			if (this.hitBox.intersects(wall.hitBox)) {
//				System.out.println("Collission Y");
				hitBox.y -= ySpeed;
				while (!wall.hitBox.intersects(this.hitBox)) {
					hitBox.y += Math.signum(ySpeed); // ability to get right to the
				}
				hitBox.y -= Math.signum(ySpeed); // backstep from a wall 1 px
				return true;
			}
		}
		return false;
	}

	public boolean onGround() {
		hitBox.y++;
		for (Wall wall : panel.walls) {
			if (this.hitBox.intersects(wall.hitBox)) {
				hitBox.y--;
				return true;
			}
		}
		hitBox.y--;
		return false;
	}

	public void draw(Graphics2D gtd) {
		gtd.setColor(opt.playerColor);
		gtd.fillRect(x, y, width, height);
//		gtd.setColor(opt.hitbox);
//		gtd.fillRect(hitBox.x - 105, hitBox.y, hitBox.width + 10, hitBox.height); // hitbox checker (abit to side to see
																					// how it transforms, etc ... )
		Font f = new Font("Arial", Font.BOLD, 40);
		gtd.setFont(f);
		gtd.drawString(Integer.toString(x), 100, 100);
		gtd.drawString(Integer.toString(y), 200, 100);
		gtd.drawString(Double.toString(ySpeed), 300, 100);
		gtd.drawString(Integer.toString(panel.cameraY), 400, 100);
	}
}

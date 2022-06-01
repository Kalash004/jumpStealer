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

	private int x, y;

	private int width, height;

	private double xSpeed, ySpeed;

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
	int chargeTimes = 0;

	int number = 0;

	public void set() throws InterruptedException {
		
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
		if (kUp && !kDown) {

			hitBox.y++;
			for (Wall walls : panel.walls) {
				if (this.hitBox.intersects(walls.hitBox)) {
					ySpeed = -(6 * (1 + opt.playerGravitation));
				}
			}
			hitBox.y--;

		}
		
		
		if (kDown) {
			if (onGround()) {
				if (chargeTimes < 1) {
					if (!kUp) {
						xSpeed = 0;
						number = height / 2;
						System.out.println(number);
						hitBox.y = y + number;
						y = y + number;
						height = number;
						hitBox.height = height;
						charge += 1.4 + opt.playerGravitation;
						chargeTimes++;
					}
				}
			}
		}

		if (!kDown && onGround()) {
			hitBox.y = y - number;
			y = y - number;
			number = 0;
			if (chargeTimes != 0) {
				ySpeed = -(6 * charge);
			}
			chargeTimes = 0;
			charge = 1;
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
			ySpeed = 0;
			y = hitBox.y;
		}

		x += xSpeed;
		y += ySpeed;

		hitBox.x = x;
		hitBox.y = y;
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

	public boolean collisionY() {
		hitBox.y += ySpeed;
		for (Wall wall : panel.walls) {
			if (this.hitBox.intersects(wall.hitBox)) {
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
//		gtd.setColor(Color.red);
//		gtd.fillRect(hitBox.x - 105, hitBox.y, hitBox.width + 10, hitBox.height); //hitbox checker
		;
	}
}

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall {
	/*
	 * TODO: add type to wall, if boundary make it follow player
	 */
	
	Options opt;

	int x, y, width, height, startX, startY;

	public Rectangle hitBox;

	public Wall(int x, int y, int width, int height, Options opt) {
		this.opt = opt;
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		System.out.println(startY);
		this.width = width;
		this.height = height;

		hitBox = new Rectangle(x, y, width, height);

	}

	public void draw(Graphics2D gtd) {
		gtd.setColor(opt.wallGridColor);
		gtd.drawRect(x, y, width, height);
		gtd.setColor(opt.wallColor);
		gtd.fillRect(x + 1, y + 1, width - 2, height - 2);
	}

	public int[] set(int cameraX, int cameraY) {
		y = startY - cameraY;
		hitBox.x = x;
		hitBox.y = y;
		return new int[] { x, y };
	}
}

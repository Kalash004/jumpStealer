import java.awt.Color;

/**
 * Game options, designed for holding options and also for future
 * implementations (change of game options as the player wants).
 * 
 * @author Anton
 */
public class Options {

	// colors
	private Color playerColor = Color.BLACK;
	private Color wallColor = Color.WHITE;
	private Color wallGridColor = Color.BLACK;
	private Color backGround = Color.LIGHT_GRAY;

	// player
	private Color hitbox = Color.red;
	private int startPlayerX;
	private int playerHeight = 100;
	private int playerWidth = 50;
	private double playerGravitation = 1;
	private double playerCharge = 0.6;

	// wall
	private int wallBlockSize = 50;
	
	
	
	// Getters and setters arent handled yet, maybe in future implementations 
	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
	}

	public Color getWallColor() {
		return wallColor;
	}

	public void setWallColor(Color wallColor) {
	}

	public Color getWallGridColor() {
		return wallGridColor;
	}

	public void setWallGridColor(Color wallGridColor) {
	}

	public Color getBackGround() {
		return backGround;
	}

	public void setBackGround(Color backGround) {
	}

	public Color getHitbox() {
		return hitbox;
	}

	public void setHitbox(Color hitbox) {
	}

	public int getStartPlayerX() {
		return startPlayerX;
	}

	public void setStartPlayerX(int startPlayerX) {
		this.startPlayerX = startPlayerX;
	}

	public int getPlayerHeight() {
		return playerHeight;
	}

	public void setPlayerHeight(int playerHeight) {
	}

	public int getPlayerWidth() {
		return playerWidth;
	}

	public void setPlayerWidth(int playerWidth) {
	}

	public double getPlayerGravitation() {
		return playerGravitation;
	}

	public void setPlayerGravitation(double playerGravitation) {
	}

	public double getPlayerCharge() {
		return playerCharge;
	}

	public void setPlayerCharge(double playerCharge) {
	}

	public int getWallBlockSize() {
		return wallBlockSize;
	}

	public void setWallBlockSize(int wallBlockSize) {
	}

}

import java.io.IOException;

/**
 * Some words about the game, it runs in 60 FPS, right now there arent any goal
 * in the game and map is only made for testing purposes. I hope my hands will
 * get to add missing points to the game and create a master piece. Main
 * functionalities of the game are in place. Some i am going to list right here
 * : Player movement on Y and X axis, camera stays on same X axis but play can
 * go up and camera will follow him. This game was inspired by game called
 * JumpKing. There are normal jumps to get on smaller blocks, you can charge a
 * mid or high jumps with holding s button.
 * 
 * Some of the future things i want to implement : Lava floor, random map
 * generator, storyline, UI improvements and textures, save files and alot more.
 * 
 * 
 * 
 * 
 * Main class, starts the program. Initializes game options, map manager and
 * window. also sets current map to the first one in mapsHolder. Also for test
 * purposes i made it export the map as a testmap2.txt file.
 * 
 * @author Anton
 * @see Options
 * @see MapManager
 */
public class Main {

	public static void main(String[] args) {
		Options opt = new Options();
		MapManager mapManager = new MapManager(opt);
		new Window(opt, mapManager);
		mapManager.setCurrentMap(0);
		try {
			mapManager.exportMap(mapManager.currentMap, "testmap2");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

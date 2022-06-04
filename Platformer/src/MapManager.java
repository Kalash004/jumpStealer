import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Class to manage and import/export game maps.
 * 
 * @author Anton
 */
public class MapManager {
	public static final int COL_NUM = 4;
	public Map currentMap;
	Options opt;
	GamePanel gamePanel;

	ArrayList<Map> randomSectionsHolder = new ArrayList<Map>();
	/**
	 * Holds map that are created/imported/generated. Works only for the current
	 * session.
	 */
	ArrayList<Map> mapsHolder = new ArrayList<Map>();

	/**
	 * Map manager constructor.
	 * 
	 * @param opt - game options
	 */
	public MapManager(Options opt) {
		this.opt = opt;
		mapsHolder.add(importMap("testmap.txt", opt));
		currentMap = mapsHolder.get(0);
	}

	/**
	 * Import map from text file. Text file contains 4 columns: X, Y, HEIGHT, WIDTH.
	 * First line of the file contains headers and will be skipped during import.
	 * 
	 * @param location - import file location
	 * @param opt      - import game options
	 * @return imported map class with walls inside
	 */
	public Map importMap(String location, Options opt) {

		List<Wall> walls = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream(location)))) {

			boolean readFirstLine = true;
			String line;
			while ((line = reader.readLine()) != null) {
				if (readFirstLine) {
					readFirstLine = false;
					continue;
				}
				String[] words = line.split(",");
				if (words.length < COL_NUM) {
					throw new IllegalArgumentException(
							String.format("Expected %s columns in the file, but received %s", COL_NUM, words.length));
				}
				int x = Integer.valueOf(words[0]);
				int y = Integer.valueOf(words[1]);
				int width = Integer.valueOf(words[2]);
				int height = Integer.valueOf(words[3]);
				Wall wall = new Wall(x, y, width, height, opt);
				walls.add(wall);
			}
		} catch (IOException e) {
			throw new RuntimeException(String.format("Cannot import file %s", location), e);
		}
		Map map = new Map();
		map.setMap(walls);
		return map;
	}

	/**
	 * This method sets new map to MapManager.currentMap and notifies methods of
	 * game panel of this. Also it clears the old map and restarts the game
	 * 
	 * @param map - A map that is to be set as the current one.
	 */
	public void setCurrentMap(Map map) {
		// TODO: clear current map and draw a new one;
		currentMap = map;
		gamePanel.redrawMap = true;
	}

	/**
	 * This method sets new map to MapManager.currentMap and notifies methods of
	 * game panel of this. Also it clears the old map and restarts the game
	 * 
	 * @param index - Index of the mapsHolder array to get the map from
	 * @see MapManager.mapsHolder
	 */
	public void setCurrentMap(int index) {
		// TODO: clear current map and draw a new one;
		currentMap = mapsHolder.get(index);
		gamePanel.redrawMap = true;
	}

	// TODO: (IDEA) with wall.x and wall.y you can create auto save location;
	/**
	 * This method lets you export map from the game and save it as a .txt file.
	 * Text file contains 4 columns: X, Y, HEIGHT, WIDTH. First line of the file
	 * contains headers and will be skipped during import.
	 * 
	 * @param map      - Map to export
	 * @param filename - Created file name for the export
	 * @param adress   - Adress where to export the map
	 * @throws IOException
	 */
	public void exportMap(Map map, String filename, String adress) throws IOException {
		String path = String.format("%s\\%s.txt", adress, filename);
		File export = new File(path);
		System.out.print(path);
		export.createNewFile();
		try (FileWriter myWriter = new FileWriter(export)) {
			myWriter.write("x,y,width,height");
			for (Wall wall : map.map) {
				String wallSettings = String.format("%s,%s,%s,%s", String.valueOf(wall.getStartX()),
						String.valueOf(wall.getStartY()), String.valueOf(wall.getWidth()),
						String.valueOf(wall.getHeight()));
				myWriter.write(wallSettings);
			}
		}
	}

	/**
	 * This method lets you export map from the game and save it as a .txt file.
	 * Saves the file to a created folder "maps", also checks for other files with
	 * the same name and if they exists adds a number to a folder name. Text file
	 * contains 4 columns: X, Y, HEIGHT, WIDTH. First line of the file contains
	 * headers and will be skipped during import.
	 * 
	 * @param map      - Map to export
	 * @param filename - Created file name for the export
	 * @throws IOException
	 */
	public void exportMap(Map map, String filename) throws IOException {
		// TODO: export map to a txt file to the "working with" repository;
		String index = "";
		int i = 0;
		File mapsDir = null;
		while (true) {
			mapsDir = new File(new File(System.getProperty("user.dir")), "maps" + index);
			if (mapsDir.exists() && !mapsDir.isDirectory()) {
				index = "-" + ++i;
			} else {
				break;
			}
		}
		if (!mapsDir.exists()) {
			mapsDir.mkdirs();
		}
		File export = new File(mapsDir, String.format("%s.txt", filename));
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(export))) {
			writer.write("x,y,width,height\n");
			for (Wall wall : map.map) {
				String wallSettings = String.format("%s,%s,%s,%s\n", wall.getStartX(), wall.getStartY(),
						wall.getWidth(), wall.getHeight());
				writer.write(wallSettings);
			}
		}
	}

	/**
	 * Not implemented. This method makes you go to a mode where you can draw your
	 * own map. Not sure if it will exist in future because i might create a
	 * speacially dedicate program for that.
	 * 
	 * @return - nothing
	 */
	public Map createMap() {
		// TODO: make map
		return null;
	}

	/**
	 * Not implemented yet. This method creates a random map, which you can save and
	 * export or play thru it.
	 */
	public void createRandomMap() {
		// TODO: creating random map, not implemented yet
		ArrayList<Wall> walls = new ArrayList<Wall>();
		// int s = opt.wallBlockSize;
		// Random rand = new Random();
		// int index = rand.nextInt();
		// int sizeOfMap = rand.nextInt(9) + 1;

		Map map = new Map();
		map.map = walls;
	}

}

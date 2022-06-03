import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.Scanner;

import javafx.scene.shape.Path;

public class MapManager {
	public Map currentMap;
	Options opt;
	GamePanel gamePanel;

	ArrayList<Map> randomSectionsHolder = new ArrayList<Map>();
	ArrayList<Map> mapsHolder = new ArrayList<Map>();

	public MapManager(Options opt) {
		this.opt = opt;
		try {
			addMap("src\\testmap.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		currentMap = mapsHolder.get(0);
	}

	// working map importer
	public void addMap(String location) throws FileNotFoundException {
		Map map = new Map();
		ArrayList<Wall> walls = new ArrayList<Wall>();

		Scanner sc = new Scanner(new File(location));
		int i = 0;
		String line;
		String[] words;
		while (sc.hasNext()) { // reading file
			if (i == 0) {
				sc.next();
			} else {
				line = sc.next();
				words = line.split(",");
				int x = Integer.valueOf(words[0]);
				int y = Integer.valueOf(words[1]);
				int width = Integer.valueOf(words[2]);
				int height = Integer.valueOf(words[3]);
				Wall wall = new Wall(x, y, width, height, opt);
				walls.add(wall);
			}
			i++;
		}
		map.map = walls;
		mapsHolder.add(map);
		sc.close();
	}

	public void setCurrentMap(Map map) {
		// FIXME: clear current map and draw a new one;
		currentMap = map;
		gamePanel.redrawMap = true;
	}

	public void setCurrentMap(int index) {
		// FIXME: clear current map and draw a new one;
		currentMap = mapsHolder.get(index);
		gamePanel.redrawMap = true;
	}
	// TODO: (IDEA) with wall.x and wall.y you can create auto save location; !!!!!!!! 
	public void exportMap(Map map, String filename, String adress) throws IOException {
		// TODO: export map to a txt file to a specific folder;
		String path = String.format("%s\\%s.txt", adress, filename);
		File export = new File(path);
		System.out.print(path);
		export.createNewFile();
		FileWriter myWriter = new FileWriter(export);
		myWriter.write("x,y,width,height");
		for (Wall wall : map.map) {
			String wallSettings = String.format("%s,%s,%s,%s", String.valueOf(wall.startX), String.valueOf(wall.startY),
					String.valueOf(wall.width), String.valueOf(wall.height));
			myWriter.write(wallSettings);
		}
	}

	public void exportMap(Map map, String filename) throws IOException {
		// TODO: export map to a txt file to the "working with" repository;
		File export = new File(String.format("src\\%s.txt", filename));
		export.createNewFile();
		FileWriter myWriter = new FileWriter(export);
		myWriter.write("x,y,width,height\n");
		for (Wall wall : map.map) {
			String wallSettings = String.format("%s,%s,%s,%s\n", String.valueOf(wall.startX), String.valueOf(wall.startY),
					String.valueOf(wall.width), String.valueOf(wall.height));
			myWriter.write(wallSettings);
			System.out.println("wrote a wall to a file");
		}
		myWriter.close();
	}

	public Map createMap() {
		// TODO: make map
		return null;
	}

	public void createRandomMap() {
		// TODO: creating random map
		ArrayList<Wall> walls = new ArrayList<Wall>();
		int s = opt.wallBlockSize;
		Random rand = new Random();
		int index = rand.nextInt();
		int sizeOfMap = rand.nextInt(9) + 1;
		
		Map map = new Map();
		map.map = walls;
	}
	
	

}

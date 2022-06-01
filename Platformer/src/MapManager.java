import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class MapManager {
	public Map currentMap;
	Options opt;
	GamePanel gamePanel;
	
	ArrayList<Map> mapsHolder = new ArrayList<Map>();
	
	public MapManager(Options opt) {
		this.opt = opt;
		try {
			addMap("C:\\Users\\Anton\\git\\jumpStealer\\Platformer\\src\\testmap.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		currentMap = mapsHolder.get(0);
	}
	
	
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
				Wall wall = new Wall(x,y,width,height,opt);
				walls.add(wall);
			}
			i++;
		}		
		map.map = walls;
		mapsHolder.add(map);
		sc.close();
	}

	public void setCurrentMap(Map map) {
		// TODO: clear current map and draw a new one;
		currentMap = map;
		gamePanel.redrawMap = true;
	}
	
	public void setCurrentMap(int index) {
		// TODO: clear current map and draw a new one;
 		currentMap = mapsHolder.get(index);
		gamePanel.redrawMap = true;
	}

	public void exportMap(Map map) {
		// TODO: export map to a csv or txt file;
	}

	public Map createMap() {
		// TODO: make map
		return null;
	}
	
	public void createRandomMap() {
		// TODO: creating random map
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		Map map = new Map();
		map.map = walls;
	}

}

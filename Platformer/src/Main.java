import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Options opt = new Options();
		MapManager mapManager = new MapManager(opt);
		Window wind = new Window(opt, mapManager);
		mapManager.setCurrentMap(0);
		try {
			mapManager.exportMap(mapManager.currentMap, "testmap2");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

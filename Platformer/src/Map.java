import java.util.List;
/**
 * This class stores walls as one class, it was designed for future implementations
 * @author Anton
 */
public class Map {
	// private
	List<Wall> map;
	
	/**
	 * Get collection of walls
	 * @return - collection of walls
	 */
	public List<Wall> getMap() {
		return map;
	}

	/**
	 * Accepts only non null and non empty collection.
	 * @param map - walls collection
	 */
	public void setMap(List<Wall> map) {
		if (map != null && !map.isEmpty()) {
			this.map = map;
		} else {
			throw new IllegalArgumentException("Map is empty!");
		}
	}

}

package application;

/*
 * This loads all the tiles into the system. They then can be called by the Map or anything else for that mater.
 */

public class Tiles{
	public Tiles() {

	} // end of init( )

	public int stone() {
		return 0;
	}

	public int iron() {
		return 16;
	}

	public int gold() {
		return 32;
	}

	public int titanium() {
		return 48;
	}

	public int floor()
	// It start at the highest array tile
	{
		return 64;
	}

	protected boolean isFloor(int element) {
		if (element == floor()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isWall(int element) {
		if (element < 64) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getElementByString(String element){
		
		// there are two words in the system for floor. One that says darkStoneFloor and one that is floor
		// Floor has a different element ID floor = 64 were darkStoneFloor == 0; This difference happens when you are trying to load
		// a image into the system. Proper handling should be taken.
		if (element == "stone"){
			return stone();
		}
		if (element == "floor"){
			return floor();
		}
		if (element == "titanium"){
			return titanium();
		}
		if (element == "gold"){
			return gold();
		}
		if (element == "iron"){
			return iron();
		}
		return 0;
	}
}

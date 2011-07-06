package environment_manager;

import java.awt.image.BufferedImage;
/*
 * This loads all the tiles into the system. They then can be called by the Map or anything else for that mater.
 */

import application_controller.ApplicationData;

public class Tiles {

	public final static int SIZE = 50;


	public Tiles() {

	} // end of init( )
	
	protected BufferedImage returnImage(String name, int element)
	// assign the name image to the sprite
	{
		BufferedImage image = ApplicationData.imagesLoader.getImage(name, element);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}
		return image;
	} // end of setImage()
	
	public String idToString(int id){
		if (id == stone()){
			return "Stone";
		}else if (id == iron()){
			return "Iron";
		}else if (id == gold()){
			return "Gold";
		}else if (id == titanium()){
			return "Titanium";
		}else{
			return "Floor";
		}
	}

	public int stone() {
		return 0;
	}

	public int iron() {
		return 1;
	}

	public int gold() {
		return 2;
	}

	public int titanium() {
		return 3;
	}

	public int floor()
	// It start at the highest array tile
	{
		return 4;
	}

	protected boolean isFloor(int element) {
		if (element == floor()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isWall(int element) {
		if (element < 4) {
			return true;
		} else {
			return false;
		}
	}

	// Think of these two methods as basically this the tile you are trying to
	// change by view
	// is not always perfectly Square you need to find the margin of error
	// This system is very difficult to work with need to use this in the other
	// system
	// But don't know were yet
	// This finds the off set of the object for X
	protected int getTile(int actual) {
		return actual;
	}

	public int getElementByString(String element) {

		// there are two words in the system for floor. One that says
		// darkStoneFloor and one that is floor
		// Floor has a different element ID floor = 64 were darkStoneFloor == 0;
		// This difference happens when you are trying to load
		// a image into the system. Proper handling should be taken.
		if (element == "stone") {
			return stone();
		}
		if (element == "floor") {
			return floor();
		}
		if (element == "titanium") {
			return titanium();
		}
		if (element == "gold") {
			return gold();
		}
		if (element == "iron") {
			return iron();
		}
		return 0;
	}
}

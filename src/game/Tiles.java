package game;


import java.awt.image.BufferedImage;

/*
 * This loads all the tiles into the system. They then can be called by the Map or anything else for that mater.
 */

public class Tiles {

	// Default Size if there is no image

	private ImagesLoader imsLoader;
	protected BufferedImage image;
	protected BufferedImage mapImage;

	public Tiles(ImagesLoader imgLd) {
		imsLoader = imgLd;
		setImages("imageMap", 0);// the sprite's default image is 'name'

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

	public void getStone() {
		setWall(stone());
	}

	public void getStone(int orentation)
	// Sets the Image to a Wall piece.
	{
		setWall(stone() + orentation);
	}

	protected void getIron() {
		setWall(iron());
	}

	protected void getIron(int orentation) {
		setWall(iron() + orentation);
	}

	protected void getGold() {
		setWall(gold());
	}

	protected void getGold(int orentation) {
		setWall(gold());
	}

	protected void getTitanium() {
		setWall(titanium());
	}

	protected void getTitanium(int orentation) {
		setWall(titanium() + orentation);
	}

	protected void setWall(int element) {
		setImages("imageMap", element);
	}

	protected void setFloor() {
		setImages("darkFloorStone", 0);
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

	protected void setImages(String name, int element)
	// assign the name image to the sprite
	{
		image = imsLoader.getImage(name, element);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()
}

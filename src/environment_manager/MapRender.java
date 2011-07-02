package environment_manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import application_controller.ImagesLoader;
import application_controller.View;

public class MapRender {
	// Use this method to tell the system to recalculate the render size
	private Map map;
	private ImagesLoader imgsLoader;
	private BufferedImage[] images  = new BufferedImage[65];
	private int sx1;
	private int sy1;
	private int sx2;
	private int sy2;


	public MapRender(Map map, ImagesLoader imagesLoader) {
		this.map = map;
		this.imgsLoader = imagesLoader;
		setImages();
	}
	
	private void setImages(){
		for (int i = 0; i <= 64; i++){
				if (map.isFloor(i)) {
					images[i] = returnImage("darkFloorStone", i);
				} else if (map.isWall(i)) {
					images[i] = returnImage("imageMap", i);
				}
		}
	}

	private void moveToSafeLocation() {
	}

	protected BufferedImage returnImage(String name, int element)
	// assign the name image to the sprite
	{
		BufferedImage image = imgsLoader.getImage(name, element);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}
		return image;
	} // end of setImage()
	// Convert x or y variable for use later on don't need Two methods for both X and Y
	private int convertActual(int actualXY){
		return actualXY / Tiles.SIZE;
	}

	public void draw(Graphics g, int pWidth, int pHeight) {
		try {
			sx1 = convertActual(View.viewLocX) * View.distance;
			sy1 = convertActual(View.viewLocY) * View.distance;
			sx2 = (convertActual(pWidth) + convertActual(View.viewLocX))
					* View.distance;
			sy2 = (convertActual(pHeight) + convertActual(View.viewLocY))
					* View.distance;

			sx2 += 2;
			sy2 += 2;
			int countY = 0;
			
			// Had to round up to make the stupid thing look nice on off numbers
			int scale = (int) Math.ceil((float)Tiles.SIZE / View.distance);
			for (int y = sy1; y < sy2; y++) {
				int countX = 0;
				for (int x = sx1; x < sx2; x++) {
					g.drawImage(images[map.elements[x][y]],
							(countX * Tiles.SIZE) / View.distance,
							(countY * Tiles.SIZE) / View.distance,
							scale, scale, null);
					countX++;
				}
				countY++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Out of Bounds Error " + e);
			moveToSafeLocation();
		}
	}

}

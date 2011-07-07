package environment_manager;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import application_controller.ApplicationData;
import application_controller.View;

public class MapRender extends Tiles{
	// Use this method to tell the system to recalculate the render size

	public static int sx1;
	public static int sy1;
	public static int sx2;
	public static int sy2;
	protected final static int TOTALIMAGES = 16;
	protected final static int TOTALELEMENTS = 5;
	protected BufferedImage[][] images = new BufferedImage[TOTALELEMENTS][TOTALIMAGES];
	protected Image[][] scaledImages = new Image[TOTALELEMENTS][TOTALIMAGES];
	private int scale;
	
	public MapRender(){
		buildImages();
	}

	private void moveToSafeLocation() {
		View.LOCX = 0;
		View.LOCY = 0;
	}
		// Convert x or y variable for use later on don't need Two methods for
		// both X and Y

	private int convertActualToModified(int actual) {
		return actual / View.getScale();
	}
	
	
	private void buildImages(){
		for (int e = 0; e < TOTALELEMENTS; e++){
			for (int i = 0; i < TOTALIMAGES; i++){
				images[e][i] = returnImage(idToString(e) + "Tiles", i);
			}
		}
	}
	private void scaleImages(){
		int scale = View.getScale();
		for (int e = 0; e < TOTALELEMENTS; e++){
			for (int i = 0; i < TOTALIMAGES; i++){
				scaledImages[e][i] = images[e][i].getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
			}
		}
	}

	public void draw(Graphics g, int pWidth, int pHeight) {
		try {
			sx1 = View.getModifiedLocX();
			sy1 = View.getModifiedLocY();
			sx2 = (convertActualToModified(pWidth) + View.getModifiedLocX()) + 1;
			sy2 = (convertActualToModified(pHeight) + View.getModifiedLocY()) + 1;
			int countY = 0;
			if (scale != View.getScale()){
				scaleImages();
				scale = View.getScale();
			}
			
			// Had to round up to make the stupid thing look nice on off numbers
			for (int y = sy1; y < sy2; y++) {
				int countX = 0;
				for (int x = sx1; x < sx2; x++) {
					Tile element = ApplicationData.map.elements[x][y];
					g.drawImage(scaledImages[element.getElement()][element.getOrentation()] , (countX * scale),
							(countY * scale), null);
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

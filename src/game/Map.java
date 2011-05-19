package game;

//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.io.IOException;
//
//// A cool use if you need to down load a lot of images is to make a lot of threads.
//// That way you don't have to wait for each down load to happen in order to get the next image.
//// May have to use this some time in the future for some images depending on the slow load time.
///*
// * Maybe in the future I will do this but for updates on the game or cool textures in the game 
// * I want the user to be able to download them in the game or from the web site. They can then have the most updated 
// * pack and know it will work with out a lot of complicated problems.
// */
//public class Sprite {
//	private Image im;
//
//	/*
//	 * Basically the get image does not load it it just verifies the image
//	 * location. There are several different ways most of them involve don't
//	 * really matter.
//	 */
//
//	public void init() {
//
//		/*
//		 * All the images in this game will not be loaded right off that bat.
//		 * They will be loaded as needed. We just pass in a string to the init
//		 * and there we go. Things like media tracker will not work on this
//		 * because it is not a web app. Normal you would do a try catch on
//		 * something like this but for learning we will ignore this. If you are
//		 * me that mean let it go move on don't worry about it.
//		 * 
//		 * I used this image loading in the old game but I did not use the frame
//		 * handling and UPS
//		 */
//		String filename = "dwarfs.gif";
//		{
//			try {
//				im = ImageIO.read(getClass().getResource(filename));
//			} catch (IOException e) {
//				System.out.println("IOException: " + e);
//			}
//		} // end of init( )
//	}
//
//	public void paint(Graphics g) {
//		// can't us this no panel or anything really just set to null
//		g.drawImage(im, 0, 0, null);
//	}
//}
//

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

public class Map extends Tiles {
	protected static final int HEIGHT = 60;
	protected static final int WIDTH = 60;
	private int[][] elements = new int[HEIGHT][WIDTH];
	private Grid grid;

	public Map(ImagesLoader imgLd) {
		super(imgLd);
		generateMap();
	}

	private void generateMap() {
		int count = 0;
		int totalTiles = HEIGHT * WIDTH;
		Random generator = new Random();
		DecimalFormat percent = new DecimalFormat("0.0#%");
		System.out.println("Generating Map");
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				// elements[y][x] = generator.nextInt(65);
				elements[y][x] = 64;
				orientation();
				count++;

			}
			System.out.println("Percentage: "
					+ percent.format((float) count / (float) totalTiles));
		}
		System.out.println("Map Generated");
	}

	public int returnElement(int x, int y) {
		return elements[grid.getTileY(y)][grid.getTileX(x)];
	}

	private void setElementBase(int y, int x) {
		if (elements[y][x] < iron()) {
			elements[y][x] = stone();
		} else if (elements[y][x] < gold()) {
			elements[y][x] = iron();
		} else if (elements[y][x] < titanium()) {
			elements[y][x] = gold();
		} else if (elements[y][x] < floor()) {
			elements[y][x] = titanium();
		} else {
			elements[y][x] = floor();
		}
	}

	protected void changeElement(int x, int y, int element) {
		elements[grid.getTileY(y)][grid.getTileX(x)] = element;
		orientation();
	}

	// You have subtract 1 count starts at 0
	private int moveRight(int x) {
		if (x < WIDTH - 1) {
			return x + 1;
		}
		return x;
	}

	private int moveLeft(int x) {
		if (x > 0) {
			return x - 1;
		}
		return x;
	}

	private int moveUp(int y) {
		if (y > 0) {
			return y - 1;
		}
		return y;
	}

	// You have to subtract 1 count starts at 0
	private int moveDown(int y) {
		if (y < HEIGHT - 1) {
			return y + 1;
		}
		return y;
	}
	
	protected boolean validMove(int x, int y){
		if (x > WIDTH || x < 0){
			return false;
		}else if (y < HEIGHT || y > HEIGHT){
			return false;
		}else{
			return true;
		}
		
	}

	private void orientation() {
		int total;
		int top = 1;
		int left = 2;
		int right = 4;
		int bottom = 8;

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				total = 0;
				if (isWall(elements[y][x])) {
					if (isWall(elements[moveUp(y)][x])) {
						total += top;
					}
					if (isWall(elements[moveDown(y)][x])) {
						total += bottom;
					}
					if (isWall(elements[y][moveLeft(x)])) {
						total += left;
					}
					if (isWall(elements[y][moveRight(x)])) {
						total += right;
					}
				}
				setElementBase(y, x);

				elements[y][x] += total;
			}
		}
	}

	public void draw(Graphics g) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {

				if (isFloor(elements[y][x])) {
					setFloor();
				} else if (isWall(elements[y][x])) {
					setWall(elements[y][x]);
				}

				g.drawImage(image, grid.locationX(x), grid.locationY(y), null);
			}
		}

	}

	public int getWidthInTiles() {
		// Returns the Height of a tile
		return WIDTH;
	}

	public int getHeightInTiles() {
		return HEIGHT;
	}
} // end of ShowImage class


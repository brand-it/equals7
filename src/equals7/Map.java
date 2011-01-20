package equals7;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map {
	private static int WALL_ARRAY_NUMBER = 4;
	private static int TILE_SIZE = 23;
	// The clear number is to indicate a material that is not going to have to make rounded corners and such
	private static int CLEAR = WALL_ARRAY_NUMBER;
	// You set the single images to numbers like 5 and 6 indicating grass and stoneFloor
	public static int STONE = 0;
	public static int RED = 1;
	public static int GOLD = 2;
	public static int GREEN = 3;
	// EveryThing bellow this is considered walk able
	public static int GRASS = 4;
	public static int STONE_FLOOR = 5;
	// This is the total amount of elements this is used to create random maps
	public static int TOTAL_ELEMENTS = 5;
	// This indicates how many tiles there should be in each chunk
	private static int WIDTH = 43;
	private static int HEIGHT = 43;
	// This is the array used to store the type of material that is being used
	private int[][] data = new int[HEIGHT][WIDTH];
	// This handles the tile if it needs to be a wall.
	private int[][] orentation = new int[HEIGHT][WIDTH];

	BufferedImage[][] image;
	BufferedImage stoneFloor, grass;

	public Map(Game game) {
		// for loop here
		// array example
		/*
		 * [[[][][][][][][][][][]] [[][][][][][][][][][]] [[][][][][][][][][][]]
		 * [[][][][][][][][][][]] [[][][][][][][][][][]] [[][][][][][][][][][]]]
		 * 
		 * in this array you would run the x and y backwards. you would first
		 * find the height then your would build the width.
		 */
		/*
		 * There is no way to create a effective method for loop with out
		 * sacking a lot of resorces. Sadly means that there is going to be more
		 * code but it will run a lot faster. I will just have to keep double
		 * checking the code and putting what I in methods.
		 */

		Random generator = new Random();

		int numberOf = generator.nextInt(50);
		numberOf += 10;
		
		for (int m = 0; m <= TOTAL_ELEMENTS; m++) {

			for (int n = 0; n < numberOf; n++) {

				int amount = generator.nextInt(10);
				amount += 10;
				int yRandLoc = generator.nextInt(HEIGHT);
				int xRandLoc = generator.nextInt(WIDTH);
				int nextLocation;
				
				for (int a = 0; a <= amount; a++) {
					if (yRandLoc < HEIGHT && xRandLoc < WIDTH)
						if (lookAround(yRandLoc, xRandLoc)) {
							data[yRandLoc][xRandLoc] = m;
							// The number of direction that the random element move can move is 4 it can't move more then 5
							nextLocation = generator.nextInt(4);
							if (nextLocation == 0 && lookUp(yRandLoc, xRandLoc)) {
								yRandLoc -= 1;
							} else if (nextLocation == 1
									&& lookLeft(yRandLoc, xRandLoc)) {

								xRandLoc -= 1;
							} else if (nextLocation == 2
									&& lookRight(yRandLoc, xRandLoc)) {
								xRandLoc += 1;
							} else if (nextLocation == 3
									&& lookRight(yRandLoc, xRandLoc)) {
								yRandLoc += 1;
							}
						}
				}
			}
		}

		doubleCheck();

		Sprite sprite = new Sprite();
		image = sprite.loadStripImageArray("images/imageMap.jpg", 4, 4, 2, 2);
		loadImages(sprite);
		
	}
	
	public void loadImages(Sprite sprite){
		grass = sprite.loadImage("images/grass.jpg");
		stoneFloor = sprite.loadImage("images/darkFloorStone.jpg");
	}

	public boolean lookAround(int y, int x) {
		if (x <= WIDTH && x >= 0 && y <= HEIGHT && y >= 0) {
			return true;
		} else {
			return false;
		}
	}

	// This is to check to see if there is a tile above
	public boolean lookUp(int y, int x) {
		if (y != 0) {
			if (data[y - 1][x] < CLEAR) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// This is to check to see if there is a tile below
	public boolean lookDown(int y, int x) {
		if (y < HEIGHT - 1) {
			if (data[y + 1][x] < CLEAR) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	// This is to check to see if there is a tile to the right
	public boolean lookRight(int y, int x) {
		if (x < WIDTH - 1) {
			if (data[y][x + 1] < CLEAR) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// This is to check to see if there is a tile to the left
	public boolean lookLeft(int y, int x) {
		if (x != 0) {
			if (data[y][x - 1] < CLEAR) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// This is to check to see if there is a tile that is not blank
	public boolean isClear(int y, int x) {
		if (data[y][x] < CLEAR) {
			return true;
		} else {
			return false;
		}
	}

	public void doubleCheck() {
		int total;

		int top = 1;
		int left = 2;
		int right = 4;
		int bottom = 8;

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				total = 0;
				if (isClear(y, x)) {

					if (lookUp(y, x)) {
						total += top;
					}
					if (lookDown(y, x)) {
						total += bottom;
					}

					if (lookRight(y, x)) {
						total += right;
					}
					if (lookLeft(y, x)) {
						total += left;
					}
					orentation[y][x] = total;
				}
			}
		}

	}

	public void changeTiles(int xPressed, int yPressed, int xReleased,
			int yReleased, int element) {
		int xStart, yStart, xEnd, yEnd;
		// Handles the calculation for generalization of were the mouse is.
		yReleased = (yReleased - Game.ORIGINy) / TILE_SIZE;
		yPressed = (yPressed - Game.ORIGINy) / TILE_SIZE;
		xReleased = (xReleased - Game.ORIGINx) / TILE_SIZE;
		xPressed = (xPressed - Game.ORIGINx) / TILE_SIZE;
		if (xPressed < xReleased) {
			xStart = xPressed;
			xEnd = xReleased;
		} else {
			xStart = xReleased;
			xEnd = xPressed;
		}
		if (yPressed < yReleased) {
			yStart = yPressed;
			yEnd = yReleased;
		} else {
			yStart = yReleased;
			yEnd = yPressed;
		}

		for (int y = yStart; y <= yEnd; y++) {
			for (int x = xStart; x <= xEnd; x++) {
				try {
					data[y][x] = element;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("You have exceded the map size");
				}

			}
		}
		doubleCheck();
	}

	// Highlight
	public void highlight(Graphics g, int xStart, int yStart, int x, int y) {
		int tileSizeWidth, tileSizeHeight;

		x = x - Game.ORIGINx;
		y = y - Game.ORIGINy;
		xStart = ((xStart - Game.ORIGINx) / TILE_SIZE) * TILE_SIZE;
		yStart = ((yStart - Game.ORIGINy) / TILE_SIZE) * TILE_SIZE;

		tileSizeWidth = (((x / TILE_SIZE) - (xStart / TILE_SIZE)) * TILE_SIZE)
				+ TILE_SIZE;
		tileSizeHeight = (((y / TILE_SIZE) - (yStart / TILE_SIZE)) * TILE_SIZE)
				+ TILE_SIZE;

		g.setColor(g.getColor().darker());
		g.drawRect(xStart, yStart, tileSizeWidth, tileSizeHeight);
	}

	public void paint(Graphics g) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (data[y][x] == STONE_FLOOR) {
					g.drawImage(stoneFloor, x * TILE_SIZE, y * TILE_SIZE, null);
				} else if (data[y][x] == GRASS){
					g.drawImage(grass, x * TILE_SIZE, y * TILE_SIZE, null);
				} else {
					g.drawImage(image[data[y][x]][orentation[y][x]], x
							* TILE_SIZE, y * TILE_SIZE, null);
				}
			}
		}
	}
}

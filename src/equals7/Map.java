package equals7;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map {
	private static int TILE_SIZE = 23;
	// This is the total amount of elements that are available to use in the
	// system start count at zero
	// This number is used for the random map generator
	private static int TOTAL_ELEMENTS = 5;
	// There are going to be 3 layers for now wall floor water
	// 3 and less is considered a wall.
	public static int WALL = 3;
	// 5 and greater then 3 is floor
	public static int FLOOR = 6;
	// 6 and greater then 5 is water
	public static int WATER = 7;
	// The order that they are number also indicates which element is changed by
	// what.
	// Wall elements These are considered not passable
	public static int STONE = 0;
	public static int RED = 1;
	public static int GOLD = 2;
	public static int GREEN = 3;
	// Floor Elements EveryThing bellow this is considered passable
	public static int STONE_FLOOR = 4;
	public static int DIRT = 5;
	public static int GRASS = 6;
	// This indicates how many tiles there should be in each chunk
	private static int WIDTH = 43;
	private static int HEIGHT = 43;
	// Each y and x value indicates a location in the array. Each location
	// contains a Element.
	private int[][] data = new int[HEIGHT][WIDTH];
	// This value does not need to be called for the game to work. Just makes
	// the tiles face the correct way.
	private int[][] orentation = new int[HEIGHT][WIDTH];
	// First variable should be the element in question or image chunk the
	// second peace should be the element direction
	BufferedImage[][] image;
	// Places holders all the tiles for the image should go in the buffered
	// Image array.
	BufferedImage stoneFloor, grass;

	public Map(Game game) {
		
		generateMap();
		Sprite sprite = new Sprite();
		image = sprite.loadStripImageArray("images/imageMap.jpg", 4, 4, 2, 2);
		loadImages(sprite);

	}
	
	public void generateMap(){
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
		 * sacking a lot of resources. Sadly means that there is going to be
		 * more code but it will run a lot faster. I will just have to keep
		 * double checking the code and putting what I in methods.
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
						data[yRandLoc][xRandLoc] = m;
					// The number of direction that the random element move can
					// move is 4 it can't move more then 5
					nextLocation = generator.nextInt(4);
					if (nextLocation == 0) {
						yRandLoc = moveUp(yRandLoc);
					} else if (nextLocation == 1) {
						xRandLoc = moveLeft(xRandLoc);
					} else if (nextLocation == 2) {
						xRandLoc = moveRight(xRandLoc);
					} else if (nextLocation == 3) {
						yRandLoc = moveDown(yRandLoc);
					}
				}
			}
		}
		doubleCheck();
	}
	
	// You have subtract 1 count starts at 0
	public int moveRight(int x) {
		if (x < WIDTH - 1) {
			return x + 1;
		}
		return x;
	}

	public int moveLeft(int x) {
		if (x > 0) {
			return x - 1;
		}
		return x;
	}

	public int moveUp(int y) {
		if (y > 0) {
			return y - 1;
		}
		return y;
	}
	// You have to subtract 1 count starts at 0
	public int moveDown(int y) {
		if (y < HEIGHT - 1) {
			return y + 1;
		}
		return y;
	}

	public void loadImages(Sprite sprite) {
		grass = sprite.loadImage("images/grass.jpg");
		stoneFloor = sprite.loadImage("images/darkFloorStone.jpg");
	}

	public boolean isWall(int x, int y) {
		if (data[y][x] <= WALL) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFloor(int x, int y) {
		if (data[y][x] <= FLOOR && isWall(x, y)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isWater(int x, int y) {
		if (data[y][x] <= WATER && isWall(x, y) && !isFloor(x, y)) {
			return true;
		} else {
			return false;
		}
	}

	// the double checker needs to take into account the layers it has to look
	// at
	public void doubleCheck() {
		int total;

		int top = 1;
		int left = 2;
		int right = 4;
		int bottom = 8;

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				
				total = 0;
				// If this is a wall then we need make sure that only the floor
				// tiles effect orientation
				if (isWall(x, y)) {
					if (isFloor(x, moveUp(y)) || isWater(x, moveUp(y))) {
						total += top;
					}
					if (isFloor(x, moveDown(y)) || isWater(x, moveDown(y))) {
						total += bottom;
					}
					if (isFloor(moveRight(x), y) || isWater(moveRight(x), y)) {
						total += right;
					}
					if (isFloor(moveLeft(x), y) || isWater(moveLeft(x), y)) {
						total += left;
					}

				}
				
				orentation[y][x] = total;
				// These currently don't have any function
				// if (isFloor(x, y)){
				//
				// }
				// if (isWater(x, y)){
				//
				// }
			}
		}
	}


	// This is only for development
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

	// Highlight, For Development as well
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

	// This paints the tiles call this to build the map
	public void paint(Graphics g) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (data[y][x] == STONE_FLOOR) {
					g.drawImage(stoneFloor, x * TILE_SIZE, y * TILE_SIZE, null);
					// Because I don't have grass and dirt is number five and grass is six we have to use dirt here or we get a error.
				} else if (data[y][x] == DIRT) {
					g.drawImage(grass, x * TILE_SIZE, y * TILE_SIZE, null);
				} else {
					g.drawImage(image[data[y][x]][orentation[y][x]], x
							* TILE_SIZE, y * TILE_SIZE, null);
				}
			}
		}
	}
}

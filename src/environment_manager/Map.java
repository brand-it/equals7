package environment_manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Random;

public class Map extends Tiles {
	public static final int HEIGHT = 1000;
	public static final int WIDTH = 1000;
	protected Tile[][] elements = new Tile[WIDTH][HEIGHT];
	private String saveDir = "./saves/map.dat";

	public Map() {
		generateMap();
	}

	private void generateMap() {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new FileInputStream(saveDir));
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					elements[x][y] = new Tile(inputStream.readInt());
				}
			}

		} catch (FileNotFoundException e) {
			int count = 0;
			int totalTiles = HEIGHT * WIDTH;
			Random generator = new Random();
			DecimalFormat percent = new DecimalFormat("0.0#%");
			System.out.println("Generating Map");
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					elements[x][y] = new Tile(4);

					count++;
					System.out
							.println("Percentage: "
									+ percent.format((float) count
											/ (float) totalTiles));
				}

			}

			System.out.println("Map Generated");

		} catch (IOException e) {
			e.printStackTrace();
		}
		orientation();
	}

	public int returnElement(int modfiedX, int modfiedY) {

		return elements[modfiedX][modfiedY].getElement();
	}

	public void changeElement(int modfiedX, int modfiedY, int element) {
		// This is the only system that uses Modified X and Modified Y
		elements[modfiedX][modfiedY].element = element;
		fastOrientation(modfiedX, modfiedY);
	}

	private void fastOrientation(int modifiedX, int modifiedY) {
		int total = 0;
		int top = 1;
		int left = 2;
		int right = 4;
		int bottom = 8;
		int setupY = moveLeft(modifiedY);
		int setupX = moveUp(modifiedX);

		for (int y = setupY; y < (setupY + 3); y++) {
			for (int x = setupX; x < (setupX + 3); x++) {
				total = 0;
				if (elements[x][y].isWall()) {
					if (elements[x][moveUp(y)].isWall()) {
						total += top;
					}
					if (elements[x][moveDown(y)].isWall()) {
						total += bottom;
					}
					if (elements[moveLeft(x)][y].isWall()) {
						total += left;
					}
					if (elements[moveRight(x)][y].isWall()) {
						total += right;
					}
				}
				elements[x][y].orentation = total;
			}
		}
	}

	/**
	 * Move the map up down left and right but don't go out of bounds of the map
	 * area
	 */
	public int moveRight(int x) {
		if (x < WIDTH - 1) {
			return x + 1;
		}
		return x;
	}

	/**
	 * Move the map up down left and right but don't go out of bounds of the map
	 * area
	 */
	public int moveLeft(int x) {
		if (x > 0) {
			return x - 1;
		}
		return x;
	}

	/**
	 * Move the map up down left and right but don't go out of bounds of the map
	 * area
	 */
	public int moveUp(int y) {
		if (y > 0) {
			return y - 1;
		}
		return y;
	}

	/**
	 * Move the map up down left and right but don't go out of bounds of the map
	 * area
	 */
	public int moveDown(int y) {
		if (y < HEIGHT - 1) {
			return y + 1;
		}
		return y;
	}

	protected boolean validMove(int x, int y) {
		if (x > WIDTH || x < 0) {
			return false;
		} else if (y < HEIGHT || y > HEIGHT) {
			return false;
		} else {
			return true;
		}

	}

	public boolean isBlocked(int x, int y) {
		if (elements[x][y].isWall()) {
			return true;
		}
		return false;
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
				if (elements[x][y].isWall()) {
					if (elements[x][moveUp(y)].isWall()) {
						total += top;
					}
					if (elements[x][moveDown(y)].isWall()) {
						total += bottom;
					}
					if (elements[moveLeft(x)][y].isWall()) {
						total += left;
					}
					if (elements[moveRight(x)][y].isWall()) {
						total += right;
					}
				}
				elements[x][y].orentation = total;
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

	public void save() {
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(saveDir));
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					outputStream.writeInt(elements[x][y].element);
				}
			}
			outputStream.close();
			System.out.println("Map Saved");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
} // end of ShowImage class


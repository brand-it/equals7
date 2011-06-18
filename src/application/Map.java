package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Random;

public class Map extends Tiles {
	public static final int HEIGHT = 500;
	public static final int WIDTH = 500;
	protected int[][] elements = new int[WIDTH][HEIGHT];
	protected Grid grid;
	private String saveDir;

	public Map() {
		saveDir = "./saves/map.dat";
		generateMap();
		this.grid = new Grid();

	}

	private void generateMap() {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new FileInputStream(saveDir));
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					elements[x][y] = inputStream.readInt();
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
					elements[x][y] = generator.nextInt(65);
					orientation();
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

	}

	public int returnElement(int x, int y) {
		return elements[x][y];
	}

	private void setElementBase(int y, int x) {
		if (elements[x][y] < iron()) {
			elements[x][y] = stone();
		} else if (elements[x][y] < gold()) {
			elements[x][y] = iron();
		} else if (elements[x][y] < titanium()) {
			elements[x][y] = gold();
		} else if (elements[x][y] < floor()) {
			elements[x][y] = titanium();
		} else {
			elements[x][y] = floor();
		}
	}

	public void changeElement(int x, int y, int element) {
		elements[grid.getTileXByView(x)][grid.getTileYByView(y)] = element;
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

	protected boolean validMove(int x, int y) {
		if (x > WIDTH || x < 0) {
			return false;
		} else if (y < HEIGHT || y > HEIGHT) {
			return false;
		} else {
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
				if (isWall(elements[x][y])) {
					if (isWall(elements[x][moveUp(y)])) {
						total += top;
					}
					if (isWall(elements[x][moveDown(y)])) {
						total += bottom;
					}
					if (isWall(elements[moveLeft(x)][y])) {
						total += left;
					}
					if (isWall(elements[moveRight(x)][y])) {
						total += right;
					}
				}
				setElementBase(y, x);

				elements[x][y] += total;
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
					outputStream.writeInt(elements[x][y]);
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


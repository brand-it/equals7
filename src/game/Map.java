package game;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;


public class Map extends Tiles {
	protected static final int HEIGHT = 30;
	protected static final int WIDTH = 50;
	private int[][] elements = new int[HEIGHT][WIDTH];
	private Grid grid;
	private String saveDir;

	public Map(ImagesLoader imgLd) {
		super(imgLd);
		saveDir = "../saves/map.dat";
		generateMap();
		grid = new Grid();
		
		
	}

	private void generateMap() {
	
		try {
			
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveDir));
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					elements[y][x] = inputStream.readInt();
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
					 elements[y][x] = generator.nextInt(65);
					orientation();
					count++;

				}
				System.out.println("Percentage: "
						+ percent.format((float) count / (float) totalTiles));
			}
			
			System.out.println("Map Generated");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int returnElement(int x, int y) {
		return elements[y][x];
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
	
	public void save(){
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(saveDir));
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {

					outputStream.writeInt(elements[y][x]);
	
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


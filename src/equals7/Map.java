package equals7;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map {
	private int width = 43;
	private int height = 43;
	private int[][] data = new int[height][width];
	private int[][] orentation = new int[height][width];
	private static int TILE_SIZE = 23;
	BufferedImage[][] image;
	BufferedImage clearImage;
	public static int CLEAR = 2;

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
		Random generator = new Random();

		int randomNumber = 3;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				randomNumber = generator.nextInt(3);
				
				data[y][x] = randomNumber;
			}
		}
		doubleCheck();

		Sprite sprite = new Sprite();
		image = sprite.loadStripImageArray("images/imageMap.jpg", 4, 4, 1, 2);
		clearImage = sprite.loadImage("images/darkFloorStone.jpg");
	}

	public void doubleCheck() {
		int total;

		int top = 1;
		int left = 2;
		int right = 4;
		int bottom = 8;


		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				total = 0;
				if (data[y][x] != CLEAR) {

					if (y != 0) {
						if (data[y - 1][x] < CLEAR) {
							total += top;
						}
					}
					if (y < height - 1) {
						if (data[y + 1][x] < CLEAR) {
							total += bottom;
						}
					}

					if (x < width - 1) {
						if (data[y][x + 1] < CLEAR) {
							total += right;
						}
					}
					if (x != 0) {
						if (data[y][x - 1] < CLEAR) {
							total += left;
						}
					}
					orentation[y][x] = total;
				}
			}
		}

	}

	public void paint(Graphics g) {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				if (data[y][x] == CLEAR){
					g.drawImage(clearImage, x * TILE_SIZE, y * TILE_SIZE,
							null);
				}else{
					g.drawImage(image[data[y][x]][orentation[y][x]], x * TILE_SIZE, y * TILE_SIZE,
							null);
				}
				
			}
		}
	}
}

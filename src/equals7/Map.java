package equals7;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map {
	private int width = 43;
	private int height = 43;
	private int[][] data = new int[height][width];
	private static int TILE_SIZE = 23;
	BufferedImage[] image;

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

		int randomNumber = 17;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				randomNumber = generator.nextInt(2);

				data[y][x] = 15 + randomNumber;
			}
		}
		doubleCheck();

		Sprite sprite = new Sprite();
		image = sprite.loadStripImageArray("images/imageMap.jpg", 5, 4);
	}

	public void doubleCheck() {
		int total;

		int top = 1;
		int left = 2;
		int right = 4;
		int bottom = 8;
		int clear = 16;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				total = 0;
				if (data[y][x] != clear) {

					if (y != 0) {
						if (data[y - 1][x] < clear) {
							total += top;
						}
					}
					if (y < height - 1) {
						if (data[y + 1][x] < clear) {
							total += bottom;
						}
					}

					if (x < width - 1) {
						if (data[y][x + 1] < clear) {
							total += right;
						}
					}
					if (x != 0) {
						if (data[y][x - 1] < clear) {
							total += left;
						}
					}
					data[y][x] = total;
				}
			}
		}

	}

	public void paint(Graphics g) {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				g.drawImage(image[data[y][x]], x * TILE_SIZE, y * TILE_SIZE,
						null);
				// g.drawImage(image, locationX, locationY, x + TILE_SIZE, y +
				// TILE_SIZE, 0, 0, 23, 23, null);
			}
		}
	}
}

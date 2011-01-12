package equals7;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;



public class Map {
	private int width = 100;
	private int height = 100;
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

		int randomNumber = 2;

		for (int y = 2; y < height; y++) {
			for (int x = 1; x < width; x++) {
				randomNumber = generator.nextInt(2);
				data[y][x] = randomNumber;
			}
		}
	
		Sprite sprite = new Sprite();
		image = sprite.loadStripImageArray("images/imageMap.jpg", 5, 4);	
	}

	public void paint(Graphics g) {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int number = 0;
				if (data[y][x] == 1){
					number = 16;
				}
				g.drawImage(image[number], x*TILE_SIZE,y*TILE_SIZE, null);
//				g.drawImage(image, locationX, locationY, x + TILE_SIZE, y + TILE_SIZE, 0, 0, 23, 23, null);
			}
		}
	}
}

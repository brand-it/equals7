package equals7;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class Map {
	
	private static int TILE_SIZE = 23;
	private int width = 100;
	private int height = 100;
	private int[][] data = new int[height][width];
	
	private int numberOfImages = 2;
	private Image[] image = new Image[numberOfImages];
	
	private String solidRef = "images/solidStone.jpg";
	private String darkRef = "images/darkFloorStone.jpg";
	private SpriteStore spriteStore;
	
	public Map(Game game) {
		// for loop here
		// array example 
		/*
		 * [[[][][][][][][][][][]]
		 * [[][][][][][][][][][]]
		 * [[][][][][][][][][][]]
		 * [[][][][][][][][][][]]
		 * [[][][][][][][][][][]]
		 * [[][][][][][][][][][]]]
		 * 
		 * in this array you would run the x and y  backwards. you would first find the height then your would 
		 * build the width.
		 */
		
		// DAMIT I NEED TO FIGURE OUT THE MATH FOR THE CORNORS FUCK
		Random generator = new Random();
		
		int randomNumber = 0;
		
		
		
		for (int y = 2; y < height; y++){
			for (int x = 1; x < width; x++){
				randomNumber = generator.nextInt(2);
				data[y][x] = randomNumber;
			}
		}
		for (int i = 0; i < numberOfImages; i++){
			spriteStore = new SpriteStore();
			if (i == 0){
				image[i] = spriteStore.getSprite(solidRef);
			}else if (i == 1){
				image[i] = spriteStore.getSprite(darkRef);
			}
		}
	}

	public void paint(Graphics2D g){
		
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				g.drawImage(image[data[y][x]], x * TILE_SIZE, y * TILE_SIZE, null);
				
			}
		}
	}
}

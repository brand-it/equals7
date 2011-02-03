package source;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Dwarfs {

	private int[][] data = new int[Map.HEIGHT][Map.WIDTH];
	BufferedImage dwarf;

	public Dwarfs() {
		Sprite sprite = new Sprite();
		dwarf = sprite.loadImage("images/lava.png");
		data[20][20] = 1;
		data[20][15] = 1;
	}

	public void paint(Graphics g) {

		for (int y = 0; y < Map.HEIGHT; y++) {
			for (int x = 0; x < Map.WIDTH; x++) {
				if (data[x][y] == 1) {
					g.drawImage(dwarf, x * Map.TILE_SIZE, y * Map.TILE_SIZE,
							null);
				}
			}
		}

	}
}

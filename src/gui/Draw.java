package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import application.Grid;
import application.ImagesLoader;

public class Draw {
	int locYRollover = 0;
	int locXRollover = 0;
	protected Grid grid;
	private ImagesLoader imsLoader;
	protected BufferedImage image;
	protected JButton jButton;
	

	public Draw(Grid grid, ImagesLoader imgs) {
		this.grid = grid;
		imsLoader = imgs;
	}

	public void highlightUnit(Graphics g) {
	}

	public void drawBox(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(locXRollover, locYRollover, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}

	public void drawMouse(Graphics g, int mouseX, int mouseY) {
		g.setColor(Color.green);
		g.fillRect(mouseX, mouseY, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}
	

	protected void setImages(String name, int element)
	// assign the name image to the sprite
	{
		image = imsLoader.getImage(name, element);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()

	public void drawAll(Graphics g, int mouseX, int mouseY) {
		drawBox(g);
		highlightUnit(g);
		drawMouse(g, mouseX, mouseY);
	}
}

package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

import units.Unit;
import application.Grid;
import application.ImagesLoader;
import application.Map;
import application.View;

public class Draw {
	int locYRollover = 0;
	int locXRollover = 0;
	protected Grid grid;
	protected Map map;
	protected ImagesLoader imgsLoader;
	protected BufferedImage image;
	protected JButton jButton;
	protected Unit selectedUnit;
	

	public Draw(Map map, ImagesLoader imgsLoader) {
		this.grid = new Grid();
		this.map = map;
		this.imgsLoader = imgsLoader;
	}

	public void highlightUnit(Graphics g) {
		if(selectedUnit != null){
			g.setColor(Color.red);
			g.drawRect(selectedUnit.getX() + View.viewLocX, selectedUnit.getY() + View.viewLocY, Grid.TILE_SIZE, Grid.TILE_SIZE);
		}
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
		image = imgsLoader.getImage(name, element);
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

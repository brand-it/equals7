package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

import environment_manager.Map;
import environment_manager.Tiles;

import units_manager.Unit;
import application_controller.ImagesLoader;
import application_controller.View;

public class Draw {
	private static final int MOUSESIZE = 25;
	int locYRollover = 0;
	int locXRollover = 0;
	protected Map map;
	protected ImagesLoader imgsLoader;
	protected BufferedImage image;
	protected JButton jButton;
	protected Unit selectedUnit;

	public Draw(Map map, ImagesLoader imgsLoader) {
		this.map = map;
		this.imgsLoader = imgsLoader;
	}

	public void highlightUnit(Graphics g) {
		if (selectedUnit != null) {
			g.setColor(Color.red);
			g.drawRect(selectedUnit.getX() + View.LOCX, selectedUnit.getY()
					+ View.LOCY, Tiles.SIZE, Tiles.SIZE);
		}
	}

	public void drawMouse(Graphics g, int mouseX, int mouseY) {
		g.setColor(Color.green);
		g.fillRect(mouseX, mouseY, MOUSESIZE, MOUSESIZE);
	}

	public void drawBox(Graphics g, int mouseX, int mouseY) {
		int adjustmentMouseX = (mouseX / View.getScale()) * View.getScale();
		int adjustmentMouseY = (mouseY / View.getScale()) * View.getScale();

		g.setColor(Color.blue);
		g.drawRect(adjustmentMouseX, adjustmentMouseY, View.getScale(),
				View.getScale());
	}

	protected void setImages(String name, int element)
	// assign the name image to the sprite
	{
		image = imgsLoader.getImage(name, element);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()
}

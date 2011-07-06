package units_manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import environment_manager.Tiles;
import application_controller.*;

public class UnitRender {
	protected ImagesLoader imgLoader;
	private BufferedImage image;
	protected int locX;
	protected int locY;
	public String unitClass;

	public void setImage(String name) {
		image = imgLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	}

	public void draw(Graphics g) {
		g.drawImage(image, locX, locY, Tiles.SIZE, Tiles.SIZE, null);
	}
}

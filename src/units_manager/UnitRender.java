package units_manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import application_controller.*;

public class UnitRender {
	private BufferedImage image;
	protected int locX;
	protected int locY;
	public String unitClass;

	public void setImage(String name) {
		image = ApplicationData.imagesLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	}

	public void draw(Graphics g) {
		g.drawImage(image, locX, locY, View.getScale(), View.getScale(), null);
	}
}

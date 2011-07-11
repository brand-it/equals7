package units_manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import application_controller.*;

public class UnitRender {
	private BufferedImage image;
	public String unitClass;
	protected int tileX, tileY;

	public void setImage(String name) {
		image = ApplicationData.imagesLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	}

	public int getActualX() {
		return (tileX * View.getScale()) - View.getRoundedX();
	}

	public int getActualY() {
		return (tileY * View.getScale()) - View.getRoundedY();
	}

	public void draw(Graphics g) {
		g.drawImage(image, getActualX(), getActualY(), View.getScale(),
				View.getScale(), null);
	}
}

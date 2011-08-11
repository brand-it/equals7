package units_manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import application_controller.*;

public class UnitRender {
	private BufferedImage image;
	public String unitClass;
	protected int tileX, tileY;
	// Every unit has a turn value of 0
	public int initiative = 0;

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
		g.drawString(Integer.toString(initiative), getActualX(), getActualY() - 10);
	}
}

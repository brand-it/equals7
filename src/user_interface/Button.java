package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import application_controller.ApplicationData;

public class Button {

	protected int buttonX, buttonY, buttonH, buttonW;
	private BufferedImage image;
	private Color color;

	public void setBounds(int x, int y, int width, int height) {
		buttonX = x;
		buttonY = y;
		buttonH = height;
		buttonW = width;
	}

	public void changeLocations(int x, int y) {
		buttonX = x;
		buttonY = y;
	}

	public boolean isButton(int mouseX, int mouseY) {
		if (mouseX >= buttonX && mouseX <= (buttonW + buttonX)
				&& mouseY >= buttonY && mouseY <= (buttonH + buttonY)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean clicked(int mouseX, int mouseY) {
		if (isButton(mouseX, mouseY)) {
			color = Color.blue;
			return true;
		} else {
			return false;
		}
	}

	public void hovered(int mouseX, int mouseY) {
		if (isButton(mouseX, mouseY)) {
			color = Color.orange;
		} else {
			color = Color.red;
		}
	}

	public void setImages(String name)
	// assign the name image to the sprite
	{
		image = ApplicationData.imagesLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(buttonX, buttonY, buttonW, buttonH);
	}

}

package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import application.ImagesLoader;

public class CustomButton {

	protected int buttonX, buttonY, buttonH, buttonW;
	protected ImagesLoader imsLoader;
	private BufferedImage image;
	private Color color;
	protected Reaction reaction;
	protected String action;

	public CustomButton(Reaction reaction, ImagesLoader imgs, String action, String image,
			int x, int y) {
		buttonX = x;
		buttonY = y;
		buttonH = 50;
		buttonW = 50;
		defaults(reaction, imgs, action, image);
	}
	
	public CustomButton(Reaction reaction, ImagesLoader imgs, String action, String image,
			int height, int width, int x, int y) {
		buttonX = x;
		buttonY = y;
		buttonH = height;
		buttonW = width;
		defaults(reaction, imgs, action, image);
	}
	
	private void defaults(Reaction reaction, ImagesLoader imgs, String action, String image) {
		imsLoader = imgs;
		setImages(image);
		color = Color.red;
		this.reaction = reaction;
		this.action = action;
	}
	
	// this calls the method you want when you click the button
	public void action() {
		try {
			Method method = reaction.getClass().getDeclaredMethod(action, new Class[] {});
			try {
				method.invoke(reaction, new Object[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			System.out.println(e);
		}

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

	protected void setImages(String name)
	// assign the name image to the sprite
	{
		image = imsLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(buttonX - 1, buttonY - 1, buttonW + 2, buttonH + 2);
		g.drawImage(image, buttonX, buttonY, buttonW, buttonH, null);
	}

}

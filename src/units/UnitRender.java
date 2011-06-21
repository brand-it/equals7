package units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import application.*;

public class UnitRender {
	protected ImagesLoader imgLoader;
	private BufferedImage image;
	// As of current width and height only matters to the draw system
	protected int width = 25;
	protected int height = 25;
	protected int locX;
	protected int locY;
	public String unitClass;
	
	public void setImage(String name){
		image = imgLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	}
	
	public void draw(Graphics g){
		g.drawImage(image, locX + View.viewLocX, locY + View.viewLocY, width, height, null);
	}
}

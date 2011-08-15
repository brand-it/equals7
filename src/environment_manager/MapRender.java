package environment_manager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import application_controller.ApplicationData;
import application_controller.View;

public class MapRender extends Tiles {
	// Use this method to tell the system to recalculate the render size

	protected final static int TOTALIMAGES = 16;
	protected final static int TOTALELEMENTS = 5;
	protected BufferedImage[][] images = new BufferedImage[TOTALELEMENTS][TOTALIMAGES];
	protected Image[][] scaledImages = new Image[TOTALELEMENTS][TOTALIMAGES];
	private int scale;

	public MapRender() {
		buildImages();
	}

	private void moveToSafeLocation() {
		View.LOCX = 0;
		View.LOCY = 0;
	}

	private void buildImages() {
		for (int e = 0; e < TOTALELEMENTS; e++) {
			for (int i = 0; i < TOTALIMAGES; i++) {
				images[e][i] = returnImage(idToString(e) + "Tiles", i);
			}
		}
	}

	private void scaleImages() {
		int scale = View.getScale();
		for (int e = 0; e < TOTALELEMENTS; e++) {
			for (int i = 0; i < TOTALIMAGES; i++) {
				scaledImages[e][i] = images[e][i].getScaledInstance(scale,
						scale, Image.SCALE_DEFAULT);
			}
		}
	}

	public void drawZones(Graphics g) {

		int countY = 0;
		for (int y = View.startY(); y < View.endY(); y++) {
			int countX = 0;
			for (int x = View.startX(); x < View.endX(); x++) {
				Tile element = ApplicationData.map.elements[x][y];
				if (element.getZone() != null) {
					g.setColor(Color.WHITE);
					g.drawString(
							Integer.toString(element.getZone().getNumber()),
							(countX * scale) + 20, (countY * scale) + 20);
				}

				countX++;
			}
			countY++;
		}
	}

	public void draw(Graphics g) {
		try {
			// This little bit of math right here needs to be moved but for now
			// just copy

			int countY = 0;
			if (scale != View.getScale()) {
				scaleImages();
				scale = View.getScale();
			}

			// Had to round up to make the stupid thing look nice on off numbers
			for (int y = View.startY(); y < View.endY(); y++) {
				int countX = 0;
				for (int x = View.startX(); x < View.endX(); x++) {
					Tile element = ApplicationData.map.elements[x][y];
					g.drawImage(scaledImages[element.getElement()][element
							.getOrentation()], (countX * scale),
							(countY * scale), null);
					countX++;
				}
				countY++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Out of Bounds Error " + e);
			moveToSafeLocation();
		}
	}

}

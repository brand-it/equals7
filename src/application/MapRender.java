package application;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MapRender  {

	public int drawWidth = 0;
	public int drawHeight = 0;
	private Map map;
	private Grid grid;
	private ImagesLoader imgsLoader;
	private BufferedImage image;

	public MapRender(Map map, ImagesLoader imagesLoader) {
		this.map = map;
		this.imgsLoader = imagesLoader;
		grid = new Grid();
	}

	public void resize(int width, int height) {
		// Render one extra block on the edges so there are no black empty spots
		drawWidth = (width / Grid.TILE_SIZE) + 1;
		drawHeight = (height / Grid.TILE_SIZE) + 1;
	}

	private void moveToSafeLocation() {
		grid.setViewXByTile(0);
		grid.setViewYByTile(0);
	}
	
	protected void setImages(String name, int element)
	// assign the name image to the sprite
	{
		image = imgsLoader.getImage(name, element);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()
	

	protected void setWall(int element) {
		setImages("imageMap", element);
	}

	protected void setFloor() {
		setImages("darkFloorStone", 0);
	}

	public void draw(Graphics g) {
		// This is truly the fastest way to render the map.
		int gridY;
		int gridX;

		int height = drawHeight + grid.viewLocYByTile();
		int width = drawWidth + grid.viewLocXByTile();

		for (int y = grid.viewLocYByTile(); y < height; y++) {
			for (int x = grid.viewLocXByTile(); x < width; x++) {
				try {
					if (map.isFloor(map.elements[x][y])) {
						setFloor();
					} else if (map.isWall(map.elements[x][y])) {
						setWall(map.elements[x][y]);
					}

					gridX = grid.locationXByView(x);
					gridY = grid.locationYByView(y);

					g.drawImage(image, gridX, gridY, gridX + Grid.TILE_SIZE,
							gridY + Grid.TILE_SIZE, 0, 0, Grid.TILE_SIZE,
							Grid.TILE_SIZE, null);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Out of Bounds Error " + e);
					moveToSafeLocation();
				}

			}
		}
	}

}

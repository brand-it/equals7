package game;


import java.awt.Graphics;

public class MapRender extends Map{
	
	public int drawWidth = 0;
	public int drawHeight = 0;

	public MapRender(ImagesLoader imgLd, Grid grid) {
		super(imgLd, grid);
		// TODO Auto-generated constructor stub
	}
	
	public void resize(int width, int height){
		// Render one extra block on the edges so there are no black empty spots
		drawWidth = (width / Grid.TILE_SIZE) + 1;
		drawHeight = (height / Grid.TILE_SIZE) + 1;
	}
	

	private void moveToSafeLocation() {
		grid.setViewXByTile(0);
		grid.setViewYByTile(0);
	}
	
	public void draw(Graphics g) {
		// This is truly the fastest way to render the map.
		int gridY;
		int gridX;
		
		int height = drawHeight + grid.viewLocYByTile();
		int width =  drawWidth + grid.viewLocXByTile();
		
		for (int y = grid.viewLocYByTile(); y < height; y++) {
			for (int x = grid.viewLocXByTile(); x < width; x++) {
				try {
					if (isFloor(elements[x][y])) {
						setFloor();
					} else if (isWall(elements[x][y])) {
						setWall(elements[x][y]);
					}
					
					gridX = grid.locationXByView(x);
					gridY = grid.locationYByView(y);

					g.drawImage(image, gridX, gridY, gridX + Grid.TILE_SIZE, gridY
							+ Grid.TILE_SIZE, 0, 0, Grid.TILE_SIZE, Grid.TILE_SIZE,
							null);
				}catch (ArrayIndexOutOfBoundsException e){
					System.out.println("Out of Bounds Error " + e);
					moveToSafeLocation();
				}



			}
		}
	}


}

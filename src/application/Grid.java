package application;

public class Grid {

	public static final int TILE_SIZE = 50;
	
	// Use Location X to figure out your location acording to mouse
	public int locationX(int x)
	// converts from tile x and y to pixel x and y.
	// Used for drawing.
	{
		return x * TILE_SIZE;

	}

	public int locationXByView(int x) {

		int locX = locationX(x);
		return locX + View.viewLocX;
	}
	// Use Location Y to figure out your location acording to mouse
	public int locationY(int y) {
		return y * TILE_SIZE;
	}

	public int locationYByView(int y) {

		int locY = locationY(y);
		return locY + View.viewLocY;
	}
	// calling tile number based off mouse x or y
	// location.
	public int getTileX(int mouseX)
	{
		int locX = (mouseX / TILE_SIZE);
		return locX;
	}

	public int getTileY(int mouseY) {
		int locY = (mouseY / TILE_SIZE);
		return locY;
	}

	public int getTileYByView(int y) {
		int shiftY = 0;
		shiftY = (View.viewLocY / TILE_SIZE) / TILE_SIZE;
		shiftY = (shiftY - View.viewLocY);
		int locY = getTileY(y + shiftY);
		return locY;
	}

	public int getTileXByView(int x) {
		int shiftX = 0;
		shiftX = (View.viewLocX / TILE_SIZE) / TILE_SIZE;
		shiftX = (shiftX - View.viewLocX);
		int locX = getTileX(x + shiftX);
		return locX;
	}

	public int mouseBoxTileX(int mouseX)
	// tile rollover calculation. simplifies to which tile, then corrects for
	// pixel location.
	{
		int locX = (mouseX / TILE_SIZE) * TILE_SIZE;
		return locX;
	}

	public int mouseBoxTileY(int mouseY) {
		int locY = (mouseY / TILE_SIZE) * TILE_SIZE;
		return locY;
	}
	// This calculates in the Shift in the map
	public int mouseBoxTileYByView(int mouseY) {

		int shiftY = 0;
		shiftY = (View.viewLocY / TILE_SIZE) * TILE_SIZE;
		shiftY = shiftY - View.viewLocY;
		int locY = mouseBoxTileY(mouseY + shiftY);
		return -shiftY + locY;
	}

	public int viewLocXByTile() {
		return Math.abs(View.viewLocX / TILE_SIZE);
	}

	public int viewLocYByTile() {
		return Math.abs(View.viewLocY / TILE_SIZE);
	}

	public int mouseBoxTileXByView(int mouseX) {

		int shiftX = 0;
		shiftX = (View.viewLocX / TILE_SIZE) * TILE_SIZE;
		shiftX = shiftX - View.viewLocX;
		int locX = mouseBoxTileX(mouseX + shiftX);

		return -shiftX + locX;
	}

	public void setViewYByTile(int i) {
		View.viewLocY = (i / TILE_SIZE);
	}

	public void setViewXByTile(int i) {
		View.viewLocX = (i / TILE_SIZE);
	}
}

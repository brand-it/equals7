package application;

public class Grid {
	public static final int TILE_SIZE = 25;

	// viewLocX and viewLocY will be the defalut draw information for the hole
	// game.
	public int viewLocX = 0;
	public int viewLocY = 0;

	public Grid() {
	}

	public int locationX(int x)
	// converts from tile x and y to pixel x and y.
	// Used for drawing.
	{
		int locX = x * TILE_SIZE;
		return locX;

	}

	public int locationXByView(int x) {

		int locX = locationX(x);
		return locX + viewLocX;
	}

	public int locationY(int y) {
		int locY = y * TILE_SIZE;
		return locY;
	}

	public int locationYByView(int y) {

		int locY = locationY(y);
		return locY + viewLocY;
	}

	public int getTileX(int x)// calling tile number based off mouse x or y
								// location.
	{
		int locX = (x / TILE_SIZE);
		return locX;
	}

	public int getTileY(int y) {
		int locY = (y / TILE_SIZE);
		return locY;
	}

	public int getTileYByView(int y) {
		int shiftY = 0;
		shiftY = (viewLocY / TILE_SIZE) / TILE_SIZE;
		shiftY = (shiftY - viewLocY);
		int locY = getTileY(y + shiftY);
		return locY;
	}

	public int getTileXByView(int x) {
		int shiftX = 0;
		shiftX = (viewLocX / TILE_SIZE) / TILE_SIZE;
		shiftX = (shiftX - viewLocX);
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

	public int mouseBoxTileYByView(int mouseY) {

		int shiftY = 0;
		shiftY = (viewLocY / TILE_SIZE) * TILE_SIZE;
		shiftY = shiftY - viewLocY;
		int locY = mouseBoxTileY(mouseY + shiftY);
		return -shiftY + locY;
	}

	public int viewLocXByTile() {
		return Math.abs(viewLocX / TILE_SIZE);
	}

	public int viewLocYByTile() {
		return Math.abs(viewLocY / TILE_SIZE);
	}

	public int mouseBoxTileXByView(int mouseX) {

		int shiftX = 0;
		shiftX = (viewLocX / TILE_SIZE) * TILE_SIZE;
		shiftX = shiftX - viewLocX;
		int locX = mouseBoxTileX(mouseX + shiftX);

		return -shiftX + locX;
	}

	public void setViewYByTile(int i) {
		viewLocY = (i / TILE_SIZE);
	}

	public void setViewXByTile(int i) {
		viewLocX = (i / TILE_SIZE);
	}
}

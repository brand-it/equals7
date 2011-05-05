package game;

public class Grid {
	protected static final int TILE_SIZE = 23;

	public Grid() {

	}

	public int locationX(int x)// converts from tile x and y to pixel x and y.
								// Used for drawing.
	{
		int locX = x * TILE_SIZE;
		return locX;
	}

	public int locationY(int y) {
		int locY = y * TILE_SIZE;
		return locY;
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

	public int mouseBoxTileX(int x)
	// tile rollover calculation. simplifies to which tile, then corrects for
	// pixel location.
	{
		int locX = (x / TILE_SIZE) * TILE_SIZE;
		return locX;
	}

	public int mouseBoxTileY(int y) {
		int locY = (y / TILE_SIZE) * TILE_SIZE;
		return locY;
	}
}

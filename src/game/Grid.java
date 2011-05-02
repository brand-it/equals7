package game;

public class Grid {
	protected static final int TILE_SIZE = 23;
	public Grid() {

	}
	
	public int locationX(int x){
		int locX = x * TILE_SIZE;
		return locX;
	}
	
	public int locationY(int y){
		int locY = y * TILE_SIZE;
		return locY;
	}

	public int getTileX(int x) {
		int locX = (x / TILE_SIZE);
		return locX;
	}
	
	public int getTileY(int y) {
		int locY = (y / TILE_SIZE);
		return locY;
	}
	
	public int findTileX(int x){
		int locX = (x / TILE_SIZE) * TILE_SIZE;
		return locX;
	}
	public int findTileY(int y){
		int locY = (y / TILE_SIZE) * TILE_SIZE;
		return locY;
	}
}

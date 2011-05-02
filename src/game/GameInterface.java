package game;

import java.awt.Graphics;

public class GameInterface {
	int locY = 0;
	int locX = 0;
	private Map map;
	
	public GameInterface(Map map) {
		this.map = map;
	}
	
	public void changeBoxLocation(double mouseX, double mouseY){
		Grid grid = new Grid();
		locX = grid.findTileX((int) mouseX);
		locY = grid.findTileY((int) mouseY);
	}
	
	
	
	public void drawBox(Graphics g) {
		g.setColor(g.getColor().blue);
		g.drawRect(locX, locY, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}
}

package game;

import java.awt.Color;
import java.awt.Graphics;

public class GameInterface {
	int locY = 0;
	int locX = 0;
	
	public GameInterface() {
	}
	
	public void changeBoxLocation(double mouseX, double mouseY){
		Grid grid = new Grid();
		locX = grid.findTileX((int) mouseX);
		locY = grid.findTileY((int) mouseY);
	}
	
	
	
	public void drawBox(Graphics g) {
		g.getColor();
		g.setColor(Color.blue);
		g.drawRect(locX, locY, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}
}

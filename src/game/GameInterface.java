package game;

import java.awt.Color;
import java.awt.Graphics;

public class GameInterface {
	int locYRollover = 0;
	int locXRollover = 0;

	protected Dwarfs dwarfs;
	protected Map map;
	protected Dwarfs.Dwarf selectedDwarf;
	protected Pathfinder pathfinder;
	protected int element;
	protected Grid grid;

	public GameInterface(Map map, Dwarfs dwarfs, Pathfinder pathfinder,
			Grid grid) {
		this.dwarfs = dwarfs;
		this.map = map;
		this.pathfinder = pathfinder;
		this.grid = grid;
	}

	public void highlightUnit(Graphics g) {
		g.setColor(Color.red);
		if (selectedDwarf != null) {
			g.drawRect(selectedDwarf.smoothXLoc(), selectedDwarf.smoothYLoc(),
					Grid.TILE_SIZE, Grid.TILE_SIZE);
		}
	}

	public void changeBoxLocation(double mouseX, double mouseY) {
		locXRollover = grid.mouseBoxTileXByView((int) mouseX);
		locYRollover = grid.mouseBoxTileYByView((int) mouseY);
	}

	public void changeElement(int number) {
		element = number;
	}

	public void drawBox(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(locXRollover, locYRollover, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}

	public void draw(Graphics g) {
		drawBox(g);
		highlightUnit(g);
	}
}

package game;

import java.awt.Color;
import java.awt.Graphics;

public class GameInterface {
	int locYRollover = 0;
	int locXRollover = 0;

	private Dwarfs dwarfs;
	private Map map;
	private Dwarfs.Dwarf selectedDwarf;
	private Grid grid;
	private Pathfinder pathfinder;

	public GameInterface(Map map, Dwarfs dwarfs, Pathfinder pathfinder) {
		this.dwarfs = dwarfs;
		this.map = map;
		grid = new Grid();
		this.pathfinder = pathfinder;
	}

	public void mouseClick(int mouseX, int mouseY) {
		int currentElement = map.returnElement(grid.getTileX(mouseX),
				grid.getTileX(mouseY));
		if (map.isWall(currentElement)) {
			map.changeElement(mouseX, mouseY, map.floor());
		} else if (dwarfs.isDwarf(mouseX, mouseY)) {
			selectedDwarf = dwarfs.getDwarfByMouse(mouseX, mouseY);
			System.out.println(selectedDwarf);

		}

	}

	public void rightClick(int mouseX, int mouseY) {
		if (!dwarfs.isDwarf(mouseX, mouseY) && selectedDwarf != null) {
			System.out.print("Finding Path");
			selectedDwarf.path(pathfinder.findPath(selectedDwarf.locX,
					selectedDwarf.locY, mouseX, mouseY));

		}
	}

	public void highlightUnit(Graphics g) {
		g.setColor(Color.red);
		if (selectedDwarf != null) {
			g.drawRect(grid.locationX(selectedDwarf.locX),
					grid.locationY(selectedDwarf.locY), Grid.TILE_SIZE,
					Grid.TILE_SIZE);
		}
	}

	public void changeBoxLocation(double mouseX, double mouseY) {
		Grid grid = new Grid();
		locXRollover = grid.mouseBoxTileX((int) mouseX);
		locYRollover = grid.mouseBoxTileY((int) mouseY);
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

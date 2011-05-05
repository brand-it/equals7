package game;


import java.awt.Color;
import java.awt.Graphics;

public class GameInterface {
	int locY = 0;
	int locX = 0;

	private Dwarfs dwarfs;
	private Map map;
	
	public GameInterface(Map map, Dwarfs dwarfs) {
		this.dwarfs = dwarfs;
		this.map = map;
	}
	
	public void mouseClick(int mouseX, int mouseY){
		int currentElement = map.returnElement((int) mouseX, (int) mouseY);
		if (map.isWall(currentElement)) {
			map.changeElement(mouseX, mouseY, map.floor());
		} else if (!dwarfs.isDwarf(mouseX, mouseY)){
			System.out.println("Creating Dwarf");
			Dwarfs.Dwarf dwarf = dwarfs.new Dwarf(mouseX, mouseY);
			dwarfs.saveDwarf(dwarf);
		} else {
			dwarfs.selectDwarf(mouseX, mouseY);
			System.out.println("Unit Selected");
		}
	}
	
	public void changeBoxLocation(double mouseX, double mouseY){
		Grid grid = new Grid();
		locX = grid.findTileX((int) mouseX);
		locY = grid.findTileY((int) mouseY);
	}
	
	public void selectObject(Graphics g, int x, int y){
		g.getColor();
		g.setColor(Color.blue);
		g.drawRect(x, y, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}
	
	public void drawBox(Graphics g) {
		g.getColor();
		g.setColor(Color.blue);
		g.drawRect(locX, locY, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}
}

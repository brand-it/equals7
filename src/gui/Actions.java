package gui;

import game.Grid;
import game.Map;
import game.MapRender;
import window.Panel;

public class Actions extends InputActions {
	
	private static final int NUDGE = 5;
	protected MapRender mapRender;
	protected Panel panel;

	public Actions(MapRender mapRender, Grid grid, Panel panel) {
		super(grid);
		this.panel = panel;
		this.mapRender = mapRender;
	}

	public void changeElement(Map map, int mouseX, int mouseY, int element) {
		map.changeElement(mouseX, mouseY, element);
	}
	
	public void moveMap(int mouseX, int mouseY){
		if (mouseX < 100){
			nudgeLeft();
		}
		if (mouseY < 100){
			nudgeUp();
		}
		if (mouseX > panel.pWidth - 100){
			nudgeRight();
		}
		if (mouseY > panel.pHeight - 100){
			nudgeDown();
		}
	}

	public void nudgeLeft() {
		if (grid.viewLocXByTile() > 0) {
			grid.viewLocX += NUDGE;
		}

	}

	public void nudgeRight() {
		if (grid.viewLocXByTile() < (Map.WIDTH - mapRender.drawWidth) - 1) {
			grid.viewLocX -= NUDGE;
		}

	}

	public void nudgeUp() {
		if (grid.viewLocYByTile() > 0) {
			grid.viewLocY += NUDGE;
		}

	}

	public void nudgeDown() {
		// You have to have it at one because the draw height can on non prime numbers it can get higher then Map.HEIGHT
		if (grid.viewLocYByTile() < (Map.HEIGHT - mapRender.drawHeight) - 1) {
			grid.viewLocY -= NUDGE;
		}

	}
}

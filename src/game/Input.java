package game;

public class Input extends GameInterface {
	private static final int NUDGE = 50;

	public Input(MapRender mapRender, Dwarfs dwarfs, Pathfinder pathfinder, Grid grid) {
		super(mapRender, dwarfs, pathfinder, grid);
		// TODO Auto-generated constructor stub
	}

	public Dwarfs.Dwarf selectDwarf(Dwarfs dwarfs, int mouseX, int mouseY) {
		return dwarfs.getDwarfByMouse(mouseX, mouseY);

	}

	public void changeElement(Map map, int mouseX, int mouseY, int element) {
		map.changeElement(mouseX, mouseY, element);
	}

	public void leftClick(int mouseX, int mouseY) {

		if (dwarfs.isDwarf(mouseX, mouseY)) {
			selectedDwarf = selectDwarf(dwarfs, mouseX, mouseY);
		} else {
			changeElement(mapRender, mouseX, mouseY, element);
		}

	}

	public void rightClick(int mouseX, int mouseY) {
		if (!dwarfs.isDwarf(mouseX, mouseY) && selectedDwarf != null) {
			selectedDwarf.path(pathfinder.findPath(selectedDwarf.locX,
					selectedDwarf.locY, mouseX, mouseY));

		}
	}

	public void arrowLeft() {
		if (grid.viewLocXByTile() > 0) {
			grid.viewLocX += NUDGE;
		}

	}

	public void arrowRight() {
		if (grid.viewLocXByTile() < (Map.WIDTH - mapRender.drawWidth) - 1) {
			grid.viewLocX -= NUDGE;
		}

	}

	public void arrowUp() {
		if (grid.viewLocYByTile() > 0) {
			grid.viewLocY += NUDGE;
		}

	}

	public void arrowDown() {
		// You have to have it at one because the draw height can on non prime numbers it can get higher then Map.HEIGHT
		if (grid.viewLocYByTile() < (Map.HEIGHT - mapRender.drawHeight) - 1) {
			grid.viewLocY -= NUDGE;
		}

	}
}

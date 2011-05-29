package game;

public class Input extends GameInterface {
	private static final int NUDGE = 49;

	public Input(Map map, Dwarfs dwarfs, Pathfinder pathfinder, Grid grid) {
		super(map, dwarfs, pathfinder, grid);
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
			changeElement(map, mouseX, mouseY, element);
		}

	}

	public void rightClick(int mouseX, int mouseY) {
		if (!dwarfs.isDwarf(mouseX, mouseY) && selectedDwarf != null) {
			selectedDwarf.path(pathfinder.findPath(selectedDwarf.locX,
					selectedDwarf.locY, mouseX, mouseY));

		}
	}

	public void arrowLeft() {
		if (grid.viewLocX < 0) {
			grid.viewLocX += NUDGE;
		}

	}

	public void arrowRight() {
		if (Math.abs(grid.viewLocX) <= 600) {
			grid.viewLocX -= NUDGE;
		}

	}

	public void arrowUp() {
		if (grid.viewLocY < 0) {
			grid.viewLocY += NUDGE;
		}

	}

	public void arrowDown() {
		if (Math.abs(grid.viewLocY) <= 1300) {
			grid.viewLocY -= NUDGE;
		}

	}
}

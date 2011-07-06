package user_interface;

import java.awt.event.MouseWheelEvent;

import environment_manager.Map;
import environment_manager.MapRender;
import runner.Panel;
import application_controller.*;
import units_manager.*;

public class Reaction extends Draw {

	protected Panel panel;
	protected Buttons buttons;
	protected int storedElement;
	private Units units;
	public Pathfinder pathFinder;

	public Reaction(Map map, MapRender mapRender, Panel panel,
			ImagesLoader imgsLoader, Buttons buttons, Units units) {
		super(map, imgsLoader);
		// This builder is big but it needs to be that way Almost every thing
		// needs to be accessed in the GUI
		this.panel = panel;
		this.map = map;
		this.buttons = buttons;
		this.units = units;

		pathFinder = new Pathfinder(map);
	}

	public void changeElement(int mouseX, int mouseY, int element) {
		
		// Do to the fact that View uses actual x and mouseX is not need to be set to zero on a adjustment
		// and how the map is rendered you just need to zero out View X and Y
		mouseX = ((View.getModifiedLocX() * View.getScale()) + mouseX) / View.getScale();
		mouseY = ((View.getModifiedLocY() * View.getScale()) + mouseY) / View.getScale();

		map.changeElement(mouseX, mouseY, element);
	}

	// this is going to move the map around based on were your mouse is
	public void moveMap(int mouseX, int mouseY) {
		View view = new View();
		if (mouseX < 1) {
			view.nudgeLeft();
		}
		if (mouseY < 1) {
			view.nudgeUp();
		}
		if (mouseX > panel.pWidth - 1) {
			view.nudgeRight();
		}
		if (mouseY > panel.pHeight - 1) {
			view.nudgeDown();
		}
	}

	public void keyPressed(int key) {
		// This is going to handle all the default key input for the game
		if (key == 50) {
			changeStoredElementStone();
		}
		if (key == 49) {
			changeStoredElementFloor();
		}
	}

	public void changeStoredElementFloor() {
		storedElement = map.getElementByString("floor");
		setImages("darkFloorStone", 0);
	}

	public void changeStoredElementStone() {
		storedElement = map.getElementByString("stone");
		setImages("imageMap", storedElement);
	}

	public void leftClick(int mouseX, int mouseY) {
		if (buttons.isButton(mouseX, mouseY)) {
			CustomButton button = buttons.getButtonByLocation(mouseX, mouseY);
			button.action();
		} else if (units.isUnit(mouseX, mouseY)) {
			selectedUnit = units.getUnitByLocation(mouseX, mouseY);
		} else {
			changeElement(mouseX, mouseY, storedElement);
			selectedUnit = null;
		}

	}

	public void rightClick(int mouseX, int mouseY) {

		if (selectedUnit != null) {
			// Path path = pathFinder.findPath(selectedUnit.getX(),
			// selectedUnit.getY(),
			// grid.getTileXByView(mouseX),grid.getTileYByView(mouseY));
			// selectedUnit.newPath(path);
		} else {
			Unit unit = new Unit("dwarf", map, imgsLoader, mouseX, mouseY);
			units.save(unit);
		}
	}

	public void wheelMouse(MouseWheelEvent e) {
		View view = new View();
		if (e.getWheelRotation() == 1) {
			view.zoomOut();
		}
		if (e.getWheelRotation() == -1) {
			view.zoomIn();
		}

	}
}

package user_interface;

import java.awt.event.MouseWheelEvent;
import environment_manager.MapRender;
import runner.Panel;
import application_controller.*;
import units_manager.*;

public class Reaction extends Draw {

	protected Panel panel;
	protected Buttons buttons;
	protected int storedElement;

	public Reaction(MapRender mapRender, Panel panel) {
		// This builder is big but it needs to be that way Almost every thing
		// needs to be accessed in the GUI
		this.panel = panel;

	}

	private int getModfiedMouseX(int mouseX) {
		return ((View.getModifiedLocX() * View.getScale()) + mouseX)
				/ View.getScale();
	}

	private int getModfiedMouseY(int mouseY) {
		return ((View.getModifiedLocY() * View.getScale()) + mouseY)
				/ View.getScale();
	}

	public void changeElement(int mouseX, int mouseY, int element) {

		// Do to the fact that View uses actual x and mouseX is not need to be
		// set to zero on a adjustment
		// and how the map is rendered you just need to zero out View X and Y
		mouseX = getModfiedMouseX(mouseX);
		mouseY = getModfiedMouseY(mouseY);

		ApplicationData.map.changeElement(mouseX, mouseY, element);
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
		storedElement = ApplicationData.map.getElementByString("floor");
	}

	public void changeStoredElementStone() {
		storedElement = ApplicationData.map.getElementByString("stone");
	}

	public void leftClick(int mouseX, int mouseY) {

		int modifiedX = getModfiedMouseX(mouseX);
		int modifiedY = getModfiedMouseY(mouseY);

		if (ApplicationData.units.isUnit(modifiedX, modifiedY)) {
			selectedUnit = ApplicationData.units.getUnitByLocation(modifiedX,
					modifiedY);
		} else if (selectedUnit != null) {
			selectedUnit.dig(modifiedX, modifiedY);
			changeElement(mouseX, mouseY, storedElement);
		}

	}

	public void rightClick(int mouseX, int mouseY) {

		int modifiedX = getModfiedMouseX(mouseX);
		int modifiedY = getModfiedMouseY(mouseY);

		if (selectedUnit != null) {
			Pathfinder pathFinder = new Pathfinder();
			Path path = pathFinder.findPath(selectedUnit.getX(),
					selectedUnit.getY(), modifiedX, modifiedY);
			selectedUnit.newPath(path);
		} else {
			Unit unit = new Unit("dwarf", modifiedX, modifiedY);
			ApplicationData.units.save(unit);
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

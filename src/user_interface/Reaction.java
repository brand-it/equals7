package user_interface;

import java.awt.event.MouseWheelEvent;
import environment_manager.MapRender;
import runner.Panel;
import application_controller.*;
import units_manager.*;

public class Reaction extends Draw {

	protected Panel panel;
	protected int storedElement;
	protected boolean attackMode, moveMode;

	public Reaction(MapRender mapRender, Panel panel) {
		// This builder is big but it needs to be that way Almost every thing
		// needs to be accessed in the GUI
		this.panel = panel;
		actionsMenu = new ActionsMenu(this);

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
		if (mouseX > View.panelWidth() - 1) {
			view.nudgeRight();
		}
		if (mouseY > View.panelHeight() - 1) {
			view.nudgeDown();
		}
	}

	public void keyPressed(int key) {
		// This is going to handle all the default key input for the game
		TurnsController turnsController = ApplicationData.turnsController;
		if (key == 50) {
			changeStoredElementStone();
		}
		if (key == 49) {
			changeStoredElementFloor();
		}
		if (key == 67) {
			turnsController.compressInitiative();
			setSelectedUnit(turnsController.getUnit());
		}
		if (key == 86) {
			nextTurn();
		}
	}

	public void changeStoredElementFloor() {
		System.out.println("Setting Stored Element to Floor");
		storedElement = ApplicationData.map.getElementByString("floor");
	}

	public void changeStoredElementStone() {
		System.out.println("Setting Stored Element to Stone");
		storedElement = ApplicationData.map.getElementByString("stone");
	}

	
	private void setSelectedUnit(Unit unit){
		if (unit != null){
			selectedUnit = unit;
		}

	}
	
	public void moveMode(){
		if (selectedUnit != null){
			clearAttackRange();
			clearMoveArea();
			buildMoveArea();
			moveMode = true;
			attackMode = false;
		}
	}
	
	public void attackMode(){
		if(selectedUnit != null){
			clearMoveArea();
			clearAttackRange();
			buildAttackRange();
			moveMode = false;
			attackMode = true;
		}
	}
	
	public void leftClick(int mouseX, int mouseY) {

//		int modifiedX = getModfiedMouseX(mouseX);
//		int modifiedY = getModfiedMouseY(mouseY);
		if(!actionsMenu.clicked(mouseX, mouseY)){
			actionsMenu.setActionMenuLocation(mouseX, mouseY);
			actionsMenu.showActionMenu();
		} else {
			actionsMenu = new ActionsMenu(this);
		}


	}

	public void rightClick(int mouseX, int mouseY) {

		int modifiedX = getModfiedMouseX(mouseX);
		int modifiedY = getModfiedMouseY(mouseY);
		
		if (selectedUnit != null) {
			if (attackMode){
				selectedUnit.setTarget(ApplicationData.units.getUnitByLocation(modifiedX, modifiedY));
				selectedUnit.attackTarget();
				nextTurn();
				moveMode();
			} else if (moveMode){
				pathfinder = new Pathfinder();
				Path path = pathfinder.findPath(selectedUnit.getX(),
						selectedUnit.getY(), modifiedX, modifiedY);
				if (selectedUnit.rangeCheck(path)) {
					selectedUnit.newPath(path);
					nextTurn();
					moveMode();
				}
			}


		} else {
			Unit unit = new Unit("dwarf", modifiedX, modifiedY);
			ApplicationData.units.add(unit);
		}
	}
	
	public void nextTurn(){
		ApplicationData.turnsController.goToNextTurn();
		setSelectedUnit(ApplicationData.turnsController.getUnit());
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

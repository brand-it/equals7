package gui;

import application.*;
import units.*;
import window.Panel;

public class Reaction extends Draw {

	private static final int NUDGE = 10;
	protected Panel panel;
	protected Buttons buttons;
	protected int storedElement;
	private MapRender mapRender;
	private Units units;
	public Pathfinder pathFinder;
	private int speed;
	
	public Reaction(Map map, MapRender mapRender, Panel panel, ImagesLoader imgsLoader, Buttons buttons, Units units) {
		super(map, imgsLoader);
		// This builder is big but it needs to be that way Almost every thing needs to be accessed in the GUI
		this.panel = panel;
		this.map = map;
		this.mapRender = mapRender;
		this.buttons = buttons;
		this.units = units;
		
		pathFinder = new Pathfinder(map);
	}

	public void changeElement(int mouseX, int mouseY, int element) {
		map.changeElement(mouseX, mouseY, element);
	}
	
	// this is going to move the map around based on were your mouse is
	public void moveMap(int mouseX, int mouseY) {
		if (mouseX < 1) {
			nudgeLeft();
		}
		if (mouseY < 1) {
			nudgeUp();
		}
		if (mouseX > panel.pWidth - 1) {
			nudgeRight();
		}
		if (mouseY > panel.pHeight - 1) {
			nudgeDown();
		}
	}

	public void nudgeLeft() {
		if (grid.viewLocXByTile() > 0) {
			View.viewLocX += NUDGE;
		}

	}

	public void nudgeRight() {
		if (grid.viewLocXByTile() < (Map.WIDTH - mapRender.drawWidth) - 1) {
			View.viewLocX -= NUDGE;
		}

	}

	public void nudgeUp() {
		if (grid.viewLocYByTile() > 0) {
			View.viewLocY += NUDGE;
		}

	}
	
	public void nudgeDown() {
		// You have to have it at one because the draw height can on non prime
		// numbers it can get higher then Map.HEIGHT
		if (grid.viewLocYByTile() < (Map.HEIGHT - mapRender.drawHeight) - 1) {
			View.viewLocY -= NUDGE;
		}
	}
	
	public void keyPressed(int key){
		// This is going to handle all the default key input for the game
		if (key == 50){
			changeStoredElementStone();
		}
		if (key == 49){
			changeStoredElementFloor();
		}
	}
	
	public void changeStoredElementFloor(){
		storedElement = map.getElementByString("floor");
		setImages("darkFloorStone", 0);
	}
	
	public void changeStoredElementStone(){
		storedElement = map.getElementByString("stone");
		setImages("imageMap", storedElement);
	}

	public void leftClick(int mouseX, int mouseY){
		if (buttons.isButton(mouseX, mouseY)){
			CustomButton button = buttons.getButtonByLocation(mouseX, mouseY);
			button.action();
		}else if (units.isUnit(mouseX, mouseY)){
			selectedUnit = units.getUnitByLocation(mouseX, mouseY);
		}else{
			changeElement(mouseX, mouseY, storedElement);
			selectedUnit = null;
		}
		
	}

	public void rightClick(int mouseX, int mouseY) {

		if(selectedUnit != null){
			Path path = pathFinder.findPath(selectedUnit.getX(), selectedUnit.getY(), grid.getTileXByView(mouseX),grid.getTileYByView(mouseY));
			selectedUnit.newPath(path);
			System.out.println("New Path Set");
		}else{
			speed++;
			Unit unit = new Unit("dwarf", map, imgsLoader, mouseX, mouseY, speed);
			units.save(unit);
		}
	}

	public void changeBoxLocation(double mouseX, double mouseY) {
		locXRollover = grid.mouseBoxTileXByView((int) mouseX);
		locYRollover = grid.mouseBoxTileYByView((int) mouseY);
	}
}

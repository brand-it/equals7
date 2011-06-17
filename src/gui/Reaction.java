package gui;

import application.Grid;
import application.ImagesLoader;
import application.Map;
import application.MapRender;
import window.Panel;

public class Reaction extends Draw{

	private static final int NUDGE = 10;
	protected MapRender mapRender;
	protected Panel panel;
	protected Buttons buttons;
	protected int storedElement;
	
	public Reaction(MapRender mapRender, Grid grid, Panel panel, ImagesLoader imgs, Buttons buttons) {
		super(grid, imgs);
		this.panel = panel;
		this.mapRender = mapRender;
		this.buttons = buttons;
	}

	public void changeElement(int mouseX, int mouseY, int element) {
		mapRender.changeElement(mouseX, mouseY, element);
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
		storedElement = mapRender.getElementByString("floor");
		setImages("darkFloorStone", 0);
	}
	
	public void changeStoredElementStone(){
		storedElement = mapRender.getElementByString("stone");
		setImages("imageMap", storedElement);
	}

	public void nudgeDown() {
		// You have to have it at one because the draw height can on non prime
		// numbers it can get higher then Map.HEIGHT
		if (grid.viewLocYByTile() < (Map.HEIGHT - mapRender.drawHeight) - 1) {
			grid.viewLocY -= NUDGE;
		}
	}

	public void leftClick(int mouseX, int mouseY){
		if (buttons.isButton(mouseX, mouseY)){
			CustomButton button = buttons.getButtonByLocation(mouseX, mouseY);
			button.action();
		}else {
			changeElement(mouseX, mouseY, storedElement);
		}
		
	}

	public void rightClick(int mouseX, int mouseY) {
	}

	public void changeBoxLocation(double mouseX, double mouseY) {
		locXRollover = grid.mouseBoxTileXByView((int) mouseX);
		locYRollover = grid.mouseBoxTileYByView((int) mouseY);
	}
}

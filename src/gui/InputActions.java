package gui;

import game.Grid;

import java.awt.Color;
import java.awt.Graphics;


public class InputActions {
	int locYRollover = 0;
	int locXRollover = 0;

	protected int element;
	protected Grid grid;

	public InputActions(Grid grid) {
		this.grid = grid;
	}

	public void highlightUnit(Graphics g) {
	}
	
	public void leftClick(int mouseX, int mouseY) {
	}

	public void rightClick(int mouseX, int mouseY) {
	}

	public void changeBoxLocation(double mouseX, double mouseY) {
		locXRollover = grid.mouseBoxTileXByView((int) mouseX);
		locYRollover = grid.mouseBoxTileYByView((int) mouseY);
	}

	public void changeElement(int number) {
		element = number;
	}

	public void drawBox(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(locXRollover, locYRollover, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}
	
	public void drawMouse(Graphics g, int mouseX, int mouseY){
		g.setColor(Color.green);
		g.fillRect(mouseX, mouseY, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}

	public void draw(Graphics g, int mouseX, int mouseY) {
		drawBox(g);
		highlightUnit(g);
		drawMouse(g, mouseX, mouseY);
	}
}

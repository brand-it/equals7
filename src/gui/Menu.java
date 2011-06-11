package gui;

import game.Grid;

import java.awt.Color;
import java.awt.Graphics;


public class Menu extends InputActions{
	
	private int drawX, drawY, height, width;
	
	public Menu(Grid grid) {
		super(grid);
	}
	
	public void setDrawLocations(int x, int y, int width, int height){
		drawX = x;
		drawY = y;
		this.height = height / 4;
		this.width = width;
	}

	public void draw(Graphics g){
		g.setColor(Color.red);
		g.fillRect(drawX, drawY - 100, width, height);
	}
}

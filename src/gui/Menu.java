package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Menu {
	
	private int drawX, drawY;
	
	public void setDrawLocations(int x, int y){
		drawX = x;
		drawY = y;
	}

	public void draw(Graphics g){
		g.setColor(Color.red);
		g.fillRect(drawX, drawY, 100, 100);
	}
}

package application_controller;

import environment_manager.Tiles;

// This is a global class
public class View {
	// the number is all way negative.
	public static int viewLocX = 0;
	public static int viewLocY = 0;
	// When you scroll in and out in the game you change the distance you are view the objects
	public static int distance = 1;
	public static int SCALEDTILE = Tiles.SIZE;
	
	private static int NUDGE = 10;
	
	
	public void nudgeLeft() {
		if (viewLocX > 0){
			viewLocX -= NUDGE;
		}
		
	}

	public void nudgeRight() {
		viewLocX += NUDGE;

	}

	public void nudgeUp() {
		if (viewLocY > 0){
			viewLocY -= NUDGE;
		}
		
	}
	
	public void nudgeDown() {
		viewLocY += NUDGE;
	}
	
	public void zoomOut(){
		distance++;
	}
	public void zoomIn(){
		if (distance > 1){
			distance--;
		}

	}
}

package application_controller;

import environment_manager.Map;
import environment_manager.Tiles;

// This is a global class
public class View {

	private static int pWidth = 900;
	private static int pHeight = 700;
	private static int sx;
	private static int sy;
	private static int ex;
	private static int ey;
	
	// the number is all way negative.
	public static int LOCX = 0;
	public static int LOCY = 0;
	// When you scroll in and out in the game you change the distance you are
	// view the objects
	public static int distance = 2;
	public static int SCALEDTILE = Tiles.SIZE;

	private static int NUDGE = 10;

	public static int getRoundedX() {
		return (LOCX / getScale()) * getScale();
	}

	public static int getRoundedY() {
		return (LOCY / getScale()) * getScale();
	}

	public static int getModifiedLocX() {
		return LOCX / getScale();
	}

	public static int getModifiedLocY() {
		return LOCY / getScale();
	}

	public static int getScale() {
		return Tiles.SIZE / View.distance;
	}

	public void nudgeLeft() {
		if (LOCX >= nudgeCalculation()) {
			LOCX -= nudgeCalculation();
		}else {
			LOCX -= sx;
		}
		updateRenderWidthHeight();

	}

	public void nudgeRight() {
		if (ex + nudgeCalculation() < Map.WIDTH) {
			LOCX += nudgeCalculation();
		} else {
			LOCX += Map.WIDTH - ex;
		}
		updateRenderWidthHeight();
	}

	public void nudgeUp() {
		if (LOCY >= nudgeCalculation()) {
			LOCY -= nudgeCalculation();
		} else {
			LOCY -= sy;
		}
		updateRenderWidthHeight();
	}

	public void nudgeDown() {
		if (ey + nudgeCalculation() < Map.HEIGHT) {
			LOCY += nudgeCalculation();
		} else {
			LOCY += Map.HEIGHT - ey;
		}
		updateRenderWidthHeight();
	}

	private int nudgeCalculation() {
		return NUDGE * distance;
	}

	public void zoomOut() {
		if (distance < 5) {
			distance++;
		}
		updateRenderWidthHeight();
	}

	public void zoomIn() {
		if (distance > 1) {
			distance--;
		}
		updateRenderWidthHeight();
	}
	
	public static int panelHeight(){
		return pHeight;
	}
	
	public static int panelWidth(){
		return pWidth;
	}
	
	public static int startX(){
		return sx;
	}
	
	public static int startY(){
		return sy;
	}
	
	public static int endX(){
		return ex;
	}
	
	public static int endY(){
		return ey;
	}
	
	public static void setPDimensions(int width, int height){
		pWidth = width;
		pHeight = height;
	}
	
	// this will update the render width and height of the map and anything else that uses this.
	public static void updateRenderWidthHeight(){
		sx = View.getModifiedLocX();
		sy = View.getModifiedLocY();
		ex = ((pWidth / getScale()) + View.getModifiedLocX()) + 1;
		ey = ((pHeight / getScale()) + View.getModifiedLocY()) + 1;
	}
}

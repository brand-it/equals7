package application_controller;

import environment_manager.Tiles;

// This is a global class
public class View {
	// the number is all way negative.
	public static int LOCX = 0;
	public static int LOCY = 0;
	// When you scroll in and out in the game you change the distance you are
	// view the objects
	public static int distance = 5;
	public static int SCALEDTILE = Tiles.SIZE;

	private static int NUDGE = 1;

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
		if (LOCX > 0) {
			LOCX -= nudgeCalculation();
		}

	}

	public void nudgeRight() {
		LOCX += nudgeCalculation();

	}

	public void nudgeUp() {
		if (LOCY > 0) {
			LOCY -= nudgeCalculation();
		}

	}

	public void nudgeDown() {
		LOCY += nudgeCalculation();
	}

	private int nudgeCalculation() {
		return NUDGE;
	}

	public void zoomOut() {
		distance++;
	}

	public void zoomIn() {
		if (distance > 1) {
			distance--;
		}

	}
}

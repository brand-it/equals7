package application_controller;

import environment_manager.Map;
import environment_manager.MapRender;
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
		}

	}

	public void nudgeRight() {
		if (MapRender.sx2 + nudgeCalculation() < Map.WIDTH) {
			LOCX += nudgeCalculation();
		} else {
			LOCX += Map.WIDTH - MapRender.sx2;
		}

	}

	public void nudgeUp() {
		if (LOCY >= nudgeCalculation()) {
			LOCY -= nudgeCalculation();
		}

	}

	public void nudgeDown() {
		if (MapRender.sy2 + nudgeCalculation() < Map.HEIGHT) {
			LOCY += nudgeCalculation();
		} else {
			LOCY += Map.HEIGHT - MapRender.sy2;
		}

	}

	private int nudgeCalculation() {
		return NUDGE * distance;
	}

	public void zoomOut() {
		if (distance < 15) {
			distance++;
		}

	}

	public void zoomIn() {
		if (distance > 1) {
			distance--;
		}

	}
}

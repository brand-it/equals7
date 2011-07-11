package units_manager;

import java.util.ArrayList;

import units_manager.Path.Step;

public class Mover extends UnitRender {

	protected ArrayList<Path> paths = new ArrayList<Path>();
	// You store the paths in the array above and then place the one you are on
	// in the
	// Path Class I think it is still called class
	private Path path = null;
	// The unit movement speed is a basic division
	// You want the unit to move faster you divided by a smaller number.
	public int movementSpeed = 40;
	// The delay is basic when you choose to make the unit pause a second before
	// moving. 0 = no delay 1 means pause
	// once and then keep moving.
	protected int delay = 0;
	// This is the index number for the path
	// If you don't start at one it will have a delay in the system before it
	// starts moving
	private int stepsTaken = 0;

	// this tells the unit that there is an new location you need to go two
	public void newPath(Path path) {
		paths.clear();
		stepsTaken = 0;
		this.path = path;
	}

	public void moveUnit() {
		if (movementSpeed == delay) {
			stepsTaken++;
			delay = 0;
			if (stepsTaken < path.getLength()) {
				Step step = path.getStep(stepsTaken);
				tileX = step.getX();
				tileY = step.getY();
			} else {
				path = null;
			}
		} else {
			delay++;
		}

	}

	public boolean ifPath() {
		if (path != null) {
			return true;
		}
		return false;
	}

	public int getEndX() {
		return path.getStep(path.getLength() - 1).getX();
	}

	public int getEndY() {
		return path.getStep(path.getLength() - 1).getY();
	}

	// not in use yet still working out the smaller problems
	@SuppressWarnings("unused")
	private void nextWayPoint() {
	}

	public void move() {
		if (path != null) {
			moveUnit();
		}
	}

}

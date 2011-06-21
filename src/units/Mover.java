package units;

import java.util.ArrayList;

import application.*;

public class Mover extends UnitRender {

	protected Grid grid;
	protected ArrayList<Path> paths = new ArrayList<Path>();
	// You store the paths in the array above and then place the one you are on
	// in the
	// Path Class I think it is still called class
	private Path path = null;
	// Have to store the next move location so it moves there then updates the path
	protected Map map;
	// Tells the system that we have got to that way point.
	// Can be used to create a patrol path. Don't delete a wayPointIndex
	private int wayPointIndex = 0;
	// The unit movement speed is a basic division
	// You want the unit to move faster you divided by a smaller number.
	public int movementSpeed = 0;
	// The delay is basic when you choose to make the unit pause a second before moving. 0 = no delay 1 means pause
	// once and then keep moving.
	protected int delay = 0;
	// number of times delayed
	private int delayed = 0;
	// This is the index number for the path
	// If you don't start at one it will have a delay in the system before it starts moving
	private int step = 1;

	// The number of moves that can be taken per step
	private int movesPerStep = 0;
	// The number of steps that have been taken.
	private int stepsTaken = 0;


	private void pathSetup() {
		if (movesPerStep == 0 && movementSpeed != 0 && stepsTaken == 0) {
			// All ways rounds down
			movesPerStep = Grid.TILE_SIZE / movementSpeed;
			System.out.println("moves per Step " + movesPerStep);
		}
	}
	
	private void stillMoving(){
		stepsTaken = movesPerStep - stepsTaken;
	}

	// this tells the unit that there is an new location you need to go two
	public void newPath(Path path) {
		paths.clear();
		if (this.path == null){
			step = 1;
		}else{
			step = 0;
			stillMoving();
		}
		wayPointIndex = 0;
		saveWayPoint(path);
		this.path = path;
	}
	// Call this in the next step method and it will correct any errors that may happen do to strange movements speeds
	private void theFixer() {
		if (locY != nextConverstionY() || locX != nextConverstionX()){
			locY = nextConverstionY();
			locX = nextConverstionX();
		}
	}

	// This tells the unit it has some were it needs to go after it get to were
	// it is going.
	public void saveWayPoint(Path path) {
		pathSetup();
		paths.add(path);
	}

	private int nextConverstionX() {
		return grid.locationX(path.getStep(step).getX());
	}

	private int nextConverstionY() {
		return grid.locationY(path.getStep(step).getY());
	}

	private void moveXY() {

		// If it some how runs this method with out a path set it will try to
		// set one for you
		if (path == null && paths.size() >= 1) {
			System.out.println("Move path was null trying to set");
			path = paths.get(wayPointIndex);
		}
		int nextStepX = nextConverstionX();
		int nextStepY = nextConverstionY();

		if (locX < nextStepX) {
			locX += movementSpeed;
		}
		if (locX > nextStepX) {
			locX -= movementSpeed;
		}
		if (locY < nextStepY) {
			locY += movementSpeed;
		}
		if (locY > nextStepY) {
			locY -= movementSpeed;
		}
		stepsTaken++;
	}


	private void nextStep() {
		if (movesPerStep < stepsTaken) {
			theFixer();
			step++;
			if (path.getLength() == step) {
				step = 0;
				path = null;
			}
			stepsTaken = 0;
		}

	}
	// not in use yet still working out the smaller problems
	private void nextWayPoint() {
		if (path.getLength() == step) {
			wayPointIndex++;
			path = paths.get(wayPointIndex);
		}
	}
	// This handles slowing the unit down even more. 
	private boolean isDelayed(){

		if (delay != delayed ){
			delayed++;
			return false;
		} else {
			delayed = 0;
			return true;
		}
	}

	public void move() {
		if (path != null) {
			if (isDelayed()){
				moveXY();
				nextStep();
			}
			// nextWayPoint();
		}
	}

}

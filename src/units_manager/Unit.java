package units_manager;

import application_controller.ApplicationData;

public class Unit extends Mover implements Comparable<Unit> {

	
	// On creation set the units Location this will be based off of mouseX and
	// mouseY
	// It will round off and put it square in the center of the tile for you.
	public Unit(String unitClass, int modifiedX, int modifiedY) {
		setup(unitClass, modifiedX, modifiedY);
		// After setup we need to set the units speed and anything else relevant
		// to the unit
		unitSetup();
		// There is current only one unit type and it is a dwarf sad
		setImage(unitClass);
	}

	public int getX() {
		return tileX;
	}

	public int getY() {
		return tileY;
	}
	
	public int getInitiative(){
		return initiative;
	}
	
	public void setInitiative(int initiative){
		this.initiative = initiative;
	}

	private void setup(String unitClass, int modifiedX, int modifiedY) {
		// Unit Current Location it is set center of a tile. After that it does
		// not mater
		// were the unit stops. It can stop on the moon if it wants.
		tileY = modifiedY;
		tileX = modifiedX;
		this.unitClass = unitClass;
	}

	private void unitSetup() {
		// This data will need to be set in some sort of binary files
		if (unitClass == "dwarf") {
			range = 4;
		}

	}
	// 
	// 
	/*
	 * this system has problems needs more work will move to the closet open tile but tile not checked to see if there
	 * is a path.
	 */
	public void moveClosesOpenTile(int modifiedX, int modifiedY) {
		int left = ApplicationData.map.moveLeft(modifiedX);
		int up = ApplicationData.map.moveUp(modifiedY);
		int down = ApplicationData.map.moveDown(modifiedY);
		int right = ApplicationData.map.moveRight(modifiedX);
		Node node = new Node(modifiedX, modifiedY);
		System.out.println(modifiedX + ", " + modifiedY);
		if (euclidianCalculation(left, modifiedY, tileX, tileY) > node.cost
				&& !ApplicationData.map.isBlocked(left, modifiedY)) {
			node = new Node(left, modifiedY);
			node.cost = euclidianCalculation(left, modifiedY, tileX, tileY);
			System.out.println("left");
		}

		if (euclidianCalculation(right, modifiedY, tileX, tileY) > node.cost
				&& !ApplicationData.map.isBlocked(right, modifiedY)) {
			node = new Node(right, modifiedY);
			node.cost = euclidianCalculation(right, modifiedY, tileX, tileY);
			System.out.println("Right");
		}
		if (euclidianCalculation(modifiedX, down, tileX, tileY) > node.cost
				&& !ApplicationData.map.isBlocked(modifiedX, down)
				|| node == null) {
			node = new Node(modifiedX, down);
			node.cost = euclidianCalculation(modifiedX, down, tileX, tileY);
			System.out.println("Down");
		}
		if (euclidianCalculation(modifiedX, up, tileX, tileY) > node.cost
				&& !ApplicationData.map.isBlocked(modifiedX, up)
				|| node == null) {
			node = new Node(modifiedX, up);
			node.cost = euclidianCalculation(modifiedX, up, tileX, tileY);
			System.out.println("UP");
		}

		System.out.println(node.getX() + ", " + node.getY());
		Pathfinder pathfinder = new Pathfinder();
		Path path = pathfinder.findPath(tileX, tileY, node.getX(), node.getY());
		this.newPath(path);
	}

	public float euclidianCalculation(int tx, int ty, int x, int y) {
		// Euclidian Calculation. Slower but much more accurate.

		int eucDistanceX = Math.abs(x - tx);
		int eucDistanceY = Math.abs(y - ty);

		return (float) Math
				.sqrt(((eucDistanceX * eucDistanceX) + (eucDistanceY * eucDistanceY)));
	}

	public void dig(int modifiedX, int modifiedY) {
		if (ApplicationData.map.isBlocked(modifiedX, modifiedY)) {
			moveClosesOpenTile(modifiedX, modifiedY);
		}

		// ApplicationData.map.changeElement(1, 1, ApplicationData.map.floor());
	}

	public Unit getUnitByLocation(int modifiedX, int modifiedY) {
		if (isUnit(modifiedX, modifiedY)) {
			return this;
		} else {
			return null;
		}
	}

	// This is a very fast simple way to create a hit box on object. Need to
	// name it better
	public boolean isUnit(int modifiedX, int modifiedY) {
		if (modifiedX == tileX && modifiedY == tileY) {
			return true;
		}
		return false;
	}

	private class Node {
		protected int x, y;
		private double cost;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	@Override
	public int compareTo(Unit unit) {
		
		if (unit.initiative > initiative){
			return -1;
		} else if ( unit.initiative < initiative){
			return 1;
		}else{
			return 0;
		}
	}

	public boolean rangeCheck(Path path) {
		if (path.getLength() <= range+1){
			return true;
		}
		return false;
	}
}

package units_manager;

import application_controller.ApplicationData;

public class Unit extends Mover implements Comparable<Unit> {

	private int health = 10;
	private int attackRange = 1;
	
	private Unit target;

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
	
	public void setTarget(Unit unit){
		target = unit;
	}

	public int getX() {
		return tileX;
	}

	public int getY() {
		return tileY;
	}
	
	public int getAttackRange(){
		return attackRange;
	}
	
	public void attackTarget(){
		if (target != null && target.inAttackRange(this)){
			target.damaged(5);
			System.out.println("Health: " + health);
			if (target.dead()){
				System.out.println("Target is dead");
				ApplicationData.units.remove(target);
				ApplicationData.turnsController.compressInitiative();
			}
		}
	}
	
	public boolean inAttackRange(Unit unit){
		int range = (int) Math.sqrt(Math.pow((unit.getX() - this.getX()), 2) + Math.pow((unit.getY() - this.getY()), 2));
		if (range <= attackRange){
			return true;
		}
		return false;
	}
	
	public boolean dead(){
		if (health > 0) {
			return false;
		}
		return true;
	}
	
	public void damaged(int damage){
		health = health - damage;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
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

	@Override
	public int compareTo(Unit unit) {

		if (unit.initiative > initiative) {
			return -1;
		} else if (unit.initiative < initiative) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean rangeCheck(Path path) {
		if (path != null){
			if (path.getLength() <= range + 1) {
				return true;
			}
		}
		return false;
	}
}

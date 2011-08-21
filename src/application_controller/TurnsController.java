package application_controller;

import java.util.ArrayList;
import java.util.Collections;

import units_manager.Unit;

public class TurnsController {
	/*
	 * This will handle all the systems turns. Unit Manage, is the only system
	 * that is going to use this system as of yet. Other system are going to
	 * need to access this. It is going to store the turns for each unit.
	 * 
	 * there is going to be a sorted array that stores each unit based on its
	 * initiative value in the unit manage. it runs a Array.sort on the list
	 * based off the initiative value
	 */
	private Unit unit;
	// this lets me call the units variable in this class with out writing so
	// much
	private ArrayList<Unit> units = ApplicationData.units.getUnitsArray();

	/**
	 * 
	 * returns the first unit in the turns controller
	 * 
	 * @return Unit
	 */
	private Unit getFirst() {
		return units.get(0);
	}

	public Unit getUnit() {
		return unit;
	}

	public void goToNextTurn() {
		Collections.rotate(units, 1);
		compressInitiative();
		unit = getFirst();
	}

	public void save(Unit unit) {
		units.add(unit);
		Collections.sort(units);
	}

	/**
	 * This will update the initiative of all the units in the turns Controller.
	 * this will take all the numbers in the initiative compare it to the next
	 * one and reduce that number based on that.
	 * 
	 * Also Runs a sort on the units
	 */
	public void compressInitiative() {
		int loopNumber = 0;
		int initiative = 0;
		Unit unit;
		while (units.size() > loopNumber) {
			unit = units.get(loopNumber);
			if (unit.getInitiative() == 0 && loopNumber == 0) {
				initiative = 0;
			} else if (unit.getInitiative() == 0) {
				initiative = loopNumber;
			} else {
				initiative++;
			}
			unit.setInitiative(initiative);
			loopNumber++;
		}
		Collections.sort(units);
	}

}

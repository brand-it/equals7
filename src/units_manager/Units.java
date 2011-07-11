package units_manager;

import java.awt.Graphics;
import java.util.ArrayList;

// Wow that is the most amount comments I have ever left
public class Units {
	private ArrayList<Unit> units = new ArrayList<Unit>();

	// a cleaner way to get unit by index
	public Unit getUnitByIndex(int index) {
		return units.get(index);
	}

	public boolean isUnit(int mouseX, int mouseY) {
		for (int u = 0; u < units.size(); u++) {
			if (getUnitByIndex(u).isUnit(mouseX, mouseY)) {
				return true;
			}
		}
		return false;
	}

	// This will return null if there is no dwarf at the location you selected
	// DUH
	public Unit getUnitByLocation(int modifedX, int modifedY) {
		Unit unit;
		for (int u = 0; u < units.size(); u++) {
			// only leave loop if there is unit
			unit = getUnitByIndex(u).getUnitByLocation(modifedX, modifedY);
			// Brake loop if a unit is returned
			if (unit != null) {
				return unit;
			}
		}
		// If the location you have selected contains no unit return null
		return null;
	}

	public void draw(Graphics g) {
		for (int u = 0; u < units.size(); u++) {
			getUnitByIndex(u).draw(g);
		}
	}

	public void move() {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).move();
		}
	}

	// Saves the Units to a array and can be called by index
	public void save(Unit unit) {
		units.add(unit);
	}
}

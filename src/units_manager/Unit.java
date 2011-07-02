package units_manager;

import environment_manager.Map;
import environment_manager.Tiles;
import application_controller.*;

public class Unit extends Mover {

	// On creation set the units Location this will be based off of mouseX and mouseY
	// It will round off and put it square in the center of the tile for you.
	public Unit(String unitClass, Map map, ImagesLoader imgLoader, int mouseX, int mouseY){
		this.map = map;
		setup(imgLoader, unitClass, mouseX, mouseY);
		// After setup we need to set the units speed and anything else relevant to the unit
		unitSetup();
		// There is current only one unit type and it is a dwarf sad
		setImage(unitClass);
	}
	
	private void setup(ImagesLoader imgLoader, String unitClass, int mouseX, int mouseY){
		// Unit Current Location it is set center of a tile. After that it does not mater
		// were the unit stops. It can stop on the moon if it wants.
		locY = mouseY;
		locX = mouseX;
		this.imgLoader = imgLoader;
		this.unitClass = unitClass;
	}
	
	private void unitSetup() {
		// This data will need to be set in some sort of binary files
		if (unitClass == "dwarf") {
			movementSpeed = 10;
		}
		

	}
	
	public Unit getUnitByLocation(int mouseX, int mouseY){
		if (isUnit(mouseX, mouseY)){
			return this;
		}else{
			return null;
		}
	}
	
	public int getX(){
		return locX;
	}
	
	public int getY(){
		return locY;
	}
	
	// This is a very fast simple way to create a hit box on object. Need to name it better
	public boolean isUnit(int mouseX, int mouseY){
		
		if (mouseX >= locX && mouseX <= (Tiles.SIZE + locX)
				&& mouseY >= locY && mouseY <= (Tiles.SIZE + locY)){
			return true;
		}else{
			return false;
		}
	}
}

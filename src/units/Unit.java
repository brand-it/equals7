package units;

import application.*;

public class Unit extends Mover {

	// On creation set the units Location
	public Unit(Map map, ImagesLoader imgLoader, int mouseX, int mouseY){
		grid = new Grid();
		this.map = map;
		// Unit Current Location it is set center of a tile. After that it does not mater
		// were the unit stops. It can stop on the moon if it wants.
		locY = grid.locationY(grid.getTileY(mouseY - View.viewLocY));
		locX = grid.locationX(grid.getTileX(mouseX - View.viewLocX));
		this.imgLoader = imgLoader;
		// There is current only one unit type and it is a dwarf sad
		setImage("dwarf");
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
		int viewX = locX + View.viewLocX;
		int viewY = locY + View.viewLocY;
		
		if (mouseX >= viewX && mouseX <= (width + viewX)
				&& mouseY >= viewY && mouseY <= (height + viewY)){
			return true;
		}else{
			System.out.println("FALSE");
			return false;
			
		}
	}
}

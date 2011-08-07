package environment_manager;

public class Tile extends Tiles {
	private int orentation = 0;
	private int element = 0;
	private Zone zone;
	
	public boolean isFloor(){
		return this.isFloor(element);
	}
	
	public boolean isWall(){
		return this.isWall(element);
	}
	
	public void setOrentation(int orentation){
		this.orentation = orentation;
	}
	
	public void changeElement(int element){
		this.element = element;
	}

	public Tile(int element) {
		this.element = element;

	}

	protected int getElement() {
		return element;
	}

	protected int getOrentation() {
		return orentation;
	}
	
	public boolean isZoneless(){
		if (zone == null){
			return true;
		}
		return false;
	}
	
	public Zone getZone(){
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
		
	}
}

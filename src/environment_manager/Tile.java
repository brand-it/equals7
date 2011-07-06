package environment_manager;


public class Tile extends Tiles {
	protected int orentation = 0;
	protected int element = 0;
	
	public Tile(int element){
		this.element = element;
		
	}
	
	protected int getElement(){
		return element;
	}
	protected int getOrentation(){
		return orentation;
	}
	
	protected boolean isWall(){
		if (element < 4) {
			return true;
		} else {
			return false;
		}
	}
}

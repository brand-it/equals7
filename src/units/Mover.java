package units;

import java.util.ArrayList;

import application.*;

public class Mover extends UnitRender{
	
	protected Grid grid;
	protected ArrayList<Path> paths = new ArrayList<Path>();
	protected Map map;
	
	public Mover(){
	}
	
	
	public void savePath(Path path){
		paths.add(path);
	}
	
	
	
	public void move(){
		for (int p = 0; p < paths.size(); p++){
			Path path = paths.get(p);
			for (int i = 0; i < paths.get(0).getLength(); i++){
				locX = path.getStep(i).getX();
				locY = path.getStep(i).getY();
			}
		}

	}
}

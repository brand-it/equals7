package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
@SuppressWarnings("rawtypes")
public class Dwarfs {
	
	protected static int STEPS = 7;
	
	protected BufferedImage image;
	protected Grid grid;
	protected ImagesLoader imsLoader;
	protected int selectedDwarf = 0;
	protected int[][] dwarfsIDs = new int[Map.WIDTH][Map.HEIGHT];

	private ArrayList dwarfsObjects = new ArrayList();

	public Dwarfs(ImagesLoader imsL) {
		grid = new Grid();
		imsLoader = imsL;
		setImages("dwarf");
	}

	@SuppressWarnings("unchecked")
	protected void saveDwarf(Dwarf dwarf) {
		
		dwarfsObjects.add(dwarf);
		dwarfsIDs[dwarf.locX][dwarf.locY] = dwarfsObjects.size() - 1;
	}

	protected void setImages(String name)
	// assign the name image to the sprite
	{
		image = imsLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}

	} // end of setImage()

	public void draw(Graphics g) {
		if (dwarfsObjects.size() > 0) {
			for (int d = 0; d < dwarfsObjects.size(); d++) {
				g.drawImage(image, grid.locationX(getDwarfByIndex(d).locX),
						grid.locationY(getDwarfByIndex(d).locY), null);
			}
		}
	}
	
	protected Dwarf getDwarfByIndex(int index){
		 return (Dwarf) dwarfsObjects.get(index);
		
	}
	
	protected Dwarf getDwarfByMouse(int mouseX, int mouseY){
		int index = dwarfsIDs[grid.getTileX(mouseX)][grid.getTileY(mouseY)];
		
		Dwarf dwarf = (Dwarf) dwarfsObjects.get(index);
		if (dwarf.locX == grid.getTileX(mouseX) && dwarf.locY == grid.getTileY(mouseY)){
			return dwarf;
		} else {
			return null;
		}

	}
	
	protected boolean isDwarf(int mouseX, int mouseY){
		Dwarf dwarf = getDwarfByMouse(mouseX, mouseY);
		if (dwarf != null){
			System.out.println("This is a Dwarf");
			return true;
		}else{
			System.out.println("This is not a Dwarf");
			return false;
		}
	}
	
	public void move() {
		for (int i = 0; i < dwarfsObjects.size(); i++){
			Dwarf dwarf = getDwarfByIndex(i);
			dwarf.move();
		}
	}

	public class Dwarf {
		protected int updates = 0;
		protected int locX;
		protected int locY;
		private Path path;
		private int indexPath = 0;
		
		public void path(Path path){
			 this.path = path;
			 indexPath = 0;
			 System.out.println("moving dwarf");
		}

		public Dwarf(int mouseX, int mouseY) {
			locX = grid.getTileX(mouseX);
			locY = grid.getTileY(mouseY);
		}
		
		private void moveDwarf(){
			if (path.getLength() > indexPath){
				int dwarfID = dwarfsIDs[locX][locY];
				dwarfsIDs[locX][locY] = 0;
				locX = path.getX(indexPath);
				locY = path.getY(indexPath);
				dwarfsIDs[locX][locY] = dwarfID;
			} else {
				path = null;
				indexPath = 0;
				
			}

		}
		
		private void nextIndex(){
			indexPath++;
		}

		public void move() {
			
			if (path != null){
				if (updates == STEPS) {
					moveDwarf();
					nextIndex();
					updates = 0;
				} else {
					updates++;
				}

			}
		}
	}
}

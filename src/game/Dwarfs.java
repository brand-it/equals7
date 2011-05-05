package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Dwarfs {
	protected BufferedImage image;
	protected Grid grid;
	protected ImagesLoader imsLoader;
	protected int selectedDwarf = 0;
	private int isDwarfID;
	private int totalDwarfs = 0;
	private GameInterface gameInterface;
	
	HashMap<Integer, Dwarf> dwarfHash;
	

	@SuppressWarnings("unused")
	protected boolean isDwarf(int mouseX, int mouseY){
		for(int d = 0; d < totalDwarfs; d++){
			if (getDwarf(d).locX == grid.getTileX(mouseX) && getDwarf(d).locY == grid.getTileX(mouseY)){
				isDwarfID = d;
				System.out.println("Success");
				return true;
			}
		}
		System.out.println("Failed For Loop");
		return false;
		
	}
	public Dwarfs(ImagesLoader imsL){
		grid = new Grid();
		imsLoader = imsL;
		dwarfHash = new HashMap<Integer, Dwarf>();
		
		setImages("dwarf");
	}

	protected void saveDwarf(Dwarf dwarf){
		int dwarfID = totalDwarfs;
		dwarfHash.put(dwarfID, dwarf);
		totalDwarfs++;
	}
	protected Dwarf getDwarf(int dwarfID){
		if(dwarfID <= totalDwarfs){
			return dwarfHash.get(dwarfID);
		} else {
			return null;
		}
	
	}
	
	protected void selectDwarf(int mouseX, int mouseY){
		if (isDwarf(mouseX, mouseY)){
			selectedDwarf = isDwarfID;
			System.out.println("SelectedDwarf: " + selectedDwarf);
			System.out.println("Dwarf: " + getDwarf(selectedDwarf));
		}else{
			selectedDwarf = 0;
		}
		
	}
	
	protected void setImages(String name)
	// assign the name image to the sprite
	{
		image = imsLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}
		
	} // end of setImage()
	
	private void highlightDwarf(Graphics g) {
		if (getDwarf(selectedDwarf) != null){
			int x = getDwarf(selectedDwarf).locX;
			int y = getDwarf(selectedDwarf).locY;
			System.out.println("X, Y: " + x + ", " + y);
//			gameInterface.selectObject(g, x, y);
		}

		

	}
	
	public void draw(Graphics g){
		if (totalDwarfs > 0){
			for(int d = 0; d < totalDwarfs; d++){
				g.drawImage(image, grid.locationX(getDwarf(d).locX), grid.locationY(getDwarf(d).locY), null);
			}
			highlightDwarf(g);
		}
	}



	public class Dwarf{
		private int locX;
		private int locY;

		public Dwarf(int x, int y){
			locX = grid.getTileX(x);
			locY = grid.getTileY(y);
			
		}
		public void moveUp(){
			locY -= 1;
		}
		

	}


}

package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Dwarfs {
	protected BufferedImage image;
	protected Grid grid;
	protected ImagesLoader imsLoader;
	protected int count = 0;
	HashMap<Integer, Dwarf> dwarfHash;
	public Dwarfs(ImagesLoader imsL){
		grid = new Grid();
		imsLoader = imsL;
		dwarfHash = new HashMap<Integer, Dwarf>();
		
		setImages("dwarf");
	}

	protected void saveDwarf(Dwarf dwarf){
		dwarfHash.put(count, dwarf);
	}

	protected void setImages(String name)
	// assign the name image to the sprite
	{
		image = imsLoader.getImage(name);
		if (image == null) { // no image of that name was found
			System.out.println("No sprite image for " + name);
		}
		
	} // end of setImage()
	
	public void draw(Graphics g){
		for(int d = 1; d <= count; d++){
			g.drawImage(image, grid.locationX(dwarfHash.get(d).locX), grid.locationY(dwarfHash.get(d).locY), null);
		}
	}

	public class Dwarf{
		private int locX;
		private int locY;

		public Dwarf(int x, int y){
			locX = grid.getTileX(x);
			locY = grid.getTileY(y);
			count += 1;
		}
		public void moveUp(){
			locY -= 1;
		}
		

	}


}

package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Dwarfs {
	protected BufferedImage image;
	protected Grid grid;
	protected ImagesLoader imsLoader;
	protected int selectedDwarf = 0;
	private int totalDwarfs = 0;
	protected int unitsType[][] = new int[Map.HEIGHT][Map.WIDTH];
	protected int unitsID[][] = new int[Map.HEIGHT][Map.WIDTH];

	HashMap<Integer, Dwarf> dwarfHash;

	public Dwarfs(ImagesLoader imsL) {
		grid = new Grid();
		imsLoader = imsL;
		dwarfHash = new HashMap<Integer, Dwarf>();
		setImages("dwarf");
	}

	protected void saveDwarf(Dwarf dwarf) {
		int dwarfID = totalDwarfs;
		dwarfHash.put(dwarfID, dwarf);
		unitsID[dwarf.locY][dwarf.locX] = dwarfID;
		totalDwarfs++;
	}

	protected Dwarf getDwarfHash(int dwarfID) {
		if (dwarfID <= totalDwarfs) {
			return dwarfHash.get(dwarfID);
		} else {
			return null;
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

	public void draw(Graphics g) {
		if (totalDwarfs > 0) {
			for (int d = 0; d < totalDwarfs; d++) {
				g.drawImage(image, grid.locationX(getDwarfHash(d).locX),
						grid.locationY(getDwarfHash(d).locY), null);
			}
		}
	}

	protected int getDwarf(int mouseX, int mouseY) {
		return unitsType[grid.getTileY(mouseY)][grid.getTileX(mouseX)];
	}

	protected int getDwarfID(int mouseX, int mouseY) {
		int getDwarf = getDwarf(mouseX, mouseY);
		if (getDwarf != 0) {
			return unitsID[grid.getTileY(mouseY)][grid.getTileX(mouseX)];
		}
		return 0;

	}

	public class Dwarf {
		protected int locX;
		protected int locY;

		public Dwarf(int mouseX, int mouseY) {
			locX = grid.getTileX(mouseX);
			locY = grid.getTileY(mouseY);
			unitsType[locY][locX] = 1;

		}

		public void moveUp() {
			locY -= 1;
		}
	}
}

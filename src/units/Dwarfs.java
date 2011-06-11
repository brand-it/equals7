package units;

import game.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import units.Dwarfs.Dwarf;

@SuppressWarnings("rawtypes")
public class Dwarfs {

	protected static int STEPS = 7;

	protected BufferedImage image;
	protected Grid grid;
	protected ImagesLoader imsLoader;
	protected int selectedDwarf = 0;
	protected int[][] dwarfsIDs = new int[Map.WIDTH][Map.HEIGHT];
	private ArrayList<Dwarf> dwarfsObjects = new ArrayList<Dwarf>();

	public Dwarfs(ImagesLoader imsL, Grid grid) {
		this.grid = grid;
		imsLoader = imsL;
		setImages("dwarf");
	}
	
	public void saveDwarf(Dwarf dwarf) {
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
				Dwarf dwarf = getDwarfByIndex(d);
				g.drawImage(image, dwarf.smoothXLoc(), dwarf.smoothYLoc(), null);
			}
		}
	}

	protected Dwarf getDwarfByIndex(int index) {
		return (Dwarf) dwarfsObjects.get(index);

	}

	public Dwarf getDwarfByMouse(int mouseX, int mouseY) {
		int index = dwarfsIDs[grid.getTileXByView(mouseX)][grid
				.getTileYByView(mouseY)];

		Dwarf dwarf = (Dwarf) dwarfsObjects.get(index);
		if (dwarf.locX == grid.getTileXByView(mouseX)
				&& dwarf.locY == grid.getTileYByView(mouseY)) {
			return dwarf;
		} else {
			return null;
		}

	}

	public boolean isDwarf(int mouseX, int mouseY) {
		Dwarf dwarf = getDwarfByMouse(mouseX, mouseY);
		if (dwarf != null) {
			return true;
		} else {
			return false;
		}
	}

	public void move() {
		for (int i = 0; i < dwarfsObjects.size(); i++) {
			Dwarf dwarf = getDwarfByIndex(i);
			dwarf.move();
		}
	}

	public class Dwarf {
		protected int updates = 0;
		public int locX;
		public int locY;
		private Path path;
		private int indexPath = 0;
		private int nextX;
		private int nextY;

		public void path(Path path) {
			this.path = path;
			indexPath = 0;
		}

		public Dwarf(int mouseX, int mouseY) {
			locX = grid.getTileX(mouseX);
			locY = grid.getTileY(mouseY);
		}

		private void moveDwarf() {
			if (path.getLength() > indexPath) {
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

		private void nextIndex() {
			indexPath += 1;
		}

		private int getNextIndex() {
			int result = indexPath;
			if (path != null) {
				if (path.getLength() > (indexPath + 1)) {
					result++;
				}
			}
			return result;
		}

		public int getPathX(int index) {
			return path.getX(index);
		}

		public void setPathX(int index) {
			nextX = path.getX(index);
		}

		public void setPathY(int index) {
			nextY = path.getY(index);
		}

		public int getPathY(int index) {
			return path.getY(index);
		}

		public boolean validIndex(int index) {
			if (path != null) {
				if (path.getLength() > index) {
					return true;
				}
			}
			return false;
		}

		public int smoothXLoc() {
			int result = grid.locationXByView(locX);
			double percent = ((float) updates / (float) STEPS);

			int nextIndex = getNextIndex();
			if (validIndex(nextIndex)) {

				setPathX(nextIndex);

				if (nextX > locX) {
					result = (int) ((percent * Grid.TILE_SIZE) + grid
							.locationXByView(locX));
				} else if (nextX < locX) {
					result = (int) (grid.locationXByView(locX) - (percent * Grid.TILE_SIZE));
				}
			}
			return result;
		}

		public int smoothYLoc() {
			int result = grid.locationYByView(locY);
			double percent = ((float) updates / (float) STEPS);

			int nextIndex = getNextIndex();
			if (validIndex(nextIndex)) {

				setPathY(nextIndex);

				if (nextY > locY) {
					result = (int) ((percent * Grid.TILE_SIZE) + grid
							.locationYByView(locY));

				} else if (nextY < locY) {
					result = (int) (grid.locationYByView(locY) - (percent * Grid.TILE_SIZE));
				}
			}
			return result;
		}

		public void move() {
			if (path != null) {
				if (updates == STEPS) {
					nextIndex();
					moveDwarf();
					updates = 0;
				} else {
					updates++;
				}

			}
		}
	}
}

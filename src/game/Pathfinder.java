package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

public class Pathfinder implements Runnable{

	private Grid grid;
	private Map map;
	private int localX;
	private int localY;
	private int nextMoveX;
	private int nextMoveY;
	private static final int STEPS = 5;
	private int updates = 0;
	private int[][] passableTerrain;
	/** The set of nodes that have been searched through */
	private ArrayList closed = new ArrayList ();
	/** The set of nodes that we do not yet consider fully searched */
	private SortedList open = new SortedList ();


	public Pathfinder(Map map) {
		ImagesLoader img = new ImagesLoader();
		this.map = map;
		grid = new Grid();

		localX = 2;
		localY = 2;

	}

	public void findPath(int mouseX, int mouseY){
		int endTileX = grid.getTileX(mouseX);
		int endTileY = grid.getTileY(mouseY);
		int nextStepX = localX;
		int nextStepY = localY;
		int dx;
		int dy;
		int manhattanDistance;
		double euclidianDistance;
		double fValue;
				
	    dx = endTileX - nextStepX;
	    dy = endTileY - nextStepY;
	    euclidianDistance = Math.sqrt((dx*dx)+(dy*dy));
	    
	    dx = Math.abs(endTileX - nextStepX);
	    dy = Math.abs(endTileY - nextStepY);
	    manhattanDistance = dx+dy;
	    
	    fValue = euclidianDistance + manhattanDistance;
	    
	    
}
		
	public void move() {
		if (updates == STEPS) {
			localX = nextMoveX;
			localY = nextMoveY;
			updates = 0;

		} else {
			updates++;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.green);
		int x = grid.locationX(localX);
		int y = grid.locationY(localY);
		g.drawRect(x, y, Grid.TILE_SIZE, Grid.TILE_SIZE);
		g.fillRect(x, y, Grid.TILE_SIZE, Grid.TILE_SIZE);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	private class SortedList {
		/** The list of elements */
		private ArrayList list = new ArrayList();
		
		/**
		 * Retrieve the first element from the list
		 *  
		 * @return The first element from the list
		 */
		public Object first() {
			return list.get(0);
		}
		
		/**
		 * Empty the list
		 */
		public void clear() {
			list.clear();
		}
		
		/**
		 * Add an element to the list - causes sorting
		 * 
		 * @param o The element to add
		 */
		public void add(Object o) {
			list.add(o);
			Collections.sort(list);
		}
		
		/**
		 * Remove an element from the list
		 * 
		 * @param o The element to remove
		 */
		public void remove(Object o) {
			list.remove(o);
		}
	
		/**
		 * Get the number of elements in the list
		 * 
		 * @return The number of element in the list
 		 */
		public int size() {
			return list.size();
		}
		
		/**
		 * Check if an element is in the list
		 * 
		 * @param o The element to search for
		 * @return True if the element is in the list
		 */
		public boolean contains(Object o) {
			return list.contains(o);
		}
	}
}

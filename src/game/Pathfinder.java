package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

public class Pathfinder {

	private Grid grid;
	private Map map;
	private int locationX;
	private int locationY;
	private static final int STEPS = 5;
	private int updates = 0;
	private Node[][] nodes;
	/** The set of nodes that have been searched through */
	private ArrayList closed = new ArrayList();
	/** The set of nodes that we do not yet consider fully searched */
	private SortedList open = new SortedList();

	public Pathfinder(Map map) {
		ImagesLoader img = new ImagesLoader();
		grid = new Grid();
		locationX = 0;
		locationY = 0;
		
		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x=0;x<map.getWidthInTiles();x++) {
			for (int y=0;y<map.getHeightInTiles();y++) {
				nodes[x][y] = new Node(x,y);
			}
		}
	}
	
	public int manhattanCalculation(int x, int y){
		int manDistanceX = Math.abs(x - locationX);
		int manDistanceY = Math.abs(y - locationY);
		
		return manDistanceX + manDistanceY;
		
	}

	public void findPath(int mouseX, int mouseY) {
		
		int[] lookAbout = null;
		
		int endTileX = grid.getTileX(mouseX);
		int endTileY = grid.getTileY(mouseY);
		int currentX = locationX;
		int currentY = locationY;
		
		closed.clear();
		open.clear();
		
		int manhattan = manhattanCalculation(currentX, currentY);
		
		int eucDistanceX = Math.abs(currentX - endTileX);
		int eucDistanceY = Math.abs(currentY - endTileY);

		float euclidian = (float) Math.sqrt((Math.pow(eucDistanceX, 2) + Math.pow(eucDistanceY, 2)));
		double fValue = manhattan + euclidian;
		
		closed.add(nodes[currentX][currentY]);
		
		// need to make while loop based on. current x and y == your end tiles x
		while(open.size() != 0){
			// Basically We don't loop threw the array top to bottom we just keep pulling the one on top.
			Node current = getFirstInOpen();
			if (current == nodes[currentX][currentY]) {
				break;
			}
						int manDistanceX = Math.abs(x - locationX);
						int manDistanceY = Math.abs(y - locationY);
						
						eucDistanceX = Math.abs(x - endTileX);
						eucDistanceY = Math.abs(y - endTileY);
						
						manhattan = manDistanceX + manDistanceY;
						euclidian = (float) Math.sqrt((Math.pow(eucDistanceX, 2) + Math.pow(eucDistanceY, 2)));
						fValue = manhattan + euclidian;
						
						// Ok we are just going to add the nodes around self
					      addNode (N, N.x+1, N.y);
					      addNode (N, N.x-1, N.y);
					      addNode (N, N.x, N.y+1);
					      addNode (N, N.x, N.y-1);
					      open.add
						open.add(nodes[x][y]);
			}
		}	

	public void move() {
		if (updates == STEPS) {
			updates = 0;

		} else {
			updates++;
		}
	}
	
	protected Node getFirstInOpen() {
		return (Node) open.first();
	}

	public void draw(Graphics g) {
		g.setColor(Color.green);
		int x = grid.locationX(locationX);
		int y = grid.locationY(locationY);
		g.drawRect(x, y, Grid.TILE_SIZE, Grid.TILE_SIZE);
		g.fillRect(x, y, Grid.TILE_SIZE, Grid.TILE_SIZE);
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
		 * @param o
		 *            The element to add
		 */
		public void add(Object o) {
			list.add(o);
			Collections.sort(list);
		}

		/**
		 * Remove an element from the list
		 * 
		 * @param o
		 *            The element to remove
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
		 * @param o
		 *            The element to search for
		 * @return True if the element is in the list
		 */
		public boolean contains(Object o) {
			return list.contains(o);
		}
	}
	
	
	/**
	 * A single node in the search graph
	 */
	private class Node implements Comparable<Object> {
		/** The x coordinate of the node */
		private int x;
		/** The y coordinate of the node */
		private int y;
		/** The path cost for this node */
		private float cost;
		/** The parent of this node, how we reached it in the search */
		private Node parent;
		/** The heuristic cost of this node */
		private float heuristic;
		/** The search depth of this node */
		private int depth;
		
		/**
		 * Create a new node
		 * 
		 * @param x The x coordinate of the node
		 * @param y The y coordinate of the node
		 */
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Set the parent of this node
		 * 
		 * @param parent The parent node which lead us to this node
		 * @return The depth we have no reached in searching
		 */
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			
			return depth;
		}
		
		/**
		 * @see Comparable#compareTo(Object)
		 */
		public int compareTo(Object other) {
			Node o = (Node) other;
			
			float f = heuristic + cost;
			float of = o.heuristic + o.cost;
			
			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}

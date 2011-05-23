package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

public class Pathfinder {

	private Grid grid;
	private Map map;
	private int startLocationX;
	private int startLocationY;
	private int destinationX;
	private int destinationY;
	private Node[][] nodes;
	/** The set of nodes that have been searched through */
	private ArrayList closed = new ArrayList();
	/** The set of nodes that we do not yet consider fully searched */
	private SortedList open = new SortedList();

	public Pathfinder(Map map) {
		ImagesLoader img = new ImagesLoader();
		grid = new Grid();
		this.map = map;
		startLocationX = 0;
		startLocationY = 0;

		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x = 0; x < map.getWidthInTiles(); x++) {
			for (int y = 0; y < map.getHeightInTiles(); y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
	}

	public Path findPath(int x, int y, int mouseX, int mouseY) {

		destinationX = grid.getTileX(mouseX);
		destinationY = grid.getTileY(mouseY);
		
		startLocationX = x;
		startLocationY = y;

		closed.clear();
		open.clear();

		int moves = 0;

		fValueNeighbours(nodes[startLocationX][startLocationY]);
		open.remove(nodes[startLocationX][startLocationY]);
		closed.add(nodes[startLocationX][startLocationY]);

		// need to make while loop based on. current x and y == your end tiles x
		int maxDepth = 0;
		while ((open.size() != 0)) {
			moves += 1;
			// Basically We don't loop threw the array top to bottom we just
			// keep pulling the one on top.
			Node current = getFirstInOpen();
			if (current == nodes[destinationX][destinationY]) {
				break;
			}

			closed.add(current);
			open.remove(current);
			fValueNeighbours(current);
			maxDepth++;

		}
		
		if (nodes[destinationX][destinationY].parent == null) {
			return null;
		}
		
		Path path = new Path();
		Node target = nodes[destinationX][destinationY];
		while (target != nodes[startLocationX][startLocationY]) {
			path.prependStep(target.x, target.y);
			target = target.parent;
		}
		path.prependStep(startLocationX,startLocationY);
		
		// thats it, we have our path 
		return path;
		
	}

	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}

	public void fValueNeighbours(Node current) {
		Node node;
		int up = current.y + 1;
		int down = current.y - 1;
		int right = current.x + 1;
		int left = current.x - 1;
		if (Map.HEIGHT >= up) {
			node = nodes[current.x][up];
			if (!isBlocked(current.x, up) && !inClosedList(node)) {
				node.cost = fValue(node);
				node.setParent(current);
				open.add(node);
			}
		}
		if (down >= 0) {
			node = nodes[current.x][down];
			if (!isBlocked(current.x, down) && !inClosedList(node)) {
				node.cost = fValue(node);
				node.setParent(current);
				open.add(node);
				
			}

		}

		if (Map.WIDTH >= right) {
			node = nodes[right][current.y];
			if (!isBlocked(right, current.y) && !inClosedList(node)) {
				node.cost = fValue(node);
				node.setParent(current);
				open.add(node);
				
			}

		}

		if (left >= 0) {
			node = nodes[left][current.y];
			if (!isBlocked(left, current.y) && !inClosedList(node)) {
				node.cost = fValue(node);
				node.setParent(current);
				open.add(node);
			}
		}
	}

	public boolean isBlocked(int x, int y) {
		return map.isWall(map.returnElement(x, y));
	}

	public float euclidianCalculation(int tx, int ty, int x, int y) {
		// Euclidian Calculation. Slower but much more accurate.

		int eucDistanceX = Math.abs(x - tx);
		int eucDistanceY = Math.abs(y - ty);

		return (float) Math
				.sqrt(((eucDistanceX * eucDistanceX) + (eucDistanceY * eucDistanceY)));

	}
	
	public int manhattanCalculation(int sx, int sy, int x, int y) {
		// Manhattan Calculation. Slower but much more accurate.

		int manDistanceX = Math.abs(x - sx);
		int manDistanceY = Math.abs(y - sy);

		return (manDistanceX + manDistanceY);

	}

	public float fValue(Node current) {
		// int manhattan = manhattanCalculation(node.x, node.y);
		float euclidian = euclidianCalculation(destinationX, destinationY,
				current.x, current.y);
		int manhattan = manhattanCalculation(startLocationX, startLocationY, current.x, current.y);
		return euclidian + manhattan;
	}

	public void addNode(int x, int y) {
		open.add(nodes[x][y]);
	}

	protected Node getFirstInOpen() {
		return (Node) open.first();
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		int x = grid.locationX(startLocationX);
		int y = grid.locationY(startLocationY);
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
		private double cost;
		/** The parent of this node, how we reached it in the search */
		private Node parent;
		/** The heuristic cost of this node */
		private float heuristic;
		/** The search depth of this node */
		private int depth;

		/**
		 * Create a new node
		 * 
		 * @param x
		 *            The x coordinate of the node
		 * @param y
		 *            The y coordinate of the node
		 */
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Set the parent of this node
		 * 
		 * @param parent
		 *            The parent node which lead us to this node
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

			double f = heuristic + cost;
			double of = o.heuristic + o.cost;

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

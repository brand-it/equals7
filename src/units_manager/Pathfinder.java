package units_manager;

import java.util.ArrayList;
import java.util.Collections;

import environment_manager.Map;

import application_controller.ApplicationData;

public class Pathfinder {
	private Map map = ApplicationData.map;
	private int startLocationX;
	private int startLocationY;
	private int destinationX;
	private int destinationY;
	private Node[][] nodes = new Node[map.getWidthInTiles()][map
			.getHeightInTiles()];
	/** The set of nodes that have been searched through */
	@SuppressWarnings("rawtypes")
	private ArrayList closed = new ArrayList();
	/** The set of nodes that we do not yet consider fully searched */
	private SortedList open = new SortedList();

	@SuppressWarnings("unchecked")
	public Path findPath(int startModifiedX, int startModifiedY, int modifiedX,
			int modifiedY) {

		destinationX = modifiedX;
		destinationY = modifiedY;

		startLocationX = startModifiedX;
		startLocationY = startModifiedY;
		// You need to add these nodes to the system if you want it to work
		// right.
		nodes[startLocationX][startLocationY] = new Node(startLocationX,
				startLocationY);
		nodes[destinationX][destinationY] = new Node(destinationX, destinationY);

		closed.clear();
		open.clear();

		fValueNeighbours(nodes[startLocationX][startLocationY]);
		open.remove(nodes[startLocationX][startLocationY]);
		closed.add(nodes[startLocationX][startLocationY]);

		int maxloops = 0;// need to make while loop based on. current x and y ==
							// your end tiles x
		while (open.size() != 0) {
			maxloops++; // Basically We don't loop threw the array top to bottom
						// we just
			// keep pulling the one on top.
			Node current = getFirstInOpen();
			if (current == nodes[destinationX][destinationY]) {
				System.out.println("The system took a total of " + maxloops
						+ " loops.");
				break;
			}
			open.remove(current);
			closed.add(current);

			fValueNeighbours(current);

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
		path.prependStep(startLocationX, startLocationY);

		// thats it, we have our path
		return path;

	}

	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}

	public void addNextNodes() {
	}

	public void fValueNeighbours(Node current) {

		Node node = null;
		int up = map.moveUp(current.y);
		int down = map.moveDown(current.y);
		int right = map.moveRight(current.x);
		int left = map.moveLeft(current.x);

		node = getNode(current.x, up);
		if (!isBlocked(current.x, up) && !inClosedList(node)
				&& !inOpenList(node)) {
			node.increaseDistance(current);
			node.cost = fValue(node);
			node.setParent(current);
			open.add(node);
		}

		node = getNode(current.x, down);
		if (!isBlocked(current.x, down) && !inClosedList(node)
				&& !inOpenList(node)) {
			node.increaseDistance(current);
			node.cost = fValue(node);
			node.setParent(current);
			open.add(node);

		}
		node = getNode(right, current.y);
		if (!isBlocked(right, current.y) && !inClosedList(node)
				&& !inOpenList(node)) {
			node.increaseDistance(current);
			node.cost = fValue(node);
			node.setParent(current);
			open.add(node);

		}

		node = getNode(left, current.y);
		if (!isBlocked(left, current.y) && !inClosedList(node)
				&& !inOpenList(node)) {
			node.increaseDistance(current);
			node.cost = fValue(node);
			node.setParent(current);
			open.add(node);
		}
	}

	protected Node getNode(int x, int y) {
		Node node;
		if (nodes[x][y] == null) {
			node = nodes[x][y] = new Node(x, y);
		} else {
			node = nodes[x][y];
		}
		return node;
	}

	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}

	public boolean isBlocked(int x, int y) {
		return ApplicationData.map.isBlocked(x, y);
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
		// int manhattan = manhattanCalculation(startLocationX, startLocationY,
		// current.x, current.y);
		return (float) (current.distance + euclidian);
	}

	public void addNode(int x, int y) {
		open.add(nodes[x][y]);
	}

	protected Node getFirstInOpen() {
		return (Node) open.first();
	}

	private class SortedList {
		/** The list of elements */
		@SuppressWarnings("rawtypes")
		private ArrayList list = new ArrayList();

		/**
		 * Retrieve the first element from the list
		 * 
		 * @return The first element from the list
		 */
		public Object first() {
			return list.get(0);
		}

		@SuppressWarnings("unused")
		public Object get(int index) {
			return list.get(index);
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
		@SuppressWarnings("unchecked")
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
		private double distance = 0;

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

		public void increaseDistance(Node node) {
			this.distance = node.distance + 0.6;
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

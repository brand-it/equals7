package environment_manager;

import java.util.ArrayList;
import application_controller.ApplicationData;

/*Basically this is the path finder but a lot of the complicated code has been taken out. 
 This end result will make the zone system much faster then the path finder
 and end result will make the path finder even faster at what it does.
 */

public class ZoneBuilder {

	private Map map = ApplicationData.map;
	private Node[][] nodes = new Node[map.getWidthInTiles()][map
			.getHeightInTiles()];
	/** The set of nodes that have been searched through */
	@SuppressWarnings("rawtypes")
	private ArrayList closed = new ArrayList();
	/** The set of nodes that we do not yet consider fully searched */
	private ArrayList<Node> open = new ArrayList<Node>();

	public ZoneBuilder(int startX, int startY, int zoneNumber) {
		buildZones(startX, startY, zoneNumber);
	}

	@SuppressWarnings("unchecked")
	public void buildZones(int startX, int startY, int zoneNumber) {
		Zone zone = new Zone(zoneNumber);

		closed.clear();
		open.clear();

		Node startNode = new Node(startX, startY);
		addNodes(startNode);
		Tile tile = ApplicationData.map.getTile(startX, startY);
		tile.setZone(zone);

		while (open.size() != 0) {
			Node current = getFirstInOpen();
			tile = ApplicationData.map.getTile(current.x, current.y);
			addNodes(current);
			tile.setZone(zone);
			open.remove(current);
			closed.add(current);

		}
	}

	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}

	public void addNodes(Node current) {
		// This is the setup for the node
		Node node;
		int up = map.moveUp(current.y);
		int down = map.moveDown(current.y);
		int right = map.moveRight(current.x);
		int left = map.moveLeft(current.x);

		node = getNode(current.x, up);
		addToOpen(node);

		node = getNode(current.x, down);
		addToOpen(node);

		node = getNode(right, current.y);
		addToOpen(node);

		node = getNode(left, current.y);
		addToOpen(node);
	}

	private void addToOpen(Node node) {
		if (!isBlocked(node) && !inClosedList(node) && !inOpenList(node)) {
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

	public boolean isBlocked(Node node) {
		return ApplicationData.map.isBlocked(node.x, node.y);
	}

	public void addNode(int x, int y) {
		open.add(nodes[x][y]);
	}

	protected Node getFirstInOpen() {
		return (Node) open.get(0);
	}

	/**
	 * A single node in the search graph
	 */
	private class Node {
		/** The x coordinate of the node */
		private int x;
		/** The y coordinate of the node */
		private int y;

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
	}
}

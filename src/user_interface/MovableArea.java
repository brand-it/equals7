package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import units_manager.Unit;
import environment_manager.Map;
import application_controller.ApplicationData;
import application_controller.View;

public class MovableArea {
	private Map map = ApplicationData.map;
	
	private ArrayList<Node> closed = new ArrayList<Node>();
	private ArrayList<Node> open = new ArrayList<Node>();
	private Node[][] nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
	public ArrayList<Node> movableArea = new ArrayList<Node>();
	private Unit unit;
	
	public ArrayList<Node> buildMoveArea(Unit unit){
		this.unit = unit;
		Node startNode = new Node(unit.getX(), unit.getY(), unit.getX(), unit.getY());
		addNodes(startNode);
		
		while(open.size() != 0){
			Node current = getFirstInOpen();
			addNodes(current);
			movableArea.add(current);
			open.remove(current);
			closed.add(current);
		}
		return movableArea;
	}
	
	protected Node getFirstInOpen() {
		return open.get(0);
	}

	
	private void addNodes(Node current) {
		// This is the setup for the node
		Node node;
		int up = map.moveUp(current.y1);
		int down = map.moveDown(current.y1);
		int right = map.moveRight(current.x1);
		int left = map.moveLeft(current.x1);
		
		node = getNode(current.x1, up);
		addToOpen(node);

		node = getNode(current.x1, down);
		addToOpen(node);
		
		node = getNode(right, current.y1);
		addToOpen(node);
		
		node = getNode(left, current.y1);
		addToOpen(node);
	}
	
	protected Node getNode(int x, int y) {
		Node node;
		if (nodes[x][y] == null) {
			node = nodes[x][y] = new Node(x, y, unit.getX(), unit.getY());
			System.out.println("X, Y " + x + ", " + y);
			System.out.println("Range: " + node.range);
		} else {
			node = nodes[x][y];
		}
		return node;
	}
	
	private void addToOpen(Node node){
		if (!isBlocked(node) && !inClosedList(node)
				&& !inOpenList(node) && node.range <= unit.range){
			open.add(node);
		}
	}
	
	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}
	
	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}

	public boolean isBlocked(Node node) {
		return ApplicationData.map.isBlocked(node.x1, node.y1);
	}
	
	public class Node {
		protected int x1, y1;
		protected double range;
		private float alpha = 0.50f;
		private Color color = new Color(1, 1, 1, alpha);
		private Node parent;
		
		/**
		 * The system uses range to calculate the distance of the node you are trying to go to.
		 * 
		 * If out of range node should not be added the arrar open list.
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 */
		public Node(int x1, int y1, int x2, int y2){
			range(x1, y1, x2, y2);
		}
		
		public void setAlpha(float alpha){
			this.alpha = alpha;
		}

		private void range(int x1, int y1, int x2, int y2){
			range = Math.sqrt((Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
			this.x1 = x1;
			this.y1 = y1;
		}
		
		public double setParent(Node parent) {
			range = parent.range + 1;
			this.parent = parent;
			return range;
		}
		
		public void draw(Graphics g){
			g.setColor(color);
			g.fillRect(View.getActualX(x1), View.getActualY(y1), View.getScale(), View.getScale());
		}
	}
}

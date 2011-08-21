package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import units_manager.Unit;
import application_controller.ApplicationData;
import application_controller.View;
import environment_manager.Map;

public class AttackRange {
	
	private Map map = ApplicationData.map;
	private ArrayList<Node> open = new ArrayList<Node>();
	private ArrayList<Node> attackArea = new ArrayList<Node>();
	private Unit unit;
	private Node[][] nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
	private static final int marginOfError = 500;

	public ArrayList<Node> buildAttackArea(Unit unit) {
		this.unit = unit;
		// this is your core your start location
		Node node = createNode(0, unit.getX(), unit.getY());
		node.setAlpha(0);
		int loopNumber = 0;
		while (open.size() != 0) {
			Node current = getFirstInOpen();
			addNodes(current);
			open.remove(current);
			
			if (marginOfError == loopNumber){
				System.out.println("This loop has lasted to long Exiting.");
				break;
			}
			loopNumber++;
		}
		return attackArea;
	}

	protected Node getFirstInOpen() {
		return open.get(0);
	}

	private void addNodes(Node current) {
		// This will add the four nodes around the target location as long as they have not all ready been added.
		int nextRange = current.getRange() + 1;
		// use these number they will make sure you are not out of bounds
		int up = map.moveUp(current.y1);
		int down = map.moveDown(current.y1);
		int right = map.moveRight(current.x1);
		int left = map.moveLeft(current.x1);
		
		createNode(nextRange, current.getX(), up);

		createNode(nextRange, current.getX(), down);

		createNode(nextRange, left, current.getY());

		createNode(nextRange, right, current.getY());
		
		createNode(nextRange, left, up);

		createNode(nextRange, left, down);

		createNode(nextRange, right, up);

		createNode(nextRange, right, down);
	}

	protected Node createNode(int range, int x, int y) {
		Node node;
	
		if (nodes[x][y] == null) {
			node = nodes[x][y] = new Node(range, x, y);
		} else {
			node = nodes[x][y];
		}
		addToOpen(node);
		return node;
	}

	private void addToOpen(Node node) {
		if (!isBlocked(node) && !inOpenList(node) && node.getRange() <= unit.getAttackRange() && !inMovable(node)) {
			attackArea.add(node);
			open.add(node);
		}
	}
	
	protected boolean inMovable(Node node){
		return attackArea.contains(node);
	}
	
	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}

	public boolean isBlocked(Node node) {
		return map.isBlocked(node.x1, node.y1);
	}

	public class Node {
		private int x1, y1;
		private int attackRange = 0;
		private float alpha = 0.50f;
		private Color color = new Color(1, 0, 0, alpha);

		/**
		 * The system uses range to calculate the distance of the node you are
		 * trying to go to.
		 * 
		 * If out of range node should not be added the arrar open list.
		 * 
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 */
		public Node(int range, int x, int y) {
			x1 = x;
			y1 = y;
			setRange(range);
		}

		public void setAlpha(float alpha) {
			this.alpha = alpha;
		}

		public void setRange(int range) {
			this.attackRange = range;
		}
		
		public int getX(){
			return x1;
		}
		
		public int getY(){
			return y1;
		}
	
		public int getRange(){
			return attackRange;
		}

		public void draw(Graphics g) {
			g.setColor(color);
			g.fillRect(View.getActualX(x1), View.getActualY(y1),
					View.getScale(), View.getScale());
		}
	}
}

package user_interface;

import java.awt.Graphics;
import java.util.ArrayList;
import environment_manager.Map;
import application_controller.ApplicationData;
import units_manager.Pathfinder;
import units_manager.Unit;
import user_interface.MovableArea.Node;

public class Gui {
	/*
	 * This handles all the buttons and the look of the graphics on the
	 * interface. Because it is more complicated then the draw system which just
	 * handles the basics like unit highlight this will handle things like
	 * buttons and information that might matter to the user.
	 * 
	 * This will always be called when the Reaction system is built in the panel
	 * system. oh it is also extended by the draw method
	 */
	protected ActionsMenu actionsMenu;
	
	public ArrayList<Node> moveableArea = new ArrayList<Node>();
	public ArrayList<AttackRange.Node> attackRange = new ArrayList<AttackRange.Node>();

	Map map = ApplicationData.map;
	public Unit selectedUnit;
	protected Pathfinder pathfinder;
	

	public Gui() {
		moveableArea.clear();
	}

	public void clearMoveArea() {
		moveableArea.clear();
	}

	public void buildMoveArea() {
		MovableArea moveableArea = new MovableArea();
		this.moveableArea = moveableArea.buildMoveArea(selectedUnit);
	}
	
	public void clearAttackRange(){
		attackRange.clear();
	}
	
	public void buildAttackRange(){
		AttackRange attackRange = new AttackRange();
		this.attackRange = attackRange.buildAttackArea(selectedUnit);
	}

	public void hovered(int mouseX, int mouseY) {
		actionsMenu.hovered(mouseX, mouseY);
	}
	

	private void drawMoveArea(Graphics g) {
		int loop = 0;
		while (moveableArea.size() > loop) {
			moveableArea.get(loop).draw(g);
			loop++;
		}
	}
	
	private void drawAttackArea(Graphics g){
		int loop = 0;
		while (attackRange.size() > loop) {
			attackRange.get(loop).draw(g);
			loop++;
		}
	}
	
	public void drawAreas(Graphics g){
		drawAttackArea(g);
		drawMoveArea(g);
	}
	
	public void drawMenus(Graphics g){
		actionsMenu.draw(g);
	}
}

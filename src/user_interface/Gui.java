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
	protected Button attack = new Button();
	protected Button move = new Button();

	public ArrayList<Node> movableArea = new ArrayList<Node>();

	Map map = ApplicationData.map;
	protected Unit selectedUnit;
	protected Pathfinder pathfinder;

	public Gui() {
		movableArea.clear();
		move.setBounds(50, 50, 20, 20);
		attack.setBounds(20, 20, 20, 20);
	}

	public void clearMoveArea() {
		movableArea.clear();
	}

	public void buildMoveArea() {
		MovableArea moveableArea = new MovableArea();
		movableArea = moveableArea.buildMoveArea(selectedUnit);
	}

	public void hovered(int mouseX, int mouseY) {
		attack.hovered(mouseX, mouseY);
		move.hovered(mouseX, mouseY);
	}

	public void drawMoveArea(Graphics g) {
		int loop = 0;
		while (movableArea.size() > loop) {
			movableArea.get(loop).draw(g);
			loop++;
		}
	}
	public void drawButtons(Graphics g){
		attack.draw(g);
		move.draw(g);
	}
}

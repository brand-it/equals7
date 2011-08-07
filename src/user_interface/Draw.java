package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import units_manager.Pathfinder;
import units_manager.Unit;
import application_controller.View;

public class Draw extends Gui{
	private static final int MOUSESIZE = 25;
	int locYRollover = 0;
	int locXRollover = 0;
	protected JButton jButton;
	protected Unit selectedUnit;
	protected Pathfinder pathfinder;

	public void highlightUnit(Graphics g) {
		if (selectedUnit != null) {
			g.setColor(Color.red);
			g.drawRect(selectedUnit.getActualX(), selectedUnit.getActualY(),
					View.getScale(), View.getScale());
		}
	}

	public void drawMouse(Graphics g, int mouseX, int mouseY) {
		g.setColor(Color.green);
		g.fillRect(mouseX, mouseY, MOUSESIZE, MOUSESIZE);
	}

	public void drawBox(Graphics g, int mouseX, int mouseY) {
		int adjustmentMouseX = (mouseX / View.getScale()) * View.getScale();
		int adjustmentMouseY = (mouseY / View.getScale()) * View.getScale();

		g.setColor(Color.blue);
		g.drawRect(adjustmentMouseX, adjustmentMouseY, View.getScale(),
				View.getScale());
	}
	
	public void drawPathfinder(Graphics g, int pWidth, int pHeight){
		if (pathfinder != null){
			pathfinder.draw(g, pWidth, pHeight);
		}
		
	}

	public void drawEndLocation(Graphics g) {
		if (selectedUnit != null) {
			if (selectedUnit.ifPath()) {
				g.setColor(Color.orange);
				int adjustmentMouseX = (selectedUnit.getEndX() * View
						.getScale()) - View.getRoundedX();
				int adjustmentMouseY = (selectedUnit.getEndY() * View
						.getScale()) - View.getRoundedY();
				g.drawRect(adjustmentMouseX, adjustmentMouseY, View.getScale(),
						View.getScale());
			}
		}
	}
}

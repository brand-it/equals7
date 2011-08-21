package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import application_controller.View;

public class ActionsMenu {


	private int menuX, menuY;
	private boolean actionsMenuHidden = true;
	private int actionMenuWidth = 100;
	private int actionMenuHeight = 300;

	private Button nextTurn = new Button("Next Turn");
	
	private Reaction reaction;
	
	public ActionsMenu(Reaction reaction){
		this.reaction = reaction;
	}

	public void setActionMenuLocation(int x, int y) {
		int onScreen = x + actionMenuWidth;
		if (onScreen > View.panelWidth()) {
			onScreen = onScreen - View.panelWidth();
			x = x - onScreen;
		}
		onScreen = y + actionMenuHeight;
		if (onScreen > View.panelHeight()) {
			onScreen = onScreen - View.panelHeight();
			y = y - onScreen;
		}
		menuX = x;
		menuY = y;
		updateButtons();

	}

	public void hovered(int mouseX, int mouseY) {
		nextTurn.hovered(mouseX, mouseY);
	}

	public void updateButtons() {
		nextTurn.setBounds(10 + menuX, 50 + menuY, 70, 20);
	}

	public boolean clicked(int mouseX, int mouseY) {

		if (nextTurn.clicked(mouseX, mouseY)) {
			reaction.nextTurn();
			return true;
		}
		return false;
	}

	public void showActionMenu() {
		actionsMenuHidden = false;
	}

	public void hideActionMenu() {
		actionsMenuHidden = true;
	}

	public void draw(Graphics g) {
		if (!actionsMenuHidden) {
			g.setColor(Color.lightGray);
			g.drawRect(menuX, menuY, actionMenuWidth, actionMenuHeight);
			g.fillRect(menuX, menuY, actionMenuWidth, actionMenuHeight);
			nextTurn.draw(g);
		}

	}

}

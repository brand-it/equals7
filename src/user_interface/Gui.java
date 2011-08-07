package user_interface;

import java.awt.Graphics;

public class Gui {
	/*
	 * This handles all the buttons and the look of the graphics on the interface. Because it is more complicated
	 * then the draw system which just handles the basics like unit highlight this will handle things like buttons and
	 * information that might matter to the user.
	 * 
	 * This will always be called when the Reaction system is built in the panel system. oh it is also extended by the draw method
	 */
	protected Button attack = new Button();
	protected Button move = new Button();
	
	
	public Gui(){
		move.setBounds(50, 50, 20, 20);
		attack.setBounds(20, 20, 20, 20);
	}
	
	public void hovered(int mouseX, int mouseY){
		attack.hovered(mouseX, mouseY);
		move.hovered(mouseX, mouseY);
	}
	
	public void drawInteface(Graphics g){
		attack.draw(g);
		move.draw(g);
	}
}

package user_interface;

import java.awt.Graphics;
import java.util.ArrayList;

public class Buttons {

	private ArrayList<CustomButton> buttonObject = new ArrayList<CustomButton>();

	public void save(CustomButton button) {
		buttonObject.add(button);
	}

	public CustomButton getButtonByIndex(int index) {
		return buttonObject.get(index);
	}
	
	public void hovered(int mouseX, int mouseY) {
		if (buttonObject.size() > 0) {
			for (int d = 0; d < buttonObject.size(); d++) {
				CustomButton button = getButtonByIndex(d);
				button.hovered(mouseX, mouseY);
			}
		}
	}
	
	public boolean isButton(int mouseX, int mouseY){
		if (buttonObject.size() > 0) {
			for (int e = 0; e < buttonObject.size(); e++) {
				CustomButton button = getButtonByIndex(e);
				if (button.isButton(mouseX, mouseY)){
					return true;
				}
			}
		}
		return false;
	}
	
	public CustomButton getButtonByLocation(int mouseX, int mouseY){
		if (buttonObject.size() > 0) {
			for (int d = 0; d < buttonObject.size(); d++) {
				CustomButton button = getButtonByIndex(d);
				if (button.isButton(mouseX, mouseY)){
					return button;
				}
			}
		}
		return null;
	}

	public void draw(Graphics g) {
		if (buttonObject.size() > 0) {
			for (int d = 0; d < buttonObject.size(); d++) {
				CustomButton button = getButtonByIndex(d);
				button.draw(g);
			}
		}
	}
}

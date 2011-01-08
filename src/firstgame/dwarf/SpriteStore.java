package firstgame.dwarf;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class SpriteStore {
	
	// This will do everything that the space invaders game does with about 50% less code
	
	private Image sprite;
	
	// We will load the sprite and store it here then call it at some point
	
	public Image getSprite(String ref) {
		
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(ref);
			if (url == null) {
				System.err.println("Unable to find sprite:" + ref);
				System.exit(0);
			}
			sprite = ImageIO.read(url);
		} catch (IOException e){
			System.err.println("Unable to load sprite:" + ref);
			System.exit(0);
		}
		// If every thing goes well we should return the sprite
		return sprite;
	}
}

package equals7;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Sprite {

	private GraphicsConfiguration gc;

	public Sprite() {

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
	}

	public BufferedImage loadImage(String fnm) {

		try {
			URL url = this.getClass().getClassLoader().getResource(fnm);
			BufferedImage im = ImageIO.read(url);

			int transparency = im.getColorModel().getTransparency();
			BufferedImage copy = gc.createCompatibleImage(im.getWidth(),
					im.getHeight(), transparency);
			// create a graphics context
			Graphics2D g2d = copy.createGraphics();

			// copy image
			g2d.drawImage(im, 0, 0, null);
			g2d.dispose();
			return copy;
		} catch (IOException e) {
			System.out.println("Load Image error for " + fnm + ":\n" + e);
			return null;
		}
	}

	public BufferedImage[] loadStripImageArray(String fnm, int numberW,
			int numberH) {
		if (numberH <= 0 || numberW <= 0) {
			System.out.println("number <= 0; returning null");
			return null;
		}

		BufferedImage stripIm;
		if ((stripIm = loadImage(fnm)) == null) {
			System.out.println("Returning null");
			return null;
		}

		int imWidth = stripIm.getWidth() / numberW;
		int imHeight = stripIm.getHeight() / numberH;
		int transparency = stripIm.getColorModel().getTransparency();

		BufferedImage[] strip = new BufferedImage[numberW * numberH];
		Graphics2D stripGC;


		// each BufferedImage from the strip file is stored in strip[]
		int count = 0;
		for (int h = 0; h < numberH; h++) {
			for (int w = 0; w < numberW; w++) {
				strip[count] = gc.createCompatibleImage(imWidth, imHeight,
						transparency);
				// create a graphics context
				stripGC = strip[count].createGraphics();
				// copy images
				stripGC.drawImage(stripIm, 0, 0, imWidth, imHeight,
						w * imWidth, h * imHeight, (w * imWidth) + imWidth,
						(h * imHeight) + imHeight, null);
				stripGC.dispose();
				count++;
			}
		}

		return strip;
	} // end of loadStripImageArray( )
}

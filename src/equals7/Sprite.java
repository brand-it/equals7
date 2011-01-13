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

	public BufferedImage[][] loadStripImageArray(String fnm, int numberW,
			int numberH, int chunksW, int chunksH) {
		if (numberH <= 0 || numberW <= 0) {
			System.out.println("number <= 0; returning null");
			return null;
		}

		BufferedImage stripIm;
		if ((stripIm = loadImage(fnm)) == null) {
			System.out.println("Returning null");
			return null;
		}
		int imWidth = stripIm.getWidth();
		int imHeight = stripIm.getHeight();
		
		int chunks = (chunksW + chunksH) - 1;

		int tileWidth = imWidth / (numberW * chunksW);
		int tileHeight = imHeight / (numberH * chunksH);
		// Example chunkH = 92 = 23 * 4;
		int chunkH = tileWidth * numberW;
		int chunkW = tileHeight * numberH;
		int transparency = stripIm.getColorModel().getTransparency();

		BufferedImage[][] strip = new BufferedImage[chunks][numberW * numberH];
		Graphics2D stripGC;

		// each BufferedImage from the strip file is stored in strip[]
		int count = 0;
		int chunkLocH = 0;
		int chunkLocW = 0;
		int topLeftImLocX = 0;
		int topLeftImLocY = 0;
		int bottomRightImLocX = 0;
		int bottomRightImLocY = 0;
		
		for (int c = 0; c < chunks; c++) {
			chunkLocW = chunkW * c;
			chunkLocH = chunkH * c;
			if (imWidth == chunkLocW ){
				chunkLocW = 0;
			}
			count = 0;
			for (int h = 0; h < numberH; h++) {
				for (int w = 0; w < numberW; w++) {
					strip[c][count] = gc.createCompatibleImage(tileWidth,
							tileHeight, transparency);
					// create a graphics context
					topLeftImLocX = (w * tileWidth) + chunkLocW;
					topLeftImLocY = (h * tileHeight) + chunkLocH;
					bottomRightImLocX = ((w * tileWidth) + tileWidth) + chunkLocW;
					bottomRightImLocY = ((h * tileHeight) + tileHeight) + chunkLocH;
					stripGC = strip[c][count].createGraphics();
					// copy images
					stripGC.drawImage(stripIm, 0, 0, tileWidth, tileHeight, topLeftImLocX, topLeftImLocY, bottomRightImLocX, bottomRightImLocY, null);
					stripGC.dispose();
					count++;
				}
			}
		}
		return strip;
	} // end of loadStripImageArray( )
}

package firstgame.dwarf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

// By using the buffer we can increase the frame rate.
import java.awt.image.BufferStrategy;


// Use the canvas class to make this hole thing build the UI
public class Game extends Canvas {
	
	private BufferStrategy strategy;
	private static final int ORIGINx = 0;
	private static final int ORIGINy = 0;
	private Image cursorImage;
	private Map map;
	
	private SpriteStore spriteStore;
	
	int height = 900;
	int width = 1000;
	
	private Cursor hiddenCursor;
	
	// Need to create the new frame out side of everything
	Frame frame = new Frame("First Game");
	
	private static final long serialVersionUID = -7803629994015778818L;
	public Game(){
		
		
		// This is what is going to handle the loading of the mouse. Working on clean up the code is going to be needed.
		spriteStore = new SpriteStore();
		cursorImage = spriteStore.getSprite("images/solidStone.jpg");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		hiddenCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "hiddenCursor");
		frame.setCursor(hiddenCursor);
		
		
		frame.setLayout(null);
		
		// This set were your x and y is when you build the frame and how much room you have to work with.
		setBounds(ORIGINx, ORIGINy, width, height);
		frame.add(this);
		frame.setSize(width, height);
		frame.setResizable(true);
		
		
		// add a listener to respond to the window closing so we can
		// exit the game
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		frame.setVisible(true);
		//Make sure you create the buffer strategy
		createBufferStrategy(2);
		strategy = getBufferStrategy();	
		
		map = new Map(this);
		gameLoop();
	}
	
	public void resize(int getSizeHeight, int getSizeWidth){
		frame.setSize(getSizeWidth, getSizeHeight);
		setBounds(ORIGINx, ORIGINy, getSizeWidth, getSizeHeight);
		height = getSizeHeight;
		width = getSizeWidth;
		gameLoop();
	}
	// May be trying to do to much in this method may want to spread it out a bit.
	public void gameLoop(){
		
		boolean gameRunning = true;
		int getSizeHeight;
		int getSizeWidth;
		

		
		while (gameRunning){
			
			getSizeHeight = frame.getSize().height;
			getSizeWidth = frame.getSize().width;
			
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			g.setColor(Color.black);
			
			g.fillRect(ORIGINx, ORIGINy, width, height);
			g.translate(ORIGINx, ORIGINy);
			
			g.translate(ORIGINx, ORIGINy);
			map.paint(g);
			
			g.dispose();
			strategy.show();
			try { Thread.sleep(4); } catch (Exception e) {};
			
			if (getSizeHeight != height || getSizeWidth != width){
				resize(getSizeHeight, getSizeWidth);
			}
		}
	}
	public static void main(String[] argv) {
		new Game();
	}
}

package equals7;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// By using the buffer we can increase the frame rate.
import java.awt.image.BufferStrategy;


// Use the canvas class to make this hole thing build the UI
public class Game extends Canvas {

	private BufferStrategy strategy;
	public static final int ORIGINx = 4;
	public static final int ORIGINy = 15;
	private int element = 0;
	private boolean weBeDragging = false;
	private int mouseDragX, mouseDragY, xPressed, yPressed;
	private Map map;
	int height = 900;
	int width = 1000;
	private boolean mousePressed = false;

	// private Cursor hiddenCursor;

	// Need to create the new frame out side of everything
	Frame frame = new Frame("First Game");

	private static final long serialVersionUID = -7803629994015778818L;

	public Game() {

		// This is what is going to handle the loading of the mouse. Working on
		// clean up the code is going to be needed.
		// Image cursor = SpriteStore.get().getImage("images/cursor.gif", 0, 0);
		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		// toolkit.getBestCursorSize(12, 17);
		// hiddenCursor = toolkit.createCustomCursor(cursor, new Point(0, 0),
		// "hiddenCursor");
		// frame.setCursor(hiddenCursor);

		frame.setLayout(null);

		// This set were your x and y is when you build the frame and how much
		// room you have to work with.
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
		addMouseListener(new mouseInputHandler());
		addKeyListener(new keyInputHandler());
		addMouseMotionListener(new mouseDragHandler());
		
		// Make sure you create the buffer strategy
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		map = new Map(this);
		new mouseDragHandler();
		gameLoop();
	}

	public void resize(int getSizeHeight, int getSizeWidth) {
		frame.setSize(getSizeWidth, getSizeHeight);
		setBounds(ORIGINx, ORIGINy, getSizeWidth, getSizeHeight);
		height = getSizeHeight;
		width = getSizeWidth;
		gameLoop();
	}

	private class keyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_1) {
				element = 1;
			}
			if (e.getKeyCode() == KeyEvent.VK_2) {
				element = 2;
			}
			if (e.getKeyCode() == KeyEvent.VK_3) {
				element = 3;
			}
			if (e.getKeyCode() == KeyEvent.VK_4) {
				element = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_5) {
				element = Map.CLEAR;
			}
		}

	}

	// This is not a good way to use this we just created a way to change the
	// object
	private class mouseInputHandler extends MouseAdapter {
		int xReleased, yReleased;
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == 1) {
				xPressed = e.getX();
				yPressed = e.getY();
			}
		}



		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == 1) {
				xReleased = e.getX();
				yReleased = e.getY();
				mousePressed = false;
			}
			map.changeTiles(xPressed, yPressed, xReleased, yReleased,
						element);
		}
		

	}
	private class mouseDragHandler extends MouseAdapter {
		public void mouseDragged(MouseEvent e) {
			mousePressed = true;
			mouseDragX = e.getX();
			mouseDragY = e.getY();
		}
	}


	// May be trying to do to much in this method may want to spread it out a
	// bit.
	public void gameLoop() {
		boolean gameRunning = true;
		int getSizeHeight;
		int getSizeWidth;
		
		while (gameRunning) {
			getSizeHeight = frame.getSize().height;
			getSizeWidth = frame.getSize().width;

			Graphics g = (Graphics) strategy.getDrawGraphics();

			g.setColor(Color.black);

			g.fillRect(ORIGINx, ORIGINy, width, height);

			g.translate(ORIGINx, ORIGINy);
			map.paint(g);
			
			if (mousePressed){
				map.highlight(g, xPressed, yPressed, mouseDragX, mouseDragY);
			}
			
			

			g.dispose();

			strategy.show();
			try {
				Thread.sleep(4);
			} catch (Exception e) {
			}
			;

			if (getSizeHeight != height || getSizeWidth != width) {
				resize(getSizeHeight, getSizeWidth);
			}
		}
	}

	public static void main(String[] argv) {
		new Game();
	}
}

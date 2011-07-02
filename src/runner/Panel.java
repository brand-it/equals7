package runner;

import application_controller.*;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

import environment_manager.Map;
import environment_manager.MapRender;

import units_manager.Units;
import user_interface.*;

public class Panel extends JPanel implements MouseMotionListener, Runnable, MouseWheelListener {
	// Need to create a globe class that holds every so the system can access with a smaller call
	/**
	 * 
	 */
	private static final long serialVersionUID = 7712717603765541381L;
	// This is all the image data that may be loaded into the game
	private static final String IMS_INFO = "imsInfo.txt";

	public int pWidth = 900; // size of panel this variable will most certainly
								// change as soon as the game starts
	public int pHeight = 700;

	private static final int NO_DELAYS_PER_YIELD = 16;

	private static final int MAX_FRAME_SKIPS = 5;
	private int mouseX = 100, mouseY = 100;
	protected int pCenterX;
	protected int pCenterY;

	// Find the center of the Panel
	private int mouseCenterX;
	private int mouseCenterY;
	
	private Robot robot;

	private long period; // the amount of time between animate. In nanosec

	private volatile boolean gameOver = false;
	private volatile boolean isPaused = false;
	private Thread animator;
	private volatile boolean running = false; // used to stop the animation
												// thread
	private long gameStartTime; // when the game started
	private Graphics dbg;

	// May end up putting this in sort of a global class that all can access
	private MapRender mapRender;
	private Image dbImage = null;
	private Reaction reaction;
	private Buttons buttons;
	private Map map;
	private Units units;

	public Panel(GameRunner gr, long period) {

		this.period = period;

		setDoubleBuffered(false);
		setPreferredSize(new Dimension(pWidth, pWidth));
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus(); // the JPanel now has focus, so receives key events

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				processKey(e);
			}
		});
		
		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				processMouse(e);
			}

		});
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		buttons = new Buttons();
		// Basically every thing uses grid
		ImagesLoader imsLoader = new ImagesLoader(IMS_INFO);
		map = new Map();
		// Dew to how complicated the map rendering system is we gave it a different class name
		mapRender = new MapRender(map, imsLoader);
		units = new Units();
		reaction = new Reaction(map, mapRender, this, imsLoader, buttons, units);
	}

	public void mouseMoved(MouseEvent e) {
		mouseRebotReplace(e);

	}

	private void mouseRebotReplace(MouseEvent e) {

		int x = e.getX() - pCenterX;
		int y = e.getY() - pCenterY;

		if (!isPaused) {
			if (mouseX < 0) {
				mouseX = 0;
			} else if (mouseX > pWidth) {
				mouseX = pWidth;
			} else if (mouseY < 0) {
				mouseY = 0;
			} else if (mouseY > pHeight) {
				mouseY = pHeight;
			} else {
				mouseX += x;
				mouseY += y;
			}

			try {
				robot = new Robot();
				robot.mouseMove(mouseCenterX, mouseCenterY);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void processMouse(MouseEvent e)
	// If some one clicks the mouse
	{
		System.out.println(e.getButton());
		if (!isPaused) {

			if (e.getButton() == 1) {
				reaction.leftClick(mouseX, mouseY);
			}
			if (e.getButton() == 3) {
				reaction.rightClick(mouseX, mouseY);
			}
		}

	}

	public void mouseDragged(MouseEvent e) {
		mouseRebotReplace(e);
	}

	protected void showMouse() {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	protected void hideMouse() {
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		setCursor(blankCursor);

	}

	// All the keyboard input is handle here
	private void processKey(KeyEvent e)
	// handles termination and game-play keys
	{
		int keyCode = e.getKeyCode();

		// termination keys
		// listen for esc, q, end, ctrl-c on the canvas to
		// allow a convenient exit from the full screen configuration
		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q)
				|| (keyCode == KeyEvent.VK_END)
				|| ((keyCode == KeyEvent.VK_C) && e.isControlDown())) {
			running = false;
		} else if (keyCode == KeyEvent.VK_P) {
			if (isPaused == false) {
				isPaused = true;
				showMouse();
			} else {
				isPaused = false;
				// hideMouse();
				hideMouse();
			}

		}
		reaction.keyPressed(keyCode);
	} // end of processKey()

	@Override
	public void addNotify()
	// wait for the JPanel to be added to the JFrame before starting
	{
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}

	private void startGame()
	// initialise and start the thread
	{
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	} // end of startGame()

	public void stopGame() {
		running = false;
	}

	// Basically if you you want things to move and stuff you call this...
	private void gameUpdate() {
		if (!isPaused && !gameOver) {
			reaction.moveMap(mouseX, mouseY);
			buttons.hovered(mouseX, mouseY);
			units.move();
		}
	} // end of gameUpdate()

	public void run() {

		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		gameStartTime = System.nanoTime();

		beforeTime = gameStartTime;

		running = true;
		while (running) {
			// The three most important things in the game
			gameUpdate();
			gameRender();
			paintScreen();


			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime / 1000000L); // nano -> ms
				} catch (InterruptedException ex) {
				}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else { // sleepTime <= 0; the frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield(); // give another thread a chance to run
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			/*
			 * If frame animation is taking too long, update the game state
			 * without rendering it, to get the updates/sec nearer to the
			 * required FPS.
			 */
			int skips = 0;
			while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				gameUpdate(); // update state but don't render
				skips++;
			}
		}
		map.save();
		System.exit(0);
	}

	public void resizeCanves(int width, int height) {
		createDBImage(width, height);
		pHeight = getHeight();
		pWidth = getWidth();
		pCenterX = pWidth / 2;
		pCenterY = pHeight / 2;
		setCenter();
	}

	protected void setCenter() {
		mouseCenterX = pCenterX + getLocationOnScreen().x;
		mouseCenterY = pCenterY + getLocationOnScreen().y;
	}

	private void createDBImage(int width, int height)
	// When you create a db Image it will update all or Make all you variables
	// for the game to run.
	{
		dbImage = createImage(width, height);
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		} else {
			dbg = dbImage.getGraphics();
			dbg.fillRect(0, 0, width, height);

		}
	}

	private void gameRender() {

		if (dbImage == null) {
			System.out.println("dbImage is null Building");
			// Those two numbers can be anything it just needs to create them in order to be resized a nano sec later.
			createDBImage(200, 200);

		}
		// For every thing draw it right here.
		mapRender.draw(dbg, this.pWidth, this.pHeight);
		units.draw(dbg);
		reaction.highlightUnit(dbg);
		buttons.draw(dbg);
		reaction.drawMouse(dbg, mouseX, mouseY);
	}

	private void paintScreen()
	// use active rendering to put the buffered image on-screen
	{
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);

			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)
			Toolkit.getDefaultToolkit().sync();

			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	} // end of paintScreen()

	public void mouseWheelMoved(MouseWheelEvent e) {
		reaction.wheelMouse(e);
		
	}

}
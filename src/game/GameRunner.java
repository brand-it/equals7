package game;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class GameRunner extends JFrame implements WindowListener,
		ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1754800786979400078L;

	private Panel p;

	private static int DEFAULT_FPS = 60;

	public GameRunner(long period) {
		File f = new File("./saves");
		try {
			if (f.mkdir())
				System.out.println("Directory Created" + " " + f);
			else
				System.out.println("Directory is not created" + " " + f);
		} catch (Exception e) {
			e.getStackTrace();
		}

		setLocation(200, 200);

		this.setTitle("Dwarf");

		Container c = getContentPane(); // default BorderLayout used
		p = new Panel(this, period);
		c.add(p, "Center");

		p.addComponentListener(this);

		addWindowListener(this);
		pack();
		setResizable(true);
		setVisible(true);
		setIcon("images/dwarfico.ico");
		

	} // end of BugRunner() constructor
	
	public void setIcon(String imageName){

		Image im = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(imageName));
		setIconImage(im);
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		p.stopGame();

	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		long period = (long) 1000.0 / DEFAULT_FPS;
		// System.out.println("fps: " + DEFAULT_FPS + "; period: " + period +
		// " ms");
		new GameRunner(period * 1000000L); // ms --> nanosecs
	}

	public void componentResized(ComponentEvent e) {
		p.resizeCanves(getWidth(), getHeight());
	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}

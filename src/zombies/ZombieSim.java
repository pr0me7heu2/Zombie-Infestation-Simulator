package zombies;

import util.DotPanel;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ZombieSim extends JFrame implements MouseListener{

	private static final long serialVersionUID = -5176170979783243427L;


	// NOTE: this is protected static!

	protected static DotPanel dp;

	/* Define constants using static final variables */
	public static final int MAX_X = 200;
	public static final int MAX_Y = 200;
	private static final int DOT_SIZE = 3;
	private static final int NUM_HUMANS = 100;
	private static final int NUM_BUILDINGS = 12;

	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares to the screen.
	 */
	public ZombieSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Braaiinnnnnsss");

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);
		
		/* Add the mouse listener for this simulation */
		this.addMouseListener(this);

		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);

		/* Create our city */
		City world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS);

		// run loop
		while(true)
		{
			// Run update rules for world and everything in it
			world.update();
			// Draw to screen and then refresh
			world.draw();
			dp.repaintAndSleep(33);
		}
	}

	public static void main(String[] args) {
		/* Create a new GUI window  */
		new ZombieSim();
	}

	@Override
	// adds human at mouse location if that space is empty
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			City.addhuman(e.getX() / DOT_SIZE, e.getY() / DOT_SIZE);
		if(e.getButton() == MouseEvent.BUTTON3)
			City.addzombie(e.getX() / DOT_SIZE, e.getY() / DOT_SIZE);
	}
	
/* NO NEED TO IMPLEMENT ANY OF THIS BELOW */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

package zombies;

import util.Helper;

import java.awt.Color;
import java.util.ArrayList;


public class City {

	/** Extra FUN
	 * 
	 * This is a option for students who want an extra challenge. 
	 * If you do use the walls you will get extra credit (only if your project works).
	 * 
	 *  walls is a 2D array with an entry for each space in the city.
	 *  If walls[x][y] is true, that means there is a wall in the space.
	 *  else the space is free. Humans & zombies should never go into spaces that
	 *  have a wall.
	 *
	 */
	protected static boolean walls[][];

	protected static boolean humans[][];
	protected static boolean UserHumans[][];
	protected static ArrayList<Human> HumanList;

	// private boolean zombies[][];
	// TODO arraylist

	//private boolean creatures[][];
	// TODO arraylist

	protected static int width, height;

	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numB number of buildings
	 * @param numP number of people
	 * You should modify this function to take the number of:
	 *  zombies and other creatures too. 
	 */
	public City(int w, int h, int numB, int numP) {
		width = w;
		height = h;
		walls = new boolean[w][h];
		humans = new boolean[w][h];

		randomBuildings(numB);
		
		populate(numP);
	}


	/**
	 * Generates numPeople random people distributed throughout the city.
	 * ETRA FUN : People, zombies & other creatures must not be placed inside walls!
	 * You can modify this function to 
	 *
	 * @param numPeople the number of people to generate
	 * 
	 * 
	 */
	private void populate(int numPeople) {
		HumanList = new ArrayList<>();
		for (int i = 0; i < numPeople ; i++) {
			Human temp = new Human();
			HumanList.add(temp);
		}
	}

	protected static void  addhuman(int x, int y) {
		if(!City.humans[x][y] && !City.walls[x][y]) {
			Human human = new Human(x,y);
			HumanList.add(human);
		}
		else {
			System.out.println("Warning: Can not place a new human there; space is currently occupied");
		}
	}


	/**
	 * Generates a random set of numB buildings.
	 *
	 * @param numB the number of buildings to generate
	 */
	private void randomBuildings(int numB) {
		/* Create buildings of a reasonable size for this map */
		int bldgMaxSize = width/6;
		int bldgMinSize = width/50;

		/* Produce a bunch of random rectangles and fill in the walls array */
		for(int i=0; i < numB; i++) {
			int tx, ty, tw, th;
			tx = Helper.nextInt(width);
			ty = Helper.nextInt(height);
			tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
			th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

			for(int r = ty; r < ty + th; r++) {
				if(r >= height)
					continue;  // returns to beginning of loop
				for(int c = tx; c < tx + tw; c++) {
					if(c >= width)
						break;
					walls[c][r] = true;
				}
			}
		}
	}

	
	/**
	 * Updates the state of the city for a time step.
	 */
	public void update() {
		for (Human human : HumanList) {
			human.go();
		}
	}

	/**
	 * Draw all humans, zombies, and buildings (.
	 */
	public void draw(){
		/* Clear the screen */
		ZombieSim.dp.clear(Color.BLACK);
		
		// EXTRA FUN: uncomment this to draw the walls
		drawWalls();

		drawHumans();

	}

	// fills walls based on walls matrix
	private void drawWalls() {
		ZombieSim.dp.setPenColor(Color.DARK_GRAY);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(walls[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

	// draws humans based on human matrix
	private void drawHumans() {
		ZombieSim.dp.setPenColor(Color.GREEN);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(humans[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

}

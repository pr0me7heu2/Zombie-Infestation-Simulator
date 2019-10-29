package zombies;

import util.Helper;

import java.awt.Color;


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
	private boolean walls[][];
	private int width, height;

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
		
		/* 
		 * EXTRA FUN: uncomment this to add buildings
		 */
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
	private void populate(int numPeople)
	{
		// Generate numPeople new humans randomly placed around the city.
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
					continue;
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
		// Move humans, zombies, etc
	}

	/**
	 * Draw all humans, zombies, and buildings (.
	 */
	public void draw(){
		/* Clear the screen */
		ZombieSim.dp.clear(Color.BLACK);
		
		// EXTRA FUN: uncomment this to draw the walls
		drawWalls();
	}

	/**
	 * EXTRA FUN function... not used unless you are going for that
	 * Draw the buildings.
	 * First set the color for drawing, then draw a dot at each space
	 * where there is a wall.
	 */
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

}

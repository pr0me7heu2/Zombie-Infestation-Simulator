package zombies;

import util.Helper;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;


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
	// location matrix for walls
	protected static boolean walls[][];
	// location matrix and ArrayList of humans
	protected static boolean humans[][];
	protected static ArrayList<Human> HumanList;
	// location matrix and ArrayList of survivors
	protected static boolean survivors[][];
	protected static ArrayList<Survivor> SurvivorList;
	// location matrix and ArrayList of zombies
	protected static boolean zombies[][];
	protected static ArrayList<Zombie> ZombieList;
	// location matrix and ArrayList of ghosts
	protected static boolean ghosts[][];
	protected static ArrayList<Ghost> GhostList;
	// location matrix and ArrayList of dogs
	protected static boolean dogs[][];
	protected static ArrayList<Dog> DogList;


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
		zombies = new boolean[w][h];
		ghosts = new boolean[w][h];
		dogs = new boolean[w][h];
		survivors = new boolean[w][h];

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
		// create humans
		HumanList = new ArrayList<Human>();
		for (int i = 0; i < numPeople ; i++) {
			Human tempH = new Human();
			HumanList.add(tempH);
		}

		// create survivors
		// 5% of people are just that badass that they can kill zombies all day
		SurvivorList = new ArrayList<Survivor>();
		for (int i = 0; i < numPeople * .05 ; i++) {
			Survivor tempS = new Survivor();
			SurvivorList.add(tempS);
		}

		// create a single zombie
		ZombieList = new ArrayList<Zombie>();
		Zombie tempZ = new Zombie();
		ZombieList.add(tempZ);

		// create ghosts
		// it's halloween - here are some ghosts to scare everyone
		GhostList = new ArrayList<Ghost>();
		for (int i = 0; i < numPeople * .05; i++) {
			Ghost tempG = new Ghost();
			GhostList.add(tempG);
		}

		// ten percent of people own some crazy mean dogs
		DogList = new ArrayList<Dog>();
		for (int i = 0; i < numPeople * .1 ; i++) {
			Dog tempD = new Dog();
			DogList.add(tempD);
		}
	}

	// create additional humans after initial populate(int numPeople) call
	// new human created at Jplane coordinate (x,y)
	protected static void  addhuman(int x, int y) {
		if(!City.humans[x][y] && !City.walls[x][y]) {
			Human human = new Human(x,y);
			HumanList.add(human);
		}
		else {
			System.out.println("Warning: Can not place a new human there; space is currently occupied");
		}
	}

	// remove humans from the human array list and the
	// human location matrix
	private static void removehuman(Human human) {
		humans[human.getX()][human.getY()] = false;
		HumanList.remove(human);
	}

	// adds zombie at (x,y)
	// note that unlike addhuman, it does not check to
	// see if the space is actually empty
	private static void addzombie(int x, int y) {
		Zombie zombie = new Zombie(x,y);
		ZombieList.add(zombie);
	}

	// adds zombie where human is located and then removes human
	private static void infect(Human human) {
		addzombie(human.getX(),human.getY());
		removehuman(human);
	}

	// remove zombies from the zombie array list and the
	// zombie location matrix
	private static void kill(Zombie zombie) {
		zombies[zombie.getX()][zombie.getY()] = false;
		ZombieList.remove(zombie);
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

		/* Uses for loop to move through HumanList and checks to see if each
		human element as adjacent to a zombie.  If so, infects human and decrements
		i so it can correctly check the next human as the current human will be removed
		by the infect() function.  Use of for loop rather than for each or iterators is to
		avoid ConcurrentModificationException*/

		for (int i = 0; i < HumanList.size(); i++) {
			if (HumanList.get(i).adjacentTo(zombies)) {
				infect(HumanList.get(i));
				i--;
			}
			else
				HumanList.get(i).go();
		}

		/* Uses for loop to move through ZombieList and checks to see if each
		human element as adjacent to a dog.  If so, kills zombie and decrements
		i so it can correctly check the next zombie as the current zombie will be removed
		by the kill() function.  Use of for loop rather than for each or iterators is to
		avoid ConcurrentModificationException*/

		for (int i = 0; i < ZombieList.size(); i++) {
			// dogs and survivors can kill zombies if they are close enough
			if (ZombieList.get(i).adjacentTo(dogs) || ZombieList.get(i).adjacentTo(survivors)) {
				kill(ZombieList.get(i));
				i--;
			}
			else
				ZombieList.get(i).go();
		}

		for (Ghost ghost : GhostList) {
			ghost.go();
		}

		for (Dog dog : DogList) {
			dog.go();
		}

		for(Survivor survivor : SurvivorList) {
			survivor.go();
		}
	}

	/**
	 * Draw all humans, zombies, and buildings (.
	 */
	public void draw(){
		/* Clear the screen */
		ZombieSim.dp.clear(Color.BLACK);

		drawWalls();

		drawHumans();

		drawZombies();

		drawGhosts();

		drawDogs();

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

	private void drawZombies() {
		ZombieSim.dp.setPenColor(Color.RED);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(zombies[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

	private void drawGhosts() {
		ZombieSim.dp.setPenColor(Color.WHITE);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(ghosts[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

	private void drawDogs() {
		ZombieSim.dp.setPenColor(Color.ORANGE);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(dogs[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

}

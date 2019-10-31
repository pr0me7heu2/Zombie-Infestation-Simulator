package zombies;

import util.DotPanel;
import util.Helper;

public class Human extends Beings {

    private Directions direction;

    public Human() {
        int tx, ty;
        this.direction = Directions.North;  // North is default direction

        do {  // assign random positions
            tx = Helper.nextInt(City.width);
            ty = Helper.nextInt(City.height);
        } while (City.humans[tx][ty] | City.walls[tx][ty]);  // try again if wall or already a human there

        this.x = tx;
        this.y = ty;

        City.humans[x][y] = true; // update human matrix


    }

    public Human(int x, int y) {
        this.direction = Directions.North;  // North is default direction
            this.x = x;
            this.y = y;
            City.humans[x][y] = true; // update human matrix


    }

    // TODO getters and setters


    @Override
    protected void tryChangeDirection() {
        Directions currentDirection = this.direction;
        double chance = Helper.nextDouble();
        if (chance <= 0.1) {
            do {
                this.direction = Directions.byOrder(Helper.nextInt(4) + 1);  // changes direction to random enum direction
            } while (this.direction == currentDirection);  // repeat if new direction is same as previous
        }
    }

    @Override
    protected void move() {
        if (this.direction == Directions.North && this.y - 1 > 0 && !City.walls[x][this.y - 1])
        /* if the direction is North (in the negative y direction in Java graphics, and if
        moving north doesn't move off the screen and if moving north doesn't run into a wall,
        then move and update the human location matrix accordingly
         */ {
            City.humans[this.x][this.y] = false;
            this.y -= 1;
            City.humans[this.x][this.y] = true;
        } else if (this.direction == Directions.South && this.y + 1 < City.height && !City.walls[x][this.y + 1]) {
            City.humans[this.x][this.y] = false;
            this.y += 1;
            City.humans[this.x][this.y] = true;
        }
        else if (this.direction == Directions.East && this.x + 1 < City.width && !City.walls[this.x+1][y]) {
            City.humans[this.x][this.y] = false;
            this.x += 1;
            City.humans[this.x][this.y] = true;
        }
        else if (this.direction == Directions.West && this.x - 1 > 0 && !City.walls[this.x-1][y]) {
            City.humans[this.x][this.y] = false;
            this.x -= 1;
            City.humans[this.x][this.y] = true;
        }
    }

    @Override
    protected void go() {
        this.tryChangeDirection();
        this.move();
    }
}



package zombies;

import util.DotPanel;
import util.Helper;

public class Human extends Beings {

    protected Human() {
        int tx, ty;
        this.direction = Directions.North;  // North is default direction

        do {  // assign random positions
            tx = Helper.nextInt(City.width);
            ty = Helper.nextInt(City.height);
            // try again if not empty
        } while (City.survivors[tx][ty] | City.dogs[tx][ty] | City.ghosts[tx][ty]
                | City.zombies[tx][ty] | City.humans[tx][ty] | City.walls[tx][ty]);

        this.x = tx;
        this.y = ty;

        City.humans[x][y] = true; // update human matrix


    }

    protected Human(int x, int y) {
        this.direction = Directions.North;  // North is default direction
            this.x = x;
            this.y = y;
            City.humans[x][y] = true; // update human matrix
    }

    @Override
    protected void move() {
         /* if the direction is North (in the negative y direction in Java graphics) and if
        moving north doesn't move off the screen and if moving north doesn't run into a wall,
        then move and update the human location matrix accordingly
         */
        if (this.direction == Directions.North && this.y - 1 > 0 && !City.walls[this.x][this.y - 1]) {
            City.humans[this.x][this.y] = false;
            this.y -= 1;
            City.humans[this.x][this.y] = true;
        } else if (this.direction == Directions.South && this.y + 1 < City.height && !City.walls[this.x][this.y + 1]) {
            City.humans[this.x][this.y] = false;
            this.y += 1;
            City.humans[this.x][this.y] = true;
        }
        else if (this.direction == Directions.East && this.x + 1 < City.width && !City.walls[this.x+1][this.y]) {
            City.humans[this.x][this.y] = false;
            this.x += 1;
            City.humans[this.x][this.y] = true;
        }
        else if (this.direction == Directions.West && this.x - 1 > 0 && !City.walls[this.x-1][this.y]) {
            City.humans[this.x][this.y] = false;
            this.x -= 1;
            City.humans[this.x][this.y] = true;
        }
    }

    @Override
    protected void go() {
        if (seesAnother(City.zombies)) {
            this.turnAway();
            this.move();
            this.move();
        }
        if (seesAnother(City.ghosts)) {
            this.turnAway();
            this.move();
            this.move();
        }
        else if (this.againstWall()){
            this.tryChangeDirection(100);
            this.move();
        }
        else {
            this.tryChangeDirection(10);
            this.move();
        }
    }
}



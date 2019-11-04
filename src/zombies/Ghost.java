package zombies;

import util.Helper;

public class Ghost extends Beings {
    protected Ghost() {
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

        City.ghosts[x][y] = true; // update ghosts matrix
    }


    @Override
    protected void move() {
         /* if the direction is North (in the negative y direction in Java graphics) and if
        moving north doesn't move off the screen and if moving north doesn't run into a wall,
        then move and update the ghosts location matrix accordingly
         */
        if (this.direction == Directions.North && this.y - 1 > 0 && !City.walls[this.x][this.y - 1]) {
            City.ghosts[this.x][this.y] = false;
            this.y -= 1;
            City.ghosts[this.x][this.y] = true;
        } else if (this.direction == Directions.South && this.y + 1 < City.height && !City.walls[this.x][this.y + 1]) {
            City.ghosts[this.x][this.y] = false;
            this.y += 1;
            City.ghosts[this.x][this.y] = true;
        }
        else if (this.direction == Directions.East && this.x + 1 < City.width && !City.walls[this.x+1][this.y]) {
            City.ghosts[this.x][this.y] = false;
            this.x += 1;
            City.ghosts[this.x][this.y] = true;
        }
        else if (this.direction == Directions.West && this.x - 1 > 0 && !City.walls[this.x-1][this.y]) {
            City.ghosts[this.x][this.y] = false;
            this.x -= 1;
            City.ghosts[this.x][this.y] = true;
        }
    }

    @Override
    protected void go() {

        if (this.againstWall()){
            this.tryChangeDirection(100);
            this.move();
            this.move();

        }
        else {
            this.tryChangeDirection(45);
            this.move();
            this.move();
        }
    }
}

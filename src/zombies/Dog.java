package zombies;

import util.Helper;

public class Dog extends Beings{
    public Dog() {
        int tx, ty;
        this.direction = Beings.Directions.North;  // North is default direction

        do {  // assign random positions
            tx = Helper.nextInt(City.width);
            ty = Helper.nextInt(City.height);
            // try again if not empty
        } while (City.survivors[tx][ty] | City.dogs[tx][ty] | City.ghosts[tx][ty]
                | City.zombies[tx][ty] | City.humans[tx][ty] | City.walls[tx][ty]);
        this.x = tx;
        this.y = ty;

        City.dogs[x][y] = true; // update dog matrix


    }
    
    @Override
    protected void move() {
         /* if the direction is North (in the negative y direction in Java graphics) and if
        moving north doesn't move off the screen and if moving north doesn't run into a wall,
        then move and update the dog location matrix accordingly
         */
        if (this.direction == Beings.Directions.North && this.y - 1 > 0 && !City.walls[this.x][this.y - 1]) {
            City.dogs[this.x][this.y] = false;
            this.y -= 1;
            City.dogs[this.x][this.y] = true;
        } else if (this.direction == Beings.Directions.South && this.y + 1 < City.height && !City.walls[this.x][this.y + 1]) {
            City.dogs[this.x][this.y] = false;
            this.y += 1;
            City.dogs[this.x][this.y] = true;
        }
        else if (this.direction == Beings.Directions.East && this.x + 1 < City.width && !City.walls[this.x+1][this.y]) {
            City.dogs[this.x][this.y] = false;
            this.x += 1;
            City.dogs[this.x][this.y] = true;
        }
        else if (this.direction == Beings.Directions.West && this.x - 1 > 0 && !City.walls[this.x-1][this.y]) {
            City.dogs[this.x][this.y] = false;
            this.x -= 1;
            City.dogs[this.x][this.y] = true;
        }
    }

    @Override
    protected void go() {
        if (seesAnother(City.zombies)) {
            this.move();
            this.move();
        }
        if (seesAnother(City.ghosts)) {
            this.turnAway();
            this.move();
        }
        else if (this.againstWall()){
            this.tryChangeDirection(100);
            this.move();
        }
        else {
            this.tryChangeDirection(15);
            this.move();
        }
    }
}

package zombies;

import util.Helper;

public abstract class Beings {
    protected enum Directions {
        North, East, South, West;
    }

    protected Directions direction;
    protected int x;
    protected int y;

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    // returns true if a being e.g. zombie, human is within
    // 10 spaces of the direction the callee is facing
    protected boolean seesAnother(boolean[][] beings) {
        if (this.direction == Beings.Directions.North) {
            for (int i = this.y; i > this.y - 10  && i > 0 ; i--) {
                if (beings[this.x][i])
                    return true;
            }
            return false;

        } else if (this.direction == Beings.Directions.South) {
            for (int i = this.y; i < this.y + 10 && i < City.height ; i++) {
                if (beings[this.x][i])
                    return true;
            }
            return false;
        }
        else if (this.direction == Beings.Directions.East) {
            for (int i = this.x; i < this.x + 10 && i < City.width; i++) {
                if (beings[i][this.y])
                    return true;
            }
            return false;
        }
        else if (this.direction == Beings.Directions.West) {
            for (int i = this.x; i > this.x - 10 && i > 0; i--) {
                if (beings[i][this.y])
                    return true;
            }
            return false;
        }
        return false;  // default
    }

    // returns true if callee is within one square (no diagonals)
    // of a being as defined by that being's location matrix
    protected boolean adjacentTo(boolean[][] beings) {
        if (this.y+1 < City.height && beings[this.x][this.y+1])
            return true;
        else if (this.y-1 > 0 && beings[this.x][this.y-1])
            return true;
        else if (this.x+1 < City.width && beings[this.x+1][this.y])
            return true;
        else return (this.x-1 > 0 && beings[x - 1][this.y]);
    }

    // prob % probability of changing the direction of being to a random direction
    // other than which it is currently pointing

    protected void tryChangeDirection(double prob) {
        Directions currentDirection = this.direction;
        if (Helper.nextDouble() <= prob / 100) {
            do {
                this.direction = Directions.values()[Helper.nextInt(4)]; // changes direction to random enum direction
            } while (this.direction == currentDirection);  // repeat if new direction is same as previous
        }
    }

    // turns being in the opposite direction
    protected void turnAway() {
        if (this.direction == Directions.North)
            this.direction = Directions.South;
        else if (this.direction == Directions.South)
            this.direction = Directions.North;
        else if (this.direction == Directions.East)
            this.direction = Directions.West;
        else if (this.direction == Directions.West)
            this.direction = Directions.East;
    }

    // returns true if being is against the wall of the Jplane
    protected boolean againstWall() {
        if (City.height - this.y == 1)
            return true;
        else if (this.y == 1)
            return true;
        else if (City.width - this.x == 1)
            return true;
        else if (this.x == 1)
            return true;
        else return false;
    }

    protected abstract void move();

    protected abstract void go();
}

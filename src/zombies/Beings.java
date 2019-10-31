package zombies;

public abstract class Beings {
    protected enum Directions {
        North, East, South, West;
    }

    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected abstract void tryChangeDirection();

    protected abstract void move();

    protected abstract void go();
}

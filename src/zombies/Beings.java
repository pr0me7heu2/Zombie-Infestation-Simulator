package zombies;

public abstract class Beings {
    protected enum Directions {
        North(1), East(2), South(3), West(4);
        private int directionOrder;

        Directions(int directionOrder) {
            this.directionOrder = directionOrder;
        }

        static Directions byOrder(int ord) {
            for (Directions d : Directions.values()) {
                if (d.directionOrder == ord) {
                    return d;
                }
            }
            return null;
        }
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

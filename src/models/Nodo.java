package models;

public class Nodo {

    private int x, y;

    public Nodo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Nodo)) return false;
        Nodo n = (Nodo) o;
        return x == n.x && y == n.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }
}


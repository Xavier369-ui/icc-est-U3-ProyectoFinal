package models;

public class Nodo {
    private final String id;
    private int x;
    private int y;

    public Nodo(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    @Override
    public String toString() {
        return id + "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nodo)) return false;
        Nodo nodo = (Nodo) o;
        return id.equals(nodo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

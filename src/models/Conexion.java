package models;

public class Conexion {

    private Nodo a;
    private Nodo b;

    public Conexion(Nodo a, Nodo b) {
        this.a = a;
        this.b = b;
    }

    public Nodo getA() { return a; }
    public Nodo getB() { return b; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Conexion)) return false;
        Conexion c = (Conexion) o;
        return (a.equals(c.a) && b.equals(c.b)) ||
               (a.equals(c.b) && b.equals(c.a));
    }

    @Override
    public int hashCode() {
        return a.hashCode() + b.hashCode();
    }
}


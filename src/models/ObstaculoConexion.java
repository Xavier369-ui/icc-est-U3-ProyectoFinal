package models;

import nodes.Node;

public class ObstaculoConexion {

    private final Node<?> a;
    private final Node<?> b;

    public ObstaculoConexion(Node<?> a, Node<?> b) {
        this.a = a;
        this.b = b;
    }

    public boolean bloquea(Node<?> x, Node<?> y) {
        return (a.equals(x) && b.equals(y)) || (a.equals(y) && b.equals(x));
    }

    public Node<?> getA() { return a; }
    public Node<?> getB() { return b; }
}

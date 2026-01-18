package models;

public class Arista {

    Nodo a;
    Nodo b;

    public Arista(Nodo a, Nodo b) {
        this.a = a;
        this.b = b;
    }

    public Nodo getA() {
        return a;
    }

    public Nodo getB() {
        return b;
    }
    
}

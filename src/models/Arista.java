package models;

public class Arista {
    private final Nodo desde;
    private final Nodo hasta;
    private final double peso;

    public Arista(Nodo desde, Nodo hasta) {
        this(desde, hasta, 1.0);
    }

    public Arista(Nodo desde, Nodo hasta, double peso) {
        this.desde = desde;
        this.hasta = hasta;
        this.peso = peso;
    }

    public Nodo getDesde() { return desde; }
    public Nodo getHasta() { return hasta; }
    public double getPeso() { return peso; }

    @Override
    public String toString() {
        return desde.getId() + "->" + hasta.getId();
    }
}

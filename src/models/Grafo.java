package models;

import java.util.ArrayList;

public class Grafo {

    private ArrayList<Nodo> nodos;

    public Grafo() {
        nodos = new ArrayList<Nodo>();
    }

    public void agregarNodo(Nodo nodo) {
        nodos.add(nodo);
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }
}



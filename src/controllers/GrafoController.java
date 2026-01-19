package controllers;

import models.Grafo;
import models.Nodo;

public class GrafoController {

    private Grafo grafo;

    public GrafoController() {
        grafo = new Grafo();
    }

    public void agregarNodo(int x, int y) {

        Nodo nodo = new Nodo(x, y);
        grafo.agregarNodo(nodo);
    }

    public Grafo getGrafo() {
        return grafo;
    }
}

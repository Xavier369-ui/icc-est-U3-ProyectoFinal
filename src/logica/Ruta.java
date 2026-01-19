package logica;

import java.util.ArrayList;
import models.Nodo;

public class Ruta {

    private ArrayList<Nodo> nodos;

    public Ruta() {
        nodos = new ArrayList<Nodo>();
    }

    public void agregarNodo(Nodo nodo) {
        nodos.add(nodo);
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }
}

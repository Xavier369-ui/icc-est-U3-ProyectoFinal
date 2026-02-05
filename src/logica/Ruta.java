package logica;

import java.util.*;
import models.Nodo;

public class Ruta {

    private List<Nodo> nodos;

    public Ruta(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    public List<Nodo> getNodos() {
        return nodos;
    }
}



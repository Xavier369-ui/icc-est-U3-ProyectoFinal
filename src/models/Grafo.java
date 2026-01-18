package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

    List<Nodo> nodos;
    Map<Nodo, List<Nodo>> conexiones;

    public Grafo() {
        nodos = new ArrayList<>();
        conexiones = new HashMap<>();
    }

    public void agregarNodo(Nodo n) {
        nodos.add(n);
        conexiones.put(n, new ArrayList<>());
    }

    public void conectar(Nodo a, Nodo b) {
        conexiones.get(a).add(b);
        conexiones.get(b).add(a);
    }

    public List<Nodo> getNodos() {
        return nodos;
    }

    public List<Nodo> getVecinos(Nodo n) {
        return conexiones.get(n);
    }
    
}

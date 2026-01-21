package logica;

import java.util.ArrayList;
import java.util.List;
import models.Nodo;

public class Ruta {

    private List<Nodo> nodos;

    // Constructor vacío (NO se elimina)
    public Ruta() {
        nodos = new ArrayList<>();
    }

    // ✅ CONSTRUCTOR QUE FALTABA
    public Ruta(List<Nodo> nodos) {
        this.nodos = new ArrayList<>(nodos);
    }

    public void agregarNodo(Nodo n) {
        nodos.add(n);
    }

    public List<Nodo> getNodos() {
        return nodos;
    }
}

 


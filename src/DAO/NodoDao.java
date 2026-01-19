package DAO;

import models.Nodo;
import java.util.ArrayList;

public class NodoDao {

    private ArrayList<Nodo> nodos;

    public NodoDao() {
        nodos = new ArrayList<Nodo>();
    }

    public void guardar(Nodo nodo) {
        nodos.add(nodo);
    }

    public ArrayList<Nodo> obtenerTodos() {
        return nodos;
    }
}


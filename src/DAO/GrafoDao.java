package dao;

import model.Grafo;
import model.Nodo;
import model.Obstaculo;
import persistence.FileManager;

import java.util.ArrayList;

public class GrafoDAO {
    private final NodoDAO nodoDAO;
    private final AristaDAO aristaDAO;
    private final ObstaculoDAO obstaculoDAO;

    public GrafoDAO(FileManager fm) {
        this.nodoDAO = new NodoDAO(fm);
        this.aristaDAO = new AristaDAO(fm);
        this.obstaculoDAO = new ObstaculoDAO(fm);
    }

    public Grafo cargar() {
        Grafo g = new Grafo();
        for (Nodo n : nodoDAO.cargar()) {
            g.addNodo(n);
        }
        aristaDAO.cargar(g);
        for (Obstaculo o : obstaculoDAO.cargar()) {
            g.addObstaculo(o);
        }
        return g;
    }

    public void guardar(Grafo g) {
        nodoDAO.guardar(new ArrayList<>(g.getNodos()));
        aristaDAO.guardar(g);
        obstaculoDAO.guardar(g.getObstaculos());
    }
}

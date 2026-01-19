package DAO;

import models.Obstaculo;
import java.util.ArrayList;

public class ObstaculoDao {

    private ArrayList<Obstaculo> obstaculos;

    public ObstaculoDao() {
        obstaculos = new ArrayList<Obstaculo>();
    }

    public void guardar(Obstaculo obstaculo) {
        obstaculos.add(obstaculo);
    }

    public ArrayList<Obstaculo> obtenerTodos() {
        return obstaculos;
    }
}

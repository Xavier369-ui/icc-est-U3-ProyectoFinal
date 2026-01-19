package models;

import java.util.ArrayList;

public class Nodo {

    private int x;
    private int y;
    private ArrayList<Nodo> vecinos;

    public Nodo(int x, int y) {
        this.x = x;
        this.y = y;
        this.vecinos = new ArrayList<Nodo>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Nodo> getVecinos() {
        return vecinos;
    }

    public void agregarVecino(Nodo nodo) {
        vecinos.add(nodo);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Nodo otro = (Nodo) obj;

        if (this.x != otro.x) {
            return false;
        }

        if (this.y != otro.y) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int hash = 7;

        hash = 31 * hash + x;
        hash = 31 * hash + y;

        return hash;
    }
}


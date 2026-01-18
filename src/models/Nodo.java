package models;

public class Nodo {

    int id;
    int x;
    int y;
    boolean bloqueado;

    public Nodo(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.bloqueado = false;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
}

package models;

public class Obstaculo {

    Nodo nodo;

    public Obstaculo(Nodo nodo) {
        this.nodo = nodo;
        this.nodo.setBloqueado(true);
    }

    public Nodo getNodo() {
        return nodo;
    }

    public void quitar() {
        nodo.setBloqueado(false);
    }
    
}

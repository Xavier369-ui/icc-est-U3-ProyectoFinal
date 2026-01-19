package controllers;

public class EdicionController {

    public static final int MODO_NODO = 1;
    public static final int MODO_OBSTACULO = 2;
    public static final int MODO_INICIO = 3;
    public static final int MODO_DESTINO = 4;

    private int modoActual;

    public EdicionController() {
        modoActual = MODO_NODO;
    }

    public void setModoNodo() {
        modoActual = MODO_NODO;
    }

    public void setModoObstaculo() {
        modoActual = MODO_OBSTACULO;
    }

    public void setModoInicio() {
        modoActual = MODO_INICIO;
    }

    public void setModoDestino() {
        modoActual = MODO_DESTINO;
    }

    public int getModoActual() {
        return modoActual;
    }
}



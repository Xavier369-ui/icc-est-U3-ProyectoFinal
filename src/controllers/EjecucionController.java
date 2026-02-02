package controllers;

import views.PanelMapa;

public class EjecucionController {

    public static final int MODO_BFS = 1;
    public static final int MODO_DFS = 2;

    private int modoRecorrido = 0;
    private PanelMapa panel;

    public EjecucionController(PanelMapa panel) {
        this.panel = panel;
    }

    // 👉 Solo selecciona
    public void seleccionarBFS() {
        modoRecorrido = MODO_BFS;
    }

    public void seleccionarDFS() {
        modoRecorrido = MODO_DFS;
    }

    //  Ejecuta
    public void run() {
        if (modoRecorrido == MODO_BFS) {
            panel.ejecutarBFS();
        } else if (modoRecorrido == MODO_DFS) {
            panel.ejecutarDFS();
        }
    }
}

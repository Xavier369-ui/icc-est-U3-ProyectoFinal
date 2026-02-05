package controllers;

import views.PanelMapa;

public class EjecucionController {

    public static final int BFS = 1;
    public static final int DFS = 2;

    private int modo = 0;
    private PanelMapa panel;

    public EjecucionController(PanelMapa panel) {
        this.panel = panel;
    }

    public void seleccionarBFS() { modo = BFS; }
    public void seleccionarDFS() { modo = DFS; }

    public void run() {
        if (modo == BFS) panel.ejecutarBFS();
        if (modo == DFS) panel.ejecutarDFS();
    }
}

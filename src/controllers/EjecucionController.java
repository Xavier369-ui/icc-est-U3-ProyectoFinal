package controllers;

import logica.BFS;
import logica.DFS;
import logica.Ruta;
import views.PanelMapa;

public class EjecucionController {

    private PanelMapa panel;

    public EjecucionController(PanelMapa panel) {
        this.panel = panel;
    }

    public void ejecutarBFS() {
        Ruta ruta = BFS.ejecutar(panel.getNodoInicio(), panel.getNodoDestino());
        panel.setRuta(ruta, "BFS");
    }

    public void ejecutarDFS() {
        Ruta ruta = DFS.ejecutar(panel.getNodoInicio(), panel.getNodoDestino());
        panel.setRuta(ruta, "DFS");
    }
}



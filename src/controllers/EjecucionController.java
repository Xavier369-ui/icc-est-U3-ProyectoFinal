package controllers;

import views.PanelMapa;
import nodes.Node;
import models.Nodo;
import java.util.ArrayList;
import java.util.List;

public class EjecucionController {

    private PanelMapa panel;

    public EjecucionController(PanelMapa panel) {
        this.panel = panel;
    }

    public void ejecutarBFS() {
        panel.ejecutarBFS();
    }

    public void ejecutarDFS() {
        panel.ejecutarDFS();
    }
}




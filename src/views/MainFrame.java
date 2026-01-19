package views;

import controllers.GrafoController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(GrafoController controller) {
        super("Proyecto Final - Grafo BFS/DFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PanelMapa panelMapa = new PanelMapa();
        panelMapa.setController(controller);
        controller.bindPanel(panelMapa);

        BarraHerramientas barra = new BarraHerramientas(controller);

        setLayout(new BorderLayout());
        add(barra, BorderLayout.NORTH);
        add(new JScrollPane(panelMapa), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
}

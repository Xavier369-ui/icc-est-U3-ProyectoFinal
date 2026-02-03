package views;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import controllers.EdicionController;
import controllers.EjecucionController;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Mapa");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ✅ Layout correcto para que el panel se adapte
        setLayout(new BorderLayout());

        // Controladores
        EdicionController edicion = new EdicionController();

        // Panel del mapa
        PanelMapa panel = new PanelMapa(edicion);

        // ✅ MUY IMPORTANTE: agregar al CENTER
        add(panel, BorderLayout.CENTER);

        // Ejecución BFS / DFS
        EjecucionController ejecucion = new EjecucionController(panel);

        // Menú
        MenuPrincipal menu = new MenuPrincipal(edicion, ejecucion, panel);
        setJMenuBar(menu);

        setVisible(true);
    }
}






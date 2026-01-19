package views;

import javax.swing.JFrame;
import controllers.EdicionController;
import controllers.EjecucionController;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Proyecto Final - BFS y DFS");
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EdicionController edicion = new EdicionController();

        PanelMapa panel = new PanelMapa(edicion);
        panel.setBounds(0, 0, 900, 550);
        add(panel);

        EjecucionController ejecucion = new EjecucionController(panel);

        MenuPrincipal menu = new MenuPrincipal(edicion, ejecucion, panel);
        setJMenuBar(menu);

        setVisible(true);
    }
}




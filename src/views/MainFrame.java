package views;

import controllers.ConexionController;
import controllers.EdicionController;
import controllers.EjecucionController;
import java.awt.*;
import javax.swing.*;


public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Mapa");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        EdicionController edicion = new EdicionController();
        ConexionController conexion = new ConexionController();

        PanelMapa panel = new PanelMapa(edicion, conexion);
        EjecucionController ejecucion = new EjecucionController(panel);

        setJMenuBar(new MenuPrincipal(edicion, conexion, ejecucion, panel));
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

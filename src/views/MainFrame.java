package views;

import javax.swing.JFrame;
import controllers.EdicionController;
import controllers.EjecucionController;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Mapa");
        setSize(900, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        EdicionController edicion = new EdicionController();

        PanelMapa panel = new PanelMapa(edicion);
        panel.setBounds(0, 0, 900, 550);
        add(panel);

        EjecucionController ejecucion = new EjecucionController(panel);

        // 👇 AQUÍ ESTÁ LA CORRECCIÓN
        MenuPrincipal menu = new MenuPrincipal(edicion, ejecucion, panel);
        setJMenuBar(menu);

        setVisible(true);
    }
}





package views;

import javax.swing.*;
import controllers.*;

public class MenuPrincipal extends JMenuBar {

    public MenuPrincipal(
            EdicionController edicion,
            ConexionController conexion,
            EjecucionController ejecucion,
            PanelMapa panel) {

        JMenu ed = new JMenu("Edicion");
        JMenu rec = new JMenu("Recorrido");
        JMenu con = new JMenu("Conexiones");

        JMenuItem nodo = new JMenuItem("Agregar nodo");
        nodo.addActionListener(e -> edicion.setModoNodo());

        JMenuItem obs = new JMenuItem("Agregar obstaculo");
        obs.addActionListener(e -> edicion.setModoObstaculo());

        ed.add(nodo);
        ed.add(obs);

        JMenuItem bfs = new JMenuItem("BFS");
        JMenuItem dfs = new JMenuItem("DFS");
        JButton run = new JButton("Run");

        bfs.addActionListener(e -> ejecucion.seleccionarBFS());
        dfs.addActionListener(e -> ejecucion.seleccionarDFS());
        run.addActionListener(e -> ejecucion.run());

        rec.add(bfs);
        rec.add(dfs);

        JMenuItem uni = new JMenuItem("Agregar unidireccional");
        JMenuItem bi = new JMenuItem("Agregar bidireccional");
        JMenuItem del = new JMenuItem("Quitar conexion");
        JMenuItem hide = new JMenuItem("Ocultar conexiones");
        JMenuItem show = new JMenuItem("Mostrar conexiones");

        uni.addActionListener(e -> conexion.setUnidireccional());
        bi.addActionListener(e -> conexion.setBidireccional());
        del.addActionListener(e -> conexion.setEliminar());
        hide.addActionListener(e -> panel.ocultarConexiones());
        show.addActionListener(e -> panel.mostrarConexiones());

        con.add(uni);
        con.add(bi);
        con.add(del);
        con.addSeparator();
        con.add(hide);
        con.add(show);

        add(ed);
        add(rec);
        add(con);
        add(Box.createHorizontalGlue());
        add(run);
    }
}

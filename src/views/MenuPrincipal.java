package views;

import javax.swing.*;
import controllers.*;
import views.VerTiemposFrame;

public class MenuPrincipal extends JMenuBar {

    public MenuPrincipal(
            EdicionController edicion,
            ConexionController conexion,
            EjecucionController ejecucion,
            PanelMapa panel) {

        // ===== MENUS =====
        JMenu ed = new JMenu("Edicion");
        JMenu rec = new JMenu("Recorrido");
        JMenu con = new JMenu("Conexiones");
        JMenu tiempos = new JMenu("Ver tiempos");

        VerTiemposFrame tiemposFrame = new VerTiemposFrame();

        // ====== EDICION ======

        JMenuItem nodo = new JMenuItem("Agregar nodo");
        nodo.addActionListener(e -> {
            conexion.limpiar();     // ← IMPORTANTE
            edicion.setModoNodo();
        });

        JMenuItem eliminarNodo = new JMenuItem("Eliminar nodo");
        eliminarNodo.addActionListener(e -> {
            conexion.limpiar();
            edicion.setModoEliminar();
        });

        JMenuItem obs = new JMenuItem("Agregar obstaculo");
        obs.addActionListener(e -> {
            conexion.limpiar();
            edicion.setModoObstaculo();
        });

        JMenuItem limpiarMapa = new JMenuItem("Limpiar mapa");
        limpiarMapa.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(
            null,
            "¿Seguro que deseas limpiar todo el mapa?",
            "Confirmar",
                JOptionPane.YES_NO_OPTION
            );

            if (op == JOptionPane.YES_OPTION) {
                panel.limpiarMapa();
            }
        });

        ed.add(nodo);
        ed.add(obs);
        ed.add(eliminarNodo);
        ed.addSeparator();
        ed.add(limpiarMapa);


        // ====== RECORRIDO ======

        JMenuItem setInicio = new JMenuItem("Seleccionar inicio");
        setInicio.addActionListener(e -> {
            conexion.limpiar();
            edicion.setModoInicio();
        });

        JMenuItem setDestino = new JMenuItem("Seleccionar destino");
        setDestino.addActionListener(e -> {
            conexion.limpiar();
            edicion.setModoDestino();
        });

        JMenuItem bfs = new JMenuItem("BFS");
        bfs.addActionListener(e -> ejecucion.seleccionarBFS());

        JMenuItem dfs = new JMenuItem("DFS");
        dfs.addActionListener(e -> ejecucion.seleccionarDFS());

        JButton run = new JButton("Run");
        run.addActionListener(e -> ejecucion.run());

        JMenuItem limpiarRec = new JMenuItem("Limpiar recorrido");
        limpiarRec.addActionListener(e -> {
            panel.limpiarRecorrido();
        });

        rec.add(setInicio);
        rec.add(setDestino);
        rec.addSeparator();
        rec.add(bfs);
        rec.add(dfs);
        rec.addSeparator();
        rec.add(limpiarRec);


        // ====== CONEXIONES ======

        JMenuItem uni = new JMenuItem("Agregar unidireccional");
        uni.addActionListener(e -> {
            edicion.limpiar();
            conexion.setUnidireccional();
        });

        JMenuItem bi = new JMenuItem("Agregar bidireccional");
        bi.addActionListener(e -> {
            edicion.limpiar();
            conexion.setBidireccional();
        });

        JMenuItem del = new JMenuItem("Quitar conexion");
        del.addActionListener(e -> {
            edicion.limpiar();
            conexion.setEliminar();
        });

        JMenuItem hide = new JMenuItem("Ocultar conexiones");
        hide.addActionListener(e -> panel.ocultarConexiones());

        JMenuItem show = new JMenuItem("Mostrar conexiones");
        show.addActionListener(e -> panel.mostrarConexiones());

        con.add(uni);
        con.add(bi);
        con.add(del);
        con.addSeparator();
        con.add(hide);
        con.add(show);

        JMenuItem quitarObs = new JMenuItem("Retirar obstáculo");

        quitarObs.addActionListener(e -> {
            conexion.limpiar();
            edicion.setModoRetirarObstaculo();
        });

        ed.add(quitarObs);


        // ====== VER TIEMPOS ======

        JMenuItem ver = new JMenuItem("Mostrar tiempos");
        ver.addActionListener(e -> {

            if (panel.getDestino() == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "No hay destino seleccionado"
                );
                return;
            }

            String coordenadas = "("
                    + panel.getDestino().getX() + ", "
                    + panel.getDestino().getY() + ")";

            tiemposFrame.agregarFila(
                    coordenadas,
                    panel.getTiempoBFS(),
                    panel.getTiempoDFS()
            );

            tiemposFrame.setVisible(true);
        });

        tiempos.add(ver);

        // ====== AGREGAR A BARRA ======

        add(ed);
        add(rec);
        add(con);
        add(tiempos);

        add(Box.createHorizontalGlue());
        add(run);
    }
}

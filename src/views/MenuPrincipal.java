package views;

import controllers.EdicionController;
import controllers.EjecucionController;
import javax.swing.*;

public class MenuPrincipal extends JMenuBar {

    public MenuPrincipal(
            EdicionController edicion,
            EjecucionController ejecucion,
            PanelMapa panel
    ) {

        JMenu menuEdicion = new JMenu("Edicion");
        JMenu menuSeleccion = new JMenu("Seleccion");
        JMenu menuRecorrido = new JMenu("Recorrido");

        JMenuItem itemNodo = new JMenuItem("Agregar Nodo");
        JMenuItem itemObstaculo = new JMenuItem("Agregar Obstaculo");
        JMenuItem itemConectar = new JMenuItem("Conectar Nodos");
        JMenuItem itemEliminar = new JMenuItem("Eliminar Nodo");

        JMenuItem itemInicio = new JMenuItem("Seleccionar Inicio");
        JMenuItem itemDestino = new JMenuItem("Seleccionar Destino");

        JMenuItem itemBFS = new JMenuItem("BFS");
        JMenuItem itemDFS = new JMenuItem("DFS");

        JMenuItem itemNuevoRecorrido = new JMenuItem("Iniciar otro recorrido");

        JButton btnRun = new JButton("Run");

        // ===== EDICIÓN =====
        itemNodo.addActionListener(e -> edicion.setModoNodo());
        itemObstaculo.addActionListener(e -> edicion.setModoObstaculo());
        itemConectar.addActionListener(e -> edicion.setModoConectar());
        itemEliminar.addActionListener(e -> edicion.setModoEliminar());

        // ===== SELECCIÓN =====
        itemInicio.addActionListener(e -> edicion.setModoInicio());
        itemDestino.addActionListener(e -> edicion.setModoDestino());

        // ===== RECORRIDO =====
        itemBFS.addActionListener(e -> ejecucion.seleccionarBFS());
        itemDFS.addActionListener(e -> ejecucion.seleccionarDFS());
        btnRun.addActionListener(e -> ejecucion.run());

        // 👉 LO QUE PEDISTE
        itemNuevoRecorrido.addActionListener(e -> panel.iniciarOtroRecorrido());

        menuEdicion.add(itemNodo);
        menuEdicion.add(itemObstaculo);
        menuEdicion.add(itemConectar);
        menuEdicion.add(itemEliminar);

        menuSeleccion.add(itemInicio);
        menuSeleccion.add(itemDestino);

        menuRecorrido.add(itemBFS);
        menuRecorrido.add(itemDFS);
        menuRecorrido.add(itemNuevoRecorrido);

        add(menuEdicion);
        add(menuSeleccion);
        add(menuRecorrido);

        add(Box.createHorizontalGlue());
        add(btnRun);
    }
}


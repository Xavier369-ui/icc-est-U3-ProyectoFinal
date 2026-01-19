package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controllers.EdicionController;
import controllers.EjecucionController;

public class MenuPrincipal extends JMenuBar {

    public MenuPrincipal(EdicionController edicion, EjecucionController ejecucion, PanelMapa panel) {

        JMenu menuEdicion = new JMenu("Edicion");
        JMenu menuSeleccion = new JMenu("Seleccion");
        JMenu menuEjecucion = new JMenu("Ejecucion");

        JMenuItem itemNodo = new JMenuItem("Agregar Nodo");
        JMenuItem itemObstaculo = new JMenuItem("Agregar Obstaculo");

        JMenuItem itemInicio = new JMenuItem("Seleccionar Inicio");
        JMenuItem itemDestino = new JMenuItem("Seleccionar Destino");

        JMenuItem itemBFS = new JMenuItem("BFS");
        JMenuItem itemDFS = new JMenuItem("DFS");

        JMenuItem itemMostrar = new JMenuItem("Mostrar Recorrido");
        JMenuItem itemOcultar = new JMenuItem("Ocultar Recorrido");
        JMenuItem itemLimpiar = new JMenuItem("Limpiar Recorrido");

        itemNodo.addActionListener(e -> edicion.setModoNodo());
        itemObstaculo.addActionListener(e -> edicion.setModoObstaculo());

        itemInicio.addActionListener(e -> edicion.setModoInicio());
        itemDestino.addActionListener(e -> edicion.setModoDestino());

        itemBFS.addActionListener(e -> ejecucion.ejecutarBFS());
        itemDFS.addActionListener(e -> ejecucion.ejecutarDFS());

        itemMostrar.addActionListener(e -> panel.mostrarRuta());
        itemOcultar.addActionListener(e -> panel.ocultarRuta());
        itemLimpiar.addActionListener(e -> panel.limpiarRuta());

        menuEdicion.add(itemNodo);
        menuEdicion.add(itemObstaculo);

        menuSeleccion.add(itemInicio);
        menuSeleccion.add(itemDestino);

        menuEjecucion.add(itemBFS);
        menuEjecucion.add(itemDFS);
        menuEjecucion.add(itemMostrar);
        menuEjecucion.add(itemOcultar);
        menuEjecucion.add(itemLimpiar);

        add(menuEdicion);
        add(menuSeleccion);
        add(menuEjecucion);
    }
}



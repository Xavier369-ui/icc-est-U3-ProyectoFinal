package views;

import javax.swing.*;
import controllers.EdicionController;
import controllers.EjecucionController;

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
        JMenuItem itemMostrar = new JMenuItem("Mostrar Camino");
        JMenuItem itemOcultar = new JMenuItem("Ocultar Camino");
        JMenuItem itemLimpiarConexiones = new JMenuItem("Limpiar Conexiones");
        JMenuItem itemLimpiarTodo = new JMenuItem("Limpiar Todo");

        itemNodo.addActionListener(e -> edicion.setModoNodo());
        itemObstaculo.addActionListener(e -> edicion.setModoObstaculo());
        itemConectar.addActionListener(e -> edicion.setModoConectar());
        itemEliminar.addActionListener(e -> edicion.setModoEliminar());

        itemInicio.addActionListener(e -> edicion.setModoInicio());
        itemDestino.addActionListener(e -> edicion.setModoDestino());

        itemBFS.addActionListener(e -> ejecucion.ejecutarBFS());
        itemDFS.addActionListener(e -> ejecucion.ejecutarDFS());
        itemMostrar.addActionListener(e -> panel.mostrarRecorrido(true));
        itemOcultar.addActionListener(e -> panel.mostrarRecorrido(false));
        itemLimpiarConexiones.addActionListener(e -> panel.limpiarConexiones());
        itemLimpiarTodo.addActionListener(e -> panel.limpiarTodo());

        menuEdicion.add(itemNodo);
        menuEdicion.add(itemObstaculo);
        menuEdicion.add(itemConectar);
        menuEdicion.add(itemEliminar);

        menuSeleccion.add(itemInicio);
        menuSeleccion.add(itemDestino);

        menuRecorrido.add(itemBFS);
        menuRecorrido.add(itemDFS);
        menuRecorrido.add(itemMostrar);
        menuRecorrido.add(itemOcultar);
        menuRecorrido.add(itemLimpiarConexiones);
        menuRecorrido.add(itemLimpiarTodo);

        add(menuEdicion);
        add(menuSeleccion);
        add(menuRecorrido);
    }
}






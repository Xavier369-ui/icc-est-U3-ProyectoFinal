package views;

import controller.EdicionController;
import controller.EjecucionController;
import controller.GrafoController;
import logic.ModoVisualizacion;

import javax.swing.*;
import java.awt.*;

public class BarraHerramienta extends JPanel {
    public BarraHerramientas(GrafoController grafoController) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnCargar = new JButton("Cargar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEdicion = new JButton("Modo Edicion");
        JButton btnEjecucion = new JButton("Modo Ejecucion");
        JButton btnBFS = new JButton("BFS");
        JButton btnDFS = new JButton("DFS");
        JButton btnLimpiar = new JButton("Limpiar");

        JComboBox<ModoVisualizacion> cmb = new JComboBox<>(ModoVisualizacion.values());

        EdicionController ed = new EdicionController(grafoController);
        EjecucionController ej = new EjecucionController(grafoController);

        btnCargar.addActionListener(e -> grafoController.cargar());
        btnGuardar.addActionListener(e -> grafoController.guardar());
        btnEdicion.addActionListener(e -> ed.activar());
        btnEjecucion.addActionListener(e -> ej.activar());
        btnBFS.addActionListener(e -> ej.ejecutarBFS());
        btnDFS.addActionListener(e -> ej.ejecutarDFS());
        btnLimpiar.addActionListener(e -> grafoController.limpiar());

        cmb.addActionListener(e -> {
            ModoVisualizacion mv = (ModoVisualizacion) cmb.getSelectedItem();
            if (mv != null) grafoController.setModoVisualizacion(mv);
        });

        add(btnCargar);
        add(btnGuardar);
        add(new JSeparator(SwingConstants.VERTICAL));
        add(btnEdicion);
        add(btnEjecucion);
        add(new JLabel("Visualizacion:"));
        add(cmb);
        add(new JSeparator(SwingConstants.VERTICAL));
        add(btnBFS);
        add(btnDFS);
        add(btnLimpiar);

        add(Box.createHorizontalStrut(10));
        add(new JLabel("Edicion: click crea nodo, SHIFT+click conecta, click derecho agrega/quita obstaculo"));
    }
}
package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VerTiemposFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public VerTiemposFrame() {

        setTitle("Tiempos de recorrido");
        setSize(500, 300);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Nodo destino");
        modelo.addColumn("Tiempo BFS (ns)");
        modelo.addColumn("Tiempo DFS (ns)");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla));
    }

    public void agregarFila(String nodo, long bfs, long dfs) {
        modelo.addRow(new Object[]{nodo, bfs, dfs});
    }
}

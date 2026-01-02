import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame {

    private ImageIcon imagenMapa;   // Imagen actual del mapa
    private JPanel panel;           // Panel que dibuja el mapa

    public App() {
        setTitle("Vista del Mapa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Imagen inicial
        imagenMapa = new ImageIcon("mapa.png");

        // Panel que dibuja el mapa ajustado al tamaño del frame
        panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenMapa != null) {
                    g.drawImage(imagenMapa.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Crear barra de menú con estilo azul
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 60, 120));
        menuBar.setForeground(Color.WHITE);

        // Menú Opciones
        JMenu menuOpciones = new JMenu("Opciones");
        menuOpciones.setForeground(Color.WHITE);

        // Ítem Archivos
        JMenuItem itemArchivos = new JMenuItem("Archivos");
        itemArchivos.setBackground(new Color(50, 90, 160));
        itemArchivos.setForeground(Color.WHITE);
        itemArchivos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Aquí podrías implementar la gestión de archivos.",
                    "Archivos",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Ítem Nuevo mapa
        JMenuItem itemNuevoMapa = new JMenuItem("Nuevo mapa");
        itemNuevoMapa.setBackground(new Color(50, 90, 160));
        itemNuevoMapa.setForeground(Color.WHITE);
        itemNuevoMapa.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Aquí podrías implementar la creación de un nuevo mapa.",
                    "Nuevo mapa",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Ítem Salir
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setBackground(new Color(50, 90, 160));
        itemSalir.setForeground(Color.WHITE);
        itemSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que quieres salir?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
            );
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        // Añadir ítems al menú
        menuOpciones.add(itemArchivos);
        menuOpciones.add(itemNuevoMapa);
        menuOpciones.addSeparator();
        menuOpciones.add(itemSalir);

        // Añadir menú a la barra
        menuBar.add(menuOpciones);

        // Asignar barra de menú a la ventana
        setJMenuBar(menuBar);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
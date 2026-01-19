package views;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import controllers.EdicionController;
import logica.Ruta;
import models.Nodo;
import models.Obstaculo;

public class PanelMapa extends JPanel {

    private Image mapa;
    private EdicionController edicion;

    private ArrayList<Nodo> nodos;
    private ArrayList<Obstaculo> obstaculos;

    private Nodo inicio;
    private Nodo destino;

    private Ruta ruta;
    private boolean mostrarRuta;

    private static final int DISTANCIA_VECINO = 60;
    private static final int RADIO_SELECCION = 15;

    public PanelMapa(EdicionController edicion) {

        this.edicion = edicion;
        setLayout(null);

        mapa = new ImageIcon("data/mapa.png").getImage();

        nodos = new ArrayList<Nodo>();
        obstaculos = new ArrayList<Obstaculo>();

        ruta = null;
        mostrarRuta = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                if (edicion.getModoActual() == EdicionController.MODO_NODO) {
                    agregarNodo(new Nodo(x, y));
                }

                if (edicion.getModoActual() == EdicionController.MODO_OBSTACULO) {
                    obstaculos.add(new Obstaculo(x, y));
                }

                if (edicion.getModoActual() == EdicionController.MODO_INICIO) {
                    inicio = buscarNodo(x, y);
                }

                if (edicion.getModoActual() == EdicionController.MODO_DESTINO) {
                    destino = buscarNodo(x, y);
                }

                repaint();
            }
        });
    }

    // 🔹 CONECTA NODOS AUTOMÁTICAMENTE
    private void agregarNodo(Nodo nuevo) {

        for (Nodo existente : nodos) {

            int dx = existente.getX() - nuevo.getX();
            int dy = existente.getY() - nuevo.getY();
            double distancia = Math.sqrt(dx * dx + dy * dy);

            if (distancia <= DISTANCIA_VECINO) {
                existente.agregarVecino(nuevo);
                nuevo.agregarVecino(existente);
            }
        }

        nodos.add(nuevo);
    }

    // 🔹 SELECCIÓN MÁS TOLERANTE
    private Nodo buscarNodo(int x, int y) {

        for (Nodo n : nodos) {
            if (Math.abs(n.getX() - x) <= RADIO_SELECCION &&
                Math.abs(n.getY() - y) <= RADIO_SELECCION) {
                return n;
            }
        }
        return null;
    }

    public Nodo getNodoInicio() {
        return inicio;
    }

    public Nodo getNodoDestino() {
        return destino;
    }

    public void setRuta(Ruta ruta, String metodo) {

        if (inicio == null || destino == null) {
            JOptionPane.showMessageDialog(this,
                "Debe seleccionar nodo de inicio y destino",
                "Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        this.ruta = invertirRuta(ruta);
        this.mostrarRuta = true;
        repaint();
    }

    // 🔹 INVIERTE LA RUTA PARA QUE SE VEA BIEN
    private Ruta invertirRuta(Ruta original) {

        Ruta nueva = new Ruta();
        for (int i = original.getNodos().size() - 1; i >= 0; i--) {
            nueva.agregarNodo(original.getNodos().get(i));
        }
        return nueva;
    }

    public void mostrarRuta() {
        mostrarRuta = true;
        repaint();
    }

    public void ocultarRuta() {
        mostrarRuta = false;
        repaint();
    }

    public void limpiarRuta() {
        ruta = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);

        // Obstáculos
        g.setColor(Color.RED);
        for (Obstaculo o : obstaculos) {
            g.fillOval(o.getX() - 4, o.getY() - 4, 8, 8);
        }

        // Nodos
        g.setColor(Color.BLACK);
        for (Nodo n : nodos) {
            g.fillOval(n.getX() - 5, n.getY() - 5, 10, 10);
        }

        // Inicio
        if (inicio != null) {
            g.setColor(Color.GREEN);
            g.fillOval(inicio.getX() - 7, inicio.getY() - 7, 14, 14);
        }

        // Destino
        if (destino != null) {
            g.setColor(Color.BLUE);
            g.fillOval(destino.getX() - 7, destino.getY() - 7, 14, 14);
        }

        // Ruta BFS / DFS
        if (ruta != null && mostrarRuta) {
            g.setColor(new Color(128, 0, 128));
            for (Nodo n : ruta.getNodos()) {
                g.fillOval(n.getX() - 4, n.getY() - 4, 8, 8);
            }
        }
    }
}







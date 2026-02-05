package views;

import controllers.ConexionController;
import controllers.EdicionController;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import logica.Ruta;
import models.Nodo;
import models.Obstaculo;
import nodes.Node;
import structures.graphs.Graph;

public class PanelMapa extends JPanel {

    private Image mapa;
    private EdicionController edicion;
    private ConexionController conexion;

    // ===== Datos =====
    private List<Nodo> nodos = new ArrayList<>();
    private List<Obstaculo> obstaculos = new ArrayList<>();

    private Graph<Nodo> grafo = new Graph<>();
    private Map<Nodo, Node<Nodo>> mapaNodos = new HashMap<>();

    private Nodo inicio;
    private Nodo destino;

    private Nodo nodoConexionInicio;

    // ===== Recorrido =====
    private List<Node<Nodo>> recorridoExploracion = new ArrayList<>();
    private List<Node<Nodo>> recorridoCompleto = new ArrayList<>();
    private int indiceAnimacion = 0;
    private Timer timer;

    private Ruta rutaFinal;
    private boolean mostrarRecorrido = true;
    private boolean mostrarConexiones = true;

    private static final int RADIO_NODO = 12;

    public PanelMapa(EdicionController edicion, ConexionController conexion) {

        this.edicion = edicion;
        this.conexion = conexion;

        setLayout(new BorderLayout());

        mapa = new ImageIcon(
                PanelMapa.class.getResource("/data/mapa.png")
        ).getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                if (SwingUtilities.isLeftMouseButton(e)) {
                    manejarClickIzquierdo(x, y);
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    manejarClickDerecho(x, y);
                }

                repaint();
            }
        });
    }

    /* ===================== CLICK ===================== */

    private void manejarClickIzquierdo(int x, int y) {

        switch (edicion.getModoActual()) {

            case EdicionController.MODO_NODO ->
                    agregarNodo(new Nodo(x, y));

            case EdicionController.MODO_OBSTACULO ->
                    obstaculos.add(new Obstaculo(x, y));

            case EdicionController.MODO_INICIO ->
                    inicio = buscarNodo(x, y);

            case EdicionController.MODO_DESTINO ->
                    destino = buscarNodo(x, y);

            case EdicionController.MODO_ELIMINAR ->
                    eliminarNodo(x, y);

            default ->
                    manejarConexion(x, y);
        }
    }

    private void manejarClickDerecho(int x, int y) {
        if (conexion.getModo() == ConexionController.ELIMINAR) {
            eliminarConexion(x, y);
        }
    }

    /* ===================== CONEXIONES ===================== */

    private void manejarConexion(int x, int y) {

        if (conexion.getModo() == ConexionController.NINGUNO) return;

        Nodo seleccionado = buscarNodo(x, y);
        if (seleccionado == null) return;

        if (nodoConexionInicio == null) {
            nodoConexionInicio = seleccionado;
            return;
        }

        Node<Nodo> a = mapaNodos.get(nodoConexionInicio);
        Node<Nodo> b = mapaNodos.get(seleccionado);

        if (conexion.getModo() == ConexionController.UNIDIRECCIONAL) {
            grafo.addEdge(a, b);
        }

        if (conexion.getModo() == ConexionController.BIDIRECCIONAL) {
            grafo.addEdge(a, b);
            grafo.addEdge(b, a);
        }

        nodoConexionInicio = null;
    }

    private void eliminarConexion(int x, int y) {

        for (Nodo n : nodos) {

            Node<Nodo> a = mapaNodos.get(n);
            Iterator<Node<Nodo>> it = grafo.getNeighbors2(a).iterator();

            while (it.hasNext()) {
                Node<Nodo> b = it.next();
                if (lineaTocada(n, b.getValue(), x, y)) {
                    it.remove();
                    return;
                }
            }
        }
    }

    private boolean lineaTocada(Nodo a, Nodo b, int x, int y) {
        return Line2D.ptSegDist(
                a.getX(), a.getY(),
                b.getX(), b.getY(),
                x, y
        ) <= 5;
    }

    /* ===================== BFS / DFS ===================== */

    public void ejecutarBFS() {
        ejecutarRecorrido(true);
    }

    public void ejecutarDFS() {
        ejecutarRecorrido(false);
    }

    private void ejecutarRecorrido(boolean bfs) {

        if (inicio == null || destino == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un nodo de inicio y uno de destino",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (timer != null && timer.isRunning()) timer.stop();

        recorridoExploracion.clear();
        recorridoCompleto.clear();
        rutaFinal = null;
        indiceAnimacion = 0;

        if (bfs) {
            rutaFinal = grafo.bfsRuta(
                    mapaNodos.get(inicio),
                    mapaNodos.get(destino),
                    recorridoCompleto,
                    obtenerBloqueados()
            );
        } else {
            rutaFinal = grafo.dfsRuta(
                    mapaNodos.get(inicio),
                    mapaNodos.get(destino),
                    recorridoCompleto,
                    obtenerBloqueados()
            );
        }

        timer = new Timer(350, e -> {
            if (indiceAnimacion < recorridoCompleto.size()) {
                recorridoExploracion.add(
                        recorridoCompleto.get(indiceAnimacion)
                );
                indiceAnimacion++;
                repaint();
            } else {
                timer.stop();
                repaint();
            }
        });

        timer.start();
    }

    private Set<Nodo> obtenerBloqueados() {
        Set<Nodo> bloqueados = new HashSet<>();
        for (Obstaculo o : obstaculos) {
            bloqueados.add(new Nodo(o.getX(), o.getY()));
        }
        return bloqueados;
    }

    /* ===================== NODOS ===================== */

    private void agregarNodo(Nodo n) {
        nodos.add(n);
        Node<Nodo> gn = new Node<>(n);
        mapaNodos.put(n, gn);
        grafo.addNode(gn);
    }

    private void eliminarNodo(int x, int y) {
        Nodo n = buscarNodo(x, y);
        if (n == null) return;

        nodos.remove(n);
        mapaNodos.remove(n);

        if (n == inicio) inicio = null;
        if (n == destino) destino = null;
    }

    private Nodo buscarNodo(int x, int y) {
        for (Nodo n : nodos)
            if (Math.hypot(n.getX() - x, n.getY() - y) <= RADIO_NODO)
                return n;
        return null;
    }

    /* ===================== VISUAL ===================== */

    public void ocultarConexiones() {
        mostrarConexiones = false;
        repaint();
    }

    public void mostrarConexiones() {
        mostrarConexiones = true;
        repaint();
    }

    /* ===================== DIBUJO ===================== */

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        if (mostrarConexiones) {
            g2.setColor(Color.BLACK);
            for (Nodo n : nodos) {
                Node<Nodo> a = mapaNodos.get(n);
                for (Node<Nodo> b : grafo.getNeighbors2(a)) {
                    dibujarFlecha(g2, n, b.getValue());
                }
            }
        }

        if (mostrarRecorrido) {
            g2.setColor(Color.BLUE);
            for (int i = 1; i < recorridoExploracion.size(); i++) {
                Nodo a = recorridoExploracion.get(i - 1).getValue();
                Nodo b = recorridoExploracion.get(i).getValue();
                g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
            }
        }

        if (rutaFinal != null) {
            g2.setColor(new Color(128, 0, 128));
            List<Nodo> r = rutaFinal.getNodos();
            for (int i = 0; i < r.size() - 1; i++) {
                g2.drawLine(
                        r.get(i).getX(), r.get(i).getY(),
                        r.get(i + 1).getX(), r.get(i + 1).getY()
                );
            }
        }

        g2.setColor(Color.RED);
        for (Obstaculo o : obstaculos)
            g2.fillOval(o.getX() - 4, o.getY() - 4, 8, 8);

        g2.setColor(Color.BLACK);
        for (Nodo n : nodos)
            g2.fillOval(
                    n.getX() - RADIO_NODO,
                    n.getY() - RADIO_NODO,
                    RADIO_NODO * 2,
                    RADIO_NODO * 2
            );

        if (inicio != null) {
            g2.setColor(Color.BLUE);
            g2.fillOval(inicio.getX() - 16, inicio.getY() - 16, 32, 32);
        }

        if (destino != null) {
            g2.setColor(Color.GREEN);
            g2.fillOval(destino.getX() - 16, destino.getY() - 16, 32, 32);
        }
    }

    private void dibujarFlecha(Graphics2D g, Nodo a, Nodo b) {

        g.drawLine(a.getX(), a.getY(), b.getX(), b.getY());

        double ang = Math.atan2(
                b.getY() - a.getY(),
                b.getX() - a.getX()
        );

        int f = 10;

        int x1 = (int) (b.getX() - f * Math.cos(ang - Math.PI / 6));
        int y1 = (int) (b.getY() - f * Math.sin(ang - Math.PI / 6));

        int x2 = (int) (b.getX() - f * Math.cos(ang + Math.PI / 6));
        int y2 = (int) (b.getY() - f * Math.sin(ang + Math.PI / 6));

        g.drawLine(b.getX(), b.getY(), x1, y1);
        g.drawLine(b.getX(), b.getY(), x2, y2);
    }
}

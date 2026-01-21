package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.Timer;
import controllers.EdicionController;
import logica.Ruta;
import models.Nodo;
import models.Obstaculo;
import structures.graphs.Graph;
import nodes.Node;

public class PanelMapa extends JPanel {

    private Image mapa;
    private EdicionController edicion;

    private java.util.List<Nodo> nodos;
    private java.util.List<Obstaculo> obstaculos;

    private Graph<Nodo> grafo;
    private Map<Nodo, Node<Nodo>> mapaNodos;

    private Nodo inicio;
    private Nodo destino;
    private Nodo nodoConexionInicio;

    // 🔵 animación
    private java.util.List<Node<Nodo>> recorridoExploracion;
    private java.util.List<Node<Nodo>> recorridoCompleto;
    private int indiceAnimacion;
    private Timer timer;

    // 🟪 ruta final
    private Ruta rutaFinal;

    private boolean mostrarExploracion = true;

    private static final int RADIO_NODO = 12;

    public PanelMapa(EdicionController edicion) {

        this.edicion = edicion;
        setLayout(null);

        mapa = new ImageIcon("data/mapa.png").getImage();

        nodos = new ArrayList<>();
        obstaculos = new ArrayList<>();
        recorridoExploracion = new ArrayList<>();

        grafo = new Graph<>();
        mapaNodos = new HashMap<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                switch (edicion.getModoActual()) {

                    case EdicionController.MODO_NODO:
                        agregarNodo(new Nodo(x, y));
                        break;

                    case EdicionController.MODO_OBSTACULO:
                        obstaculos.add(new Obstaculo(x, y));
                        break;

                    case EdicionController.MODO_CONECTAR:
                        conectarNodos(x, y);
                        break;

                    case EdicionController.MODO_INICIO:
                        inicio = buscarNodo(x, y);
                        break;

                    case EdicionController.MODO_DESTINO:
                        destino = buscarNodo(x, y);
                        break;

                    case EdicionController.MODO_ELIMINAR:
                        eliminarNodo(x, y);
                        break;
                }
                repaint();
            }
        });
    }

    /* ===================== NODOS ===================== */

    private void agregarNodo(Nodo n) {
        nodos.add(n);
        Node<Nodo> gn = new Node<>(n);
        mapaNodos.put(n, gn);
        grafo.addNode(gn);
        guardarCoordenada(n);
    }

    private void eliminarNodo(int x, int y) {
        Nodo n = buscarNodo(x, y);
        if (n == null) return;

        nodos.remove(n);
        mapaNodos.remove(n);
        recorridoExploracion.clear();
        rutaFinal = null;

        if (inicio == n) inicio = null;
        if (destino == n) destino = null;
    }

    private Nodo buscarNodo(int x, int y) {
        for (Nodo n : nodos) {
            if (Math.hypot(n.getX() - x, n.getY() - y) <= RADIO_NODO)
                return n;
        }
        return null;
    }

    private void guardarCoordenada(Nodo n) {
        try (FileWriter fw = new FileWriter("data/coordenadas.txt", true)) {
            fw.write(n.getX() + "," + n.getY() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ===================== CONEXIONES ===================== */

    private void conectarNodos(int x, int y) {

        Nodo seleccionado = buscarNodo(x, y);
        if (seleccionado == null) return;

        if (nodoConexionInicio == null) {
            nodoConexionInicio = seleccionado;
        } else {
            grafo.addEdge(
                    mapaNodos.get(nodoConexionInicio),
                    mapaNodos.get(seleccionado)
            );
            nodoConexionInicio = null;
        }
    }

    public void limpiarConexiones() {
        grafo.clearEdges();
        repaint();
    }

    /* ===================== BFS / DFS ANIMADO ===================== */

    public void ejecutarBFS() {
        ejecutarRecorrido(true);
    }

    public void ejecutarDFS() {
        ejecutarRecorrido(false);
    }

    private void ejecutarRecorrido(boolean bfs) {

        if (inicio == null || destino == null) return;

        if (timer != null && timer.isRunning())
            timer.stop();

        recorridoExploracion.clear();
        rutaFinal = null;

        recorridoCompleto = new ArrayList<>();

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

        indiceAnimacion = 0;

        timer = new Timer(400, e -> {
            if (indiceAnimacion < recorridoCompleto.size()) {
                recorridoExploracion.add(recorridoCompleto.get(indiceAnimacion));
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

    public void mostrarRecorrido(boolean mostrar) {
        mostrarExploracion = mostrar;
        repaint();
    }

    public void limpiarTodo() {
        nodos.clear();
        obstaculos.clear();
        recorridoExploracion.clear();
        rutaFinal = null;
        grafo = new Graph<>();
        mapaNodos.clear();
        repaint();
    }

    /* ===================== DIBUJO ===================== */

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        // Conexiones
        g2.setColor(Color.BLACK);
        for (Nodo n : nodos) {
            Node<Nodo> gn = mapaNodos.get(n);
            for (Node<Nodo> v : grafo.getNeighbors2(gn)) {
                dibujarFlecha(g2, n, v.getValue());
            }
        }

        // 🔵 exploración animada
        if (mostrarExploracion) {
            g2.setColor(Color.BLUE);
            for (int i = 1; i < recorridoExploracion.size(); i++) {
                Nodo a = recorridoExploracion.get(i - 1).getValue();
                Nodo b = recorridoExploracion.get(i).getValue();
                dibujarFlecha(g2, a, b);
            }
        }

        // 🟪 ruta final
        if (rutaFinal != null && (timer == null || !timer.isRunning())) {
            g2.setColor(new Color(128, 0, 128));
            java.util.List<Nodo> r = rutaFinal.getNodos();
            for (int i = 0; i < r.size() - 1; i++) {
                dibujarFlecha(g2, r.get(i), r.get(i + 1));
            }
        }


        // Obstáculos
        g2.setColor(Color.RED);
        for (Obstaculo o : obstaculos)
            g2.fillOval(o.getX() - 4, o.getY() - 4, 8, 8);

        // Nodos + coordenadas
        for (Nodo n : nodos) {
            g2.setColor(Color.BLACK);
            g2.fillOval(
                    n.getX() - RADIO_NODO,
                    n.getY() - RADIO_NODO,
                    RADIO_NODO * 2,
                    RADIO_NODO * 2
            );
            g2.drawString(
                    "(" + n.getX() + "," + n.getY() + ")",
                    n.getX() + 10,
                    n.getY()
            );
        }

        // Inicio / Destino
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
    }
}











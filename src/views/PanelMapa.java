package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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

/**
 * Panel principal del mapa
 */
public class PanelMapa extends JPanel {

    private Image mapa;
    private EdicionController edicion;

    // === Datos ===
    private java.util.List<Nodo> nodos = new ArrayList<>();
    private java.util.List<Obstaculo> obstaculos = new ArrayList<>();

    private Graph<Nodo> grafo = new Graph<>();
    private Map<Nodo, Node<Nodo>> mapaNodos = new HashMap<>();

    private Nodo inicio;
    private Nodo destino;
    private Nodo nodoConexionInicio;

    // === Animación ===
    private java.util.List<Node<Nodo>> recorridoExploracion = new ArrayList<>();
    private java.util.List<Node<Nodo>> recorridoCompleto = new ArrayList<>();
    private int indiceAnimacion = 0;
    private Timer timer;

    private Ruta rutaFinal;
    private boolean mostrarRecorrido = true;

    private static final int RADIO_NODO = 12;

    public PanelMapa(EdicionController edicion) {

        this.edicion = edicion;
        setLayout(new BorderLayout());


        mapa = new ImageIcon(PanelMapa.class.getResource("/data/mapa.png")).getImage();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                switch (edicion.getModoActual()) {

                    case EdicionController.MODO_NODO -> agregarNodo(new Nodo(x, y));
                    case EdicionController.MODO_OBSTACULO -> obstaculos.add(new Obstaculo(x, y));
                    case EdicionController.MODO_CONECTAR -> conectarNodos(x, y);
                    case EdicionController.MODO_INICIO -> inicio = buscarNodo(x, y);
                    case EdicionController.MODO_DESTINO -> destino = buscarNodo(x, y);
                    case EdicionController.MODO_ELIMINAR -> eliminarNodo(x, y);
                }
                repaint();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });

    }

    /* ===================== BFS / DFS ===================== */

    public void ejecutarBFS() {
        ejecutarRecorrido(true);
    }

    public void ejecutarDFS() {
        ejecutarRecorrido(false);
    }

    /**
     * Ejecuta BFS o DFS
     * bfs = true -> BFS
     * bfs = false -> DFS
     */
    private void ejecutarRecorrido(boolean bfs) {

        //  VALIDACIÓN: inicio y destino obligatorios
        if (inicio == null || destino == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un nodo de inicio y un nodo destino.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (timer != null && timer.isRunning())
            timer.stop();

        recorridoExploracion.clear();
        recorridoCompleto.clear();
        rutaFinal = null;
        indiceAnimacion = 0;

        // Ejecuta BFS o DFS
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

        // Animación paso a paso
        timer = new Timer(400, e -> {
            if (indiceAnimacion < recorridoCompleto.size()) {
                recorridoExploracion.add(recorridoCompleto.get(indiceAnimacion));
                indiceAnimacion++;
                repaint();
            } else {
                timer.stop();

                //  MENSAJES SOLICITADOS
                if (rutaFinal == null || rutaFinal.getNodos().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró ningún camino.",
                            "Resultado",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Camino encontrado con éxito.",
                            "Resultado",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

                repaint();
            }
        });

        timer.start();
    }

    private Set<Nodo> obtenerBloqueados() {
        Set<Nodo> bloqueados = new HashSet<>();
        for (Obstaculo o : obstaculos)
            bloqueados.add(new Nodo(o.getX(), o.getY()));
        return bloqueados;
    }

    /* ===================== MÉTODOS DEL MENÚ  ===================== */

    public void mostrarRecorrido(boolean mostrar) {
        this.mostrarRecorrido = mostrar;
        repaint();
    }

    public void limpiarConexiones() {
        grafo.clearEdges();
        repaint();
    }

    public void limpiarTodo() {
        nodos.clear();
        obstaculos.clear();
        recorridoExploracion.clear();
        recorridoCompleto.clear();
        rutaFinal = null;
        inicio = null;
        destino = null;
        grafo = new Graph<>();
        mapaNodos.clear();
        repaint();
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
        for (Nodo n : nodos)
            if (Math.hypot(n.getX() - x, n.getY() - y) <= RADIO_NODO)
                return n;
        return null;
    }

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

    private void guardarCoordenada(Nodo n) {

        try {
        
            String home = System.getProperty("user.home");

            // Ruta: Documentos/CarpetaCoordenadas
            File carpeta = new File(home + File.separator + "Documents"
                                + File.separator + "CarpetaCoordenadas");

            
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Archivo coordenadas.txt dentro de la carpeta
            File archivo = new File(carpeta, "coordenadas.txt");

            FileWriter fw = new FileWriter(archivo, true);
            fw.write(n.getX() + "," + n.getY() + "\n");
            fw.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar coordenadas:\n" + e.getMessage(),
                "Error de persistencia",
                JOptionPane.ERROR_MESSAGE
            );
        }
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
            for (Node<Nodo> v : grafo.getNeighbors2(gn))
                g2.drawLine(n.getX(), n.getY(), v.getValue().getX(), v.getValue().getY());
        }

        // Recorrido azul
        if (mostrarRecorrido) {
            g2.setColor(Color.BLUE);
            for (int i = 1; i < recorridoExploracion.size(); i++) {
                Nodo a = recorridoExploracion.get(i - 1).getValue();
                Nodo b = recorridoExploracion.get(i).getValue();
                g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
            }
        }

        // Ruta final morada
        if (rutaFinal != null && (timer == null || !timer.isRunning())) {
            g2.setColor(new Color(128, 0, 128));
            var r = rutaFinal.getNodos();
            for (int i = 0; i < r.size() - 1; i++)
                g2.drawLine(
                        r.get(i).getX(), r.get(i).getY(),
                        r.get(i + 1).getX(), r.get(i + 1).getY()
                );
        }

        // Obstáculos
        g2.setColor(Color.RED);
        for (Obstaculo o : obstaculos)
            g2.fillOval(o.getX() - 4, o.getY() - 4, 8, 8);

        // Nodos + coordenadas
        g2.setColor(Color.BLACK);
        for (Nodo n : nodos) {
            g2.fillOval(
                    n.getX() - RADIO_NODO,
                    n.getY() - RADIO_NODO,
                    RADIO_NODO * 2,
                    RADIO_NODO * 2
            );
            g2.drawString("(" + n.getX() + "," + n.getY() + ")", n.getX() + 10, n.getY());
        }

        // Inicio / destino
        if (inicio != null) {
            g2.setColor(Color.BLUE);
            g2.fillOval(inicio.getX() - 16, inicio.getY() - 16, 32, 32);
        }

        if (destino != null) {
            g2.setColor(Color.GREEN);
            g2.fillOval(destino.getX() - 16, destino.getY() - 16, 32, 32);
        }
    }

    /**
     * Limpia SOLO el recorrido y el inicio/destino
     
     */
    public void iniciarOtroRecorrido() {

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        recorridoExploracion.clear();
        recorridoCompleto.clear();
        rutaFinal = null;
        indiceAnimacion = 0;

        inicio = null;
        destino = null;

        repaint();
    }
}




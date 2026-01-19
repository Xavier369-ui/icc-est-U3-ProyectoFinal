package controllers;

import logica.BFS;
import logica.DFS;
import logica.ModoVisualizacion;
import logica.Ruta;
import models.Grafo;
import models.Nodo;
import models.Obstaculo;
import persistence.FileManager;
import views.PanelMapa;

import javax.swing.*;

import Dao.GrafoDAO;

import java.util.ArrayList;
import java.util.List;

public class GrafoController {
    public enum Modo { EDICION, EJECUCION }

    private final FileManager fileManager;
    private final GrafoDAO grafoDAO;
    private Grafo grafo;
    private PanelMapa panel;

    private Modo modo = Modo.EDICION;
    private ModoVisualizacion modoVisualizacion = ModoVisualizacion.EXPLORACION;

    private int nextId = 1;
    private Nodo selA = null;
    private Nodo inicio = null;
    private Nodo destino = null;

    public GrafoController(String dataDir) {
        this.fileManager = new FileManager(dataDir);
        this.grafoDAO = new GrafoDAO(fileManager);
        this.grafo = grafoDAO.cargar();
        this.nextId = calcularNextId();
    }

    public void bindPanel(PanelMapa panel) {
        this.panel = panel;
        panel.setGrafo(grafo);
        panel.setBackgroundImage(fileManager.loadImage("map.png"));
        panel.repaint();
    }

    public Grafo getGrafo() { return grafo; }
    public FileManager getFileManager() { return fileManager; }

    public Modo getModo() { return modo; }
    public void setModo(Modo m) {
        this.modo = m;
        this.selA = null;
        this.inicio = null;
        this.destino = null;
        if (panel != null) {
            panel.clearHighlights();
            panel.repaint();
        }
    }

    public void setModoVisualizacion(ModoVisualizacion mv) {
        this.modoVisualizacion = mv;
    }

    public void cargar() {
        this.grafo = grafoDAO.cargar();
        this.nextId = calcularNextId();
        if (panel != null) {
            panel.setGrafo(grafo);
            panel.clearHighlights();
            panel.repaint();
        }
    }

    public void guardar() {
        grafoDAO.guardar(grafo);
    }

    public void limpiar() {
        grafo.clear();
        nextId = 1;
        selA = null;
        inicio = null;
        destino = null;
        if (panel != null) {
            panel.clearHighlights();
            panel.repaint();
        }
    }

    public void onClick(int x, int y, boolean rightClick, boolean shift) {
        if (panel == null) return;

        if (modo == Modo.EDICION) {
            if (rightClick) {
                toggleObstaculo(x, y);
                panel.repaint();
                return;
            }

            Nodo hit = panel.findNodoAt(x, y);
            if (hit == null) {
                crearNodo(x, y);
            } else {
                if (shift) {
                    conectarConSeleccion(hit);
                } else {
                    selA = hit;
                    panel.setSelectedNodo(selA);
                }
            }
            panel.repaint();
        } else {
            Nodo hit = panel.findNodoAt(x, y);
            if (hit == null) return;

            if (inicio == null) {
                inicio = hit;
                panel.setStartNodo(inicio);
            } else if (destino == null) {
                destino = hit;
                panel.setEndNodo(destino);
            } else {
                inicio = hit;
                destino = null;
                panel.setStartNodo(inicio);
                panel.setEndNodo(null);
                panel.clearHighlights();
            }
            panel.repaint();
        }
    }

    private void crearNodo(int x, int y) {
        String id = "N" + nextId++;
        Nodo n = new Nodo(id, x, y);
        grafo.addNodo(n);
    }

    private void conectarConSeleccion(Nodo b) {
        if (selA == null || selA.equals(b)) {
            selA = b;
            panel.setSelectedNodo(selA);
            return;
        }
        grafo.addArista(selA, b);
        selA = null;
        panel.setSelectedNodo(null);
    }

    private void toggleObstaculo(int x, int y) {
        boolean removed = grafo.getObstaculos().removeIf(o -> o.contains(x, y));
        if (!removed) {
            grafo.addObstaculo(new Obstaculo(x - 20, y - 20, 40, 40));
        }
    }

    public void ejecutarBFS() { ejecutar(true); }
    public void ejecutarDFS() { ejecutar(false); }

    private void ejecutar(boolean bfs) {
        if (panel == null) return;
        if (inicio == null || destino == null) {
            JOptionPane.showMessageDialog(panel, "Selecciona INICIO y DESTINO en modo ejecucion.");
            return;
        }

        panel.clearHighlights();

        long t0 = System.nanoTime();
        Ruta ruta = bfs ? new BFS().buscar(grafo, inicio, destino) : new DFS().buscar(grafo, inicio, destino);
        long t1 = System.nanoTime();

        int aristas = 0;
        for (Nodo n : grafo.getNodos()) aristas += grafo.getAristasDesde(n).size();
        aristas /= 2;
        fileManager.appendTimingCsv(bfs ? "BFS" : "DFS", grafo.getNodos().size(), aristas, (t1 - t0));

        if (!ruta.tieneRuta()) {
            panel.setVisitedNodes(ruta.getVisitados());
            panel.setPathNodes(new ArrayList<>());
            panel.repaint();
            JOptionPane.showMessageDialog(panel, "No hay camino posible entre los nodos seleccionados.");
            return;
        }

        if (modoVisualizacion == ModoVisualizacion.RUTA_FINAL) {
            panel.setVisitedNodes(new ArrayList<>());
            panel.setPathNodes(ruta.getRutaFinal());
            panel.repaint();
            return;
        }

        animarExploracion(ruta.getVisitados(), ruta.getRutaFinal());
    }

    private void animarExploracion(List<Nodo> visitados, List<Nodo> rutaFinal) {
        panel.setVisitedNodes(new ArrayList<>());
        panel.setPathNodes(new ArrayList<>());

        List<Nodo> buffer = new ArrayList<>();
        final int[] i = {0};
        Timer timer = new Timer(120, null);
        timer.addActionListener(e -> {
            if (i[0] >= visitados.size()) {
                timer.stop();
                panel.setPathNodes(rutaFinal);
                panel.repaint();
                return;
            }
            buffer.add(visitados.get(i[0]++));
            panel.setVisitedNodes(buffer);
            panel.repaint();
        });
        timer.start();
    }

    private int calcularNextId() {
        int max = 0;
        for (Nodo n : grafo.getNodos()) {
            String id = n.getId();
            if (id != null && id.startsWith("N")) {
                try {
                    int v = Integer.parseInt(id.substring(1));
                    if (v > max) max = v;
                } catch (NumberFormatException ignored) {}
            }
        }
        return max + 1;
    }
}

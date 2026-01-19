package ui;

import controller.GrafoController;
import model.Arista;
import model.Grafo;
import model.Nodo;
import model.Obstaculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PanelMapa extends JPanel {
    private Grafo grafo;
    private GrafoController controller;

    private BufferedImage backgroundImage;

    private Nodo selectedNodo;
    private Nodo startNodo;
    private Nodo endNodo;

    private List<Nodo> visited = new ArrayList<>();
    private List<Nodo> path = new ArrayList<>();

    public PanelMapa() {
        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (controller == null) return;
                boolean right = SwingUtilities.isRightMouseButton(e);
                boolean shift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
                controller.onClick(e.getX(), e.getY(), right, shift);
            }
        });
    }

    public void setController(GrafoController controller) {
        this.controller = controller;
    }

    public void setGrafo(Grafo grafo) { this.grafo = grafo; }
    public void setBackgroundImage(BufferedImage img) { this.backgroundImage = img; }

    public void setSelectedNodo(Nodo n) { this.selectedNodo = n; }
    public void setStartNodo(Nodo n) { this.startNodo = n; }
    public void setEndNodo(Nodo n) { this.endNodo = n; }

    public void setVisitedNodes(List<Nodo> list) { this.visited = new ArrayList<>(list); }
    public void setPathNodes(List<Nodo> list) { this.path = new ArrayList<>(list); }

    public void clearHighlights() {
        visited.clear();
        path.clear();
    }

    public Nodo findNodoAt(int x, int y) {
        if (grafo == null) return null;
        int r = 10;
        for (Nodo n : grafo.getNodos()) {
            int dx = n.getX() - x;
            int dy = n.getY() - y;
            if (dx * dx + dy * dy <= r * r) return n;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            drawGrid(g2);
            g2.setColor(new Color(30, 30, 30));
            g2.drawString("Coloca data/map.png para usarlo como fondo", 12, 18);
        }

        if (grafo == null) {
            g2.dispose();
            return;
        }

        // Obstaculos
        g2.setColor(new Color(0, 0, 0, 70));
        for (Obstaculo o : grafo.getObstaculos()) {
            g2.fillRect(o.getX(), o.getY(), o.getW(), o.getH());
        }

        // Aristas
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(40, 40, 40, 180));
        for (Nodo a : grafo.getNodos()) {
            for (Arista ar : grafo.getAristasDesde(a)) {
                Nodo b = ar.getHasta();
                if (a.getId().compareTo(b.getId()) < 0) {
                    if (grafo.edgeBlocked(a, b)) {
                        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{6f, 6f}, 0));
                        g2.setColor(new Color(140, 0, 0, 160));
                    } else {
                        g2.setStroke(new BasicStroke(2f));
                        g2.setColor(new Color(40, 40, 40, 180));
                    }
                    g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
                }
            }
        }

        // Visitados (exploracion)
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(new Color(0, 140, 0, 160));
        for (int i = 0; i < visited.size() - 1; i++) {
            Nodo a = visited.get(i);
            Nodo b = visited.get(i + 1);
            g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
        }

        // Ruta final
        g2.setStroke(new BasicStroke(6f));
        g2.setColor(new Color(110, 0, 160, 200));
        for (int i = 0; i < path.size() - 1; i++) {
            Nodo a = path.get(i);
            Nodo b = path.get(i + 1);
            g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
        }

        // Nodos
        for (Nodo n : grafo.getNodos()) {
            drawNodo(g2, n);
        }

        g2.dispose();
    }

    private void drawNodo(Graphics2D g2, Nodo n) {
        int r = 10;
        boolean isSelected = selectedNodo != null && selectedNodo.equals(n);
        boolean isStart = startNodo != null && startNodo.equals(n);
        boolean isEnd = endNodo != null && endNodo.equals(n);

        if (isStart) g2.setColor(new Color(0, 110, 0));
        else if (isEnd) g2.setColor(new Color(160, 0, 0));
        else if (isSelected) g2.setColor(new Color(0, 90, 170));
        else g2.setColor(new Color(230, 230, 230));

        g2.fillOval(n.getX() - r, n.getY() - r, 2 * r, 2 * r);
        g2.setColor(new Color(40, 40, 40));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(n.getX() - r, n.getY() - r, 2 * r, 2 * r);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12f));
        g2.drawString(n.getId(), n.getX() + r + 3, n.getY() - r - 2);
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(230, 230, 230));
        int step = 25;
        for (int x = 0; x < getWidth(); x += step) g2.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += step) g2.drawLine(0, y, getWidth(), y);
    }
}

package models;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Obstaculo {
    private final Rectangle rect;

    public Obstaculo(int x, int y, int w, int h) {
        this.rect = new Rectangle(x, y, w, h);
    }

    public int getX() { return rect.x; }
    public int getY() { return rect.y; }
    public int getW() { return rect.width; }
    public int getH() { return rect.height; }

    public Rectangle getRect() { return rect; }

    public boolean contains(int px, int py) {
        return rect.contains(px, py);
    }

    public boolean blocksEdge(Nodo a, Nodo b) {
        Line2D line = new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY());
        Rectangle2D r = new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);
        if (r.contains(a.getX(), a.getY()) || r.contains(b.getX(), b.getY())) return true;
        return line.intersects(r);
    }

    @Override
    public String toString() {
        return "Obstaculo{" + rect.x + "," + rect.y + "," + rect.width + "," + rect.height + "}";
    }
}

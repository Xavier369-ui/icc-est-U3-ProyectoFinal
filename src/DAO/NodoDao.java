package dao;

import model.Nodo;
import persistence.FileManager;

import java.util.ArrayList;
import java.util.List;

public class NodoDAO {
    private final FileManager fm;

    public NodoDAO(FileManager fm) {
        this.fm = fm;
    }

    public List<Nodo> cargar() {
        List<String> lines = fm.readLines("nodes.csv");
        List<Nodo> out = new ArrayList<>();
        for (String l : lines) {
            if (l.trim().isEmpty() || l.startsWith("#")) continue;
            String[] p = l.split(";");
            if (p.length < 3) continue;
            String id = p[0];
            int x = Integer.parseInt(p[1]);
            int y = Integer.parseInt(p[2]);
            out.add(new Nodo(id, x, y));
        }
        return out;
    }

    public void guardar(List<Nodo> nodos) {
        List<String> lines = new ArrayList<>();
        lines.add("# id;x;y");
        for (Nodo n : nodos) {
            lines.add(n.getId() + ";" + n.getX() + ";" + n.getY());
        }
        fm.writeLines("nodes.csv", lines);
    }
}

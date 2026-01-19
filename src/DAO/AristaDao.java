package dao;

import model.Grafo;
import model.Nodo;
import persistence.FileManager;

import java.util.*;

public class AristaDAO {
    private final FileManager fm;

    public AristaDAO(FileManager fm) {
        this.fm = fm;
    }

    public void cargar(Grafo grafo) {
        List<String> lines = fm.readLines("edges.csv");
        for (String l : lines) {
            if (l.trim().isEmpty() || l.startsWith("#")) continue;
            String[] p = l.split(";");
            if (p.length < 2) continue;
            Nodo a = grafo.getNodo(p[0]);
            Nodo b = grafo.getNodo(p[1]);
            if (a != null && b != null) grafo.addArista(a, b);
        }
    }

    public void guardar(Grafo grafo) {
        Set<String> seen = new HashSet<>();
        List<String> out = new ArrayList<>();
        out.add("# desdeId;hastaId (no duplicadas)");
        for (Nodo a : grafo.getNodos()) {
            grafo.getAristasDesde(a).forEach(edge -> {
                Nodo b = edge.getHasta();
                String id1 = a.getId();
                String id2 = b.getId();
                String key = (id1.compareTo(id2) <= 0) ? (id1 + "|" + id2) : (id2 + "|" + id1);
                if (!id1.equals(id2) && seen.add(key)) out.add(key.replace("|", ";"));
            });
        }
        fm.writeLines("edges.csv", out);
    }
}

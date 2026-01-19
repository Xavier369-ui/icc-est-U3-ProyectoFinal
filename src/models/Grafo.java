package models;

import java.util.*;

public class Grafo {
    private final Map<String, Nodo> nodos = new LinkedHashMap<>();
    private final Map<Nodo, List<Arista>> ady = new HashMap<>();
    private final List<Obstaculo> obstaculos = new ArrayList<>();

    public Collection<Nodo> getNodos() { return nodos.values(); }
    public List<Arista> getAristasDesde(Nodo n) { return ady.getOrDefault(n, Collections.emptyList()); }
    public List<Obstaculo> getObstaculos() { return obstaculos; }

    public Nodo getNodo(String id) { return nodos.get(id); }

    public Nodo addNodo(Nodo n) {
        nodos.put(n.getId(), n);
        ady.putIfAbsent(n, new ArrayList<>());
        return n;
    }

    public boolean removeNodo(String id) {
        Nodo n = nodos.remove(id);
        if (n == null) return false;
        ady.remove(n);
        for (List<Arista> list : ady.values()) {
            list.removeIf(a -> a.getHasta().equals(n));
        }
        return true;
    }

    public void addArista(Nodo a, Nodo b) {
        ady.putIfAbsent(a, new ArrayList<>());
        ady.putIfAbsent(b, new ArrayList<>());
        ady.get(a).add(new Arista(a, b));
        ady.get(b).add(new Arista(b, a));
    }

    public void clear() {
        nodos.clear();
        ady.clear();
        obstaculos.clear();
    }

    public void addObstaculo(Obstaculo o) { obstaculos.add(o); }
    public void removeObstaculoAt(int x, int y) { obstaculos.removeIf(o -> o.contains(x, y)); }

    public boolean edgeBlocked(Nodo a, Nodo b) {
        for (Obstaculo o : obstaculos) {
            if (o.blocksEdge(a, b)) return true;
        }
        return false;
    }
}


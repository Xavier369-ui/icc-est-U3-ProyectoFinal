package structures.graphs;

import java.util.*;
import nodes.Node;
import models.Nodo;
import logica.Ruta;

public class Graph<T> {

    private Map<Node<T>, List<Node<T>>> mapa = new HashMap<>();

    public void addNode(Node<T> n) {
        mapa.putIfAbsent(n, new ArrayList<>());
    }

    public void addEdge(Node<T> a, Node<T> b) {
        addNode(a);
        addNode(b);
        mapa.get(a).add(b);
    }

    public List<Node<T>> getNeighbors2(Node<T> n) {
        return mapa.getOrDefault(n, List.of());
    }

    public void clearEdges() {
        for (List<Node<T>> l : mapa.values()) {
            l.clear();
        }
    }

    /* ================= BFS ================= */

    public Ruta bfsRuta(
            Node<T> inicio,
            Node<T> destino,
            List<Node<T>> exploracion,
            Set<T> bloqueados
    ) {

        Map<Node<T>, Node<T>> padre = new HashMap<>();
        Queue<Node<T>> cola = new LinkedList<>();
        Set<Node<T>> visitados = new HashSet<>();

        cola.add(inicio);
        visitados.add(inicio);
        padre.put(inicio, null);

        while (!cola.isEmpty()) {
            Node<T> actual = cola.poll();
            exploracion.add(actual);

            if (actual.equals(destino)) break;

            for (Node<T> vecino : getNeighbors2(actual)) {

                if (visitados.contains(vecino)) continue;
                if (bloqueados.contains(vecino.getValue())) continue;

                visitados.add(vecino);
                padre.put(vecino, actual);
                cola.add(vecino);
            }
        }

        return construirRuta(destino, padre);
    }

    /* ================= DFS ================= */

    public Ruta dfsRuta(
            Node<T> inicio,
            Node<T> destino,
            List<Node<T>> exploracion,
            Set<T> bloqueados
    ) {

        Map<Node<T>, Node<T>> padre = new HashMap<>();
        Set<Node<T>> visitados = new HashSet<>();

        dfsRec(inicio, destino, visitados, padre, exploracion, bloqueados);
        return construirRuta(destino, padre);
    }

    private boolean dfsRec(
            Node<T> actual,
            Node<T> destino,
            Set<Node<T>> visitados,
            Map<Node<T>, Node<T>> padre,
            List<Node<T>> exploracion,
            Set<T> bloqueados
    ) {

        visitados.add(actual);
        exploracion.add(actual);

        if (actual.equals(destino)) return true;

        for (Node<T> vecino : getNeighbors2(actual)) {

            if (visitados.contains(vecino)) continue;
            if (bloqueados.contains(vecino.getValue())) continue;

            padre.put(vecino, actual);

            if (dfsRec(vecino, destino, visitados, padre, exploracion, bloqueados))
                return true;
        }
        return false;
    }

    private Ruta construirRuta(Node<T> destino, Map<Node<T>, Node<T>> padre) {

        List<Nodo> camino = new ArrayList<>();
        Node<T> actual = destino;

        while (actual != null && padre.containsKey(actual)) {
            camino.add((Nodo) actual.getValue());
            actual = padre.get(actual);
        }

        Collections.reverse(camino);
        return new Ruta(camino);
    }
}

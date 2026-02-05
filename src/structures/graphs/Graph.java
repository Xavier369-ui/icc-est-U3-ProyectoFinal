package structures.graphs;

import java.util.*;
import logica.Ruta;
import models.Nodo;
import nodes.Node;

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
        return mapa.getOrDefault(n, new ArrayList<>());
    }

    public Ruta bfsRuta(Node<T> inicio, Node<T> destino,
                        List<Node<T>> recorrido, Set<T> bloqueados) {

        Map<Node<T>, Node<T>> padre = new HashMap<>();
        Queue<Node<T>> cola = new LinkedList<>();
        Set<Node<T>> visitados = new HashSet<>();

        cola.add(inicio);
        visitados.add(inicio);
        padre.put(inicio, null);

        while (!cola.isEmpty()) {
            Node<T> actual = cola.poll();
            recorrido.add(actual);

            if (actual.equals(destino)) break;

            for (Node<T> v : getNeighbors2(actual)) {
                if (visitados.contains(v)) continue;
                if (bloqueados.contains(v.getValue())) continue;

                visitados.add(v);
                padre.put(v, actual);
                cola.add(v);
            }
        }

        return construirRuta(destino, padre);
    }

    public Ruta dfsRuta(Node<T> inicio, Node<T> destino,
                        List<Node<T>> recorrido, Set<T> bloqueados) {

        Map<Node<T>, Node<T>> padre = new HashMap<>();
        Set<Node<T>> visitados = new HashSet<>();
        padre.put(inicio, null);
        dfs(inicio, destino, visitados, padre, recorrido, bloqueados);
        return construirRuta(destino, padre);
    }

    private boolean dfs(Node<T> actual, Node<T> destino,
                        Set<Node<T>> visitados,
                        Map<Node<T>, Node<T>> padre,
                        List<Node<T>> recorrido,
                        Set<T> bloqueados) {

        visitados.add(actual);
        recorrido.add(actual);

        if (actual.equals(destino)) return true;

        for (Node<T> v : getNeighbors2(actual)) {
            if (visitados.contains(v)) continue;
            if (bloqueados.contains(v.getValue())) continue;

            padre.put(v, actual);
            if (dfs(v, destino, visitados, padre, recorrido, bloqueados))
                return true;
        }
        return false;
    }

    private Ruta construirRuta(Node<T> destino,
                               Map<Node<T>, Node<T>> padre) {

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

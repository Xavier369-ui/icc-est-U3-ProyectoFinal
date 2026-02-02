package structures.graphs;

import java.util.*;
import nodes.Node;
import models.Nodo;
import logica.Ruta;

/**
 * Grafo genérico usado para BFS y DFS
 * Trabaja con lista de adyacencia
 */
public class Graph<T> {

    private Map<Node<T>, List<Node<T>>> mapa = new HashMap<>();

   

    public void addNode(Node<T> n) {
        mapa.putIfAbsent(n, new ArrayList<>());
    }

    public void addEdge(Node<T> a, Node<T> b) {
        addNode(a);
        addNode(b);
        mapa.get(a).add(b); // grafo dirigido (calles)
    }

    public List<Node<T>> getNeighbors2(Node<T> n) {
        return mapa.getOrDefault(n, List.of());
    }

    public void clearEdges() {
        for (List<Node<T>> l : mapa.values()) {
            l.clear();
        }
    }

    /* =========================
       BFS (ANCHURA)
       ========================= */

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

            //  se guarda el orden de exploración (AZUL)
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

        //  se construye la ruta final (MORADO)
        return construirRuta(destino, padre);
    }

    /* =========================
       DFS (PROFUNDIDAD)
       ========================= */

    public Ruta dfsRuta(
            Node<T> inicio,
            Node<T> destino,
            List<Node<T>> exploracion,
            Set<T> bloqueados
    ) {

        Map<Node<T>, Node<T>> padre = new HashMap<>();
        Set<Node<T>> visitados = new HashSet<>();

        padre.put(inicio, null);

        //  DFS RECURSIVO QUE SÍ GUARDA EL RECORRIDO
        dfsRecursivo(
                inicio,
                destino,
                visitados,
                padre,
                exploracion,
                bloqueados
        );

        return construirRuta(destino, padre);
    }

    /**
     * DFS recursivo
     * Guarda TODOS los nodos visitados en orden
     
     */
    private boolean dfsRecursivo(
            Node<T> actual,
            Node<T> destino,
            Set<Node<T>> visitados,
            Map<Node<T>, Node<T>> padre,
            List<Node<T>> exploracion,
            Set<T> bloqueados
    ) {

        visitados.add(actual);

        //  se guarda el paso para animación (AZUL)
        exploracion.add(actual);

        if (actual.equals(destino)) {
            return true;
        }

        for (Node<T> vecino : getNeighbors2(actual)) {

            if (visitados.contains(vecino)) continue;
            if (bloqueados.contains(vecino.getValue())) continue;

            padre.put(vecino, actual);

            if (dfsRecursivo(
                    vecino,
                    destino,
                    visitados,
                    padre,
                    exploracion,
                    bloqueados
            )) {
                return true;
            }
        }

        return false;
    }

    /* =========================
       CONSTRUCCIÓN DE RUTA FINAL
       ========================= */

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

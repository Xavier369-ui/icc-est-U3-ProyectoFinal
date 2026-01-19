package logica;

import models.Arista;
import models.Grafo;
import models.Nodo;

import java.util.*;

public class BFS {
    public Ruta buscar(Grafo grafo, Nodo inicio, Nodo destino) {
        Ruta r = new Ruta();
        if (inicio == null || destino == null) return r;

        Queue<Nodo> q = new ArrayDeque<>();
        Set<Nodo> vis = new HashSet<>();
        Map<Nodo, Nodo> prev = new HashMap<>();

        q.add(inicio);
        vis.add(inicio);

        while (!q.isEmpty()) {
            Nodo actual = q.poll();
            r.addVisitado(actual);

            if (actual.equals(destino)) break;

            for (Arista ar : grafo.getAristasDesde(actual)) {
                Nodo sig = ar.getHasta();
                if (vis.contains(sig)) continue;
                if (grafo.edgeBlocked(actual, sig)) continue;
                vis.add(sig);
                prev.put(sig, actual);
                q.add(sig);
            }
        }

        r.setRutaFinal(reconstruir(prev, inicio, destino));
        return r;
    }

    private List<Nodo> reconstruir(Map<Nodo, Nodo> prev, Nodo inicio, Nodo destino) {
        if (inicio.equals(destino)) return Collections.singletonList(inicio);
        if (!prev.containsKey(destino)) return Collections.emptyList();
        LinkedList<Nodo> path = new LinkedList<>();
        Nodo cur = destino;
        while (cur != null && !cur.equals(inicio)) {
            path.addFirst(cur);
            cur = prev.get(cur);
        }
        if (cur == null) return Collections.emptyList();
        path.addFirst(inicio);
        return path;
    }
}

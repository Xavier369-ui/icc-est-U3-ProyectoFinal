package logica;

import models.Arista;
import models.Grafo;
import models.Nodo;

import java.util.*;

public class DFS {
    public Ruta buscar(Grafo grafo, Nodo inicio, Nodo destino) {
        Ruta r = new Ruta();
        if (inicio == null || destino == null) return r;

        Deque<Nodo> stack = new ArrayDeque<>();
        Set<Nodo> vis = new HashSet<>();
        Map<Nodo, Nodo> prev = new HashMap<>();

        stack.push(inicio);
        while (!stack.isEmpty()) {
            Nodo actual = stack.pop();
            if (vis.contains(actual)) continue;
            vis.add(actual);
            r.addVisitado(actual);
            if (actual.equals(destino)) break;

            List<Arista> aristas = grafo.getAristasDesde(actual);
            for (int i = aristas.size() - 1; i >= 0; i--) {
                Nodo sig = aristas.get(i).getHasta();
                if (vis.contains(sig)) continue;
                if (grafo.edgeBlocked(actual, sig)) continue;
                if (!prev.containsKey(sig)) prev.put(sig, actual);
                stack.push(sig);
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


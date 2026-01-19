package logica;

import models.Nodo;
import java.util.*;

public class BFS {

    public static Ruta ejecutar(Nodo inicio, Nodo destino) {

        Queue<Nodo> cola = new LinkedList<Nodo>();
        Map<Nodo, Nodo> previo = new HashMap<Nodo, Nodo>();
        ArrayList<Nodo> visitados = new ArrayList<Nodo>();

        cola.add(inicio);
        visitados.add(inicio);
        previo.put(inicio, null);

        while (!cola.isEmpty()) {

            Nodo actual = cola.poll();

            if (actual.equals(destino)) {
                break;
            }

            for (Nodo vecino : actual.getVecinos()) {

                if (!visitados.contains(vecino)) {

                    visitados.add(vecino);
                    previo.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        Ruta ruta = new Ruta();
        Nodo paso = destino;

        while (paso != null) {
            ruta.agregarNodo(paso);
            paso = previo.get(paso);
        }

        return ruta;
    }
}

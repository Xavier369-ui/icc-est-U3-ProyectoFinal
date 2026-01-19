package logica;

import models.Nodo;
import java.util.*;

public class DFS {

    public static Ruta ejecutar(Nodo inicio, Nodo destino) {

        Stack<Nodo> pila = new Stack<Nodo>();
        Map<Nodo, Nodo> previo = new HashMap<Nodo, Nodo>();
        ArrayList<Nodo> visitados = new ArrayList<Nodo>();

        pila.push(inicio);
        previo.put(inicio, null);

        while (!pila.isEmpty()) {

            Nodo actual = pila.pop();

            if (!visitados.contains(actual)) {

                visitados.add(actual);

                if (actual.equals(destino)) {
                    break;
                }

                for (Nodo vecino : actual.getVecinos()) {

                    if (!visitados.contains(vecino)) {
                        previo.put(vecino, actual);
                        pila.push(vecino);
                    }
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



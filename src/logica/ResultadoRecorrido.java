package logica;

import java.util.List;
import nodes.Node;

public class ResultadoRecorrido<T> {

    private List<Node<T>> visitados;
    private List<Node<T>> camino;

    public ResultadoRecorrido(List<Node<T>> visitados, List<Node<T>> camino) {
        this.visitados = visitados;
        this.camino = camino;
    }

    public List<Node<T>> getVisitados() {
        return visitados;
    }

    public List<Node<T>> getCamino() {
        return camino;
    }
}

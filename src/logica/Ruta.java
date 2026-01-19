package logica;

import models.Nodo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ruta {
    private final List<Nodo> visitados = new ArrayList<>();
    private final List<Nodo> rutaFinal = new ArrayList<>();

    public List<Nodo> getVisitados() { return Collections.unmodifiableList(visitados); }
    public List<Nodo> getRutaFinal() { return Collections.unmodifiableList(rutaFinal); }

    public void addVisitado(Nodo n) { visitados.add(n); }
    public void setRutaFinal(List<Nodo> ruta) {
        rutaFinal.clear();
        rutaFinal.addAll(ruta);
    }

    public boolean tieneRuta() { return !rutaFinal.isEmpty(); }
}

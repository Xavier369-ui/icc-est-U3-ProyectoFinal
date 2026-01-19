package controllers;

public class EjecucionController {
    private final GrafoController grafoController;

    public EjecucionController(GrafoController grafoController) {
        this.grafoController = grafoController;
    }

    public void activar() {
        grafoController.setModo(GrafoController.Modo.EJECUCION);
    }

    public void ejecutarBFS() { grafoController.ejecutarBFS(); }
    public void ejecutarDFS() { grafoController.ejecutarDFS(); }
}

package controller;

public class EdicionController {
    private final GrafoController grafoController;

    public EdicionController(GrafoController grafoController) {
        this.grafoController = grafoController;
    }

    public void activar() {
        grafoController.setModo(GrafoController.Modo.EDICION);
    }
}

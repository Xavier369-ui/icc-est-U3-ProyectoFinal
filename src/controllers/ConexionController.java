package controllers;

public class ConexionController {

    public static final int NINGUNO = 0;
    public static final int UNIDIRECCIONAL = 1;
    public static final int BIDIRECCIONAL = 2;
    public static final int ELIMINAR = 3;

    private int modo = NINGUNO;

    public int getModo() {
        return modo;
    }

    public void setUnidireccional() {
        modo = UNIDIRECCIONAL;
    }

    public void setBidireccional() {
        modo = BIDIRECCIONAL;
    }

    public void setEliminar() {
        modo = ELIMINAR;
    }

    public void limpiar() {
        modo = NINGUNO;
    }
}

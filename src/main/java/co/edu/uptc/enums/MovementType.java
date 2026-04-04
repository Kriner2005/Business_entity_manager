package co.edu.uptc.enums;

public enum MovementType {
    INGRESO("Ingreso"),
    EGRESO("Egreso");

    private final String descripcion;

    MovementType(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

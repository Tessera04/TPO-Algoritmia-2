package enums;

public enum HerramientaSoporte {

    HACKEO_CUENTA("Me hackearon la cuenta", Area.CUENTA, 1),
    CAMBIAR_REGION("Cambiar la region de la cuenta", Area.CUENTA, 3),
    CAMBIAR_CONTRASENA("Solicitud de cambio de contraseña", Area.CUENTA, 3),

    REEMBOLSAR_COMPRA("Reembolsar compra dentro del juego", Area.PAGOS, 2),
    PROBLEMAS_TARJETA_REGALO("Problemas con tarjeta de regalo", Area.PAGOS, 2),
    RESOLVER_CONTRACARGO("Resolución de contracargos", Area.PAGOS, 1);

    public enum Area {
        CUENTA,
        PAGOS
    }

    private final String descripcion;
    private final Area area;
    private final int prioridad;

    HerramientaSoporte(String descripcion, Area area, int prioridad) {
        this.descripcion = descripcion;
        this.area = area;
        this.prioridad = prioridad;
    }

    public String getDescripcion() { return descripcion; }
    public Area getArea() { return area; }
    public int getPrioridad() { return prioridad; }

    @Override
    public String toString() { return descripcion; }
}

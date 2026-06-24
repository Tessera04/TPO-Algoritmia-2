package enums;

/**
 * Representa las herramientas de soporte disponibles para el jugador.
 * Cada herramienta pertenece a un area (Cuenta o Pagos) y tiene asociada
 * una prioridad fija, que el sistema asigna automaticamente al crear
 * el ticket.
 *
 * Regla de negocio: las solicitudes de Pagos son mas urgentes que las
 * de Cuenta (1 = mas urgente, 2 = menos urgente).
 */
public enum HerramientaSoporte {

    RECUPERAR_CUENTA("Recupera tu cuenta", Area.CUENTA, 2),
    OLVIDE_USUARIO("Olvidé mi nombre de usuario", Area.CUENTA, 2),
    OLVIDE_CONTRASENA("Olvidé mi contraseña", Area.CUENTA, 2),

    REEMBOLSAR_COMPRA("Reembolsar compra dentro del juego", Area.PAGOS, 1),
    CONSULTAR_REEMBOLSO("Obtener el reembolso de una compra dentro del juego", Area.PAGOS, 1),
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

    public String getDescripcion() {
        return descripcion;
    }

    public Area getArea() {
        return area;
    }

    public int getPrioridad() {
        return prioridad;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
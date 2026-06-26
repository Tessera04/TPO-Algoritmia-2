package modelo;

import enums.HerramientaSoporte;
import java.time.LocalDateTime;

/**
 * Representa un ticket individual dentro del sistema de soporte.
 * Guarda todos los datos necesarios para identificarlo, priorizarlo y seguir su estado.
 */
public class Ticket {

    private int idTicket;
    private String nombreJugador;
    private HerramientaSoporte herramienta;
    private String descripcionProblema;
    private int prioridad;
    private String estado; // "Pendiente" o "Resuelto"
    private LocalDateTime ordenLlegada; // fecha y hora en que se registró el ticket
    private String resolucion;

    public Ticket(
            int idTicket,
            String nombreJugador,
            HerramientaSoporte herramienta,
            String descripcionProblema
    ) {
        this.idTicket = idTicket;
        this.nombreJugador = nombreJugador;
        this.herramienta = herramienta;
        this.descripcionProblema = descripcionProblema;
        this.prioridad = herramienta.getPrioridad();
        this.estado = "Pendiente";
        this.ordenLlegada = LocalDateTime.now();
        this.resolucion = null;
    }

    /**
     * Constructor utilizado al cargar tickets desde el archivo JSON.
     * Conserva la fecha y hora originales.
     */
    public Ticket(
            int idTicket,
            String nombreJugador,
            HerramientaSoporte herramienta,
            String descripcionProblema,
            LocalDateTime ordenLlegada
    ) {
        this.idTicket = idTicket;
        this.nombreJugador = nombreJugador;
        this.herramienta = herramienta;
        this.descripcionProblema = descripcionProblema;
        this.prioridad = herramienta.getPrioridad();
        this.estado = "Pendiente";
        this.ordenLlegada = ordenLlegada;
        this.resolucion = null;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public HerramientaSoporte getHerramienta() {
        return herramienta;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getOrdenLlegada() {
        return ordenLlegada;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    @Override
    public String toString() {
        return "\n========================================"
                + "\n             DATOS DEL TICKET"
                + "\n========================================"
                + "\nTicket #: " + idTicket
                + "\nJugador: " + nombreJugador
                + "\nÁrea: " + herramienta.getArea()
                + "\nTipo: " + herramienta.getDescripcion()
                + "\nProblema: " + descripcionProblema
                + "\nPrioridad: " + prioridad
                + "\nEstado: " + estado
                + "\nOrden de llegada: " + ordenLlegada
                + "\nResolución: " + (resolucion != null
                        ? resolucion
                        : "Sin resolución aún")
                + "\n========================================";
    }
}
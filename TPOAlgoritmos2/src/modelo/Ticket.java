package modelo;

import enums.HerramientaSoporte;
import java.time.LocalDateTime;

/**
 * Representa un ticket individual dentro del sistema de soporte.
 * Es la "ficha" de un reclamo: guarda todos los datos necesarios
 * para identificarlo, priorizarlo y seguir su estado a lo largo
 * de todo el proceso de atencion.
 */
public class Ticket {

    private int idTicket;
    private String nombreJugador;
    private HerramientaSoporte herramienta; // define area, tipo de solicitud y prioridad
    private String descripcionProblema;
    private int prioridad;
    private String estado; // "Pendiente", "Atendido" o "Resuelto"
    private LocalDateTime ordenLlegada; // usado para desempatar tickets de igual prioridad
    private String resolucion; // comentario final, solo presente si el ticket fue resuelto

    public Ticket(int idTicket, String nombreJugador, HerramientaSoporte herramienta, String descripcionProblema) {
        this.idTicket = idTicket;
        this.nombreJugador = nombreJugador;
        this.herramienta = herramienta;
        this.descripcionProblema = descripcionProblema;
        this.prioridad = herramienta.getPrioridad();
        this.estado = "Pendiente";
        this.ordenLlegada = LocalDateTime.now();
        this.resolucion = null;
    }

    // Constructor para deserialización desde JSON (preserva el ordenLlegada original)
    public Ticket(int idTicket, String nombreJugador, HerramientaSoporte herramienta,
                  String descripcionProblema, LocalDateTime ordenLlegada) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket #").append(idTicket)
                .append(" | Jugador: ").append(nombreJugador)
                .append(" | Área: ").append(herramienta.getArea())
                .append(" | Tipo: ").append(herramienta.getDescripcion())
                .append(" | Problema: ").append(descripcionProblema)
                .append(" | Prioridad: ").append(prioridad)
                .append(" | Estado: ").append(estado)
                .append(" | Llegada: ").append(ordenLlegada);

        if (resolucion != null) {
            sb.append(" | Resolución: ").append(resolucion);
        }

        return sb.toString();
    }
}
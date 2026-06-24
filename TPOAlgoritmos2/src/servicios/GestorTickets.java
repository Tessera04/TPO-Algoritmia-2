package servicios;

import estructura.ComparadorPrioridad;
import enums.HerramientaSoporte;
import modelo.Ticket;
import util.Constantes;
import util.GeneradorID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Es el cerebro del programa. Aca vive toda la logica:
 * crear tickets, buscarlos, listar los pendientes, atender
 * al mas prioritario y resolverlos.
 *
 * Usa dos estructuras en paralelo, tal como pide la consigna:
 * - HashMap<Integer, Ticket>: conserva TODOS los tickets (pendientes,
 *   atendidos y resueltos) y permite buscarlos por ID en O(1).
 * - PriorityQueue<Ticket>: ordena unicamente los tickets pendientes,
 *   segun prioridad y, en caso de empate, segun orden de llegada
 *   (criterio definido en ComparadorPrioridad).
 */
public class GestorTickets {

    private HashMap<Integer, Ticket> tickets;
    private PriorityQueue<Ticket> colaPrioridad;

    public GestorTickets() {
        this.tickets = new HashMap<>();
        this.colaPrioridad = new PriorityQueue<>(new ComparadorPrioridad());
    }

    /**
     * Registra un nuevo ticket de soporte. La prioridad se asigna
     * automaticamente segun la herramienta seleccionada (regla de negocio).
     * Lo guarda en el HashMap y lo agrega a la cola de prioridad.
     */
    public Ticket crearTicket(String nombreJugador, HerramientaSoporte herramienta, String descripcionProblema) {
        int nuevoId = GeneradorID.generarID();
        Ticket nuevoTicket = new Ticket(nuevoId, nombreJugador, herramienta, descripcionProblema);

        tickets.put(nuevoId, nuevoTicket);
        colaPrioridad.add(nuevoTicket);

        return nuevoTicket;
    }

    /**
     * Busca un ticket por su ID. Devuelve null si no existe.
     * Funciona sin importar el estado del ticket (Pendiente, Atendido o Resuelto).
     */
    public Ticket buscarTicket(int idTicket) {
        return tickets.get(idTicket);
    }

    /**
     * Atiende el ticket de mayor prioridad pendiente (y mas antiguo, en caso
     * de empate). Lo saca de la cola y cambia su estado a "Atendido".
     * El ticket sigue almacenado en el HashMap para poder consultarlo
     * o resolverlo despues.
     * Devuelve null si no hay tickets pendientes.
     */
    public Ticket atenderTicket() {
        Ticket ticket = colaPrioridad.poll();

        if (ticket != null) {
            ticket.setEstado(Constantes.ESTADO_ATENDIDO);
        }

        return ticket;
    }

    /**
     * Marca un ticket atendido como "Resuelto" y le agrega el comentario
     * o resolucion final. Devuelve false si el ticket no existe o si
     * todavia esta en estado Pendiente (no se puede resolver sin atender antes).
     */
    public boolean resolverTicket(int idTicket, String resolucion) {
        Ticket ticket = tickets.get(idTicket);

        if (ticket == null) {
            return false;
        }

        if (ticket.getEstado().equals(Constantes.ESTADO_PENDIENTE)) {
            return false; // no se puede resolver un ticket que no fue atendido
        }

        ticket.setEstado(Constantes.ESTADO_RESUELTO);
        ticket.setResolucion(resolucion);
        return true;
    }

    /**
     * Devuelve todos los tickets registrados en el sistema,
     * sin importar su estado.
     */
    public HashMap<Integer, Ticket> listarTodos() {
        return tickets;
    }

    /**
     * Devuelve unicamente los tickets que se encuentran en estado Pendiente,
     * es decir, los que todavia esperan ser atendidos.
     * No modifica la cola de prioridad, solo la recorre para mostrarla.
     */
    public List<Ticket> listarPendientes() {
        return new ArrayList<>(colaPrioridad);
    }

    /**
     * Indica si hay tickets pendientes por atender.
     */
    public boolean hayPendientes() {
        return !colaPrioridad.isEmpty();
    }
}
package servicios;

import enums.HerramientaSoporte;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Ticket;
import tda.ColaPrioridad;
import tda.IColaPrioridad;
import util.Constantes;
import util.GeneradorID;
import util.PersistenciaJSON;
import estructura.ComparadorPrioridad;

/**
 * Gestiona el registro, la búsqueda y la atención de tickets.
 */
public class GestorTickets {

    // Diccionario: ticket por ID -> búsqueda directa por identificador.
    private final Map<Integer, Ticket> ticketsPorId;

    // Diccionario múltiple: nombre de usuario -> lista de sus tickets.
    private final Map<String, List<Ticket>> ticketsPorUsuario;

    // Cola con prioridad: contiene únicamente los tickets pendientes.
    private final IColaPrioridad<Ticket> colaPrioridad;

    // Diccionario: credenciales del staff (usuario -> contraseña).
    private final Map<String, String> credenciales;

    public GestorTickets() {
        ticketsPorId = new HashMap<>();
        ticketsPorUsuario = new HashMap<>();
        colaPrioridad = new ColaPrioridad<>(new ComparadorPrioridad());
        credenciales = new HashMap<>();

        credenciales.put("LucianaFlores58", "luciana123");
        credenciales.put("MatiasGonzalez04", "matias1234");

        cargarDatos();
        sembrarTicketEjemplo();
    }

    private void cargarDatos() {
        PersistenciaJSON.DatosGuardados datos = PersistenciaJSON.cargar();

        for (Ticket ticket : datos.tickets) {
            ticketsPorId.put(ticket.getIdTicket(), ticket);
            asociarTicketAUsuario(ticket.getNombreJugador(), ticket);

            if (ticket.getEstado().equals(Constantes.ESTADO_PENDIENTE)) {
                colaPrioridad.acolar(ticket);
            }
        }
    }

    /**
     * Agrega un ticket a la lista asociada a un usuario.
     */
    private void asociarTicketAUsuario(String nombreJugador, Ticket ticket) {
        List<Ticket> ticketsDelUsuario = ticketsPorUsuario.get(nombreJugador);

        if (ticketsDelUsuario == null) {
            ticketsDelUsuario = new ArrayList<>();
            ticketsPorUsuario.put(nombreJugador, ticketsDelUsuario);
        }

        ticketsDelUsuario.add(ticket);
    }

    /**
     * Si todavía no existe ningún ticket, genera uno de ejemplo para que el
     * sistema arranque con un caso real en vez de estar vacío.
     */
    private void sembrarTicketEjemplo() {
        if (ticketsPorId.isEmpty()) {
            crearTicket(
                    "Ana_Gamer",
                    HerramientaSoporte.HACKEO_CUENTA,
                    "No puedo entrar a mi cuenta, alguien cambió la contraseña."
            );
        }
    }

    private void guardarDatos() {
        PersistenciaJSON.guardar(ticketsPorId.values());
    }

    public Ticket crearTicket(
            String nombreJugador,
            HerramientaSoporte herramienta,
            String descripcionProblema
    ) {
        int nuevoId = GeneradorID.generarID(ticketsPorId);

        Ticket nuevoTicket = new Ticket(
                nuevoId,
                nombreJugador,
                herramienta,
                descripcionProblema
        );

        ticketsPorId.put(nuevoId, nuevoTicket);
        colaPrioridad.acolar(nuevoTicket);
        asociarTicketAUsuario(nombreJugador, nuevoTicket);

        guardarDatos();
        return nuevoTicket;
    }

    public Ticket buscarTicketPorId(int idTicket) {
        return ticketsPorId.get(idTicket);
    }

    public List<Ticket> buscarTicketsPorUsuario(String nombreJugador) {
        List<Ticket> ticketsDelUsuario = ticketsPorUsuario.get(nombreJugador);

        if (ticketsDelUsuario == null) {
            return new ArrayList<>();
        }

        return ticketsDelUsuario;
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        String guardada = credenciales.get(usuario);
        return guardada != null && guardada.equals(contrasena);
    }

    public Ticket verProximoTicket() {
        return colaPrioridad.verProximo();
    }

    public Ticket atenderYResolver(String resolucion) {
        Ticket ticket = colaPrioridad.desacolar();

        if (ticket != null) {
            ticket.setEstado(Constantes.ESTADO_RESUELTO);
            ticket.setResolucion(resolucion);
            guardarDatos();
        }

        return ticket;
    }

    public Map<Integer, Ticket> listarTodos() {
        return ticketsPorId;
    }

    public List<Ticket> listarPendientes() {
        return colaPrioridad.aLista();
    }

    public List<Ticket> listarResueltos() {
        List<Ticket> resueltos = new ArrayList<>();

        for (Ticket ticket : ticketsPorId.values()) {
            if (ticket.getEstado().equals(Constantes.ESTADO_RESUELTO)) {
                resueltos.add(ticket);
            }
        }

        return resueltos;
    }

    public boolean hayPendientes() {
        return !colaPrioridad.estaVacia();
    }
}
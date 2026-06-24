package servicios;

import enums.HerramientaSoporte;
import estructura.ComparadorPrioridad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import modelo.Ticket;
import util.Constantes;
import util.GeneradorID;
import util.PersistenciaJSON;

public class GestorTickets {

    private final HashMap<Integer, Ticket> ticketsPorId;
    private final HashMap<String, List<Ticket>> ticketsPorUsuario;
    private final PriorityQueue<Ticket> colaPrioridad;
    private final HashMap<String, String> credenciales;

    public GestorTickets() {
        this.ticketsPorId = new HashMap<>();
        this.ticketsPorUsuario = new HashMap<>();
        this.colaPrioridad = new PriorityQueue<>(new ComparadorPrioridad());
        this.credenciales = new HashMap<>();
        credenciales.put("LucianaFlores58", "luciana123");
        credenciales.put("MatiasGonzalez04", "matias1234");
        cargarDatos();
    }

    private void cargarDatos() {
        PersistenciaJSON.DatosGuardados datos = PersistenciaJSON.cargar();
        GeneradorID.setContador(datos.contadorId);
        for (Ticket t : datos.tickets) {
            ticketsPorId.put(t.getIdTicket(), t);
            ticketsPorUsuario.computeIfAbsent(t.getNombreJugador(), k -> new ArrayList<>()).add(t);
            if (t.getEstado().equals(Constantes.ESTADO_PENDIENTE)) {
                colaPrioridad.add(t);
            }
        }
    }

    private void guardarDatos() {
        PersistenciaJSON.guardar(ticketsPorId.values(), GeneradorID.getContador());
    }

    public Ticket crearTicket(String nombreJugador, HerramientaSoporte herramienta, String descripcionProblema) {
        int nuevoId = GeneradorID.generarID();
        Ticket nuevoTicket = new Ticket(nuevoId, nombreJugador, herramienta, descripcionProblema);

        ticketsPorId.put(nuevoId, nuevoTicket);
        colaPrioridad.add(nuevoTicket);
        ticketsPorUsuario.computeIfAbsent(nombreJugador, k -> new ArrayList<>()).add(nuevoTicket);

        guardarDatos();
        return nuevoTicket;
    }

    public Ticket buscarTicketPorId(int idTicket) {
        return ticketsPorId.get(idTicket);
    }

    public List<Ticket> buscarTicketsPorUsuario(String nombreJugador) {
        return ticketsPorUsuario.getOrDefault(nombreJugador, new ArrayList<>());
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        String guardada = credenciales.get(usuario);
        return guardada != null && guardada.equals(contrasena);
    }

    public Ticket verProximoTicket() {
        return colaPrioridad.peek();
    }

    public Ticket atenderYResolver(String resolucion) {
        Ticket ticket = colaPrioridad.poll();
        if (ticket != null) {
            ticket.setEstado(Constantes.ESTADO_RESUELTO);
            ticket.setResolucion(resolucion);
            guardarDatos();
        }
        return ticket;
    }

    public HashMap<Integer, Ticket> listarTodos() {
        return ticketsPorId;
    }

    public List<Ticket> listarPendientes() {
        return new ArrayList<>(colaPrioridad);
    }

    public List<Ticket> listarResueltos() {
        List<Ticket> resueltos = new ArrayList<>();
        for (Ticket t : ticketsPorId.values()) {
            if (t.getEstado().equals(Constantes.ESTADO_RESUELTO)) {
                resueltos.add(t);
            }
        }
        return resueltos;
    }

    public boolean hayPendientes() {
        return !colaPrioridad.isEmpty();
    }
}

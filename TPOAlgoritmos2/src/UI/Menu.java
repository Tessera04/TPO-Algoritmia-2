package ui;

import enums.HerramientaSoporte;
import java.util.List;
import java.util.Scanner;
import modelo.Ticket;
import servicios.GestorTickets;

public class Menu {

    private final Scanner scanner;
    private final GestorTickets gestor;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.gestor = new GestorTickets();
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n--- BIENVENIDO AL SISTEMA DE SOPORTE ---");
            System.out.println("1. Ingresar como cliente");
            System.out.println("2. Ingresar como staff");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    flujoCliente();
                    break;
                case 2:
                    flujoAdmin();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    // -------------------- FLUJO CLIENTE --------------------

    private void flujoCliente() {
        System.out.print("\nIngrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();
        if (nombreUsuario.isEmpty()) {
            System.out.println("El nombre de usuario no puede estar vacío.");
            return;
        }
        panelCliente(nombreUsuario);
    }

    private void panelCliente(String nombreUsuario) {
        int opcion;
        do {
            System.out.println("\n--- PANEL DEL CLIENTE ---");
            System.out.println("Usuario: " + nombreUsuario);
            System.out.println("1. Crear ticket");
            System.out.println("2. Consultar estado de ticket");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    menuCrearTicket(nombreUsuario);
                    break;
                case 2:
                    menuConsultarEstado(false);
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    private void menuCrearTicket(String nombreUsuario) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== CENTRO DE SOPORTE ===");
            System.out.println("--- Administración de la cuenta ---");
            System.out.println("1. Me hackearon la cuenta");
            System.out.println("2. Olvidé mi nombre de usuario");
            System.out.println("3. Olvidé mi contraseña");
            System.out.println("--- Pagos ---");
            System.out.println("4. Reembolsar compra dentro del juego");
            System.out.println("5. Problemas con tarjeta de regalo");
            System.out.println("6. Resolución de contracargos");
            System.out.println("7. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            HerramientaSoporte herramienta = null;

            switch (opcion) {
                case 1: herramienta = HerramientaSoporte.HACKEO_CUENTA; break;
                case 2: herramienta = HerramientaSoporte.CAMBIAR_REGION; break;
                case 3: herramienta = HerramientaSoporte.OLVIDE_CONTRASENA; break;
                case 4: herramienta = HerramientaSoporte.REEMBOLSAR_COMPRA; break;
                case 5: herramienta = HerramientaSoporte.PROBLEMAS_TARJETA_REGALO; break;
                case 6: herramienta = HerramientaSoporte.RESOLVER_CONTRACARGO; break;
                case 7: volver = true; break;
                default: System.out.println("Opción inválida."); break;
            }

            if (herramienta != null) {
                registrarTicket(nombreUsuario, herramienta);
                volver = true;
            }
        }
    }

    private void registrarTicket(String nombreUsuario, HerramientaSoporte herramienta) {
        System.out.println("\n--- " + herramienta.getDescripcion() + " ---");
        System.out.print("Describa su problema: ");
        String descripcion = scanner.nextLine();

        Ticket ticket = gestor.crearTicket(nombreUsuario, herramienta, descripcion);
        System.out.println("Ticket creado con éxito.");
        System.out.println("Número de ticket: " + ticket.getIdTicket()
                + " | Prioridad: " + ticket.getPrioridad());
    }

    private void menuConsultarEstado(boolean vistaCompleta) {
        int opcion;
        do {
            System.out.println("\n--- CONSULTAR ESTADO DE TICKET ---");
            System.out.println("1. Buscar por ID de ticket");
            System.out.println("2. Buscar por nombre de usuario");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    buscarPorId(vistaCompleta);
                    break;
                case 2:
                    buscarPorNombre(vistaCompleta);
                    break;
                case 3:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
    }

    // -------------------- FLUJO ADMIN --------------------

    private void flujoAdmin() {
        while (true) {
            System.out.println("\n--- ACCESO STAFF ---");
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine().trim();
            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine().trim();

            if (gestor.validarCredenciales(usuario, contrasena)) {
                System.out.println("Bienvenido, " + usuario + ".");
                panelSoporte();
                return;
            }

            System.out.println("Credenciales incorrectas.");
            System.out.println("1. Reintentar");
            System.out.println("2. Volver al menú principal");
            System.out.print("Seleccione: ");
            if (leerOpcion() != 1) return;
        }
    }

    private void panelSoporte() {
        int opcion;
        do {
            System.out.println("\n--- PANEL DE SOPORTE ---");
            System.out.println("1. Atender ticket");
            System.out.println("2. Buscar ticket");
            System.out.println("3. Mostrar tickets");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    atenderTicket();
                    break;
                case 2:
                    menuConsultarEstado(true);
                    break;
                case 3:
                    menuMostrarTickets();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    private void atenderTicket() {
        if (!gestor.hayPendientes()) {
            System.out.println("No hay tickets pendientes por atender.");
            return;
        }

        Ticket ticket = gestor.verProximoTicket();
        System.out.println("\nTicket a atender:");
        System.out.println(ticket);
        System.out.print("Ingrese la resolución o comentario final: ");
        String resolucion = scanner.nextLine();

        gestor.atenderYResolver(resolucion);
        System.out.println("Ticket #" + ticket.getIdTicket() + " marcado como Resuelto.");
    }

    private void menuMostrarTickets() {
        int opcion;
        do {
            System.out.println("\n--- MOSTRAR TICKETS ---");
            System.out.println("1. Mostrar todos los tickets");
            System.out.println("2. Mostrar tickets pendientes");
            System.out.println("3. Mostrar tickets resueltos");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    mostrarTodos();
                    break;
                case 2:
                    mostrarPendientes();
                    break;
                case 3:
                    mostrarResueltos();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    // -------------------- BÚSQUEDA Y VISUALIZACIÓN --------------------

    private void buscarPorId(boolean vistaCompleta) {
        System.out.print("Ingrese el número de ticket: ");
        int id = leerOpcion();
        Ticket ticket = gestor.buscarTicketPorId(id);
        if (ticket == null) {
            System.out.println("No se encontró ningún ticket con ese número.");
        } else {
            mostrarTicket(ticket, vistaCompleta);
        }
    }

    private void buscarPorNombre(boolean vistaCompleta) {
        System.out.print("Ingrese el nombre de usuario: ");
        String nombre = scanner.nextLine().trim();
        List<Ticket> tickets = gestor.buscarTicketsPorUsuario(nombre);
        if (tickets.isEmpty()) {
            System.out.println("No se encontraron tickets para ese usuario.");
        } else {
            for (Ticket t : tickets) {
                mostrarTicket(t, vistaCompleta);
            }
        }
    }

    private void mostrarTicket(Ticket ticket, boolean vistaCompleta) {
        if (vistaCompleta) {
            System.out.println(ticket);
        } else {
            // Vista cliente: solo tipo, estado y resolución
            System.out.println("Ticket #" + ticket.getIdTicket()
                    + " | Tipo: " + ticket.getHerramienta().getDescripcion()
                    + " | Estado: " + ticket.getEstado()
                    + " | Resolución: " + (ticket.getResolucion() != null
                            ? ticket.getResolucion() : "Sin resolución aún"));
        }
    }

    private void mostrarTodos() {
        if (gestor.listarTodos().isEmpty()) {
            System.out.println("No hay tickets registrados.");
            return;
        }
        System.out.println("\n--- TODOS LOS TICKETS ---");
        for (Ticket t : gestor.listarTodos().values()) {
            System.out.println(t);
        }
    }

    private void mostrarPendientes() {
        List<Ticket> pendientes = gestor.listarPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay tickets pendientes.");
            return;
        }
        System.out.println("\n--- TICKETS PENDIENTES ---");
        for (Ticket t : pendientes) {
            System.out.println(t);
        }
    }

    private void mostrarResueltos() {
        List<Ticket> resueltos = gestor.listarResueltos();
        if (resueltos.isEmpty()) {
            System.out.println("No hay tickets resueltos.");
            return;
        }
        System.out.println("\n--- TICKETS RESUELTOS ---");
        for (Ticket t : resueltos) {
            System.out.println(t);
        }
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

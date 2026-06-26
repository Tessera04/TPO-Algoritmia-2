package ui;

import enums.HerramientaSoporte;
import java.util.List;
import java.util.Scanner;
import modelo.Ticket;
import servicios.GestorTickets;

public class Menu {

    private final Scanner scanner;
    private final GestorTickets gestor;

    public Menu(GestorTickets gestor) {
        this.scanner = new Scanner(System.in);
        this.gestor = gestor;
    }

    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("      BIENVENIDO AL SISTEMA DE SOPORTE");
            System.out.println("========================================");
            System.out.println("1. Ingresar como cliente");
            System.out.println("2. Ingresar como staff");
            System.out.println("3. Salir");
            System.out.println("========================================");
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
                    System.out.println("Saliendo del sistema.");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
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
            System.out.println("\n========================================");
            System.out.println("           PANEL DEL CLIENTE");
            System.out.println("========================================");
            System.out.println("Usuario: " + nombreUsuario);
            System.out.println("----------------------------------------");
            System.out.println("1. Crear ticket");
            System.out.println("2. Consultar tickets registrados");
            System.out.println("3. Volver");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    menuCrearTicket(nombreUsuario);
                    break;

                case 2:
                    menuConsultarMisTickets(nombreUsuario);
                    break;

                case 3:
                    System.out.println("Regresando al menú principal.");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 3);
    }

    private void menuCrearTicket(String nombreUsuario) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("          CREAR TICKET DE SOPORTE");
            System.out.println("========================================");

            System.out.println("--- ADMINISTRACIÓN DE LA CUENTA ---");
            System.out.println("1. Me hackearon la cuenta");
            System.out.println("2. Cambiar la región de la cuenta");
            System.out.println("3. Solicitud de cambio de contraseña");

            System.out.println("\n--- PAGOS ---");
            System.out.println("4. Reembolsar compra dentro del juego");
            System.out.println("5. Problemas con tarjeta de regalo");
            System.out.println("6. Resolución de contracargos");

            System.out.println("\n7. Volver");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            HerramientaSoporte herramienta = null;

            switch (opcion) {
                case 1:
                    herramienta = HerramientaSoporte.HACKEO_CUENTA;
                    break;

                case 2:
                    herramienta = HerramientaSoporte.CAMBIAR_REGION;
                    break;

                case 3:
                    herramienta = HerramientaSoporte.CAMBIAR_CONTRASENA;
                    break;

                case 4:
                    herramienta = HerramientaSoporte.REEMBOLSAR_COMPRA;
                    break;

                case 5:
                    herramienta = HerramientaSoporte.PROBLEMAS_TARJETA_REGALO;
                    break;

                case 6:
                    herramienta = HerramientaSoporte.RESOLVER_CONTRACARGO;
                    break;

                case 7:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

            if (herramienta != null) {
                registrarTicket(nombreUsuario, herramienta);
                volver = true;
            }
        }
    }

    private void registrarTicket(String nombreUsuario,
            HerramientaSoporte herramienta) {

        System.out.println("\n========================================");
        System.out.println("          DETALLE DEL PROBLEMA");
        System.out.println("========================================");
        System.out.println("Tipo de consulta: "
                + herramienta.getDescripcion());
        System.out.println("----------------------------------------");
        System.out.print("Describa su problema: ");

        String descripcion = scanner.nextLine();

        Ticket ticket = gestor.crearTicket(
                nombreUsuario,
                herramienta,
                descripcion
        );

        System.out.println("\n========================================");
        System.out.println("      TICKET CREADO CORRECTAMENTE");
        System.out.println("========================================");
        System.out.println("Número de ticket: " + ticket.getIdTicket());
        System.out.println("Prioridad asignada: " + ticket.getPrioridad());
        System.out.println("Estado inicial: " + ticket.getEstado());
        System.out.println("========================================");
    }

    private void menuConsultarMisTickets(String nombreUsuario) {
        List<Ticket> tickets = gestor.buscarTicketsPorUsuario(nombreUsuario);

        if (tickets.isEmpty()) {
            System.out.println("\n========================================");
            System.out.println("        CONSULTA DE TICKETS");
            System.out.println("========================================");
            System.out.println("No se registran tickets asociados");
            System.out.println("al usuario ingresado.");
            System.out.println("========================================");
            return;
        }

        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("        CONSULTA DE TICKETS");
            System.out.println("========================================");
            System.out.println("1. Buscar ticket por número de identificación");
            System.out.println("2. Visualizar todos los tickets registrados");
            System.out.println("3. Regresar al panel del cliente");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    buscarMiTicketPorId(nombreUsuario);
                    break;

                case 2:
                    mostrarMisTickets(nombreUsuario);
                    break;

                case 3:
                    System.out.println("Regresando al panel del cliente.");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 3);
    }

    private void buscarMiTicketPorId(String nombreUsuario) {
        System.out.print("Ingrese el número de identificación del ticket: ");
        int id = leerOpcion();

        Ticket ticket = gestor.buscarTicketPorId(id);

        if (ticket == null
                || !ticket.getNombreJugador().equals(nombreUsuario)) {

            System.out.println("No se encontró un ticket asociado al usuario");
            System.out.println("con el número de identificación ingresado.");

        } else {
            mostrarTicket(ticket, false);
        }
    }

    private void mostrarMisTickets(String nombreUsuario) {
        List<Ticket> tickets = gestor.buscarTicketsPorUsuario(nombreUsuario);

        System.out.println("\n========================================");
        System.out.println("       TICKETS REGISTRADOS");
        System.out.println("========================================");

        for (Ticket ticket : tickets) {
            mostrarTicket(ticket, false);
        }

        System.out.println("========================================");
    }

    // -------------------- FLUJO STAFF --------------------

    private void flujoAdmin() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("             ACCESO STAFF");
            System.out.println("========================================");

            System.out.print("Usuario: ");
            String usuario = scanner.nextLine().trim();

            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine().trim();

            if (gestor.validarCredenciales(usuario, contrasena)) {
                System.out.println("\nAcceso correcto. Bienvenido/a, "
                        + usuario + ".");

                panelSoporte();
                return;
            }

            System.out.println("Credenciales incorrectas.");
            System.out.println("1. Reintentar");
            System.out.println("2. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (leerOpcion() != 1) {
                return;
            }
        }
    }

    private void panelSoporte() {
        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("           PANEL DE SOPORTE");
            System.out.println("========================================");
            System.out.println("1. Atender ticket");
            System.out.println("2. Consultar tickets");
            System.out.println("3. Volver");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    atenderTicket();
                    break;

                case 2:
                    menuConsultarTicketsStaff();
                    break;

                case 3:
                    System.out.println("Regresando al menú principal.");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 3);
    }

    private void atenderTicket() {
        if (!gestor.hayPendientes()) {
            System.out.println("No hay tickets pendientes por atender.");
            return;
        }

        Ticket ticket = gestor.verProximoTicket();

        System.out.println("\n========================================");
        System.out.println("            ATENDER TICKET");
        System.out.println("========================================");
        System.out.println("Se atenderá el siguiente ticket según");
        System.out.println("su prioridad y orden de llegada:");
        System.out.println(ticket);
        System.out.println("----------------------------------------");
        System.out.print("Ingrese la resolución final: ");

        String resolucion = scanner.nextLine();

        gestor.atenderYResolver(resolucion);

        System.out.println("\n========================================");
        System.out.println("       TICKET RESUELTO CORRECTAMENTE");
        System.out.println("========================================");
        System.out.println("Ticket #" + ticket.getIdTicket()
                + " marcado como Resuelto.");
        System.out.println("========================================");
    }

    private void menuConsultarTicketsStaff() {
        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("          CONSULTA DE TICKETS");
            System.out.println("========================================");
            System.out.println("1. Mostrar tickets no resueltos");
            System.out.println("2. Mostrar tickets resueltos");
            System.out.println("3. Buscar ticket por número de identificación");
            System.out.println("4. Volver");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    mostrarPendientes();
                    break;

                case 2:
                    mostrarResueltos();
                    break;

                case 3:
                    buscarPorId(true);
                    break;

                case 4:
                    System.out.println("Regresando al panel de soporte.");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 4);
    }

    // -------------------- BÚSQUEDA Y VISUALIZACIÓN --------------------

    private void buscarPorId(boolean vistaCompleta) {
        System.out.print("Ingrese el número de identificación del ticket: ");
        int id = leerOpcion();

        Ticket ticket = gestor.buscarTicketPorId(id);

        if (ticket == null) {
            System.out.println("No se encontró ningún ticket con ese número.");
        } else {
            mostrarTicket(ticket, vistaCompleta);
        }
    }

    private void mostrarTicket(Ticket ticket, boolean vistaCompleta) {
        if (vistaCompleta) {
            System.out.println(ticket);

        } else {
            System.out.println("\n----------------------------------------");
            System.out.println("           ESTADO DEL TICKET");
            System.out.println("----------------------------------------");
            System.out.println("Número de ticket: " + ticket.getIdTicket());
            System.out.println("Tipo de consulta: "
                    + ticket.getHerramienta().getDescripcion());
            System.out.println("Estado: " + ticket.getEstado());
            System.out.println("Resolución: " + (ticket.getResolucion() != null
                    ? ticket.getResolucion()
                    : "Sin resolución aún"));
            System.out.println("----------------------------------------");
        }
    }

    private void mostrarPendientes() {
        List<Ticket> pendientes = gestor.listarPendientes();

        if (pendientes.isEmpty()) {
            System.out.println("No hay tickets no resueltos.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("       TICKETS NO RESUELTOS");
        System.out.println("========================================");

        for (Ticket ticket : pendientes) {
            System.out.println(ticket);
        }
    }

    private void mostrarResueltos() {
        List<Ticket> resueltos = gestor.listarResueltos();

        if (resueltos.isEmpty()) {
            System.out.println("No hay tickets resueltos.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          TICKETS RESUELTOS");
        System.out.println("========================================");

        for (Ticket ticket : resueltos) {
            System.out.println(ticket);
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

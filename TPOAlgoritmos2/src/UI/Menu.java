package ui;

import enums.HerramientaSoporte;
import java.util.List;
import java.util.Scanner;
import modelo.Ticket;
import servicios.GestorTickets;

/**
 * Es la cara visible del sistema. Todo lo que ve y usa el usuario
 * pasa por aca: el menu principal del jugador (con las herramientas
 * de soporte) y el panel de soporte para gestionar los tickets.
 */
public class Menu {

    private Scanner scanner;
    private GestorTickets gestor;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.gestor = new GestorTickets();
    }

    /**
     * Arranca el sistema y muestra el menu principal
     * hasta que el usuario decida salir.
     */
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    registrarTicket(HerramientaSoporte.RECUPERAR_CUENTA);
                    break;
                case 2:
                    registrarTicket(HerramientaSoporte.OLVIDE_USUARIO);
                    break;
                case 3:
                    registrarTicket(HerramientaSoporte.OLVIDE_CONTRASENA);
                    break;
                case 4:
                    registrarTicket(HerramientaSoporte.REEMBOLSAR_COMPRA);
                    break;
                case 5:
                    registrarTicket(HerramientaSoporte.CONSULTAR_REEMBOLSO);
                    break;
                case 6:
                    registrarTicket(HerramientaSoporte.RESOLVER_CONTRACARGO);
                    break;
                case 7:
                    panelSoporte();
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 8);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== CENTRO DE SOPORTE ===");
        System.out.println("--- Administración de la cuenta ---");
        System.out.println("1. Recupera tu cuenta");
        System.out.println("2. Olvidé mi nombre de usuario");
        System.out.println("3. Olvidé mi contraseña");
        System.out.println("--- Pagos ---");
        System.out.println("4. Reembolsar compra dentro del juego");
        System.out.println("5. Problemas con tarjeta de regalo");
        System.out.println("6. Resolución de contracargos");
        System.out.println("---------------------------------");
        System.out.println("7. Panel de soporte (staff)");
        //Agregar validacion por ID en la opcion 7 para que solo el staff pueda acceder al panel de soporte
        System.out.println("8. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    // ----------------- REGISTRO DE TICKET -----------------

    private void registrarTicket(HerramientaSoporte herramienta) {
        System.out.println("\n--- " + herramienta.getDescripcion() + " ---");

        System.out.print("Ingrese su nombre de jugador: ");
        String nombreJugador = scanner.nextLine();

        System.out.print("Describa el problema: ");
        String descripcion = scanner.nextLine();

        Ticket ticket = gestor.crearTicket(nombreJugador, herramienta, descripcion);

        System.out.println("Ticket creado con exito.");
        System.out.println("Numero de ticket: " + ticket.getIdTicket() +
                " | Prioridad asignada: " + ticket.getPrioridad());
    }

    // ----------------- PANEL DE SOPORTE -----------------

    private void panelSoporte() {
        int opcion;

        do {
            mostrarPanelSoporte();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    atenderTicket();
                    break;
                case 2:
                    buscarTicket();
                    break;
                case 3:
                    mostrarPendientes();
                    break;
                case 4:
                    resolverTicket();
                    break;
                case 5:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }

    private void mostrarPanelSoporte() {
        System.out.println("\n--- PANEL DE SOPORTE ---");
        System.out.println("1. Atender ticket");
        System.out.println("2. Buscar ticket");
        System.out.println("3. Mostrar tickets");
        System.out.println("4. Resolver ticket");
        //MostrarTickets deberia poder filtrar por estado, Atender ticket y Resolver ticket deberian fusionarse 
        System.out.println("5. Volver");
        System.out.print("Seleccione una opcion: ");
    }

    private void atenderTicket() {
        if (!gestor.hayPendientes()) {
            System.out.println("No hay tickets pendientes por atender.");
            return;
        }

        Ticket ticket = gestor.atenderTicket();
        System.out.println("Atendiendo ticket:");
        System.out.println(ticket);
    }

    private void buscarTicket() {
        System.out.print("Ingrese el numero de ticket: ");
        int id = leerOpcion();

        Ticket ticket = gestor.buscarTicket(id);

        if (ticket == null) {
            System.out.println("No se encontro ningun ticket con ese numero.");
        } else {
            System.out.println(ticket);
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

    private void resolverTicket() {
        System.out.print("Ingrese el numero de ticket a resolver: ");
        int id = leerOpcion();

        Ticket ticket = gestor.buscarTicket(id);

        if (ticket == null) {
            System.out.println("No se encontro ningun ticket con ese numero.");
            return;
        }

        System.out.print("Ingrese la resolución o comentario final: ");
        String resolucion = scanner.nextLine();

        boolean exito = gestor.resolverTicket(id, resolucion);

        if (exito) {
            System.out.println("Ticket #" + id + " marcado como Resuelto.");
        } else {
            System.out.println("El ticket debe estar Atendido antes de poder resolverse.");
        }
    }

    // ----------------- UTILIDAD -----------------

    /**
     * Lee un numero entero ingresado por el usuario.
     * Si la entrada no es valida, devuelve -1 para forzar
     * a que el switch caiga en el default.
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
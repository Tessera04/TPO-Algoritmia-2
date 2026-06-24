package util;

import enums.HerramientaSoporte;
import modelo.Ticket;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersistenciaJSON {

    private static final String ARCHIVO = "tickets.json";

    public static class DatosGuardados {
        public final List<Ticket> tickets;
        public final int contadorId;

        public DatosGuardados(List<Ticket> tickets, int contadorId) {
            this.tickets = tickets;
            this.contadorId = contadorId;
        }
    }

    // -------------------- GUARDAR --------------------

    public static void guardar(Collection<Ticket> tickets, int contadorId) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            pw.println("{");
            pw.println("  \"contadorId\": " + contadorId + ",");
            pw.println("  \"tickets\": [");

            Ticket[] arr = tickets.toArray(new Ticket[0]);
            for (int i = 0; i < arr.length; i++) {
                Ticket t = arr[i];
                pw.println("    {");
                pw.println("      \"idTicket\": " + t.getIdTicket() + ",");
                pw.println("      \"nombreJugador\": \"" + escapar(t.getNombreJugador()) + "\",");
                pw.println("      \"herramienta\": \"" + t.getHerramienta().name() + "\",");
                pw.println("      \"descripcionProblema\": \"" + escapar(t.getDescripcionProblema()) + "\",");
                pw.println("      \"estado\": \"" + t.getEstado() + "\",");
                pw.println("      \"ordenLlegada\": \"" + t.getOrdenLlegada().toString() + "\",");
                if (t.getResolucion() == null) {
                    pw.println("      \"resolucion\": null");
                } else {
                    pw.println("      \"resolucion\": \"" + escapar(t.getResolucion()) + "\"");
                }
                pw.print("    }");
                if (i < arr.length - 1) pw.print(",");
                pw.println();
            }

            pw.println("  ]");
            pw.println("}");
        } catch (IOException e) {
            System.out.println("Advertencia: no se pudieron guardar los datos (" + e.getMessage() + ")");
        }
    }

    // -------------------- CARGAR --------------------

    public static DatosGuardados cargar() {
        List<Ticket> tickets = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return new DatosGuardados(tickets, 0);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            int contadorId = 0;

            Integer idTicket = null;
            String nombreJugador = null, herramientaStr = null, descripcion = null;
            String estado = null, ordenLlegadaStr = null, resolucion = null;

            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("\"contadorId\"")) {
                    contadorId = Integer.parseInt(extraerNumero(linea));
                } else if (linea.startsWith("\"idTicket\"")) {
                    idTicket = Integer.parseInt(extraerNumero(linea));
                } else if (linea.startsWith("\"nombreJugador\"")) {
                    nombreJugador = extraerTexto(linea);
                } else if (linea.startsWith("\"herramienta\"")) {
                    herramientaStr = extraerTexto(linea);
                } else if (linea.startsWith("\"descripcionProblema\"")) {
                    descripcion = extraerTexto(linea);
                } else if (linea.startsWith("\"estado\"")) {
                    estado = extraerTexto(linea);
                } else if (linea.startsWith("\"ordenLlegada\"")) {
                    ordenLlegadaStr = extraerTexto(linea);
                } else if (linea.startsWith("\"resolucion\"")) {
                    resolucion = linea.contains("null") ? null : extraerTexto(linea);
                } else if (linea.startsWith("}") && idTicket != null) {
                    HerramientaSoporte herramienta = HerramientaSoporte.valueOf(herramientaStr);
                    LocalDateTime ordenLlegada = LocalDateTime.parse(ordenLlegadaStr);

                    Ticket ticket = new Ticket(idTicket, nombreJugador, herramienta, descripcion, ordenLlegada);
                    ticket.setEstado(estado);
                    if (resolucion != null) ticket.setResolucion(resolucion);

                    tickets.add(ticket);

                    idTicket = null; nombreJugador = null; herramientaStr = null;
                    descripcion = null; estado = null; ordenLlegadaStr = null; resolucion = null;
                }
            }

            return new DatosGuardados(tickets, contadorId);

        } catch (Exception e) {
            System.out.println("Advertencia: no se pudieron cargar los datos (" + e.getMessage() + ")");
            return new DatosGuardados(tickets, 0);
        }
    }

    // -------------------- UTILIDAD --------------------

    private static String extraerNumero(String linea) {
        int colon = linea.indexOf(':');
        return linea.substring(colon + 1).trim().replace(",", "");
    }

    private static String extraerTexto(String linea) {
        int colon = linea.indexOf(':');
        int firstQuote = linea.indexOf('"', colon);
        if (firstQuote == -1) return "";
        int pos = firstQuote + 1;
        StringBuilder sb = new StringBuilder();
        while (pos < linea.length()) {
            char c = linea.charAt(pos);
            if (c == '\\' && pos + 1 < linea.length()) {
                char next = linea.charAt(pos + 1);
                switch (next) {
                    case '"':  sb.append('"');  pos += 2; break;
                    case '\\': sb.append('\\'); pos += 2; break;
                    case 'n':  sb.append('\n'); pos += 2; break;
                    case 'r':  sb.append('\r'); pos += 2; break;
                    default:   sb.append(c);    pos++;    break;
                }
            } else if (c == '"') {
                break;
            } else {
                sb.append(c);
                pos++;
            }
        }
        return sb.toString();
    }

    private static String escapar(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}

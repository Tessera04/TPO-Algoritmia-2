package estructura;

import java.util.Comparator;
import modelo.Ticket;

/**
 * Le ensena a Java cual ticket es mas importante.
 * Compara dos tickets para que la PriorityQueue sepa cual
 * debe salir primero.
 *
 * Criterio principal: a menor numero de prioridad, mas urgente
 * (prioridad 1 = Pagos, prioridad 2 = Cuenta).
 *
 * Criterio de desempate: si dos tickets tienen la misma prioridad,
 * se atiende primero el que llego antes (orden de llegada).
 */
public class ComparadorPrioridad implements Comparator<Ticket> {

    @Override
    public int compare(Ticket ticket1, Ticket ticket2) {
        int comparacionPrioridad = Integer.compare(ticket1.getPrioridad(), ticket2.getPrioridad());

        if (comparacionPrioridad != 0) {
            return comparacionPrioridad;
        }

        // Misma prioridad: gana el que llego primero
        return ticket1.getOrdenLlegada().compareTo(ticket2.getOrdenLlegada());
    }
}
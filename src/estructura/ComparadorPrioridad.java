package estructura;

import java.util.Comparator;
import modelo.Ticket;

public class ComparadorPrioridad implements Comparator<Ticket> {

    @Override
    public int compare(Ticket ticket1, Ticket ticket2) {

        // Menor número = mayor urgencia.
        if (ticket1.getPrioridad() < ticket2.getPrioridad()) {
            return -1;
        }

        if (ticket1.getPrioridad() > ticket2.getPrioridad()) {
            return 1;
        }

        // Si tienen la misma prioridad, sale primero el que llegó antes.
        if (ticket1.getOrdenLlegada().isBefore(ticket2.getOrdenLlegada())) {
            return -1;
        }

        if (ticket1.getOrdenLlegada().isAfter(ticket2.getOrdenLlegada())) {
            return 1;
        }

        return 0;
    }
}
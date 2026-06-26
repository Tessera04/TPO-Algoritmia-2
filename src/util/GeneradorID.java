package util;

import java.util.Map;
import java.util.Random;

import modelo.Ticket;

/**
 * Genera IDs aleatorios de 5 cifras para los tickets
 */
public class GeneradorID {

    private static final Random random = new Random();

    public static int generarID(Map<Integer, Ticket> ticketsPorId) {
        int id;

        do {
            id = random.nextInt(90000) + 10000;
        } while (ticketsPorId.containsKey(id));

        return id;
    }
}
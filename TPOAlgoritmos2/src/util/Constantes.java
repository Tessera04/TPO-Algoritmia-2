package util;

/**
 * Centraliza valores fijos usados en todo el sistema,
 * para no repetir strings o numeros "magicos" en cada clase.
 */
public class Constantes {

    // Estados posibles de un ticket
    public static final String ESTADO_PENDIENTE = "Pendiente";
    public static final String ESTADO_ATENDIDO = "Atendido";
    public static final String ESTADO_RESUELTO = "Resuelto";

    // Niveles de prioridad (la prioridad concreta de cada ticket
    // se define en HerramientaSoporte segun el area de soporte)
    public static final int PRIORIDAD_PAGOS = 1;   // mas urgente
    public static final int PRIORIDAD_CUENTA = 2;  // menos urgente
}
package util;

/**
 * Una "maquina" que genera numeros unicos y crecientes
 * para identificar cada ticket nuevo.
 * Primer ticket -> 1, segundo -> 2, tercero -> 3, etc.
 */
public class GeneradorID {

    private static int contador = 0;

    public static int generarID() {
        contador++;
        return contador;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int valor) {
        contador = valor;
    }
}
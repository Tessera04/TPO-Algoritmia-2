package tda;

import java.util.List;

/**
 * TDA Cola con Prioridad.
 *
 * Define las operaciones que necesita el sistema sin indicar cómo
 * se almacenan internamente los elementos.
 *
 * @param <T> tipo de los elementos almacenados.
 */
public interface IColaPrioridad<T> {

    void acolar(T elemento);

    T desacolar();

    T verProximo();

    boolean estaVacia();

    int tamano();

    List<T> aLista();
}
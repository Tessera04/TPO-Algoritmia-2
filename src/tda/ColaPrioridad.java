package tda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implementación del TDA Cola con Prioridad usando la PriorityQueue de
 * java.util, que internamente es un heap binario.
 *
 * El heap mantiene el elemento más prioritario en la raíz, logrando acolar
 * y desacolar en O(log n) (mas eficiente que una lista ordenada, que inserta
 * en O(n))
 *
 * El criterio de prioridad se recibe por constructor como un Comparator, así
 * la misma estructura sirve para cualquier criterio de orden
 *
 * @param <T> tipo de los elementos almacenados
 */
public class ColaPrioridad<T> implements IColaPrioridad<T> {

    private final PriorityQueue<T> heap;
    private final Comparator<T> comparador;

    public ColaPrioridad(Comparator<T> comparador) {
        this.comparador = comparador;
        this.heap = new PriorityQueue<>(comparador);
    }

    @Override
    public void acolar(T elemento) {
        heap.add(elemento);
    }

    @Override
    public T desacolar() {
        return heap.poll();
    }

    @Override
    public T verProximo() {
        return heap.peek();
    }

    @Override
    public boolean estaVacia() {
        return heap.isEmpty();
    }

    @Override
    public int tamano() {
        return heap.size();
    }

    /**
     * Devuelve los elementos en orden de prioridad (el primero es el más
     * urgente). El iterador del heap no está ordenado, por eso ordenamos una
     * copia con el mismo comparador antes de entregarla
     */
    @Override
    public List<T> aLista() {
        List<T> lista = new ArrayList<>(heap);
        lista.sort(comparador);
        return lista;
    }
}

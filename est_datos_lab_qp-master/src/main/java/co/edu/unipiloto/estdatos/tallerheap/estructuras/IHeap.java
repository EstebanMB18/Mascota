package co.edu.unipiloto.estdatos.tallerheap.estructuras;

/**
 * Interfaz de un heap binario genérico.
 */
public interface IHeap<T> {

    /**
     * Agrega un elemento al heap
     */
    void insertar(T elemento);

    /**
     * Retorna pero no remueve el elemento raíz del heap.
     * @return El elemento raíz del heap.
     */
    T peek();

    /**
     * Retorna y remueve el elemento raíz del heap.
     * @return El elemento raíz eliminado.
     */
    T eliminar();

    /**
     * Retorna el número de elementos en el heap.
     * @return Número de elementos.
     */
    int size();

    /**
     * Retorna true si el heap está vacío; false en caso contrario.
     * @return true si vacío.
     */
    boolean estaVacia();
}
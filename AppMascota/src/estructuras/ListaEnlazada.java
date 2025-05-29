package estructuras;

import java.util.Iterator;

public class ListaEnlazada<T> implements Iterable<T> {

    private Nodo primero;
    private int tamanio;

    private class Nodo {
        T valor;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    public ListaEnlazada() {
        primero = null;
        tamanio = 0;
    }

    public void agregar(T valor) {
        Nodo nuevo = new Nodo(valor);
        if (primero == null) {
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    public void eliminar(T valor) {
        if (primero == null) return;

        if (primero.valor.equals(valor)) {
            primero = primero.siguiente;
            tamanio--;
            return;
        }

        Nodo actual = primero;
        while (actual.siguiente != null && !actual.siguiente.valor.equals(valor)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            tamanio--;
        }
    }

    public boolean contiene(T valor) {
        Nodo actual = primero;
        while (actual != null) {
            if (actual.valor.equals(valor)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public int size() {
        return tamanio;
    }

    public boolean isEmpty() {
        return tamanio == 0;
    }

    public T get(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        Nodo actual = primero;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        return actual.valor;
    }

    public void set(int index, T valor) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        Nodo actual = primero;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        actual.valor = valor;
    }
    

    @Override
    public Iterator<T> iterator() {
        return new IteradorLista();
    }

    private class IteradorLista implements Iterator<T> {
        private Nodo actual = primero;

        public boolean hasNext() {
            return actual != null;
        }

        public T next() {
            T val = actual.valor;
            actual = actual.siguiente;
            return val;
        }
    }
    public void vaciar() {
    primero = null;
    tamanio = 0;
}
}
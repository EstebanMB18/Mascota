package estructuras;

import java.util.Iterator;

public class TablaEncadenada<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private Nodo primero;

    private class Nodo {
        Key key;
        Value value;
        Nodo next;

        public Nodo(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public TablaEncadenada() {
        primero = null;
    }

    public boolean isEmpty() {
        return primero == null;
    }

    public int size() {
        return size(primero);
    }

    private int size(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + size(nodo.next);
    }

    public void put(Key key, Value value) {
        Nodo nuevo = new Nodo(key, value);
        nuevo.next = primero;
        primero = nuevo;
    }

    public Value get(Key key) {
        return get(key, primero);
    }

    private Value get(Key key, Nodo nodo) {
        if (nodo == null) return null;
        if (key.compareTo(nodo.key) == 0) return nodo.value;
        return get(key, nodo.next);
    }

    public void delete(Key key) {
        primero = delete(key, primero);
    }

    private Nodo delete(Key key, Nodo nodo) {
        if (nodo == null) return null;
        if (key.compareTo(nodo.key) == 0) return nodo.next;
        nodo.next = delete(key, nodo.next);
        return nodo;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public Iterator<Key> iterator() {
        return new Iterador();
    }

    private class Iterador implements Iterator<Key> {
        private Nodo actual = primero;

        public boolean hasNext() {
            return actual != null;
        }

        public Key next() {
            Key k = actual.key;
            actual = actual.next;
            return k;
        }
    }
    public String mostrarContenido() {
    StringBuilder sb = new StringBuilder("[TABLA ENCADENADA - Clave → Valor]\n");
    Nodo actual = primero;
    while (actual != null) {
        sb.append(actual.key).append(" → ").append(actual.value).append("\n");
        actual = actual.next;
    }
    return sb.toString();
}

}

package estructuras;

import java.util.Iterator;

public class Cola<Item> implements Iterable<Item> {

    private Nodo<Item> primero;
    private Nodo<Item> ultimo;
    private int tamanio;

    private static class Nodo<Item> {
        Item item;
        Nodo<Item> siguiente;

        public Nodo(Item item) {
            this.item = item;
            this.siguiente = null;
        }
    }

    public Cola() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    public void enqueue(Item item) {
        Nodo<Item> nuevo = new Nodo<>(item);
        if (isEmpty()) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        tamanio++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new IllegalStateException("La cola está vacía.");
        Item item = primero.item;
        primero = primero.siguiente;
        if (primero == null) {
            ultimo = null;
        }
        tamanio--;
        return item;
    }

    public boolean isEmpty() {
        return primero == null;
    }

    public int size() {
        return tamanio;
    }

    public Item peek() {
        if (isEmpty()) throw new IllegalStateException("La cola está vacía.");
        return primero.item;
    }
    
    @Override
public Iterator<Item> iterator() {
    return new Iterador();
}

private class Iterador implements Iterator<Item> {
    private Nodo<Item> actual = primero;

    public boolean hasNext() {
        return actual != null;
    }

    public Item next() {
        Item val = actual.item;
        actual = actual.siguiente;
        return val;
    }
}
public String mostrarContenido() {
    StringBuilder sb = new StringBuilder("[COLA] Actividades en espera:\n");
    Nodo<Item> actual = primero;
    while (actual != null) {
        sb.append("- ").append(actual.item.toString()).append("\n");
        actual = actual.siguiente;
    }
    return sb.toString();
}
}

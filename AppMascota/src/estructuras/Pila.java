package estructuras;

import static javafx.scene.input.KeyCode.T;

public class Pila<Item> {

    private Nodo<Item> tope;
    private int tamanio;

    private static class Nodo<Item> {
        Item item;
        Nodo<Item> siguiente;

        public Nodo(Item item) {
            this.item = item;
        }
    }

    public Pila() {
        tope = null;
        tamanio = 0;
    }

    public void push(Item item) {
        Nodo<Item> nuevo = new Nodo<>(item);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamanio++;
    }

    public Item pop() {
        if (isEmpty()) throw new IllegalStateException("La pila está vacía.");
        Item item = tope.item;
        tope = tope.siguiente;
        tamanio--;
        return item;
    }

    public boolean isEmpty() {
        return tope == null;
    }

    public int size() {
        return tamanio;
    }

    public Item peek() {
        if (isEmpty()) throw new IllegalStateException("La pila está vacía.");
        return tope.item;
    }
 public String mostrarContenido() {
    StringBuilder sb = new StringBuilder("[PILA] Recordatorios:\n");
    Nodo<Item> actual = tope;
    while (actual != null) {
        sb.append("- ").append(actual.item.toString()).append("\n");
        actual = actual.siguiente;
    }
    return sb.toString();

}
}

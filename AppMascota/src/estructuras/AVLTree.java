package estructuras;

import java.util.function.Consumer;

public class AVLTree<Key extends Comparable<Key>, Value> {

    private class Nodo {
        Key key;
        Value value;
        Nodo left, right;
        int height;
        int size;

        Nodo(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.size = 1;
        }
    }

    private Nodo root;

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Nodo x) {
        return x == null ? 0 : x.size;
    }

    private int height(Nodo x) {
        return x == null ? 0 : x.height;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Nodo put(Nodo x, Key key, Value value) {
        if (x == null) return new Nodo(key, value);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.value = value;

        return balance(update(x));
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Nodo x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.value;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Nodo delete(Nodo x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Nodo temp = min(x.right);
            x.key = temp.key;
            x.value = temp.value;
            x.right = delete(x.right, temp.key);
        }
        return balance(update(x));
    }

    private Nodo update(Nodo x) {
        x.height = 1 + Math.max(height(x.left), height(x.right));
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Nodo balance(Nodo x) {
        int balanceFactor = height(x.left) - height(x.right);
        if (balanceFactor > 1) {
            if (height(x.left.left) < height(x.left.right)) x.left = rotateLeft(x.left);
            return rotateRight(x);
        } else if (balanceFactor < -1) {
            if (height(x.right.right) < height(x.right.left)) x.right = rotateRight(x.right);
            return rotateLeft(x);
        }
        return x;
    }

    private Nodo rotateRight(Nodo y) {
        Nodo x = y.left;
        y.left = x.right;
        x.right = y;
        update(y);
        return update(x);
    }

    private Nodo rotateLeft(Nodo x) {
        Nodo y = x.right;
        x.right = y.left;
        y.left = x;
        update(x);
        return update(y);
    }

    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    }

    private Nodo min(Nodo x) {
        return x.left == null ? x : min(x.left);
    }

    public Key max() {
        if (isEmpty()) return null;
        return max(root).key;
    }

    private Nodo max(Nodo x) {
        return x.right == null ? x : max(x.right);
    }

    public Key floor(Key key) {
        Nodo x = floor(root, key);
        return x == null ? null : x.key;
    }

    private Nodo floor(Nodo x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Nodo t = floor(x.right, key);
        return t != null ? t : x;
    }

    public Key ceiling(Key key) {
        Nodo x = ceiling(root, key);
        return x == null ? null : x.key;
    }

    private Nodo ceiling(Nodo x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        Nodo t = ceiling(x.left, key);
        return t != null ? t : x;
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Nodo x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }

    public Key select(int k) {
        Nodo x = select(root, k);
        return x == null ? null : x.key;
    }

    private Nodo select(Nodo x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

  public void deleteMin() {
    if (!isEmpty()) root = deleteMin(root);
}

private Nodo deleteMin(Nodo x) {
    if (x.left == null) return x.right;
    x.left = deleteMin(x.left);
    return balance(update(x));
}

public Iterable<Key> keys() {
    ListaEnlazada<Key> lista = new ListaEnlazada<>();
    keys(root, lista);
    return lista;
}

private void keys(Nodo x, ListaEnlazada<Key> lista) {
    if (x == null) return;
    keys(x.left, lista);
    lista.agregar(x.key);
    keys(x.right, lista);
}
public String mostrarContenido() {
    StringBuilder sb = new StringBuilder("[ÁRBOL AVL - In-Order]\n");
    mostrarContenido(root, sb);
    return sb.toString();
}

private void mostrarContenido(Nodo nodo, StringBuilder sb) {
    if (nodo != null) {
        mostrarContenido(nodo.left, sb);
        sb.append(nodo.key).append(" → ").append(nodo.value).append("\n");
        mostrarContenido(nodo.right, sb);
    }
}
public void imprimirArbol(Consumer<String> consumidor) {
    imprimirArbol(root, "", true, consumidor);
}

private void imprimirArbol(Nodo nodo, String prefijo, boolean esUltimo, Consumer<String> consumidor) {
    if (nodo != null) {
        consumidor.accept(prefijo + (esUltimo ? "└── " : "├── ") + nodo.key);
        imprimirArbol(nodo.left, prefijo + (esUltimo ? "    " : "│   "), false, consumidor);
        imprimirArbol(nodo.right, prefijo + (esUltimo ? "    " : "│   "), true, consumidor);
    }
}}

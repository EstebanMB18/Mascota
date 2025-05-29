package estructuras;

import java.util.Iterator;

public class ColaPrioridadMax<Key extends Comparable<Key>>implements Iterable<Key> {

    public final static int MAX_TAMANO = 100;
    private Key[] pq;
    private int N;

    @SuppressWarnings("unchecked")
    public ColaPrioridadMax() {
        pq = (Key[]) new Comparable[MAX_TAMANO + 1];
        N = 0;
    }

    public ColaPrioridadMax(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
        N = 0;
    }

    public ColaPrioridadMax(Key[] items) {
        pq = items;
        N = items.length - 1;
    }

    public void insert(Key v) {
        pq[++N] = v;
        swim(N);
    }

    public Key max() {
        if (N == 0) return null;
        return pq[1];
    }

    public Key delMax() {
        if (N == 0) return null;
        Key val = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        return val;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    public String mostrarContenido() {
    return this.toString();
}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MaxPQ { N = " + N + " }\n |");
        for (int i = 1; i <= N; i++) {
            sb.append(pq[i].toString()).append("|");
        }
        return sb.toString();
    }
    public Key get(int i) {
    if (i >= 1 && i <= N) {
        return pq[i];
    }
    return null;
}
      
    public Iterator<Key> iterator() {
        return new Iterator<Key>() {
            private int index = 1;

            @Override
            public boolean hasNext() {
                return index <= N;
            }

            @Override
            public Key next() {
                return pq[index++];
            }
        };
    }

}

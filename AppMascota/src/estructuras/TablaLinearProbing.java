package estructuras;

public class TablaLinearProbing<Key, Value> {
    private int M = 16;
    private Key[] keys;
    private Value[] vals;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public TablaLinearProbing() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value val) {
        if (N >= M / 2) resize(2 * M);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) return vals[i];
        }
        return null;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N <= M / 8) resize(M / 2);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }
    public String mostrarContenido() {
    StringBuilder sb = new StringBuilder("[TABLA LINEAR PROBING - Dueños → Mascotas]\n");
    for (int i = 0; i < M; i++) {
        if (keys[i] != null) {
            sb.append(keys[i]).append(" → ").append(vals[i]).append("\n");
        }
    }
    return sb.toString();
}

    @SuppressWarnings("unchecked")
    private void resize(int cap) {
        TablaLinearProbing<Key, Value> temp = new TablaLinearProbing<>();
        temp.M = cap;
        temp.keys = (Key[]) new Object[cap];
        temp.vals = (Value[]) new Object[cap];
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        M = cap;
    }
}
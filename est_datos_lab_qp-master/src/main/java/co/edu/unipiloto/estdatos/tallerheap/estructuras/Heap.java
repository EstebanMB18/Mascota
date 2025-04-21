/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unipiloto.estdatos.tallerheap.estructuras;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> implements IHeap<T> {

    private ArrayList<T> elementos;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.elementos = new ArrayList<>();
    }

    private boolean less(int i, int j) {
        return comparator.compare(elementos.get(i), elementos.get(j)) > 0;
    }


    private void swap(int i, int j) {
        T temp = elementos.get(i);
        elementos.set(i, elementos.get(j));
        elementos.set(j, temp);
    }

    private void siftUp(int k) {
        while (k > 0 && less((k - 1) / 2, k)) {
            swap(k, (k - 1) / 2);
            k = (k - 1) / 2;
        }
    }

    private void siftDown(int k) {
        int n = elementos.size();
        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j + 1 < n && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    @Override
    public void insertar(T elemento) {
        elementos.add(elemento);
        siftUp(elementos.size() - 1);
    }

    @Override
    public T eliminar() {
        if (estaVacia()) return null;
        T max = elementos.get(0);
        int lastIndex = elementos.size() - 1;
        swap(0, lastIndex);
        elementos.remove(lastIndex);
        siftDown(0);
        return max;
    }

    @Override
    public T peek() {
        if (estaVacia()) return null;
        return elementos.get(0);
    }

    @Override
    public int size() {
        return elementos.size();
    }

    @Override
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    /**
     * Devuelve una copia de los elementos actuales del heap.
     * No altera el estado del heap.
     * @return Lista con los elementos del heap.
     */
    public ArrayList<T> toList() {
        return new ArrayList<>(elementos);
    }
}


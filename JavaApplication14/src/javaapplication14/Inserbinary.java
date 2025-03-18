/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication14;

/**
 *
 * @author brand
 */
public class Inserbinary {



    // Método principal para ordenar el arreglo usando Insertion Binary Sort
    public static void insertionBinarySort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];  // Elemento a insertar
            int pos = binarySearch(arr, key, 0, i - 1); // Encuentra la posición correcta

            // Mueve los elementos a la derecha para hacer espacio para key
            for (int j = i; j > pos; j--) {
                arr[j] = arr[j - 1];
            }
            arr[pos] = key; // Inserta el elemento en la posición correcta

            // Mostrar el estado del arreglo en cada paso
            System.out.print("Paso " + i + ": ");
            printArray(arr);
        }
    }

    // Búsqueda binaria para encontrar la posición de inserción
    private static int binarySearch(int[] arr, int key, int low, int high) {
        while (low <= high) {
            int mid = (low + high) / 2;
            if (key < arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low; // Retorna la posición de inserción
    }

    // Método para imprimir el arreglo
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    // Método para verificar si el arreglo de enteros está ordenado

    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) { // Si un número es mayor que el siguiente, no está ordenado
                return false;
            }
        }
        return true; // Si no hay desorden, el arreglo está ordenado
    }

    // Método principal para probar el algoritmo
    public static void main(String[] args) {
        int[] arr = {29, 10, 14, 37, 13};

        System.out.println("Arreglo original:");
        printArray(arr);

        // Verificar si el arreglo está ordenado antes del ordenamiento
        System.out.println("\n¿Está ordenado antes?: " + isSorted(arr));

        System.out.println("\nProceso de ordenamiento:");
        Inserbinary.insertionBinarySort(arr);

        System.out.println("\nArreglo ordenado:");
        printArray(arr);

        // Verificar si el arreglo está ordenado después del ordenamiento
        System.out.println("\n¿Está ordenado después?: " + isSorted(arr));
    }
}

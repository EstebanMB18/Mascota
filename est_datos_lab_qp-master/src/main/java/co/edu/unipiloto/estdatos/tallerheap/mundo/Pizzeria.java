package co.edu.unipiloto.estdatos.tallerheap.mundo;

import co.edu.unipiloto.estdatos.tallerheap.estructuras.Heap;
import java.util.ArrayList;

public class Pizzeria {
    
    // ----------------------------------
    // Constantes
    // ----------------------------------

    public final static String RECIBIR_PEDIDO = "RECIBIR";
    public final static String ATENDER_PEDIDO = "ATENDER";
    public final static String DESPACHAR_PEDIDO = "DESPACHAR";
    public final static String FIN = "FIN";

    // ----------------------------------
    // Atributos
    // ----------------------------------

    // Cola de prioridad para pedidos recibidos (por precio)
    private Heap<Pedido> pedidosRecibidos;

    // Cola de prioridad para pedidos por despachar (por cercanía)
    private Heap<Pedido> pedidosDespacho;

    // ----------------------------------
    // Constructor
    // ----------------------------------

    public Pizzeria() {
        pedidosRecibidos = new Heap<>(new Pedido.ComparadorPorPrecio());
        pedidosDespacho = new Heap<>(new Pedido.ComparadorPorCercania());
    }

    // ----------------------------------
    // Métodos
    // ----------------------------------

    /**
     * Agrega un pedido a la cola de prioridad de pedidos recibidos
     * @param nombreAutor nombre del autor del pedido
     * @param precio precio del pedido 
     * @param cercania cercania del autor del pedido 
     */
    public void agregarPedido(String nombreAutor, double precio, int cercania) {
        Pedido pedido = new Pedido(precio, nombreAutor, cercania);
        pedidosRecibidos.insertar(pedido);
    }

    /**
     * Atiende el pedido más importante y lo mueve a la cola de despachos
     * @return Pedido atendido
     */
public Pedido atenderPedido() {
    if (pedidosRecibidos.estaVacia()) {
        return null;
    }
    Pedido pedido = pedidosRecibidos.eliminar(); // Elimina el pedido con el mayor precio
    pedidosDespacho.insertar(pedido); // Inserta el pedido en la cola de despachos (por cercanía)
    return pedido;
}

    /**
     * Despacha el pedido más cercano de la cola de despachos
     * @return Pedido despachado
     */
    public Pedido despacharPedido() {
        if (pedidosDespacho.estaVacia()) {
            return null;
        }
        return pedidosDespacho.eliminar();
    }

    /**
     * Retorna los pedidos recibidos como una lista (sin vaciar la cola).
     * Requiere que Heap tenga un método toList().
     * @return Lista de pedidos recibidos.
     */
    public ArrayList<Pedido> pedidosRecibidosList() {
        return new ArrayList<>(pedidosRecibidos.toList());
    }

    /**
     * Retorna los pedidos por despachar como una lista (sin vaciar la cola).
     * Requiere que Heap tenga un método toList().
     * @return Lista de pedidos por despachar.
     */
    public ArrayList<Pedido> colaDespachosList() {
        return new ArrayList<>(pedidosDespacho.toList());
    }

    // ----------------------------------
    // Getters
    // ----------------------------------

    public Heap<Pedido> getPedidosRecibidos() {
        return pedidosRecibidos;
    }

    public Heap<Pedido> getPedidosDespacho() {
        return pedidosDespacho;
    }
}

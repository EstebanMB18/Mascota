package co.edu.unipiloto.estdatos.tallerheap.mundo;


public class Pedido {
    // ----------------------------------
    // Atributos
    // ----------------------------------

    private double precio; // Precio del pedido
    private String autorPedido; // Autor del pedido
    private int cercania; // Cercanía del pedido

    // ----------------------------------
    // Constructor
    // ----------------------------------

    public Pedido(double precio, String autorPedido, int cercania) {
        this.precio = precio;
        this.autorPedido = autorPedido;
        this.cercania = cercania;
    }

    // ----------------------------------
    // Métodos
    // ----------------------------------

    public double getPrecio() {
        return precio;
    }

    public String getAutorPedido() {
        return autorPedido;
    }

    public int getCercania() {
        return cercania;
    }

    // Método toString para imprimir los pedidos
    @Override
    public String toString() {
        return "Pedido{" +
                "precio=" + precio +
                ", autorPedido='" + autorPedido + '\'' +
                ", cercania=" + cercania +
                '}';
    }

    // Métodos de comparación para la prioridad de los pedidos

    // Comparación por precio (mayor precio primero)
 public static class ComparadorPorPrecio implements java.util.Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        return Double.compare(p2.getPrecio(), p1.getPrecio()); // Mayor precio primero
    }
}

    // Comparación por cercanía (menor cercanía primero)
public static class ComparadorPorCercania implements java.util.Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        return Integer.compare(p1.getCercania(), p2.getCercania()); // Menor cercanía primero
    }
}}
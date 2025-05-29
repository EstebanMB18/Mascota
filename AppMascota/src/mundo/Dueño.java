package mundo;

import observer.Observador;

public class Dueño implements Observador {
    private String nombre;

    public Dueño(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("Notificación para " + nombre + ": " + mensaje);
    }

    public String getNombre() {
        return nombre;
    }
}
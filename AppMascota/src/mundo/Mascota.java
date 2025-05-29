package mundo;

import java.util.ArrayList;
import java.util.List;
import observer.Observador;

public class Mascota implements Comparable<Mascota> {
    private String nombre;
    private int edad;
    private String raza;
    private String dueño;

    public Mascota(String nombre, String raza, int edad, String dueño) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.dueño = dueño;
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getRaza() { return raza; }
    public String getDueño() { return dueño; }

    @Override
public int compareTo(Mascota otra) {
    // Orden descendente por raza
    return otra.getRaza().compareTo(this.getRaza());
}
    private List<Observador> observadores = new ArrayList<>();

public void agregarObservador(Observador o) {
    observadores.add(o);
}

public void notificarObservadores(String mensaje) {
    for (Observador o : observadores) {
        o.actualizar(mensaje);
    }
}

public String toString() {
    return nombre + " (" + raza + ", " + edad + " años)";
}
}
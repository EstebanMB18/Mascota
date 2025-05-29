package mundo;

public class Actividad {
    private String descripcion;
    private Mascota mascota;

    public Actividad(String descripcion, Mascota mascota) {
        this.descripcion = descripcion;
        this.mascota = mascota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    @Override
    public String toString() {
        return descripcion + " (Mascota: " + mascota.getNombre() + ")";
    }
}

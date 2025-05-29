package mundo;

public class Sintoma implements Comparable<Sintoma> {
    private String descripcion;
    private int gravedad;

    public Sintoma(String descripcion, int gravedad) {
        this.descripcion = descripcion;
        this.gravedad = gravedad;
    }

    public int getGravedad() { return gravedad; }
    public String getDescripcion() { return descripcion; }

    @Override
    public int compareTo(Sintoma o) {
        return Integer.compare(this.gravedad, o.gravedad);
    }

    public String toString() {
        return descripcion + " (Gravedad: " + gravedad + ")";
    }
}
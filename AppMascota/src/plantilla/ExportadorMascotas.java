package plantilla;


import estructuras.ListaEnlazada;
import mundo.Mascota;

public abstract class ExportadorMascotas {

    public final void exportar(Iterable<Mascota> mascotas) {
        abrirDocumento();
        for (Mascota m : mascotas) {
            exportarMascota(m);
        }
        cerrarDocumento();
    }

    protected abstract void abrirDocumento();
    protected abstract void exportarMascota(Mascota m);
    protected abstract void cerrarDocumento();
}
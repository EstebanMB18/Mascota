package plantilla;

import mundo.Mascota;

public class ExportadorConsola extends ExportadorMascotas {

    @Override
    protected void abrirDocumento() {
        System.out.println("=== LISTA DE MASCOTAS ===");
    }

    @Override
    protected void exportarMascota(Mascota m) {
        System.out.println("- " + m.getNombre() + " | Edad: " + m.getEdad()
            + " | Raza: " + m.getRaza() + " | Dueño: " + m.getDueño());
    }

    @Override
    protected void cerrarDocumento() {
        System.out.println("=== FIN ===");
    }
}
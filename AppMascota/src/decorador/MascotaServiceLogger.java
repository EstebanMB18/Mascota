package decorador;

import mundo.Mascota;

public class MascotaServiceLogger implements MascotaService {
    private MascotaService base;

    public MascotaServiceLogger(MascotaService base) {
        this.base = base;
    }

    @Override
    public Mascota buscar(String nombre) throws Exception {
        System.out.println("[LOG] Buscando mascota: " + nombre);
        Mascota m = base.buscar(nombre);
        System.out.println("[LOG] Resultado: " + (m != null ? "Encontrada" : "No encontrada"));
        return m;
    }
}
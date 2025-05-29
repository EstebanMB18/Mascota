package decorador;

import mundo.Mascota;

public class MascotaServiceVacunada implements MascotaService {
    private MascotaService base;

    public MascotaServiceVacunada(MascotaService base) {
        this.base = base;
    }

    @Override
    public Mascota buscar(String nombre) throws Exception {
        Mascota m = base.buscar(nombre);
        if (m != null) {
            System.out.println("[INFO] Mascota vacunada ✅: " + m.getNombre());
        }
        return m;
    }
}
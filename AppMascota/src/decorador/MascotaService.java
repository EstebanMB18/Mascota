package decorador;

import mundo.Mascota;

public interface MascotaService {
    Mascota buscar(String nombre) throws Exception;
}
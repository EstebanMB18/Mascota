package decorador;

import dao.MascotaDAO;
import mundo.Mascota;

public class MascotaServiceBase implements MascotaService {
    private MascotaDAO dao;

    public MascotaServiceBase(MascotaDAO dao) {
        this.dao = dao;
    }

    @Override
    public Mascota buscar(String nombre) throws Exception {
        return dao.buscarPorNombre(nombre);
    }}

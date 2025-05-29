package dao;

import mundo.Mascota;
import java.util.List;

public interface MascotaDAO {
    void insertar(Mascota m) throws Exception;
    Mascota buscarPorNombre(String nombre) throws Exception;
    List<Mascota> listar() throws Exception;
    void eliminar(String nombre) throws Exception;
}
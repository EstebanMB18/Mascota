package controlador;

import dao.MascotaDAO;
import dao.MascotaDAOImplementation;
import estructuras.*;
import mundo.*;
import observer.Observador;

public class Controlador {

    private ListaEnlazada<Mascota> listaMascotas = new ListaEnlazada<>();
    public Cola<Actividad> actividades = new Cola<>();
    public Pila<String> recordatorios = new Pila<>();
    public ColaPrioridadMax<Mascota> colaRaza = new ColaPrioridadMax<>();
    public ColaPrioridadMin<Sintoma> colaSintomas = new ColaPrioridadMin<>();
    public TablaLinearProbing<String, String> tablaDueños = new TablaLinearProbing<>();
    public TablaEncadenada<String, String> tablaSintomas = new TablaEncadenada<>();
    public TablaEncadenada<String, String> tablaCuidados = new TablaEncadenada<>();
    public AVLTree<String, Mascota> arbolMascotas = new AVLTree<>();
    private MascotaDAO mascotaDAO = new MascotaDAOImplementation();

    // ========== REGISTRO DE MASCOTA ==========
public void registrarMascota(Mascota m) {
    listaMascotas.agregar(m);
    arbolMascotas.put(m.getNombre(), m);
    tablaDueños.put(m.getDueño(), m.getNombre());

    // Observer: el dueño se suscribe a su mascota
    Dueño d = new Dueño(m.getDueño());
    m.agregarObservador(d);


    // DAO: guardar en base de datos
    try {
        MascotaDAO dao = new MascotaDAOImplementation();
        dao.insertar(m);
        System.out.println("Mascota también guardada en base de datos (DAO).");
    } catch (Exception e) {
        System.out.println("Error guardando en BD: " + e.getMessage());
    }
}
    // ========== ACTIVIDADES ==========
    public void agregarActividad(Actividad act) {
        actividades.enqueue(act);
        act.getMascota().notificarObservadores("Nueva actividad: " + act.getDescripcion());
        recordatorios.push("Actividad para " + act.getMascota().getNombre() + ": " + act.getDescripcion());
    }
    public Iterable<Actividad> getActividades() {
    return actividades;
}

    public void mostrarActividades() {
        for (Actividad a : actividades) {
            System.out.println("- " + a.getDescripcion());
        }
    }

    public int tamañoActividades() {
        return actividades.size();
    }
    

    // ========== PILA RECORDATORIOS ==========
    public void agregarRecordatorio(String mensaje) {
        recordatorios.push(mensaje);
    }

    public String verUltimoRecordatorio() {
        return recordatorios.isEmpty() ? "No hay recordatorios." : recordatorios.peek();
    }

    public String eliminarRecordatorio() {
        return recordatorios.isEmpty() ? "Nada que eliminar." : recordatorios.pop();
    }

    public boolean isRecordatorioVacio() {
        return recordatorios.isEmpty();
    }

    // ========== COLA DE PRIORIDAD ==========
    public void asignarPaseador(Mascota m) {
        colaRaza.insert(m);
        recordatorios.push("Mascota " + m.getNombre() + " asignada a paseador.");
    }

    public Mascota atenderPaseador() {
        Mascota m = colaRaza.delMax();
        if (m != null) {
            recordatorios.push("Mascota " + m.getNombre() + " atendida por paseador.");
        }
        return m;
    }

    public void registrarSintoma(String nombreMascota, Sintoma s) {
        colaSintomas.insert(s);
        tablaSintomas.put(nombreMascota, s.getDescripcion());
        recordatorios.push("Sintoma registrado para " + nombreMascota + ": " + s.getDescripcion());
    }

    public Sintoma atenderVeterinario() {
        return colaSintomas.delMin();
    }

    // ========== CUIDADOS Y SÍNTOMAS ==========
    public void agregarCuidados(String nombreMascota, String cuidado) {
        tablaCuidados.put(nombreMascota, cuidado);
        recordatorios.push("Cuidado agregado para " + nombreMascota + ": " + cuidado);
    }

    public ListaEnlazada<String> obtenerCuidados(String nombreMascota) {
        ListaEnlazada<String> cuidados = new ListaEnlazada<>();
        for (String clave : tablaCuidados) {
            if (clave.equals(nombreMascota)) {
                cuidados.agregar(tablaCuidados.get(clave));
            }
        }
        return cuidados;
    }

    public ListaEnlazada<String> obtenerSintomas(String nombreMascota) {
        ListaEnlazada<String> sintomas = new ListaEnlazada<>();
        for (String clave : tablaSintomas) {
            if (clave.equals(nombreMascota)) {
                sintomas.agregar(tablaSintomas.get(clave));
            }
        }
        return sintomas;
    }
public String buscarMascotaPorSintoma(String descripcionSintoma) {
    for (String nombreMascota : tablaSintomas) {
        String sintoma = tablaSintomas.get(nombreMascota);
        if (sintoma != null && sintoma.equals(descripcionSintoma)) {
            return nombreMascota;
        }
    }
    return "Desconocida";
}

    // ========== ORDENAMIENTOS ==========
    public ListaEnlazada<Mascota> listarMascotasOrdenadas() {
        ListaEnlazada<Mascota> ordenada = new ListaEnlazada<>();
        for (Mascota m : listaMascotas) {
            insertarOrdenado(ordenada, m);
        }
        return ordenada;
    }

    public ListaEnlazada<Mascota> listarMascotasPorEdadDesc() {
        ListaEnlazada<Mascota> lista = listaMascotas;
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (lista.get(j).getEdad() > lista.get(maxIdx).getEdad()) {
                    maxIdx = j;
                }
            }
            Mascota temp = lista.get(i);
            lista.set(i, lista.get(maxIdx));
            lista.set(maxIdx, temp);
        }
        return lista;
    }

    private void insertarOrdenado(ListaEnlazada<Mascota> lista, Mascota nueva) {
        ListaEnlazada<Mascota> temp = new ListaEnlazada<>();
        boolean insertado = false;
        for (Mascota m : lista) {
            if (!insertado && nueva.getNombre().compareTo(m.getNombre()) < 0) {
                temp.agregar(nueva);
                insertado = true;
            }
            temp.agregar(m);
        }
        if (!insertado) {
            temp.agregar(nueva);
        }
        lista.vaciar();
        for (Mascota m : temp) {
            lista.agregar(m);
        }
    }

   
    // ========== AVL ==========
    public ListaEnlazada<Mascota> listarMascotasAVL() {
        ListaEnlazada<Mascota> mascotasAVL = new ListaEnlazada<>();
        Iterable<String> claves = arbolMascotas.keys();
        for (String key : claves) {
            mascotasAVL.agregar(arbolMascotas.get(key));
        }
        return mascotasAVL;
    }

    public int tamañoListaMascotas() {
        return listaMascotas.size();
    }
    public Mascota buscarMascotaPorNombre(String nombre) {
    for (Mascota m : listaMascotas) {
        if (m.getNombre().equalsIgnoreCase(nombre)) {
            return m;
        }
    }
    return null;
}
    public void eliminarMascota(String nombreMascota) throws Exception {
    Mascota mascota = buscarMascotaPorNombre(nombreMascota);
    if (mascota == null) return;

    // 1. Lista enlazada
    listaMascotas.eliminar(mascota);

    // 2. AVL
     arbolMascotas.delete(nombreMascota);

    // 3. Tabla de dueños
    tablaDueños.delete(mascota.getDueño());

    // 4. Tabla de síntomas
    tablaSintomas.delete(mascota.getNombre());

    // 5. Tabla de cuidados
    tablaCuidados.delete(mascota.getNombre());

    // 6. Cola de prioridad por raza (reconstrucción)
    ColaPrioridadMax<Mascota> nuevaColaRaza = new ColaPrioridadMax<>();
    for (int i = 1; i <= colaRaza.size(); i++) {
        Mascota m = colaRaza.get(i);
        if (!m.equals(mascota)) {
            nuevaColaRaza.insert(m);
        }
    }
    colaRaza = nuevaColaRaza;

    // 7. Cola de prioridad por síntomas (reconstrucción)
    ColaPrioridadMin<Sintoma> nuevaColaSintomas = new ColaPrioridadMin<>();
    for (Sintoma s : colaSintomas) {
        String nombre = buscarMascotaPorSintoma(s.getDescripcion());
        if (!nombre.equals(nombreMascota)) {
            nuevaColaSintomas.insert(s);
        }
    }
    colaSintomas = nuevaColaSintomas;

    // 8. Eliminar de base de datos
    mascotaDAO.eliminar(nombreMascota);
}
}

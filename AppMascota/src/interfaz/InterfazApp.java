package interfaz;

import controlador.Controlador;
import dao.MascotaDAO;
import dao.MascotaDAOImplementation;
import decorador.MascotaService;
import decorador.MascotaServiceBase;
import decorador.MascotaServiceLogger;
import decorador.MascotaServiceVacunada;
import estructuras.ListaEnlazada;
import java.util.List;
import mundo.*;

import java.util.Scanner;
import plantilla.ExportadorConsola;
import plantilla.ExportadorMascotas;

public class InterfazApp {

    static Scanner sc = new Scanner(System.in);
    static Controlador controlador = new Controlador();

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Agregar Actividad");
            System.out.println("3. Mostrar Actividades");
            System.out.println("4. Asignar Paseador");
            System.out.println("5. Atender Paseador");
            System.out.println("6. Registrar Síntoma");
            System.out.println("7. Atender Veterinario");
            System.out.println("8. Agregar Cuidado");
            System.out.println("9. Ver Cuidados de Mascota");
            System.out.println("10. Listar Mascotas Ordenadas (Nombre)");
            System.out.println("11. Listar Mascotas desde AVL");
            System.out.println("12. Ver Síntomas de Mascota");
            System.out.println("13. Listar Mascotas por Edad (descendente)");
            System.out.println("14. Buscar Mascota (Decorador)");
            System.out.println("15. Exportar Mascotas a Consola (Plantilla)");
            System.out.println("16. Eliminar Mascota de BD (DAO)");
            System.out.println("17. Ver Mascotas Registradas en BD (DAO)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarMascota();
                    break;
                case 2:
                    agregarActividad();
                    break;
                case 3:
                    controlador.mostrarActividades();
                    break;
                case 4:
                    asignarPaseador();
                    break;
                case 5:
                    System.out.println("Mascota atendida: " + controlador.atenderPaseador());
                    break;
                case 6:
                    registrarSintoma();
                    break;
                case 7:
                    System.out.println("Síntoma atendido: " + controlador.atenderVeterinario());
                    break;
                case 8:
                    agregarCuidado();
                    break;
                case 9:
                    verCuidados();
                    break;
                case 10:
                    listarOrdenadas();
                    break;
                case 11:
                    listarAVL();
                    break;
                case 12:
                    verSintomas();
                    break;
                case 13:
                    listarPorEdad();
                    break;
                case 14:
                    System.out.print("Nombre de la mascota a buscar: ");
                    String nombre = sc.nextLine();

                    try {
                        MascotaDAO dao = new MascotaDAOImplementation();
                        MascotaService servicioBase = new MascotaServiceBase(dao);
                        MascotaService conLogger = new MascotaServiceLogger(servicioBase);
                        MascotaService vacunada = new MascotaServiceVacunada(conLogger);

                        Mascota resultado = vacunada.buscar(nombre);

                        if (resultado != null) {
                            System.out.println("Resultado:");
                            System.out.println(resultado);
                        } else {
                            System.out.println("Mascota no encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 15:
                    ListaEnlazada<Mascota> mascotas = controlador.listarMascotasOrdenadas(); // <- esto es lo importante
                    ExportadorMascotas exportador = new ExportadorConsola();
                    exportador.exportar(mascotas); // <- Aquí ves si le estás pasando la ListaEnlazada
                    break;
                case 16:
                    eliminarMascotaDAO();
                    break;
                case 17:
                    listarMascotasDAO();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    static void registrarMascota() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Raza: ");
        String raza = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Dueño: ");
        String dueno = sc.nextLine();
        Mascota m = new Mascota(nombre, raza, edad, dueno);
        controlador.registrarMascota(m);
        System.out.println("Mascota registrada.");
    }

    static void agregarActividad() {
        System.out.print("Nombre de la mascota: ");
        String nombreMascota = sc.nextLine();
        System.out.print("Actividad: ");
        String desc = sc.nextLine();

// Obtener o crear mascota temporalmente
        Mascota mascota = controlador.buscarMascotaPorNombre(nombreMascota);
        if (mascota == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }
        Actividad act = new Actividad(desc, mascota);
        controlador.agregarActividad(act);
    }

    static void asignarPaseador() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        Mascota m = new Mascota(nombre, "SinRaza", 0, "SinDueño");
        controlador.asignarPaseador(m);
        System.out.println("Mascota asignada a paseador.");
    }

    static void registrarSintoma() {
        System.out.print("Nombre de la mascota: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción del síntoma: ");
        String desc = sc.nextLine();
        System.out.print("Gravedad: ");
        int gravedad = sc.nextInt();
        sc.nextLine();
        Sintoma s = new Sintoma(desc, gravedad);
        controlador.registrarSintoma(nombre, s);
        System.out.println("Síntoma registrado.");
    }

    static void agregarCuidado() {
        System.out.print("Nombre de la mascota: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción del cuidado: ");
        String cuidado = sc.nextLine();
        controlador.agregarCuidados(nombre, cuidado);
        System.out.println("Cuidado agregado.");
    }

    static void verCuidados() {
        System.out.print("Nombre de la mascota: ");
        String nombre = sc.nextLine();
        ListaEnlazada<String> cuidados = controlador.obtenerCuidados(nombre);
        System.out.println("Cuidados:");
        for (String c : cuidados) {
            System.out.println("- " + c);
        }
    }

    static void listarOrdenadas() {
        ListaEnlazada<Mascota> mascotas = controlador.listarMascotasOrdenadas();
        System.out.println("Mascotas ordenadas por nombre:");
        for (Mascota m : mascotas) {
            System.out.println(m);
        }
    }

    static void listarAVL() {
        ListaEnlazada<Mascota> mascotas = controlador.listarMascotasAVL();
        System.out.println("Mascotas en AVL:");
        for (Mascota m : mascotas) {
            System.out.println(m);
        }
    }

    static void listarPorEdad() {
        ListaEnlazada<Mascota> mascotas = controlador.listarMascotasPorEdadDesc();
        System.out.println("Mascotas ordenadas por edad (descendente):");
        for (Mascota m : mascotas) {
            System.out.println(m.getNombre() + " - " + m.getEdad() + " años");
        }
    }

    static void verSintomas() {
        System.out.print("Nombre de la mascota: ");
        String nombre = sc.nextLine();
        ListaEnlazada<String> sintomas = controlador.obtenerSintomas(nombre);
        System.out.println("Síntomas:");
        for (String s : sintomas) {
            System.out.println("- " + s);
        }
    }

    static void buscarMascotaDecorador() {
        System.out.print("Nombre de la mascota a buscar: ");
        String nombre = sc.nextLine();

        try {
            MascotaService base = new MascotaServiceBase(new MascotaDAOImplementation());
            MascotaService servicioConLog = new MascotaServiceLogger(base);

            Mascota encontrada = servicioConLog.buscar(nombre);
            if (encontrada != null) {
                System.out.println("Mascota encontrada: " + encontrada);
            } else {
                System.out.println("Mascota no encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Error buscando mascota: " + e.getMessage());
        }
    }

    static void exportarMascotasConsola() {
        try {
            ListaEnlazada<Mascota> lista = controlador.listarMascotasOrdenadas();

            // Convertir a List<Mascota>
            java.util.List<Mascota> mascotas = new java.util.ArrayList<>();
            for (Mascota m : lista) {
                mascotas.add(m);
            }

            plantilla.ExportadorMascotas exportador = new plantilla.ExportadorConsola();
            exportador.exportar(mascotas);

        } catch (Exception e) {
            System.out.println("Error exportando mascotas: " + e.getMessage());
        }
    }

    static void eliminarMascotaDAO() {
        System.out.print("Nombre de la mascota a eliminar: ");
        String nombre = sc.nextLine();

        try {
            MascotaDAO dao = new MascotaDAOImplementation();
            dao.eliminar(nombre);
            System.out.println("Mascota eliminada de la base de datos.");
        } catch (Exception e) {
            System.out.println("Error eliminando la mascota: " + e.getMessage());
        }
    }

    static void listarMascotasDAO() {
        try {
            MascotaDAO dao = new MascotaDAOImplementation();
            List<Mascota> mascotas = dao.listar(); // Tipo explícito
            if (mascotas.isEmpty()) {
                System.out.println("No hay mascotas registradas en la base de datos.");
            } else {
                System.out.println("Mascotas en BD:");
                for (Mascota m : mascotas) {
                    System.out.println("- " + m.getNombre() + " | Raza: " + m.getRaza()
                            + " | Edad: " + m.getEdad() + " | Dueño: " + m.getDueño());
                }
            }
        } catch (Exception e) {
            System.out.println("Error consultando la base de datos: " + e.getMessage());
        }
    }
}

package interfaz;

import controlador.Controlador;
import mundo.Mascota;
import mundo.Sintoma;
import mundo.Actividad;
import java.util.Scanner;

public class MenuEstructuras {

    static Scanner sc = new Scanner(System.in);
    static Controlador controlador = new Controlador();

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n========= MENÚ ESTRUCTURAS =========");
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Ver Mascotas Ordenadas por Nombre (Insertion Sort)");
            System.out.println("3. Ver Mascotas Ordenadas por Edad (Selection Sort Desc)");
            System.out.println("4. Ver Mascotas por AVL (In-Order)");
            System.out.println("5. Ver Actividades Pendientes (Cola)");
            System.out.println("6. Agregar Actividad");
            System.out.println("7. Atender Actividad (dequeue)");
            System.out.println("8. Ver Recordatorios (Pila)");
            System.out.println("9. Agregar Recordatorio");
            System.out.println("10. Eliminar Último Recordatorio (pop)");
            System.out.println("11. Ver Cola de Paseadores (Prioridad Raza)");
            System.out.println("12. Ver Cola de Veterinario (Prioridad Síntomas)");
            System.out.println("13. Ver Tabla de Dueños");
            System.out.println("14. Ver Tabla de Síntomas");
            System.out.println("15. Ver Tabla de Cuidados");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: registrarMascota();
                case 2: controlador.listarMascotasOrdenadas().forEach(System.out::println);
                case 3:  controlador.listarMascotasPorEdadDesc().forEach(System.out::println);
                case 4: System.out.println(controlador.arbolMascotas.mostrarContenido());
                case 5:  System.out.println(controlador.actividades.mostrarContenido());
                case 6:  agregarActividad();
                case 7:  atenderActividad();
                case 8:  System.out.println(controlador.recordatorios.mostrarContenido());
                case 9:  agregarRecordatorio();
                case 10:  eliminarRecordatorio();
                case 11:  System.out.println(controlador.colaRaza.mostrarContenido());
                case 12:  System.out.println(controlador.colaSintomas.mostrarContenido());
                case 13:  System.out.println(controlador.tablaDueños.mostrarContenido());
                case 14:  System.out.println(controlador.tablaSintomas.mostrarContenido());
                case 15:  System.out.println(controlador.tablaCuidados.mostrarContenido());
                case 0:  System.out.println("Saliendo del programa...");
                default:  System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    private static void registrarMascota() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Raza: ");
        String raza = sc.nextLine();
        System.out.print("Dueño: ");
        String dueño = sc.nextLine();

       Mascota mascota = new Mascota(nombre, raza, edad, dueño);
        controlador.registrarMascota(mascota);
        controlador.colaRaza.insert(mascota); // Agrega a cola de paseador
        System.out.println("Mascota registrada exitosamente.");
    }

    private static void agregarActividad() {
        System.out.print("Descripción de la actividad: ");
        String descripcion = sc.nextLine();
        System.out.print("Nombre de la mascota: ");
        String nombreMascota = sc.nextLine();

    Mascota mascota = controlador.buscarMascotaPorNombre(nombreMascota);
        if (mascota != null) {
            Actividad actividad = new Actividad(descripcion, mascota);
            controlador.agregarActividad(actividad);
            System.out.println("Actividad agregada exitosamente.");
        } else {
            System.out.println("Mascota no encontrada.");
        }
    }

    private static void atenderActividad() {
        Actividad actividad = controlador.actividades.dequeue();
        if (actividad != null) {
            System.out.println("Actividad atendida: " + actividad);
        } else {
            System.out.println("No hay actividades pendientes.");
        }
    }

    private static void agregarRecordatorio() {
        System.out.print("Ingrese recordatorio: ");
        String r = sc.nextLine();
        controlador.recordatorios.push(r);
        System.out.println("Recordatorio agregado.");
    }

    private static void eliminarRecordatorio() {
        String r = controlador.recordatorios.pop();
        if (r != null) {
            System.out.println("Recordatorio eliminado: " + r);
        } else {
            System.out.println("No hay recordatorios para eliminar.");
        }
    }
}

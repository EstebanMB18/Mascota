package interfaz;

import controlador.Controlador;
import dao.MascotaDAO;
import dao.MascotaDAOImplementation;
import decorador.*;
import estructuras.ListaEnlazada;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mundo.*;
import plantilla.ExportadorConsola;
import plantilla.ExportadorMascotas;

import java.util.List;

public class MenuAppJavaFX extends Application {

    private Controlador controlador;
    private ObservableList<String> historial;

    public static void main(String[] args) {
    launch(args);
}
    @Override
    public void start(Stage primaryStage) {
        controlador = new Controlador();
        historial = FXCollections.observableArrayList();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                crearTabRegistrarMascota(),
                crearTabAgregarActividad(),
                crearTabMostrarActividades(),
                crearTabPaseador(),
                crearTabVeterinario(),
                crearTabCuidados(),
                crearTabListarMascotas(),
                crearTabBuscarDecorador(),
                crearTabExportar(),
                crearTabDAO()
        );

        ListView<String> listaHistorial = new ListView<>(historial);
        VBox root = new VBox(10, tabPane, new Label("Historial de acciones:"), listaHistorial);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("estructura.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestión de Mascotas - JavaFX");
        primaryStage.show();
    }

    private Tab crearTabRegistrarMascota() {
        Tab tab = new Tab("Registrar Mascota");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtNombre = new TextField(); txtNombre.setPromptText("Nombre");
        TextField txtRaza = new TextField(); txtRaza.setPromptText("Raza");
        TextField txtEdad = new TextField(); txtEdad.setPromptText("Edad");
        TextField txtDueño = new TextField(); txtDueño.setPromptText("Dueño");

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> {
            try {
                Mascota m = new Mascota(txtNombre.getText(), txtRaza.getText(),
                        Integer.parseInt(txtEdad.getText()), txtDueño.getText());
                controlador.registrarMascota(m);
                historial.add("Mascota registrada: " + m);
            } catch (Exception ex) {
                historial.add("Error registrando: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Datos de Mascota"), txtNombre, txtRaza, txtEdad, txtDueño, btnRegistrar);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabAgregarActividad() {
        Tab tab = new Tab("Agregar Actividad");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtNombreMascota = new TextField(); txtNombreMascota.setPromptText("Nombre Mascota");
        TextField txtActividad = new TextField(); txtActividad.setPromptText("Descripción actividad");

        Button btnAgregar = new Button("Agregar Actividad");
        btnAgregar.setOnAction(e -> {
            Mascota m = controlador.buscarMascotaPorNombre(txtNombreMascota.getText());
            if (m != null) {
                Actividad a = new Actividad(txtActividad.getText(), m);
                controlador.agregarActividad(a);
                historial.add("Actividad agregada: " + a);
            } else {
                historial.add("Mascota no encontrada.");
            }
        });

        layout.getChildren().addAll(new Label("Agregar Actividad"), txtNombreMascota, txtActividad, btnAgregar);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabMostrarActividades() {
        Tab tab = new Tab("Mostrar Actividades");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Button btnMostrar = new Button("Mostrar Actividades");
        ListView<String> lista = new ListView<>();

        btnMostrar.setOnAction(e -> {
            lista.getItems().clear();
            for (Actividad a : controlador.getActividades()) {
                lista.getItems().add(a.toString());
            }
        });

        layout.getChildren().addAll(btnMostrar, lista);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabPaseador() {
        Tab tab = new Tab("Paseador");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtNombre = new TextField(); txtNombre.setPromptText("Nombre Mascota");
        Button btnAsignar = new Button("Asignar Paseador");
        Button btnAtender = new Button("Atender Paseador");

        btnAsignar.setOnAction(e -> {
            Mascota m = new Mascota(txtNombre.getText(), "Raza", 0, "Dueño");
            controlador.asignarPaseador(m);
            historial.add("Asignada a paseador: " + m.getNombre());
        });

        btnAtender.setOnAction(e -> {
            Mascota atendida = controlador.atenderPaseador();
            historial.add("Atendida por paseador: " + (atendida != null ? atendida.getNombre() : "Ninguna"));
        });

        layout.getChildren().addAll(txtNombre, btnAsignar, btnAtender);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabVeterinario() {
        Tab tab = new Tab("Veterinario");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtNombre = new TextField();
        TextField txtSintoma = new TextField();
        TextField txtGravedad = new TextField();

        txtNombre.setPromptText("Nombre Mascota");
        txtSintoma.setPromptText("Descripción del síntoma");
        txtGravedad.setPromptText("Gravedad (0-10)");

        Button btnRegistrar = new Button("Registrar Síntoma");
        btnRegistrar.setOnAction(e -> {
            Sintoma s = new Sintoma(txtSintoma.getText(), Integer.parseInt(txtGravedad.getText()));
            controlador.registrarSintoma(txtNombre.getText(), s);
            historial.add("Síntoma registrado para: " + txtNombre.getText());
        });

        Button btnAtender = new Button("Atender Veterinario");
        btnAtender.setOnAction(e -> {
            Sintoma s = controlador.atenderVeterinario();
            historial.add("Síntoma atendido: " + (s != null ? s.getDescripcion() : "Ninguno"));
        });

        layout.getChildren().addAll(txtNombre, txtSintoma, txtGravedad, btnRegistrar, btnAtender);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabCuidados() {
        Tab tab = new Tab("Cuidados");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtMascota = new TextField(); txtMascota.setPromptText("Nombre Mascota");
        TextField txtCuidado = new TextField(); txtCuidado.setPromptText("Descripción del cuidado");
        Button btnAgregar = new Button("Agregar Cuidado");
        ListView<String> lista = new ListView<>();

        btnAgregar.setOnAction(e -> {
            controlador.agregarCuidados(txtMascota.getText(), txtCuidado.getText());
            historial.add("Cuidado agregado para: " + txtMascota.getText());
            lista.getItems().clear();
            for (String c : controlador.obtenerCuidados(txtMascota.getText())) {
                lista.getItems().add(c);
            }
        });

        layout.getChildren().addAll(txtMascota, txtCuidado, btnAgregar, new Label("Cuidados registrados:"), lista);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabListarMascotas() {
        Tab tab = new Tab("Listar Mascotas");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Button btnAVL = new Button("Listar desde AVL");
        Button btnPorEdad = new Button("Listar por Edad");
        Button btnOrdenadas = new Button("Ordenadas por Nombre");
        ListView<String> lista = new ListView<>();

        btnAVL.setOnAction(e -> {
            lista.getItems().clear();
            for (Mascota m : controlador.listarMascotasAVL()) {
                lista.getItems().add(m.toString());
            }
        });
        btnPorEdad.setOnAction(e -> {
            lista.getItems().clear();
            for (Mascota m : controlador.listarMascotasPorEdadDesc()) {
                lista.getItems().add(m.toString());
            }
        });
        btnOrdenadas.setOnAction(e -> {
            lista.getItems().clear();
            for (Mascota m : controlador.listarMascotasOrdenadas()) {
                lista.getItems().add(m.toString());
            }
        });

        layout.getChildren().addAll(btnAVL, btnPorEdad, btnOrdenadas, lista);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabBuscarDecorador() {
        Tab tab = new Tab("Buscar Decorador");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        TextField txtNombre = new TextField(); txtNombre.setPromptText("Nombre Mascota");
        Button btnBuscar = new Button("Buscar");

        btnBuscar.setOnAction(e -> {
            try {
                MascotaService servicio = new MascotaServiceVacunada(new MascotaServiceLogger(new MascotaServiceBase(new MascotaDAOImplementation())));
                Mascota encontrada = servicio.buscar(txtNombre.getText());
                historial.add("Resultado decorador: " + (encontrada != null ? encontrada.toString() : "No encontrada"));
            } catch (Exception ex) {
                historial.add("Error decorador: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(txtNombre, btnBuscar);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabExportar() {
        Tab tab = new Tab("Exportar Plantilla");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Button btnExportar = new Button("Exportar a Consola");
        btnExportar.setOnAction(e -> {
            ExportadorMascotas exp = new ExportadorConsola();
            exp.exportar(controlador.listarMascotasOrdenadas());
            historial.add("Mascotas exportadas por consola (plantilla)");
        });

        layout.getChildren().addAll(new Label("Plantilla de método para exportar"), btnExportar);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabDAO() {
        Tab tab = new Tab("DAO Mascotas");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtEliminar = new TextField();
        txtEliminar.setPromptText("Nombre a eliminar");
        Button btnEliminar = new Button("Eliminar de BD");
        btnEliminar.setOnAction(e -> {
            try {
                new MascotaDAOImplementation().eliminar(txtEliminar.getText());
                historial.add("Eliminada de BD: " + txtEliminar.getText());
            } catch (Exception ex) {
                historial.add("Error eliminando: " + ex.getMessage());
            }
        });

        Button btnListar = new Button("Listar desde BD");
        ListView<String> lista = new ListView<>();
        btnListar.setOnAction(e -> {
            try {
                lista.getItems().clear();
                for (Mascota m : new MascotaDAOImplementation().listar()) {
                    lista.getItems().add(m.toString());
                }
            } catch (Exception ex) {
                historial.add("Error listando: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(txtEliminar, btnEliminar, btnListar, lista);
        tab.setContent(layout);
        return tab;
    }
}

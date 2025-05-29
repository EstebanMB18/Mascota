package interfaz;

import controlador.Controlador;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mundo.Mascota;
import mundo.Sintoma;
import mundo.Actividad;

public class MenuAppEstructuras extends Application {

    private Controlador controlador = new Controlador();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
public void start(Stage primaryStage) {
    TabPane tabPane = new TabPane();

    tabPane.getTabs().addAll(
        crearTabMascotas(),
        crearTabActividades(),
        crearTabRecordatorios(),
        crearTabSintomasCuidados(),
        crearTabPrioridades(),
        crearTabTablas()
    );

    Scene scene = new Scene(tabPane, 800, 600);
    scene.getStylesheets().add(getClass().getResource("estructura.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.setTitle("Gestión de Estructuras - Mascotas");
    primaryStage.show();
}

 private Tab crearTabMascotas() {
    Tab tab = new Tab("Mascotas");
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    // --- Campos para registrar mascota ---
    TextField txtNombre = new TextField();
    txtNombre.setPromptText("Nombre");

    TextField txtEdad = new TextField();
    txtEdad.setPromptText("Edad");

    TextField txtRaza = new TextField();
    txtRaza.setPromptText("Raza");

    TextField txtDueño = new TextField();
    txtDueño.setPromptText("Dueño");

    Button btnRegistrar = new Button("Registrar Mascota");

    // --- Área de resultados principal ---
    TextArea areaMascotas = new TextArea();
    areaMascotas.setEditable(false);
    areaMascotas.setPrefHeight(200);

    // --- Botones de ordenamiento y AVL ---
    Button btnOrdenarNombre = new Button("Ver por Nombre (Insertion Sort)");
    Button btnOrdenarEdad = new Button("Ver por Edad Descendente");
    Button btnMostrarAVL = new Button("Ver AVL Visual");

    // --- Sección para eliminar mascota ---
    TextField txtEliminarNombre = new TextField();
    txtEliminarNombre.setPromptText("Nombre de la mascota a eliminar");

    Button btnEliminarMascota = new Button("Eliminar Mascota");

    // --- Acción para registrar mascota ---
    btnRegistrar.setOnAction(e -> {
        try {
            String nombre = txtNombre.getText();
            String raza = txtRaza.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String dueño = txtDueño.getText();

            Mascota mascota = new Mascota(nombre, raza, edad, dueño);
            controlador.registrarMascota(mascota);
            controlador.colaRaza.insert(mascota);
            areaMascotas.setText("✅ Mascota registrada:\n" + mascota);
        } catch (Exception ex) {
            areaMascotas.setText("❌ Error al registrar: " + ex.getMessage());
        }

        txtNombre.clear();
        txtEdad.clear();
        txtRaza.clear();
        txtDueño.clear();
    });

    // --- Acción ordenar por nombre ---
    btnOrdenarNombre.setOnAction(e -> {
        StringBuilder sb = new StringBuilder("📋 Mascotas por nombre:\n");
        controlador.listarMascotasOrdenadas().forEach(m -> sb.append(m).append("\n"));
        areaMascotas.setText(sb.toString());
    });

    // --- Acción ordenar por edad ---
    btnOrdenarEdad.setOnAction(e -> {
        StringBuilder sb = new StringBuilder("📋 Mascotas por edad descendente:\n");
        controlador.listarMascotasPorEdadDesc().forEach(m -> sb.append(m).append("\n"));
        areaMascotas.setText(sb.toString());
    });

    // --- Acción mostrar árbol AVL ---
    btnMostrarAVL.setOnAction(e -> {
        areaMascotas.clear();
        controlador.arbolMascotas.imprimirArbol((linea) -> {
            areaMascotas.appendText(linea + "\n");
        });
    });

    // --- Acción eliminar mascota ---
    btnEliminarMascota.setOnAction(e -> {
        String nombre = txtEliminarNombre.getText();
        if (nombre == null || nombre.isEmpty()) {
            areaMascotas.setText("⚠️ Por favor ingresa un nombre válido.");
            return;
        }

        try {
            controlador.eliminarMascota(nombre);
            areaMascotas.setText("🗑️ Mascota eliminada correctamente.");
        } catch (Exception ex) {
            areaMascotas.setText("❌ Error al eliminar: " + ex.getMessage());
        }

        txtEliminarNombre.clear();
    });

    // --- Agregar todos los elementos al layout ---
    layout.getChildren().addAll(
        new Label("Registrar Mascota"),
        txtNombre, txtEdad, txtRaza, txtDueño, btnRegistrar,
        btnOrdenarNombre, btnOrdenarEdad, btnMostrarAVL,
        new Separator(),
        new Label("Eliminar Mascota"),
        txtEliminarNombre, btnEliminarMascota,
        areaMascotas
    );

    tab.setContent(layout);
    return tab;
}


    private Tab crearTabActividades() {
        Tab tab = new Tab("Actividades");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtDesc = new TextField();
        txtDesc.setPromptText("Descripción actividad");
        TextField txtMascota = new TextField();
        txtMascota.setPromptText("Nombre de mascota");
        Button btnAgregar = new Button("Agregar Actividad");
        Button btnAtender = new Button("Atender Actividad");
        TextArea area = new TextArea();
        area.setEditable(false);

        btnAgregar.setOnAction(e -> {
            Mascota m = controlador.buscarMascotaPorNombre(txtMascota.getText());
            if (m != null) {
                controlador.agregarActividad(new Actividad(txtDesc.getText(), m));
                area.setText("Actividad agregada.");
            } else {
                area.setText("Mascota no encontrada.");
            }
            txtDesc.clear();
txtMascota.clear();
        });

        btnAtender.setOnAction(e -> {
            Actividad a = controlador.actividades.dequeue();
            if (a != null) {
                area.setText("Atendida: " + a);
            } else {
                area.setText("No hay actividades pendientes.");
            }
        });

        Button btnVerTodas = new Button("Ver todas");
        btnVerTodas.setOnAction(e -> area.setText(controlador.actividades.mostrarContenido()));

        layout.getChildren().addAll(txtDesc, txtMascota, btnAgregar, btnAtender, btnVerTodas, area);
        tab.setContent(layout);
        return tab;
    }

    private Tab crearTabRecordatorios() {
        Tab tab = new Tab("Recordatorios");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField txtRecordatorio = new TextField();
        txtRecordatorio.setPromptText("Recordatorio nuevo");
        Button btnAgregar = new Button("Agregar");
        Button btnEliminar = new Button("Eliminar último");
        Button btnVerTodos = new Button("Ver todos");
        TextArea area = new TextArea();
        area.setEditable(false);

        btnAgregar.setOnAction(e -> {
            controlador.recordatorios.push(txtRecordatorio.getText());
            area.setText("Agregado.");
        });

        btnEliminar.setOnAction(e -> {
            String r = controlador.recordatorios.pop();
            area.setText(r != null ? "Eliminado: " + r : "No hay recordatorios.");
        txtRecordatorio.clear();}
        );

        btnVerTodos.setOnAction(e -> area.setText(controlador.recordatorios.mostrarContenido()));

        layout.getChildren().addAll(txtRecordatorio, btnAgregar, btnEliminar, btnVerTodos, area);
        tab.setContent(layout);
        return tab;
    }

private Tab crearTabPrioridades() {
    Tab tab = new Tab("Prioridades");
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    // Crear el área de texto
    TextArea area = new TextArea();
    area.setEditable(false);

    // Botones
    Button btnVerRaza = new Button("Ver Cola Paseadores (raza)");
    Button btnVerSintomas = new Button("Ver Cola Veterinario (síntomas)");
    Button btnAtenderPaseo = new Button("Atender Paseo");
    Button btnAtenderSintoma = new Button("Atender Síntoma");

    // Acciones de los botones
   btnVerRaza.setOnAction(e -> {
    StringBuilder sb = new StringBuilder("🐾 Cola de Paseadores (por raza):\n\n");

    for (int i = 1; i <= controlador.colaRaza.size(); i++) {
        Mascota m = controlador.colaRaza.get(i); // necesitas agregar un método get(i)
        sb.append("• ").append(m.getNombre())
          .append(" - Raza: ").append(m.getRaza())
          .append(" - Edad: ").append(m.getEdad())
          .append(" - Dueño: ").append(m.getDueño())
          .append("\n");
    }

    area.setText(sb.toString());
});

    btnVerSintomas.setOnAction(e -> {
        StringBuilder sb = new StringBuilder("Cola Veterinario:\n");
        for (Sintoma s : controlador.colaSintomas) {
            String nombreMascota = controlador.buscarMascotaPorSintoma(s.getDescripcion());
            sb.append("Mascota: ").append(nombreMascota)
              .append(" - Síntoma: ").append(s.getDescripcion())
              .append(" (Gravedad: ").append(s.getGravedad()).append(")\n");
        }
        area.setText(sb.toString());
    });

    btnAtenderPaseo.setOnAction(e -> {
        Mascota m = controlador.atenderPaseador();
        if (m != null) {
            area.appendText("🐾 Mascota atendida por paseador: " + m + "\n");
        } else {
            area.appendText("⚠️ No hay mascotas en la cola de paseadores.\n");
        }
    });

    btnAtenderSintoma.setOnAction(e -> {
        Sintoma s = controlador.atenderVeterinario();
        if (s != null) {
            String nombreMascota = controlador.buscarMascotaPorSintoma(s.getDescripcion());
            area.appendText("🩺 Sintoma atendido: " + s.getDescripcion() + " (Mascota: " + nombreMascota + ")\n");
        } else {
            area.appendText("⚠️ No hay síntomas en la cola de veterinario.\n");
        }
    });

    layout.getChildren().addAll(
        btnVerRaza, btnVerSintomas,
        btnAtenderPaseo, btnAtenderSintoma,
        area
    );

    tab.setContent(layout);
    return tab;
}



    private Tab crearTabTablas() {
        Tab tab = new Tab("Tablas");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea area = new TextArea();
        area.setEditable(false);
        Button btnDueños = new Button("Ver Tabla Dueños");
        Button btnSintomas = new Button("Ver Tabla Síntomas");
        Button btnCuidados = new Button("Ver Tabla Cuidados");

        btnDueños.setOnAction(e -> area.setText(controlador.tablaDueños.mostrarContenido()));
        btnSintomas.setOnAction(e -> area.setText(controlador.tablaSintomas.mostrarContenido()));
        btnCuidados.setOnAction(e -> area.setText(controlador.tablaCuidados.mostrarContenido()));

        layout.getChildren().addAll(btnDueños, btnSintomas, btnCuidados, area);
        tab.setContent(layout);
        return tab;
    }
    private Tab crearTabSintomasCuidados() {
    Tab tab = new Tab("Síntomas y Cuidados");
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    // ComboBox para mascotas
    ComboBox<String> comboMascotas = new ComboBox<>();
    comboMascotas.setPromptText("Selecciona una mascota");
    actualizarComboMascotas(comboMascotas);

    // Síntomas
    TextField txtSintoma = new TextField();
    txtSintoma.setPromptText("Descripción del síntoma");

    TextField txtGravedad = new TextField();
    txtGravedad.setPromptText("Gravedad (número)");

    Button btnAgregarSintoma = new Button("Agregar Síntoma");

    // Cuidados
    TextField txtCuidado = new TextField();
    txtCuidado.setPromptText("Descripción del cuidado");

    Button btnAgregarCuidado = new Button("Agregar Cuidado");

    // Área de resultado
    TextArea area = new TextArea();
    area.setEditable(false);
    area.setPrefHeight(200);

    // Mostrar síntomas y cuidados cuando cambia la selección
    comboMascotas.setOnAction(e -> {
        String nombre = comboMascotas.getValue();
        StringBuilder sb = new StringBuilder("📋 Información para " + nombre + ":\n\n");

        sb.append("🩺 Síntomas:\n");
        controlador.obtenerSintomas(nombre).forEach(s -> sb.append("- ").append(s).append("\n"));

        sb.append("\n🛡️ Cuidados:\n");
        controlador.obtenerCuidados(nombre).forEach(c -> sb.append("- ").append(c).append("\n"));

        area.setText(sb.toString());
    });

    // Eventos de botones
    btnAgregarSintoma.setOnAction(e -> {
        try {
            String nombre = comboMascotas.getValue();
            String descripcion = txtSintoma.getText();
            int gravedad = Integer.parseInt(txtGravedad.getText());
            Sintoma s = new Sintoma(descripcion, gravedad);
            controlador.registrarSintoma(nombre, s);
            area.setText("Síntoma registrado correctamente para " + nombre);
            txtSintoma.clear();
            txtGravedad.clear();
            comboMascotas.fireEvent(new ActionEvent()); // refrescar
        } catch (Exception ex) {
            area.setText("Error: " + ex.getMessage());
        }
    });

    btnAgregarCuidado.setOnAction(e -> {
        String nombre = comboMascotas.getValue();
        String cuidado = txtCuidado.getText();
        controlador.agregarCuidados(nombre, cuidado);
        area.setText("Cuidado registrado correctamente para " + nombre);
        txtCuidado.clear();
        comboMascotas.fireEvent(new ActionEvent()); // refrescar
    });

    layout.getChildren().addAll(
        new Label("Mascota:"), comboMascotas,
        new Label("Síntomas:"), txtSintoma, txtGravedad, btnAgregarSintoma,
        new Label("Cuidados:"), txtCuidado, btnAgregarCuidado,
        area
    );
    tab.setContent(layout);
    return tab;
}
    private void actualizarComboMascotas(ComboBox<String> combo) {
    combo.getItems().clear();
    controlador.listarMascotasAVL().forEach(m -> combo.getItems().add(m.getNombre()));
}


}

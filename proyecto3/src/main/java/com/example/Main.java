package com.example;

import componentes.BotonEspecial;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

 @Override
    public void start(Stage primaryStage) {
        // Título de la aplicación
        Label lblTitulo = new Label("Registro de Incidencias - Taller Informático");
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 16));

        // 1. Campos de texto del formulario
        Label lblEquipo = new Label("Nombre del equipo:");
        TextField txtEquipo = new TextField();
        txtEquipo.setPromptText("Ej. PC-AULA2-04");

        Label lblDescripcion = new Label("Descripción de la incidencia:");
        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Describe brevemente el problema...");
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setWrapText(true);

        // 2. Botones personalizados (usando tu clase BotonEspecial del JAR)
        // Constructor: (texto, colorFondo, colorTexto, tamannoFuente, activado, textoAlluda)
        BotonEspecial btnRegistrar = new BotonEspecial(
                "Registrar incidencia", "2E7D32", "FFFFFF", 13, true, "Validar y abrir resumen de la incidencia");

        BotonEspecial btnLimpiar = new BotonEspecial(
                "Limpiar", "C62828", "FFFFFF", 13, true, "Vaciar los campos del formulario");

        // 3. Etiqueta para mostrar el estado de la operación
        Label lblEstado = new Label("");
        lblEstado.setFont(Font.font("System", FontWeight.BOLD, 12));

        // --- EVENTOS DE LOS BOTONES ---

        // Acción del botón Limpiar
        btnLimpiar.setOnAction(e -> {
            txtEquipo.clear();
            txtDescripcion.clear();
            lblEstado.setText("");
            txtEquipo.requestFocus();
        });

        // Acción del botón Registrar incidencia
        btnRegistrar.setOnAction(e -> {
            String equipo = txtEquipo.getText().trim();
            String descripcion = txtDescripcion.getText().trim();

            // Validación de campos vacíos
            if (equipo.isEmpty() || descripcion.isEmpty()) {
                lblEstado.setTextFill(Color.RED);
                lblEstado.setText("Error: Debe completarse el formulario (todos los campos son obligatorios).");
                return;
            }

            // Abrir ventana secundaria modal y esperar respuesta
            VentanaResumen ventanaModal = new VentanaResumen(primaryStage, equipo, descripcion);
            boolean confirmada = ventanaModal.mostrarYEsperar();

            // Si el usuario pulsó "Aceptar" en la ventana secundaria
            if (confirmada) {
                lblEstado.setTextFill(Color.DARKGREEN);
                lblEstado.setText("Incidencia registrada correctamente.");
                txtEquipo.clear();
                txtDescripcion.clear();
            } else {
                lblEstado.setTextFill(Color.DARKORANGE);
                lblEstado.setText("Registro cancelado por el usuario.");
            }
        });

        // --- DISEÑO / LAYOUT ---
        HBox panelBotones = new HBox(15, btnRegistrar, btnLimpiar);
        panelBotones.setAlignment(Pos.CENTER);

        VBox root = new VBox(12,
                lblTitulo,
                lblEquipo, txtEquipo,
                lblDescripcion, txtDescripcion,
                panelBotones,
                lblEstado
        );
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 430, 340);
        primaryStage.setTitle("Gestión de Incidencias");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
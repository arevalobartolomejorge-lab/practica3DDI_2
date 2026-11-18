package com.example;

import componentes.BotonEspecial;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * VentanaResumen
 */
public class VentanaResumen {

    private final Stage owner;
    private final String equipo;
    private final String descripcion;
    private boolean confirmada = false;

    public VentanaResumen(Stage owner, String equipo, String descripcion) {
        this.owner = owner;
        this.equipo = equipo;
        this.descripcion = descripcion;
    }

    public boolean mostrarYEsperar() {
        Stage stageSecundario = new Stage();
        stageSecundario.setTitle("Resumen de la Incidencia");
        
        // Configuración modal según las especificaciones[cite: 3]
        stageSecundario.initOwner(owner);
        stageSecundario.initModality(Modality.APPLICATION_MODAL);

        // Título de la ventana secundaria
        Label lblTitulo = new Label("Resumen de la Incidencia");
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Datos recibidos desde la ventana principal
        Label lblEquipoTitulo = new Label("Nombre del equipo:");
        lblEquipoTitulo.setFont(Font.font("System", FontWeight.BOLD, 12));
        Label lblEquipoValor = new Label(equipo);

        Label lblDescripcionTitulo = new Label("Descripción de la incidencia:");
        lblDescripcionTitulo.setFont(Font.font("System", FontWeight.BOLD, 12));
        Label lblDescripcionValor = new Label(descripcion);
        lblDescripcionValor.setWrapText(true);

        // Botones Aceptar y Cancelar usando BotonEspecial[cite: 1, 3]
        BotonEspecial btnAceptar = new BotonEspecial(
                "Aceptar", "2E7D32", "FFFFFF", 13, true, "Confirmar y registrar la incidencia");

        BotonEspecial btnCancelar = new BotonEspecial(
                "Cancelar", "C62828", "FFFFFF", 13, true, "Cancelar el registro y volver");

        // Comportamiento al pulsar Aceptar: confirma y cierra la ventana[cite: 3]
        btnAceptar.setOnAction(e -> {
            confirmada = true;
            stageSecundario.close();
        });

        // Comportamiento al pulsar Cancelar: cancela y cierra la ventana[cite: 3]
        btnCancelar.setOnAction(e -> {
            confirmada = false;
            stageSecundario.close();
        });

        // Contenedores y diseño
        HBox panelBotones = new HBox(15, btnAceptar, btnCancelar);
        panelBotones.setAlignment(Pos.CENTER);

        VBox root = new VBox(12,
                lblTitulo,
                lblEquipoTitulo, lblEquipoValor,
                lblDescripcionTitulo, lblDescripcionValor,
                panelBotones
        );
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 380, 250);
        stageSecundario.setScene(scene);

        // Muestra la ventana y bloquea la ejecución hasta que se cierre[cite: 3]
        stageSecundario.showAndWait();

        return confirmada;
    }

}

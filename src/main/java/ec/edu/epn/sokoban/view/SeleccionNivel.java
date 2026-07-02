package ec.edu.epn.sokoban.view;

import ec.edu.epn.sokoban.controller.GestorVentanas;
import ec.edu.epn.sokoban.model.JuegoSokoban;
import ec.edu.epn.sokoban.view.estilos.ComponentesUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SeleccionNivel extends BorderPane {
    private final JuegoSokoban juego;
    private final GestorVentanas gestorVentanas;

    //Elementos graficos
    private Button btnVolver;
    private final Button[] botonesNivel = new Button[5];

    public SeleccionNivel(
            JuegoSokoban juego,
            GestorVentanas gestorVentanas) {

        this.juego = juego;
        this.gestorVentanas = gestorVentanas;

        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }

    private void configurarVentana() {
        setStyle(
                "-fx-background-color: linear-gradient(to bottom, #10202B 0%, #0B0F12 45%, #050607 100%);"
        );

    }

    private void inicializarComponentes() {
        setTop(crearCabecera());
        setCenter(crearPanelNiveles());
        setBottom(crearPie());
    }

    private VBox crearCabecera(){
        VBox cabecera = new VBox(10);
        cabecera.setAlignment(Pos.CENTER);
        cabecera.setPadding(new Insets(35));

        Label titulo =
                ComponentesUI.crearTituloPrincipal("SELECCIÓN DE NIVELES");

        Label subtitulo =
                ComponentesUI.crearSubtitulo(
                        "Selecciona cualquier nivel para comenzar."
                );

        cabecera.getChildren().addAll(
                titulo,
                subtitulo
        );

        return cabecera;
    }

    private GridPane crearPanelNiveles() {

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);

        // Crear tarjetas
        for (int i = 0; i < botonesNivel.length; i++) {
            botonesNivel[i] = ComponentesUI.crearTarjetaNivel(i + 1, false);
        }

        // Primera fila
        grid.add(botonesNivel[0], 0, 0);
        grid.add(botonesNivel[1], 1, 0);
        grid.add(botonesNivel[2], 2, 0);

        // Segunda fila (centrada)
        grid.add(botonesNivel[3], 1, 1);
        grid.add(botonesNivel[4], 2, 1);

        return grid;
    }

    private HBox crearPie() {

        HBox pie = new HBox();

        pie.setAlignment(Pos.CENTER);
        pie.setPadding(new Insets(25));

        btnVolver = ComponentesUI.crearBotonPrincipal("Volver");

        pie.getChildren().add(btnVolver);

        return pie;
    }

    private void configurarEventos() {

        botonesNivel[0].setOnAction(e ->
                gestorVentanas.abrirNivel(1));

        botonesNivel[1].setOnAction(e ->
                gestorVentanas.abrirNivel(2));

        botonesNivel[2].setOnAction(e ->
                gestorVentanas.abrirNivel(3));

        botonesNivel[3].setOnAction(e ->
                gestorVentanas.abrirNivel(4));

        botonesNivel[4].setOnAction(e ->
                gestorVentanas.abrirNivel(5));

        btnVolver.setOnAction(e ->
                gestorVentanas.mostrarMenu());
    }

    public Button[] getBotonesNivel() {
        return botonesNivel;
    }

    public Button getBtnVolver() {
        return btnVolver;
    }

    public JuegoSokoban getJuego() {
        return juego;
    }
}

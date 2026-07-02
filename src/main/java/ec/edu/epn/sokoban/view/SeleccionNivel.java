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

import java.util.ArrayList;
import java.util.List;

public class SeleccionNivel extends BorderPane {

    private static final int TOTAL_NIVELES = 5;

    private final JuegoSokoban juego;
    private final GestorVentanas gestorVentanas;

    private Button btnVolver;

    private final List<Button> botonesNivel = new ArrayList<>();

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

    private VBox crearCabecera() {

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

        botonesNivel.clear();

        cargarNiveles();

        for (int i = 0; i < botonesNivel.size(); i++) {

            int columna = i % 3;
            int fila = i / 3;

            grid.add(botonesNivel.get(i), columna, fila);
        }

        return grid;
    }

    /**
     * Carga las tarjetas de los niveles.
     * Temporalmente se utiliza una cantidad fija de niveles.
     * Cuando GestorPersistencia permita obtener los niveles disponibles,
     * este método deberá consultarlos dinámicamente.
     */
    private void cargarNiveles() {

        for (int i = 1; i <= TOTAL_NIVELES; i++) {

            Button tarjeta =
                    ComponentesUI.crearTarjetaNivel(
                            i,
                            false
                    );

            botonesNivel.add(tarjeta);
        }

    }

    private HBox crearPie() {

        HBox pie = new HBox();

        pie.setAlignment(Pos.CENTER);
        pie.setPadding(new Insets(25));

        btnVolver =
                ComponentesUI.crearBotonPrincipal("Volver");

        pie.getChildren().add(btnVolver);

        return pie;
    }

    private void configurarEventos() {

        for (int i = 0; i < botonesNivel.size(); i++) {

            final int numeroNivel = i + 1;

            botonesNivel.get(i).setOnAction(e ->
                    gestorVentanas.abrirNivel(numeroNivel));

        }

        btnVolver.setOnAction(e ->
                gestorVentanas.mostrarMenu());

    }

    public List<Button> getBotonesNivel() {
        return botonesNivel;
    }

    public Button getBtnVolver() {
        return btnVolver;
    }

    public JuegoSokoban getJuego() {
        return juego;
    }

}
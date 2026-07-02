package ec.edu.epn.sokoban.view;

import ec.edu.epn.sokoban.controller.GestorVentanas;
import ec.edu.epn.sokoban.model.JuegoSokoban;
import ec.edu.epn.sokoban.model.historial.Nivel;
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

    private final JuegoSokoban juego;
    private final GestorVentanas gestorVentanas;

    private Button btnVolver;

    private final List<Button> botonesNivel = new ArrayList<>();
    private final List<Nivel> nivelesMostrados = new ArrayList<>();

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
        nivelesMostrados.clear();

        cargarNiveles();

        for (int i = 0; i < botonesNivel.size(); i++) {

            int columna = i % 3;
            int fila = i / 3;

            grid.add(botonesNivel.get(i), columna, fila);
        }

        return grid;
    }

    /**
     * Carga los niveles disponibles.
     * Temporalmente crea niveles ficticios.
     * Cuando exista la lectura de TXT este método no deberá cambiar.
     */
    private void cargarNiveles() {

        if (juego.getNivelesDisponibles().isEmpty()) {

            for (int i = 1; i <= 5; i++) {

                Nivel nivel = new Nivel("nivel" + i + ".txt");

                nivelesMostrados.add(nivel);

                Button tarjeta =
                        ComponentesUI.crearTarjetaNivel(
                                i,
                                nivel.isCompletado()
                        );

                botonesNivel.add(tarjeta);
            }

        } else {

            List<Nivel> niveles =
                    juego.getNivelesDisponibles();

            for (int i = 0; i < niveles.size(); i++) {

                Nivel nivel = niveles.get(i);

                nivelesMostrados.add(nivel);

                Button tarjeta =
                        ComponentesUI.crearTarjetaNivel(
                                i + 1,
                                nivel.isCompletado()
                        );

                botonesNivel.add(tarjeta);
            }

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

            final Nivel nivel = nivelesMostrados.get(i);

            botonesNivel.get(i).setOnAction(e ->
                    gestorVentanas.abrirNivel(nivel));

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
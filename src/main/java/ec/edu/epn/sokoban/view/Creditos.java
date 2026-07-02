package ec.edu.epn.sokoban.view;

import ec.edu.epn.sokoban.controller.GestorVentanas;
import ec.edu.epn.sokoban.view.estilos.ComponentesUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Creditos extends BorderPane  {
    private final GestorVentanas gestorVentanas;

    private Button btnVolver;

    public Creditos(GestorVentanas gestorVentanas) {

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

        ScrollPane scroll = new ScrollPane(crearContenido());

        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scroll.setStyle(
                "-fx-background: transparent;" +
                        "-fx-background-color: transparent;"
        );

        setCenter(scroll);
        setBottom(crearPie());
    }

    private VBox crearContenido() {

        VBox contenido = new VBox(25);

        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setPadding(new Insets(25));

        Label titulo =
                ComponentesUI.crearTituloPrincipal("CRÉDITOS");

        Label proyecto =
                ComponentesUI.crearSubtitulo("Proyecto Sokoban");

        Label universidad =
                ComponentesUI.crearSubtitulo("Escuela Politécnica Nacional");

        GridPane grupos = crearPanelGrupos();

        contenido.getChildren().addAll(
                titulo,
                proyecto,
                universidad,
                grupos
        );

        return contenido;
    }

    private GridPane crearPanelGrupos() {

        GridPane panel = new GridPane();

        panel.setAlignment(Pos.CENTER);

        panel.setHgap(40);
        panel.setVgap(30);

        panel.add(crearGrupo("Grupo 1", 6),0,0);
        panel.add(crearGrupo("Grupo 2", 6),1,0);

        panel.add(crearGrupo("Grupo 3", 6),0,1);
        panel.add(crearGrupo("Grupo 4", 6),1,1);

        panel.add(crearGrupo("Grupo 5", 7),0,2,2,1);

        return panel;

    }

    private VBox crearGrupo(String nombreGrupo, int cantidadIntegrantes) {

        VBox grupo = new VBox(10);

        grupo.setAlignment(Pos.TOP_CENTER);
        grupo.setPadding(new Insets(15));
        grupo.setPrefWidth(320);

        grupo.setStyle(
                "-fx-background-color: rgba(255,255,255,0.05);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #7A4A1D;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-width: 2;"
        );

        Label titulo = ComponentesUI.crearSubtitulo(nombreGrupo);

        GridPane integrantes = new GridPane();
        integrantes.setHgap(25);
        integrantes.setVgap(6);

        int mitad = (int) Math.ceil(cantidadIntegrantes / 2.0);

        for (int i = 1; i <= cantidadIntegrantes; i++) {

            int columna;
            int fila;

            if (i <= mitad) {
                columna = 0;
                fila = i - 1;
            } else {
                columna = 1;
                fila = i - mitad - 1;
            }

            integrantes.add(
                    ComponentesUI.crearSubtitulo("Integrante " + i),
                    columna,
                    fila
            );
        }

        grupo.getChildren().addAll(
                titulo,
                integrantes
        );

        return grupo;
    }

    private VBox crearPie() {

        VBox pie = new VBox();

        pie.setAlignment(Pos.CENTER);

        pie.setPadding(new Insets(20));

        btnVolver =
                ComponentesUI.crearBotonPrincipal("Volver");

        pie.getChildren().add(btnVolver);

        return pie;

    }

    private void configurarEventos() {

        btnVolver.setOnAction(e ->
                gestorVentanas.mostrarMenu()
        );

    }

}

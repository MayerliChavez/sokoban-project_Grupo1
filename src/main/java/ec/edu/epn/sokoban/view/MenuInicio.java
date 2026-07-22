package ec.edu.epn.sokoban.view;

import ec.edu.epn.sokoban.controller.GestorVentanas;
import ec.edu.epn.sokoban.view.estilos.ComponentesUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuInicio extends BorderPane {
        private final GestorVentanas gestorVentanas;

        private Button btnNuevaPartida;
        private Button btnSeleccionNivel;
        private Button btnCreditos;
        private Button btnSalir;

        public MenuInicio(GestorVentanas gestorVentanas) {

                this.gestorVentanas = gestorVentanas;

                configurarVentana();
                inicializarComponentes();
                configurarEventos();
        }

        private void configurarVentana() {

                setStyle(
                                "-fx-background-color: linear-gradient(to bottom, #10202B 0%, #0B0F12 45%, #050607 100%);");

        }

        private void inicializarComponentes() {

                setCenter(crearMenu());

                setBottom(crearPiePagina());

        }

        private VBox crearMenu() {

                VBox contenedor = new VBox(20);

                contenedor.setAlignment(Pos.CENTER);

                contenedor.setPadding(new Insets(40));

                Label titulo = ComponentesUI.crearTituloPrincipal("\uD83D\uDCE6 SOKOBAN");

                Label subtitulo = ComponentesUI.crearSubtitulo(
                                "Organiza las cajas y supera cada desafío");

                btnNuevaPartida = ComponentesUI.crearBotonPrincipal("Nueva partida");

                btnSeleccionNivel = ComponentesUI.crearBotonPrincipal("Seleccionar nivel");

                btnCreditos = ComponentesUI.crearBotonPrincipal("Créditos");

                btnSalir = ComponentesUI.crearBotonPrincipal("Salir");

                contenedor.getChildren().addAll(
                                titulo,
                                subtitulo,
                                btnNuevaPartida,
                                btnSeleccionNivel,
                                btnCreditos,
                                btnSalir);

                return contenedor;
        }

        private VBox crearPiePagina() {

                VBox pie = new VBox(5);

                pie.setAlignment(Pos.CENTER);

                pie.setPadding(new Insets(15));

                Label universidad = ComponentesUI.crearSubtitulo("Escuela Politécnica Nacional");

                Label grupo = ComponentesUI.crearSubtitulo("GR2SW • Sokoban • Versión 1.0");

                pie.getChildren().addAll(
                                universidad,
                                grupo);

                return pie;
        }

        private void configurarEventos() {
                btnNuevaPartida.setOnAction(e -> gestorVentanas.nuevaPartida());

                btnSeleccionNivel.setOnAction(e -> gestorVentanas.mostrarSeleccionNiveles());

                btnCreditos.setOnAction(e -> gestorVentanas.mostrarCreditos());

                btnSalir.setOnAction(e -> gestorVentanas.salir());
        }

        public Button getBtnNuevaPartida() {
                return btnNuevaPartida;
        }

        public Button getBtnSeleccionNivel() {
                return btnSeleccionNivel;
        }

        public Button getBtnCreditos() {
                return btnCreditos;
        }

        public Button getBtnSalir() {
                return btnSalir;
        }
}

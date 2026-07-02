package ec.edu.epn.sokoban;

import ec.edu.epn.sokoban.controller.GestorVentanas;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.view.VentanaPrincipal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        GestorVentanas gestor = new GestorVentanas(stage);
        gestor.mostrarMenu();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
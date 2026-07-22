package ec.edu.epn.sokoban;

import ec.edu.epn.sokoban.controller.GestorVentanas;
import javafx.application.Application;
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
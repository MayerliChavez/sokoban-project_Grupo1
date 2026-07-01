package ec.edu.epn.sokoban;

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
        Tablero tablero = crearTableroPrueba(8, 8);

        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(tablero);

        Scene scene = new Scene(ventanaPrincipal, 1280, 720);

        stage.setTitle("Sokoban");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Tablero crearTableroPrueba(int filas, int columnas) {
        Tablero tablero = new Tablero(filas, columnas);

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                boolean esPerimetro =
                        fila == 0 ||
                                fila == filas - 1 ||
                                columna == 0 ||
                                columna == columnas - 1;

                if (esPerimetro) {
                    tablero.actualizarCasilla(fila, columna, new Pared(fila, columna));
                } else {
                    tablero.actualizarCasilla(fila, columna, new SueloComun(fila, columna));
                }
            }
        }

        tablero.actualizarCasilla(2, 2, new Caja(2, 2));
        tablero.actualizarCasilla(2, 4, new Meta(2, 4));
        tablero.actualizarCasilla(4, 3, new Personaje(4, 3));

        return tablero;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package ec.edu.epn.sokoban.controller;

import ec.edu.epn.sokoban.model.JuegoSokoban;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.historial.Nivel;
import ec.edu.epn.sokoban.view.Creditos;
import ec.edu.epn.sokoban.view.MenuInicio;
import ec.edu.epn.sokoban.view.SeleccionNivel;
import ec.edu.epn.sokoban.view.VentanaPrincipal;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestorVentanas {

    private final Stage stage;
    private final JuegoSokoban juego;

    public GestorVentanas(Stage stage) {

        this.stage = stage;
        this.juego = new JuegoSokoban(new ArrayList<Nivel>());

    }

    /**
       Muestra la ventana principal del juego.
     */
    public void mostrarJuego(Tablero tablero) {

        VentanaPrincipal ventana =
                new VentanaPrincipal(tablero, this);

        Scene scene = new Scene(ventana, 1280, 720);

        stage.setTitle("Sokoban");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    /**
       Abre el nivel seleccionado.
       Temporalmente crea un tablero de prueba.
       Cuando GestorPersistencia implemente la lectura de los TXT,
       este método deberá cargar el tablero correspondiente.
     */
    public void abrirNivel(int numeroNivel) {

        Tablero tablero = crearTableroPrueba(numeroNivel);

        mostrarJuego(tablero);

        /*
        Futuro:

        Nivel nivel =
                gestorPersistencia.cargarNivelDesdeRecursos(numeroNivel);

        Tablero tablero =
                convertirNivelATablero(nivel);

        mostrarJuego(tablero);
        */

    }

    /**
       Muestra el menú principal.
     */
    public void mostrarMenu() {

        MenuInicio menu =
                new MenuInicio(juego, this);

        Scene scene = new Scene(menu, 1280, 720);

        stage.setTitle("Sokoban");
        stage.setScene(scene);
        stage.show();

    }

    /**
       Muestra la pantalla de selección de niveles.
     */
    public void mostrarSeleccionNiveles() {

        SeleccionNivel seleccion =
                new SeleccionNivel(juego, this);

        Scene scene = new Scene(seleccion, 1280, 720);

        stage.setScene(scene);
        stage.show();

    }

    /**
       Inicia una nueva partida desde el primer nivel.
     */
    public void nuevaPartida() {

        abrirNivel(1);

    }

    /**
      Muestra la ventana de créditos.
     */
    public void mostrarCreditos() {

        Creditos creditos =
                new Creditos(this);

        Scene scene = new Scene(creditos, 1280, 720);

        stage.setTitle("Sokoban");
        stage.setScene(scene);
        stage.show();

    }

    /**
       Cierra la aplicación.
     */
    public void salir() {

        stage.close();

    }

    /**
       Crea un tablero temporal para pruebas.
       Será reemplazado por la lectura de archivos TXT.
     */
    private Tablero crearTableroPrueba(int nivel) {

        int filas = 8;
        int columnas = 8;

        Tablero tablero = new Tablero(filas, columnas);

        for (int fila = 0; fila < filas; fila++) {

            for (int columna = 0; columna < columnas; columna++) {

                boolean esPerimetro =
                        fila == 0 ||
                                fila == filas - 1 ||
                                columna == 0 ||
                                columna == columnas - 1;

                if (esPerimetro) {

                    tablero.actualizarCasilla(
                            fila,
                            columna,
                            new Pared(fila, columna));

                } else {

                    tablero.actualizarCasilla(
                            fila,
                            columna,
                            new SueloComun(fila, columna));

                }

            }

        }

        tablero.actualizarCasilla(2, 2, new Caja(2, 2));
        tablero.actualizarCasilla(2, 4, new Meta(2, 4));
        tablero.actualizarCasilla(4, 3, new Personaje(4, 3));

        return tablero;

    }

}
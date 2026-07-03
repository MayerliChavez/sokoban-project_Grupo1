package ec.edu.epn.sokoban.controller;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.JuegoSokoban;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.view.VentanaPrincipal;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

// implementacion de EventHandler<KeyEvent> para que JavaFX lo reconozca
public class ControladorTeclado implements EventHandler<KeyEvent> {
    private final JuegoSokoban juego;
    private VentanaPrincipal ventanaPrincipal;
    private Tablero tablero;

    public ControladorTeclado(JuegoSokoban juego) {
        this.juego = juego;
    }

    public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal, Tablero tablero) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.tablero = tablero;
    }

    // nativo de escucha de JavaFX para presionar una tecla
    @Override
    public void handle(KeyEvent evento) {
        KeyCode codigo = evento.getCode();

        // linea de control de IntelliJ si detecta las teclas
        System.out.println(">>> Tecla detectada en controlador: " + codigo);

        Direccion direccionElegida = null;

        // esquema de las teclas WASD y Flechas
        switch (codigo) {
            case UP:
            case W:
                direccionElegida = Direccion.ARRIBA;
                break;
            case DOWN:
            case S:
                direccionElegida = Direccion.ABAJO;
                break;
            case LEFT:
            case A:
                direccionElegida = Direccion.IZQUIERDA;
                break;
            case RIGHT:
            case D:
                direccionElegida = Direccion.DERECHA;
                break;
            default:
                return; // Ignora cualquier otra tecla
        }

        if (direccionElegida != null) {
            // Modifica las posiciones lógicas en el modelo
            this.juego.procesarEntrada(direccionElegida);

            if (ventanaPrincipal != null && this.tablero != null) {
                ventanaPrincipal.actualizarTablero(this.tablero);
            }
        }
    }
}

package ec.edu.epn.sokoban.controller;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.reglas.GestorColisiones;
import ec.edu.epn.sokoban.view.VentanaPrincipal;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.function.BooleanSupplier;

/**
 * Event handler de teclado de JavaFX.
 * Se comunica directamente con la clase Personaje y Tablero para invocar el movimiento,
 * utilizando el GestorColisiones único.
 */
public class ControladorTeclado implements EventHandler<KeyEvent> {
    private Personaje personaje;
    private Tablero tablero;
    private final GestorColisiones gestorColisiones;
    private VentanaPrincipal ventanaPrincipal;
    private Runnable antesDeMover;
    private Runnable despuesDeMover;
    private Runnable accionDeshacer;
    private BooleanSupplier checkNivelCompletado;

    public ControladorTeclado(Personaje personaje, Tablero tablero, GestorColisiones gestorColisiones) {
        this.personaje = personaje;
        this.tablero = tablero;
        this.gestorColisiones = gestorColisiones;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void setAntesDeMover(Runnable antesDeMover) {
        this.antesDeMover = antesDeMover;
    }

    public void setDespuesDeMover(Runnable despuesDeMover) {
        this.despuesDeMover = despuesDeMover;
    }

    public void setAccionDeshacer(Runnable accionDeshacer) {
        this.accionDeshacer = accionDeshacer;
    }

    public void setCheckNivelCompletado(BooleanSupplier checkNivelCompletado) {
        this.checkNivelCompletado = checkNivelCompletado;
    }

    @Override
    public void handle(KeyEvent evento) {
        if (checkNivelCompletado != null && checkNivelCompletado.getAsBoolean()) {
            return;
        }
        KeyCode codigo = evento.getCode();

        System.out.println(">>> Tecla detectada en controlador: " + codigo);

        Direccion direccionElegida = null;

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
            case Z:
            case BACK_SPACE:
            case U:
                if (accionDeshacer != null) {
                    accionDeshacer.run();
                }
                return;
            default:
                return; // Ignora cualquier otra tecla
        }

        if (direccionElegida != null && personaje != null && tablero != null && gestorColisiones != null) {
            if (antesDeMover != null) {
                antesDeMover.run();
            }

            boolean movimientoRealizado = personaje.mover(direccionElegida, tablero, gestorColisiones);

            if (movimientoRealizado) {
                if (despuesDeMover != null) {
                    despuesDeMover.run();
                }
                if (ventanaPrincipal != null) {
                    ventanaPrincipal.actualizarTablero(tablero);
                    ventanaPrincipal.actualizarEstadisticas();
                }
            }
        }
    }
}

package ec.edu.epn.sokoban.controller;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.JuegoSokoban;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.historial.PartidaMomento;
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
        if (juego.getNivelActual() != null && juego.getNivelActual().isCompletado()) {
            return;
        }
        KeyCode codigo = evento.getCode();

        // linea de control de IntelliJ si detecta las teclas
        System.out.println(">>> Tecla detectada en controlador: " + codigo);

        Direccion direccionElegida = null;

        // esquema de las teclas WASD, Flechas y retroceso (Undo)
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
                this.juego.deshacerUltimaAccion();
                if (ventanaPrincipal != null && this.tablero != null) {
                    ventanaPrincipal.actualizarTablero(this.tablero);
                    ventanaPrincipal.actualizarEstadisticas();
                }
                return;
            default:
                return; // Ignora cualquier otra tecla
        }

        if (direccionElegida != null && this.tablero != null) {
            Personaje personaje = this.tablero.getPersonaje();
            if (personaje != null) {
                PartidaMomento estadoAnterior = this.juego.capturarEstadoActual();
                boolean movimientoRealizado = personaje.mover(direccionElegida, this.tablero, this.juego.getCadenaColisiones());
                if (movimientoRealizado) {
                    this.juego.getHistorial().registrarEstado(estadoAnterior);
                    this.juego.verificarYRegistrarVictoria();
                }

                if (ventanaPrincipal != null) {
                    ventanaPrincipal.actualizarTablero(this.tablero);
                    ventanaPrincipal.actualizarEstadisticas();
                }
            }
        }
    }
}

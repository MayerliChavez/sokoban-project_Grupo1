package ec.edu.epn.sokoban.controller;

import ec.edu.epn.sokoban.model.Direccion;
import ec.edu.epn.sokoban.model.JuegoSokoban;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Coordina la interacción del usuario (eventos de teclado) con el modelo.
 * Convierte las teclas presionadas en objetos Direccion y delega
 * el procesamiento a JuegoSokoban.
 */
public class ControladorTeclado {

    private final JuegoSokoban juego;

    public ControladorTeclado(JuegoSokoban juego) {
        this.juego = juego;
    }

    public JuegoSokoban getJuego() {
        return juego;
    }

    public void manejarPulsacionTeclado(KeyEvent evento) {
        Direccion direccion = mapearTecla(evento.getCode());

        if (direccion != null) {
            juego.procesarEntrada(direccion);
        }
    }

    /**
     * Traduce el código de tecla físico a una Direccion del modelo.
     * Soporta flechas y WASD.
     */
    private Direccion mapearTecla(KeyCode codigo) {
        switch (codigo) {
            case UP:
            case W:
                return Direccion.ARRIBA;
            case DOWN:
            case S:
                return Direccion.ABAJO;
            case LEFT:
            case A:
                return Direccion.IZQUIERDA;
            case RIGHT:
            case D:
                return Direccion.DERECHA;
            default:
                return null;
        }
    }
}
package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Manejador concreto de la cadena de responsabilidad que resuelve colisiones con paredes
 * físicas e infranqueables y con los límites externos del tablero.
 */
public class ManejadorPared implements ManejadorColision {
    private ManejadorColision siguiente;

    @Override
    public void setSiguiente(ManejadorColision siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public boolean procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir) {
        if (tablero == null || origen == null || dir == null) {
            return false;
        }

        int filaDestino = origen.getFila() + dir.getDeltaFila();
        int columnaDestino = origen.getColumna() + dir.getDeltaColumna();

        // Control de los límites del tablero
        if (filaDestino < 0 || filaDestino >= tablero.getFilas() ||
            columnaDestino < 0 || columnaDestino >= tablero.getColumnas()) {
            return false;
        }

        Casilla destino = tablero.obtenerCasilla(filaDestino, columnaDestino);
        if (destino instanceof Pared) {
            return false; // Bloqueado por pared
        }

        // Si no colisiona con pared/límites, delegar al siguiente manejador de la cadena
        if (siguiente != null) {
            return siguiente.procesarMovimiento(tablero, origen, dir);
        }

        return false;
    }
}

package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Manejador concreto final que ejecuta el movimiento ordinario del personaje
 * cuando la casilla de destino está libre (es transitable).
 */
public class ManejadorMovimientoBase implements ManejadorColision {
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

        // Si la casilla destino es transitable (suelo o meta libre), realizar el movimiento
        if (tablero.esTransitable(filaDestino, columnaDestino) && origen instanceof Personaje personaje) {
            return personaje.mover(dir, tablero);
        }

        // Si no, delegar o finalizar
        if (siguiente != null) {
            return siguiente.procesarMovimiento(tablero, origen, dir);
        }

        return false;
    }
}

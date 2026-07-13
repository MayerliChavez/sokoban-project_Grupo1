package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Abstracción común para el patrón Chain of Responsibility.
 * Define los métodos necesarios para encadenar manejadores de colisiones y procesar movimientos.
 */
public interface ManejadorColision {
    
    /**
     * Establece el siguiente manejador en la cadena de responsabilidad.
     *
     * @param siguiente El siguiente manejador de colisiones.
     */
    void setSiguiente(ManejadorColision siguiente);

    /**
     * Procesa el intento de movimiento de una casilla origen en una dirección dada.
     *
     * @param tablero El tablero actual del juego.
     * @param origen  La casilla de origen que intenta realizar el movimiento (e.g., Personaje).
     * @param dir     La dirección del movimiento deseado.
     * @return true si el movimiento fue exitoso y procesado, false en caso contrario.
     */
    boolean procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir);
}

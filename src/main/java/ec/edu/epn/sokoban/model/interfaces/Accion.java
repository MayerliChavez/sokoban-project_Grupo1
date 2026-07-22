package ec.edu.epn.sokoban.model.interfaces;

import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Contrato central del Patrón Strategy para las acciones dinámicas de las casillas.
 */
public interface Accion {

    /**
     * Ejecuta la lógica de la acción cuando una entidad interactúa con la casilla.
     *
     * @param casillaActual casilla sobre la que se detona la acción
     * @param tablero       tablero activo
     * @param entidad       entidad (Personaje o Caja) que pisó la casilla
     */
    void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad);

    /**
     * Retorna el identificador del sprite para esta acción.
     *
     * @return identificador del sprite, o null si no tiene representación visual
     */
    default String getSpriteKey() {
        return null;
    }

    /**
     * Verifica si la entidad proporcionada puede ingresar a la casilla con esta acción.
     *
     * @param tablero tablero activo
     * @param entidad entidad que intenta ingresar (Personaje o Caja)
     * @return true si la entidad puede ingresar; false en caso contrario
     */
    default boolean puedeIngresarEntidad(Tablero tablero, Casilla entidad) {
        return true;
    }
}

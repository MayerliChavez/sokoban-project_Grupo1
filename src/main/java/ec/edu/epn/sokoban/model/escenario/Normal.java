package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

/**
 * Acción de movimiento estándar sin efectos especiales.
 */
public class Normal implements Accion {

    public Normal() {
    }

    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        // Comportamiento nulo para terreno ordinario.
    }
}

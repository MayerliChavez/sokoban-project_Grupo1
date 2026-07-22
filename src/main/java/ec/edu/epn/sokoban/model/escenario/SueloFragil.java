package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

/**
 * Suelo que se quiebra con la primera pisada y hace reiniciar el nivel con la segunda.
 */
public class SueloFragil implements Accion {
    private boolean quebrado;

    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        if (!(entidad instanceof Personaje)) {
            return;
        }

        if (quebrado) {
            tablero.solicitarReinicio();
        } else {
            quebrado = true;
        }
    }

    @Override
    public String getSpriteKey() {
        return quebrado ? "SUELO_FRAGIL" : null;
    }
}

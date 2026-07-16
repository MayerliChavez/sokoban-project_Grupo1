package ec.edu.epn.sokoban.model.interfaces;

/**
 * Interfaz que marca una casilla como empujable (se puede empujar).
 */
public interface Empujable {
    /**
     * Verifica si una casilla es empujable por el jugador.
     * @return true si es empujable, false en caso contrario.
     */
    boolean esEmpujable();
}

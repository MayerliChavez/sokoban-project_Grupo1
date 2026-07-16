package ec.edu.epn.sokoban.model.interfaces;

/**
 * Interfaz que marca una casilla como transitable (se puede caminar sobre ella).
 */
public interface Transitable {
    /**
     * Verifica si una casilla es transitable por el jugador.
     * @return true si es transitable, false en caso contrario.
     */
    boolean esTransitable();
}

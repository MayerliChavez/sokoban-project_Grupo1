package ec.edu.epn.sokoban.model.interfaces;

/**
 * La interfaz Transitable fue definida para marcar las casillas sobre las cuales
 * el personaje o las cajas pueden desplazarse. Su contrato fue reducido a un
 * único método de verificación, tal como fue especificado en el diagrama UML revisado.
 */
public interface Transitable {
    /**
     * La transitabilidad de la casilla fue verificada.
     *
     * @return {@code true} si la casilla permite el paso; {@code false} en caso contrario.
     */
    boolean verificarTransitabilidad();
}

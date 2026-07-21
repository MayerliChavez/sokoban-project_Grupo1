package ec.edu.epn.sokoban.model.interfaces;

import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * La interfaz {@code Accion} fue definida como el contrato central del Patrón Strategy
 * aplicado al sistema de acciones dinámicas del juego.
 *
 * <p>El método {@link #iniciarAccion(Casilla, Tablero, Casilla)} fue declarado como el
 * punto de entrada unificado para cualquier acción que pueda ser registrada y ejecutada
 * por el {@code GestorAcciones}. El tipo {@link Casilla} fue elegido como superclase
 * común de {@code Personaje} y {@code Caja}, garantizando type-safety sin recurrir
 * a {@code Object}.</p>
 *
 * <p>La implementación concreta de cada comportamiento fue delegada a las clases
 * que implementen esta interfaz, tal como fue especificado en el diagrama UML revisado.</p>
 */
public interface Accion {

    /**
     * La acción fue iniciada con el contexto completo de la entidad que la disparó.
     *
     * @param casillaActual la casilla sobre la que la entidad fue posicionada al detonar la acción
     * @param tablero       el tablero sobre el que la acción fue ejecutada
     * @param entidad       la entidad ({@code Personaje} o {@code Caja}) que pisó la casilla;
     *                      cada implementación es responsable de verificar el tipo concreto
     */
    void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad);

    /**
     * Retorna el identificador del sprite para esta acción.
     * Por defecto retorna null si la acción no tiene representación visual.
     *
     * @return el identificador del sprite, o null
     */
    default String getSpriteKey() {
        return null;
    }
}

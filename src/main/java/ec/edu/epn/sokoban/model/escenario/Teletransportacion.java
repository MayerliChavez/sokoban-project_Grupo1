package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

/**
 * La clase {@code Teletransportacion} fue definida como la implementación concreta
 * de la interfaz {@link Accion} correspondiente al comportamiento de desplazamiento
 * instantáneo entre portales.
 *
 * <p>Las coordenadas del punto de destino ({@code filaDestino}, {@code columnaDestino})
 * fueron inyectadas a través del constructor, permitiendo que una misma clase
 * modele cualquier par de portales del escenario.</p>
 *
 * <p>La regla de negocio fue establecida con precisión: únicamente las instancias
 * de {@link Caja} son afectadas por esta acción. Si el personaje pisa el portal,
 * la teletransportación es ignorada de forma silenciosa.</p>
 */
public class Teletransportacion implements Accion {

    /**
     * La fila del punto de destino al que la caja será teletransportada.
     */
    private final int filaDestino;

    /**
     * La columna del punto de destino al que la caja será teletransportada.
     */
    private final int columnaDestino;

    /**
     * Una instancia de teletransportación fue inicializada con las coordenadas exactas
     * del punto de salida (Punto B) del portal.
     *
     * @param filaDestino    la fila de la casilla destino del portal
     * @param columnaDestino la columna de la casilla destino del portal
     */
    public Teletransportacion(int filaDestino, int columnaDestino) {
        this.filaDestino = filaDestino;
        this.columnaDestino = columnaDestino;
    }

    /**
     * La acción de teletransportación fue iniciada sobre la entidad proporcionada.
     *
     * <p>La regla de negocio fue aplicada estrictamente: la entidad recibida fue verificada
     * como instancia de {@link Caja}. Si la verificación falla (ej. es un {@code Personaje}),
     * la acción fue ignorada sin efecto colateral.</p>
     *
     * <p>Cuando la condición es satisfecha, las siguientes operaciones fueron realizadas:</p>
     * <ol>
     *   <li>La casilla de origen de la caja fue liberada en el tablero.</li>
     *   <li>Las coordenadas internas de la caja fueron actualizadas al destino.</li>
     *   <li>La caja fue posicionada en la casilla destino del tablero.</li>
     * </ol>
     *
     * @param casillaActual la casilla portal sobre la que la entidad fue posicionada
     * @param tablero       el tablero sobre el que la teletransportación fue ejecutada
     * @param entidad       la entidad que pisó el portal; solo actúa si es una {@link Caja}
     */
    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        // La regla de negocio fue aplicada: solo las cajas son teletransportadas.
        if (!(entidad instanceof Caja)) {
            return;
        }

        if (tablero == null || !tablero.estaDentroDelTablero(filaDestino, columnaDestino)) {
            return;
        }

        Caja caja = (Caja) entidad;

        // Las coordenadas de la caja son actualizadas al destino y el tablero es reflejado.
        // La liberación de la posición de origen (A) se realiza automáticamente por el tablero.
        tablero.actualizarCasilla(filaDestino, columnaDestino, caja);
    }

    /**
     * La fila del punto de destino fue retornada para consulta externa.
     *
     * @return la fila de la casilla destino registrada en esta acción
     */
    public int getFilaDestino() {
        return filaDestino;
    }

    /**
     * La columna del punto de destino fue retornada para consulta externa.
     *
     * @return la columna de la casilla destino registrada en esta acción
     */
    public int getColumnaDestino() {
        return columnaDestino;
    }
}

package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

/**
 * Acción concreta del Grupo 2: terreno peligroso de Lava.
 *
 * Regla de la mecánica: cualquier entidad (Personaje o Caja) que ingrese a
 * una casilla con lava provoca la derrota inmediata de la partida.
 * - Si el personaje la pisa, se quema y se pierde.
 * - Si una caja cae en ella, la partida también se pierde.
 *
 * La derrota se comunica mediante un callback ({@link Runnable}) registrado
 * por el controlador. El modelo no conoce a la vista ni a JavaFX: solo
 * ejecuta la reacción que le fue registrada.
 */
public class Lava implements Accion {

    /** Reacción ante la derrota, registrada por el controlador. */
    private static Runnable notificadorDerrota;

    /**
     * Registra la reacción que será ejecutada cuando una entidad caiga en la lava.
     *
     * @param accion lógica a ejecutar al perder la partida (ej. reiniciar el nivel)
     */
    public static void registrarNotificadorDerrota(Runnable accion) {
        notificadorDerrota = accion;
    }

    /**
     * Se ejecuta después de que la entidad fue movida con éxito a la casilla:
     * la partida se declara perdida. No requiere distinguir el tipo de entidad
     * (sin {@code instanceof}), ya que el resultado es el mismo para ambas.
     */
    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        if (notificadorDerrota != null) {
            notificadorDerrota.run();
        }
    }

    /**
     * La lava permite el ingreso de cualquier entidad (justamente para que
     * caiga en ella y se pierda la partida).
     */
    @Override
    public boolean puedeIngresarEntidad(Tablero tablero, Casilla entidad) {
        return true;
    }

    /**
     * Identificador del sprite visual que dibuja el PanelTablero.
     */
    @Override
    public String getSpriteKey() {
        return "LAVA";
    }
}

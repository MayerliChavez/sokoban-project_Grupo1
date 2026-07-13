package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Manejador concreto que evalúa la física de empuje interactivo de cajas.
 * Valida si la casilla posterior es transitable y realiza el traslado de estado físico de la caja y el personaje.
 */
public class ManejadorCaja implements ManejadorColision {
    private ManejadorColision siguiente;

    @Override
    public void setSiguiente(ManejadorColision siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public boolean procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir) {
        if (tablero == null || origen == null || dir == null) {
            return false;
        }

        int filaDestino = origen.getFila() + dir.getDeltaFila();
        int columnaDestino = origen.getColumna() + dir.getDeltaColumna();

        Casilla destino = tablero.obtenerCasilla(filaDestino, columnaDestino);
        if (destino instanceof Caja caja) {
            int filaTrasCaja = filaDestino + dir.getDeltaFila();
            int columnaTrasCaja = columnaDestino + dir.getDeltaColumna();

            // Validar límites del tablero tras la caja
            if (filaTrasCaja < 0 || filaTrasCaja >= tablero.getFilas() ||
                columnaTrasCaja < 0 || columnaTrasCaja >= tablero.getColumnas()) {
                return false;
            }

            Casilla casillaTrasCaja = tablero.obtenerCasilla(filaTrasCaja, columnaTrasCaja);
            
            // Validar que la casilla tras la caja no esté ocupada por otra caja o por una pared
            if (casillaTrasCaja == null || casillaTrasCaja instanceof Pared || casillaTrasCaja instanceof Caja) {
                return false;
            }

            // Si es transitable (SueloComun o Meta), procedemos a realizar la traslación
            boolean posteriorEsMeta = casillaTrasCaja instanceof Meta;
            boolean cajaEstabaEnMeta = tablero.esMeta(filaDestino, columnaDestino);

            // Mover la caja a su nueva posición usando su comportamiento autónomo
            caja.mover(dir, tablero);

            // Liberar la casilla de donde vino la caja
            Casilla casillaLiberada = cajaEstabaEnMeta
                    ? new Meta(filaDestino, columnaDestino)
                    : new SueloComun(filaDestino, columnaDestino);
            tablero.actualizarCasilla(filaDestino, columnaDestino, casillaLiberada);

            // Desplazar al operario a la casilla liberada usando su comportamiento autónomo
            if (origen instanceof Personaje personaje) {
                personaje.mover(dir, tablero);
            }
            return true;
        }

        // Si no es una caja, delegar al siguiente manejador
        if (siguiente != null) {
            return siguiente.procesarMovimiento(tablero, origen, dir);
        }

        return false;
    }
}

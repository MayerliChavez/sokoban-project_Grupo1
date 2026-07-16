package ec.edu.epn.sokoban.model.historial;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Suelo;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa el estado registrado de una partida en un momento dado,
 * permitiendo su restauración.
 */
public class PartidaMomento {
    private final Map<Caja, Casilla> posicionesCajas;
    private final Casilla posicionJugador;

    public PartidaMomento(Map<Caja, Casilla> posicionesCajas, Casilla posicionJugador) {
        this.posicionesCajas = Collections.unmodifiableMap(copiarPosicionesCajas(posicionesCajas));
        this.posicionJugador = copiarCasilla(posicionJugador);
    }

    /**
     * Restaura el estado guardado directamente sobre el tablero dado.
     * Limpia la posición actual del personaje y las cajas y las reubica.
     */
    public void restaurarEnTablero(Tablero t) {
        if (t == null || posicionJugador == null) {
            return;
        }

        // 1. Limpiar cajas y personaje existentes en el tablero actual
        for (int f = 0; f < t.getFilas(); f++) {
            for (int c = 0; c < t.getColumnas(); c++) {
                Casilla casilla = t.obtenerCasilla(f, c);
                if (casilla instanceof Personaje || casilla instanceof Caja) {
                    Casilla reemplazo = t.esMeta(f, c) ? new Meta(f, c) : new Suelo(f, c);
                    t.actualizarCasilla(f, c, reemplazo);
                }
            }
        }

        // 2. Restaurar cajas en sus posiciones
        for (Map.Entry<Caja, Casilla> entrada : posicionesCajas.entrySet()) {
            Caja cajaOriginal = entrada.getKey();
            Casilla pos = entrada.getValue();
            Caja cajaRestaurada = new Caja(pos.getFila(), pos.getColumna(), cajaOriginal.isEnMeta());
            t.actualizarCasilla(pos.getFila(), pos.getColumna(), cajaRestaurada);
        }

        // 3. Restaurar personaje en su posición
        Personaje personajeExistente = t.getPersonaje();
        if (personajeExistente != null) {
            personajeExistente.setFila(posicionJugador.getFila());
            personajeExistente.setColumna(posicionJugador.getColumna());
            t.actualizarCasilla(posicionJugador.getFila(), posicionJugador.getColumna(), personajeExistente);
        } else {
            Personaje personajeRestaurado = new Personaje(posicionJugador.getFila(), posicionJugador.getColumna());
            t.actualizarCasilla(posicionJugador.getFila(), posicionJugador.getColumna(), personajeRestaurado);
        }
    }

    private Map<Caja, Casilla> copiarPosicionesCajas(Map<Caja, Casilla> origen) {
        Map<Caja, Casilla> copia = new LinkedHashMap<>();
        if (origen == null) {
            return copia;
        }
        for (Map.Entry<Caja, Casilla> entrada : origen.entrySet()) {
            Caja cajaCopiada = new Caja(entrada.getKey().getFila(), entrada.getKey().getColumna(), entrada.getKey().isEnMeta());
            Casilla posicionCopiada = new Suelo(entrada.getValue().getFila(), entrada.getValue().getColumna());
            copia.put(cajaCopiada, posicionCopiada);
        }
        return copia;
    }

    private Casilla copiarCasilla(Casilla casilla) {
        if (casilla == null) {
            return null;
        }
        return new Suelo(casilla.getFila(), casilla.getColumna());
    }
}

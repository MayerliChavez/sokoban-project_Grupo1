package ec.edu.epn.sokoban.model.historial;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Un estado inmutable de partida es representado para restauracion y busqueda.
 */
public class PartidaMomento {
    private final Map<Caja, Casilla> posicionesCajas;
    private final Casilla posicionJugador;

    /**
     * Un momento de partida es inicializado mediante copias profundas.
     *
     * @param posicionesCajas mapa de cajas y posiciones asociadas
     * @param posicionJugador posicion del jugador en el estado registrado
     */
    public PartidaMomento(Map<Caja, Casilla> posicionesCajas, Casilla posicionJugador) {
        this.posicionesCajas = Collections.unmodifiableMap(copiarPosicionesCajas(posicionesCajas));
        this.posicionJugador = copiarCasilla(posicionJugador);
    }

    /**
     * Una copia profunda del mapa de posiciones de cajas es retornada.
     *
     * @return copia profunda del mapa de cajas y posiciones
     */
    public Map<Caja, Casilla> getPosicionesCajas() {
        return Collections.unmodifiableMap(copiarPosicionesCajas(posicionesCajas));
    }

    /**
     * Una copia de la posicion del jugador es retornada.
     *
     * @return copia de la posicion del jugador
     */
    public Casilla getPosicionJugador() {
        return copiarCasilla(posicionJugador);
    }

    /**
     * El estado almacenado fue restaurado en el tablero mediante reemplazo atomico.
     *
     * @param t tablero que sera restaurado
     */
    public void restaurarEnTablero(Tablero t) {
        if (t == null || posicionJugador == null) {
            return;
        }

        Casilla[][] matrizRestaurada = copiarMatrizBase(t);
        if (!posicionValida(posicionJugador.getFila(), posicionJugador.getColumna(), t)) {
            return;
        }

        for (Map.Entry<Caja, Casilla> entrada : posicionesCajas.entrySet()) {
            Casilla posicionCaja = entrada.getValue();
            if (posicionCaja == null || !posicionValida(posicionCaja.getFila(), posicionCaja.getColumna(), t)) {
                return;
            }
        }

        for (Map.Entry<Caja, Casilla> entrada : posicionesCajas.entrySet()) {
            Caja cajaOriginal = entrada.getKey();
            Casilla posicionCaja = entrada.getValue();
            Caja cajaRestaurada = copiarCajaEnPosicion(cajaOriginal, posicionCaja);
            matrizRestaurada[posicionCaja.getFila()][posicionCaja.getColumna()] = cajaRestaurada;
        }

        int filaJugador = posicionJugador.getFila();
        int columnaJugador = posicionJugador.getColumna();
        matrizRestaurada[filaJugador][columnaJugador] = new Personaje(filaJugador, columnaJugador);

        t.setCeldas(matrizRestaurada);
    }

    private Map<Caja, Casilla> copiarPosicionesCajas(Map<Caja, Casilla> origen) {
        Map<Caja, Casilla> copia = new LinkedHashMap<>();
        if (origen == null) {
            return copia;
        }

        for (Map.Entry<Caja, Casilla> entrada : origen.entrySet()) {
            Caja cajaCopiada = copiarCaja(entrada.getKey());
            Casilla posicionCopiada = copiarCasilla(entrada.getValue());
            copia.put(cajaCopiada, posicionCopiada);
        }
        return copia;
    }

    private Casilla[][] copiarMatrizBase(Tablero tablero) {
        Casilla[][] copia = new Casilla[tablero.getFilas()][tablero.getColumnas()];
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int columna = 0; columna < tablero.getColumnas(); columna++) {
                Casilla casilla = tablero.obtenerCasilla(fila, columna);
                copia[fila][columna] = copiarComoTerrenoBase(casilla, fila, columna);
            }
        }
        return copia;
    }

    private Casilla copiarComoTerrenoBase(Casilla casilla, int fila, int columna) {
        if (casilla instanceof Meta meta) {
            return new Meta(fila, columna, meta.isSatisfecha());
        }
        if (casilla instanceof Pared) {
            return new Pared(fila, columna);
        }
        return new SueloComun(fila, columna);
    }

    private Caja copiarCaja(Caja caja) {
        if (caja == null) {
            return null;
        }
        return new Caja(caja.getFila(), caja.getColumna(), caja.isEnMeta());
    }

    private Caja copiarCajaEnPosicion(Caja caja, Casilla posicion) {
        boolean enMeta = caja != null && caja.isEnMeta();
        return new Caja(posicion.getFila(), posicion.getColumna(), enMeta);
    }

    private Casilla copiarCasilla(Casilla casilla) {
        if (casilla == null) {
            return null;
        }
        if (casilla instanceof Caja caja) {
            return new Caja(caja.getFila(), caja.getColumna(), caja.isEnMeta());
        }
        if (casilla instanceof Meta meta) {
            return new Meta(meta.getFila(), meta.getColumna(), meta.isSatisfecha());
        }
        if (casilla instanceof Pared) {
            return new Pared(casilla.getFila(), casilla.getColumna());
        }
        if (casilla instanceof Personaje) {
            return new Personaje(casilla.getFila(), casilla.getColumna());
        }
        return new SueloComun(casilla.getFila(), casilla.getColumna());
    }

    private boolean posicionValida(int fila, int columna, Tablero tablero) {
        return fila >= 0 && fila < tablero.getFilas() && columna >= 0 && columna < tablero.getColumnas();
    }
}

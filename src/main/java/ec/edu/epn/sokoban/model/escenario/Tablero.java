package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Transitable;
import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * La matriz bidimensional del escenario es gestionada.
 */
public class Tablero extends Casilla {
        private int filas;
    private int columnas;
    private Casilla[][] celdas;
    private boolean[][] metas;
    private Personaje personaje;
    private Teletransportacion[][] portales;

    // =========================================================================
    // 1. Constructores e Inicialización
    // =========================================================================

    /**
     * Un tablero vacio es inicializado.
     */
    public Tablero() {
        this(new Casilla[0][0], new boolean[0][0], null);
    }

    /**
     * Un tablero es inicializado con su matriz de celdas, metas y personaje.
     *
     * @param celdas    matriz bidimensional de casillas
     * @param metas     matriz de metas del tablero
     * @param personaje personaje ubicado en el tablero
     */
    public Tablero(Casilla[][] celdas, boolean[][] metas, Personaje personaje) {
        super(0, 0);
        this.celdas = celdas != null ? celdas : new Casilla[0][0];
        this.metas = metas != null ? metas : new boolean[0][0];
        this.filas = this.celdas.length;
        this.columnas = this.filas > 0 ? this.celdas[0].length : 0;
        this.personaje = personaje;
        
        // Inicializar el registro de portales a partir de las casillas cargadas
        this.portales = new Teletransportacion[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                Casilla casilla = this.celdas[f][c];
                if (casilla != null) {
                    for (var acc : casilla.getGestorAcciones().getAcciones()) {
                        if (acc instanceof Teletransportacion) {
                            this.portales[f][c] = (Teletransportacion) acc;
                            break;
                        }
                    }
                }
            }
        }
    }

    // =========================================================================
    // 2. Métodos ejecutados durante el juego y consultas de estado
    // =========================================================================

    /**
     * Verifica si una coordenada contiene un portal.
     *
     * @param f fila
     * @param c columna
     * @return true si es portal, false en caso contrario
     */
    public boolean esPortal(int f, int c) {
        return estaDentroDelTablero(f, c) && portales != null && portales[f][c] != null;
    }

    /**
     * Retorna la acción de teletransportación de una coordenada.
     *
     * @param f fila
     * @param c columna
     * @return la instancia de Teletransportacion, o null
     */
    public Teletransportacion obtenerTeletransportacion(int f, int c) {
        if (estaDentroDelTablero(f, c) && portales != null) {
            return portales[f][c];
        }
        return null;
    }

    /**
     * La cantidad de filas es retornada.
     *
     * @return cantidad de filas del tablero
     */
    public int getFilas() {
        return filas;
    }

    /**
     * La cantidad de columnas es retornada.
     *
     * @return cantidad de columnas del tablero
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Verifica si la coordenada dada es una meta registrada.
     *
     * @param f fila
     * @param c columna
     * @return true si es una meta, false en caso contrario
     */
    public boolean esMeta(int f, int c) {
        return estaDentroDelTablero(f, c) && metas[f][c];
    }

    /**
     * La casilla ubicada en una coordenada es retornada.
     *
     * @param f fila consultada
     * @param c columna consultada
     * @return casilla encontrada o null si la coordenada esta fuera del tablero
     */
    public Casilla obtenerCasilla(int f, int c) {
        if (estaDentroDelTablero(f, c)) {
            return celdas[f][c];
        }
        return null;
    }

    /**
     * Una casilla es reemplazada de forma atomica en la matriz.
     *
     * @param f            fila que sera actualizada
     * @param c            columna que sera actualizada
     * @param nuevaCasilla nueva casilla que sera ubicada
     */
    public void actualizarCasilla(int f, int c, Casilla nuevaCasilla) {
        if (!estaDentroDelTablero(f, c)) {
            return;
        }

        if (nuevaCasilla != null) {
            int oldFila = nuevaCasilla.getFila();
            int oldColumna = nuevaCasilla.getColumna();

            if ((nuevaCasilla instanceof Personaje || nuevaCasilla instanceof Caja)
                    && (estaDentroDelTablero(oldFila, oldColumna) && celdas[oldFila][oldColumna] == nuevaCasilla)
                    && (oldFila != f || oldColumna != c)) {
                liberarPosicion(oldFila, oldColumna);
            } else if (nuevaCasilla instanceof Personaje || nuevaCasilla instanceof Caja) {
                // Si por alguna razón sus coordenadas internas ya fueron modificadas pero el objeto
                // sigue registrado en el tablero en su posición antigua, lo buscamos y liberamos.
                boolean encontrado = false;
                for (int fIdx = 0; fIdx < filas; fIdx++) {
                    for (int cIdx = 0; cIdx < columnas; cIdx++) {
                        if (celdas[fIdx][cIdx] == nuevaCasilla) {
                            if (fIdx != f || cIdx != c) {
                                liberarPosicion(fIdx, cIdx);
                            }
                            encontrado = true;
                            break;
                        }
                    }
                    if (encontrado) {
                        break;
                    }
                }
            }

            nuevaCasilla.setFila(f);
            nuevaCasilla.setColumna(c);

            if (nuevaCasilla instanceof Meta) {
                this.metas[f][c] = true;
            } else if (nuevaCasilla instanceof Personaje) {
                this.personaje = (Personaje) nuevaCasilla;
            } else if (nuevaCasilla instanceof Caja) {
                ((Caja) nuevaCasilla).setEnMeta(esMeta(f, c));
            }
        }

        celdas[f][c] = nuevaCasilla;
    }

    /**
     * El estado de transitabilidad de una coordenada es retornado.
     *
     * @param f fila consultada
     * @param c columna consultada
     * @return true si la coordenada contiene una casilla transitable; false en caso
     *         contrario
     */
    public boolean esCeldaTransitable(int f, int c) {
        Casilla casilla = obtenerCasilla(f, c);
        return casilla != null && casilla instanceof Transitable && ((Transitable) casilla).verificarTransitabilidad();
    }

    public boolean estaDentroDelTablero(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    public Caja obtenerCaja(int fila, int columna) {
        Casilla casilla = obtenerCasilla(fila, columna);
        return casilla instanceof Caja ? (Caja) casilla : null;
    }

    private void liberarPosicion(int fila, int columna) {
        Casilla reemplazo = esMeta(fila, columna) ? new Meta(fila, columna) : new Suelo(fila, columna);
        if (esPortal(fila, columna)) {
            reemplazo.getGestorAcciones().agregarAccion(portales[fila][columna]);
        }
        actualizarCasilla(fila, columna, reemplazo);
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        // El tablero en sí no se dibuja como una celda individual.
    }
}

package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Transitable;
import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * Matriz bidimensional que gestiona las casillas y entidades del escenario.
 */
public class Tablero extends Casilla {
    private int filas;
    private int columnas;
    private Casilla[][] celdas;
    private boolean[][] metas;
    private Personaje personaje;
    private Casilla[][] casillasBase;

    public Tablero() {
        this(new Casilla[0][0], new boolean[0][0], null);
    }

    public Tablero(Casilla[][] celdas, boolean[][] metas, Personaje personaje) {
        super(0, 0);
        this.celdas = celdas != null ? celdas : new Casilla[0][0];
        this.metas = metas != null ? metas : new boolean[0][0];
        this.filas = this.celdas.length;
        this.columnas = this.filas > 0 ? this.celdas[0].length : 0;
        this.personaje = personaje;

        this.casillasBase = new Casilla[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                Casilla casilla = this.celdas[f][c];
                if (casilla instanceof Personaje || casilla instanceof Caja) {
                    if (esMeta(f, c)) {
                        this.casillasBase[f][c] = new Meta(f, c);
                    } else {
                        this.casillasBase[f][c] = new Suelo(f, c);
                    }
                } else {
                    this.casillasBase[f][c] = casilla;
                }
            }
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public boolean esMeta(int f, int c) {
        return estaDentroDelTablero(f, c) && metas[f][c];
    }

    public Casilla obtenerCasilla(int f, int c) {
        if (estaDentroDelTablero(f, c)) {
            return celdas[f][c];
        }
        return null;
    }

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
        restaurarCasillaBase(fila, columna);
    }

    /**
     * Restaura la casilla de fondo original en la coordenada especificada.
     */
    public void restaurarCasillaBase(int f, int c) {
        if (estaDentroDelTablero(f, c) && casillasBase != null) {
            Casilla reemplazo = casillasBase[f][c];
            actualizarCasilla(f, c, reemplazo);
        }
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
    }
}

package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.Direccion;

/**
 * La matriz bidimensional del escenario es gestionada.
 */
public class Tablero {
    private int filas;
    private int columnas;
    private Casilla[][] celdas;

    /**
     * Un tablero vacio es inicializado.
     */
    public Tablero() {
        this(0, 0);
    }

    /**
     * Un tablero es inicializado con dimensiones definidas.
     *
     * @param filas cantidad de filas del tablero
     * @param columnas cantidad de columnas del tablero
     */
    public Tablero(int filas, int columnas) {
        validarDimensiones(filas, columnas);
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Casilla[filas][columnas];
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
     * La cantidad de filas es actualizada y la matriz es reinicializada.
     *
     * @param filas nueva cantidad de filas
     */
    public void setFilas(int filas) {
        validarDimensiones(filas, this.columnas);
        this.filas = filas;
        this.celdas = new Casilla[filas][columnas];
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
     * La cantidad de columnas es actualizada y la matriz es reinicializada.
     *
     * @param columnas nueva cantidad de columnas
     */
    public void setColumnas(int columnas) {
        validarDimensiones(this.filas, columnas);
        this.columnas = columnas;
        this.celdas = new Casilla[filas][columnas];
    }

    /**
     * La matriz de casillas es retornada.
     *
     * @return matriz bidimensional de casillas
     */
    public Casilla[][] getCeldas() {
        return celdas;
    }

    /**
     * La matriz de casillas es actualizada y sus coordenadas internas son sincronizadas.
     *
     * @param celdas nueva matriz bidimensional de casillas
     */
    public void setCeldas(Casilla[][] celdas) {
        if (celdas == null) {
            this.filas = 0;
            this.columnas = 0;
            this.celdas = new Casilla[0][0];
            return;
        }

        int nuevasFilas = celdas.length;
        int nuevasColumnas = nuevasFilas == 0 ? 0 : celdas[0].length;
        validarMatrizRectangular(celdas, nuevasColumnas);

        this.filas = nuevasFilas;
        this.columnas = nuevasColumnas;
        this.celdas = celdas;
        sincronizarCoordenadas();
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
     * @param f fila que sera actualizada
     * @param c columna que sera actualizada
     * @param nuevaCasilla nueva casilla que sera ubicada
     */
    public void actualizarCasilla(int f, int c, Casilla nuevaCasilla) {
        if (!estaDentroDelTablero(f, c)) {
            return;
        }

        if (nuevaCasilla != null) {
            nuevaCasilla.setFila(f);
            nuevaCasilla.setColumna(c);
        }

        celdas[f][c] = nuevaCasilla;
    }

    /**
     * El personaje es desplazado en una direccion cuando la casilla destino es transitable.
     *
     * @param d direccion de movimiento solicitada
     * @return true si el movimiento fue aplicado; false en caso contrario
     */
    public boolean moverOperario(Direccion d) {
        if (d == null) {
            return false;
        }

        Personaje personaje = buscarPersonaje();
        if (personaje == null) {
            return false;
        }

        int filaOrigen = personaje.getFila();
        int columnaOrigen = personaje.getColumna();
        int filaDestino = filaOrigen + d.getDeltaFila();
        int columnaDestino = columnaOrigen + d.getDeltaColumna();

        if (!esTransitable(filaDestino, columnaDestino)) {
            return false;
        }

        actualizarCasilla(filaOrigen, columnaOrigen, new SueloComun(filaOrigen, columnaOrigen));
        actualizarCasilla(filaDestino, columnaDestino, personaje);
        return true;
    }

    /**
     * El estado de transitabilidad de una coordenada es retornado.
     *
     * @param f fila consultada
     * @param c columna consultada
     * @return true si la coordenada contiene una casilla transitable; false en caso contrario
     */
    public boolean esTransitable(int f, int c) {
        Casilla casilla = obtenerCasilla(f, c);
        return casilla != null && casilla.esTransitable();
    }

    private boolean estaDentroDelTablero(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    private Personaje buscarPersonaje() {
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Casilla casilla = celdas[fila][columna];
                if (casilla instanceof Personaje personaje) {
                    return personaje;
                }
            }
        }
        return null;
    }

    private void sincronizarCoordenadas() {
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Casilla casilla = celdas[fila][columna];
                if (casilla != null) {
                    casilla.setFila(fila);
                    casilla.setColumna(columna);
                }
            }
        }
    }

    private void validarDimensiones(int filas, int columnas) {
        if (filas < 0 || columnas < 0) {
            throw new IllegalArgumentException("Las dimensiones del tablero no pueden ser negativas.");
        }
    }

    private void validarMatrizRectangular(Casilla[][] matriz, int columnasEsperadas) {
        for (Casilla[] fila : matriz) {
            if (fila == null || fila.length != columnasEsperadas) {
                throw new IllegalArgumentException("La matriz de casillas debe ser rectangular.");
            }
        }
    }
}

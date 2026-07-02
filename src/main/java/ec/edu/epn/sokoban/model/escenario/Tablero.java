package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.Direccion;

public class Tablero extends Casilla {
    private int filas;
    private int columnas;
    private Casilla[][] celdas;

    public Tablero(int filas, int columnas) {
        super(0, 0);
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Casilla[filas][columnas];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Casilla[][] getCeldas() {
        return celdas;
    }

    public Casilla obtenerCasilla(int f, int c) {
        if (f >= 0 && f < filas && c >= 0 && c < columnas) {
            return celdas[f][c];
        }
        return null;
    }

    public void actualizarCasilla(int f, int c, Casilla nuevaCasilla) {
        if (f >= 0 && f < filas && c >= 0 && c < columnas) {
            celdas[f][c] = nuevaCasilla;
        }
    }

    public boolean moverOperario(Direccion d) {
        int filaOperario = -1;
        int columnaOperario = -1;

        // Buscar al personaje en la matriz
        for (int f = 0; f < filas && filaOperario == -1; f++) {
            for (int c = 0; c < columnas; c++) {
                if (celdas[f][c] instanceof Personaje) {
                    filaOperario = f;
                    columnaOperario = c;
                    break;
                }
            }
        }

        if (filaOperario == -1) {
            return false;
        }

        // Calcular destino
        int nuevaFila = filaOperario + d.getDeltaFila();
        int nuevaColumna = columnaOperario + d.getDeltaColumna();

        // Validar limites
        if (nuevaFila < 0 || nuevaFila >= filas || nuevaColumna < 0 || nuevaColumna >= columnas) {
            return false;
        }

        Casilla destino = obtenerCasilla(nuevaFila, nuevaColumna);

        // Pared bloquea
        if (destino instanceof Pared) {
            return false;
        }

        // Caja: el empuje lo maneja GestorColisiones, aqui no
        if (destino instanceof Caja) {
            return false;
        }

        // Suelo o Meta: mover
        if (destino instanceof SueloComun || destino instanceof Meta) {
            // Nota: si el personaje estaba sobre una Meta, esa info se pierde
            actualizarCasilla(filaOperario, columnaOperario, new SueloComun(filaOperario, columnaOperario));
            actualizarCasilla(nuevaFila, nuevaColumna, new Personaje(nuevaFila, nuevaColumna));
            return true;
        }

        return false;
    }

    @Override
    public boolean esTransitable() {
        return true;
    }
}

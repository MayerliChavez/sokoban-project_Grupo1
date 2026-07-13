package ec.edu.epn.sokoban.model.factory;

import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.historial.Nivel;

public class FabricaNiveles {

    public FabricaNiveles() {
    }

    public Tablero construirTablero(Nivel n) {
        if (n == null || n.getMapaDatos() == null) {
            throw new IllegalArgumentException("El nivel no tiene datos de mapa válidos");
        }

        String[][] mapaDatos = n.getMapaDatos();
        int filas = mapaDatos.length;
        int columnas = (filas > 0) ? mapaDatos[0].length : 0;

        Tablero tablero = new Tablero(filas, columnas);

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                String simbolo = mapaDatos[f][c];
                Casilla casilla = crearCasilla(simbolo, f, c, tablero);
                tablero.actualizarCasilla(f, c, casilla);
            }
        }

        return tablero;
    }

    private Casilla crearCasilla(String simbolo, int fila, int columna, Tablero tablero) {
        if (simbolo == null) {
            return new SueloComun(fila, columna);
        }

        switch (simbolo) {
            case "#":
                return new Pared(fila, columna);

            case " ":
                return new SueloComun(fila, columna);

            case ".":
                tablero.registrarMeta(fila, columna);
                return new Meta(fila, columna);

            case "$":
                return new Caja(fila, columna);

            case "*":
                Caja cajaEnMeta = new Caja(fila, columna);
                cajaEnMeta.setEnMeta(true);
                tablero.registrarMeta(fila, columna);
                return cajaEnMeta;

            case "@":
                return new Personaje(fila, columna);

            case "+":
                tablero.registrarMeta(fila, columna);
                return new Personaje(fila, columna);

            default:
                return new SueloComun(fila, columna);
        }
    }
}

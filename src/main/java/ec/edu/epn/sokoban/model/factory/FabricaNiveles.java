package ec.edu.epn.sokoban.model.factory;

import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.ParedComun;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.CajaComun;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.CargadorTablero;
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

        Casilla[][] celdas = new Casilla[filas][columnas];
        boolean[][] metas = new boolean[filas][columnas];

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                String simbolo = mapaDatos[f][c];
                celdas[f][c] = crearCasilla(simbolo, f, c, metas);
            }
        }

        return CargadorTablero.cargar(celdas, metas);
    }

    private Casilla crearCasilla(String simbolo, int fila, int columna, boolean[][] metas) {
        if (simbolo == null) {
            return new SueloComun(fila, columna);
        }

        switch (simbolo) {
            case "#":
                return new ParedComun(fila, columna);

            case " ":
                return new SueloComun(fila, columna);

            case ".":
                metas[fila][columna] = true;
                return new Meta(fila, columna);

            case "$":
                return new CajaComun(fila, columna);

            case "*":
                Caja cajaEnMeta = new CajaComun(fila, columna);
                cajaEnMeta.setEnMeta(true);
                metas[fila][columna] = true;
                return cajaEnMeta;

            case "@":
                return new Personaje(fila, columna);

            case "+":
                metas[fila][columna] = true;
                return new Personaje(fila, columna);

            default:
                return new SueloComun(fila, columna);
        }
    }
}

package ec.edu.epn.sokoban.model.factory;

import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Suelo;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Teletransportacion;
import ec.edu.epn.sokoban.model.historial.Nivel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica encargada de instanciar y armar el tablero a partir del mapa de datos del nivel.
 */
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

        List<int[]> portales = new ArrayList<>();
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if ("T".equals(mapaDatos[f][c])) {
                    portales.add(new int[]{f, c});
                }
            }
        }

        Casilla[][] celdas = new Casilla[filas][columnas];
        boolean[][] metas = new boolean[filas][columnas];

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                String simbolo = mapaDatos[f][c];
                celdas[f][c] = crearCasilla(simbolo, f, c, metas, portales);
            }
        }

        return CargadorTablero.cargar(celdas, metas);
    }

    private Casilla crearCasilla(String simbolo, int fila, int columna, boolean[][] metas, List<int[]> portales) {
        if (simbolo == null) {
            return new Suelo(fila, columna);
        }

        switch (simbolo) {
            case "#":
                return new Pared(fila, columna);

            case " ":
                return new Suelo(fila, columna);

            case ".":
                metas[fila][columna] = true;
                return new Meta(fila, columna);

            case "$":
                return new Caja(fila, columna);

            case "*":
                Caja cajaEnMeta = new Caja(fila, columna);
                cajaEnMeta.setEnMeta(true);
                metas[fila][columna] = true;
                return cajaEnMeta;

            case "@":
                return new Personaje(fila, columna);

            case "+":
                metas[fila][columna] = true;
                return new Personaje(fila, columna);

            case "T":
                Suelo sueloPortal = new Suelo(fila, columna);
                int portalIdx = -1;
                for (int i = 0; i < portales.size(); i++) {
                    int[] p = portales.get(i);
                    if (p[0] == fila && p[1] == columna) {
                        portalIdx = i;
                        break;
                    }
                }
                if (portalIdx != -1) {
                    int parejaIdx = (portalIdx % 2 == 0) ? portalIdx + 1 : portalIdx - 1;
                    if (parejaIdx >= 0 && parejaIdx < portales.size()) {
                        int[] pareja = portales.get(parejaIdx);
                        sueloPortal.getGestorAcciones().agregarAccion(new Teletransportacion(pareja[0], pareja[1]));
                    }
                }
                return sueloPortal;

            default:
                return new Suelo(fila, columna);
        }
    }
}

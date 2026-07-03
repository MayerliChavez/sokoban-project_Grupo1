package ec.edu.epn.sokoban.model;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.factory.FabricaNiveles;
import ec.edu.epn.sokoban.model.historial.HistorialMovimientos;
import ec.edu.epn.sokoban.model.historial.Nivel;
import ec.edu.epn.sokoban.model.historial.PartidaMomento;
import ec.edu.epn.sokoban.model.persistencia.GestorPersistencia;
import ec.edu.epn.sokoban.model.reglas.GestorColisiones;
import ec.edu.epn.sokoban.model.reglas.ReglasJuego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JuegoSokoban {
    private List<Nivel> nivelesDisponibles;
    private Nivel nivelActual;
    private Tablero tableroActual;
    private HistorialMovimientos historial;
    private ReglasJuego reglas;
    private GestorColisiones colisiones;
    private GestorPersistencia persistencia;

    public JuegoSokoban(List<Nivel> nivelesDisponibles) {
        this.nivelesDisponibles = nivelesDisponibles != null ? nivelesDisponibles : new ArrayList<>();
        this.historial = new HistorialMovimientos();
        this.reglas = new ReglasJuego();
        this.colisiones = new GestorColisiones();
        this.persistencia = new GestorPersistencia("progress.txt");
    }

    public List<Nivel> getNivelesDisponibles() {
        return nivelesDisponibles;
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public Tablero getTableroActual() {
        return tableroActual;
    }

    public void seleccionarNivel(Nivel nivel) {
        if (nivel == null) {
            return;
        }

        FabricaNiveles fabrica = new FabricaNiveles();
        this.nivelActual = nivel;
        this.tableroActual = fabrica.construirTablero(nivel);
        this.historial.vaciarHistorial();
    }

    public void procesarEntrada(Direccion dir) {
        if (dir == null || tableroActual == null) {
            return;
        }

        Casilla operario = buscarOperario();

        if (operario == null) {
            return;
        }

        if (!reglas.validarMovimiento(operario, dir, tableroActual)) {
            return;
        }

        PartidaMomento estadoAnterior = capturarEstadoActual();

        int filaDestino = operario.getFila() + dir.getDeltaFila();
        int columnaDestino = operario.getColumna() + dir.getDeltaColumna();
        Casilla destino = tableroActual.obtenerCasilla(filaDestino, columnaDestino);

        boolean movimientoRealizado = false;

        if (destino instanceof Caja) {
            boolean cajaEmpujada = colisiones.resolverEmpuje(tableroActual, operario, (Caja) destino, dir);

            if (cajaEmpujada) {
                movimientoRealizado = tableroActual.moverOperario(dir);
            }
        } else if (!colisiones.verificarColision(tableroActual, operario, dir)) {
            movimientoRealizado = tableroActual.moverOperario(dir);
        }

        if (movimientoRealizado) {
            historial.registrarEstado(estadoAnterior);
            verificarYRegistrarVictoria();
        }
    }
    // Depende de la implementación de PartidaMomento.restaurarEnTablero().
    public void deshacerUltimaAccion() {
        if (tableroActual == null) {
            return;
        }

        PartidaMomento ultimoEstado = historial.extraerUltimoEstado();

        if (ultimoEstado != null) {
            ultimoEstado.restaurarEnTablero(tableroActual);
        }
    }

    public void reiniciarNivelActual() {
        if (nivelActual != null) {
            seleccionarNivel(nivelActual);
        }
    }

    public void agregarNivel(Nivel n) {
        if (n != null) {
            this.nivelesDisponibles.add(n);
        }
    }

    private Casilla buscarOperario() {
        if (tableroActual == null) {
            return null;
        }

        for (int f = 0; f < tableroActual.getFilas(); f++) {
            for (int c = 0; c < tableroActual.getColumnas(); c++) {
                Casilla casilla = tableroActual.obtenerCasilla(f, c);

                if (casilla instanceof Personaje) {
                    return casilla;
                }
            }
        }

        return null;
    }

    private PartidaMomento capturarEstadoActual() {
        Map<Caja, Casilla> posicionesCajas = new HashMap<>();
        Casilla posicionJugador = null;

        for (int f = 0; f < tableroActual.getFilas(); f++) {
            for (int c = 0; c < tableroActual.getColumnas(); c++) {
                Casilla casilla = tableroActual.obtenerCasilla(f, c);

                if (casilla instanceof Caja) {
                    posicionesCajas.put((Caja) casilla, casilla);
                }

                if (casilla instanceof Personaje) {
                    posicionJugador = casilla;
                }
            }
        }

        return new PartidaMomento(posicionesCajas, posicionJugador);
    }

    private void verificarYRegistrarVictoria() {
        if (nivelActual == null || tableroActual == null) {
            return;
        }

        // Según el UML, una Meta ocupada se representa con Caja.enMeta.
        boolean hayMetaPendiente = false;
        boolean hayCajaEnMeta = false;

        for (int f = 0; f < tableroActual.getFilas(); f++) {
            for (int c = 0; c < tableroActual.getColumnas(); c++) {
                Casilla casilla = tableroActual.obtenerCasilla(f, c);

                if (casilla instanceof Meta) {
                    hayMetaPendiente = true;
                }

                if (casilla instanceof Caja && ((Caja) casilla).isEnMeta()) {
                    hayCajaEnMeta = true;
                }
            }
        }

        if (!hayMetaPendiente && hayCajaEnMeta) {
            nivelActual.marcarComoCompletado();
            persistencia.guardarProgreso(nivelesDisponibles);
        }
    }
}





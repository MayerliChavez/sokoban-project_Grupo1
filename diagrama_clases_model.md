# Diagrama de Clases - Carpeta `model`

Este documento contiene el diagrama de clases Mermaid actualizado que representa la estructura física de la carpeta `model` del proyecto Sokoban.

```mermaid
classDiagram
    class JuegoSokoban {
        -List nivelesDisponibles
        -Nivel nivelActual
        -Tablero tableroActual
        -HistorialMovimientos historial
        -ManejadorColision cadenaColisiones
        -GestorPersistencia persistencia
        +JuegoSokoban(List nivelesDisponibles)
        +seleccionarNivel(Nivel nivel) void
        +deshacerUltimaAccion() void
        +reiniciarNivelActual() void
        +agregarNivel(Nivel n) void
        +getCadenaColisiones() ManejadorColision
        +capturarEstadoActual() PartidaMomento
        +verificarYRegistrarVictoria() void
    }

    class Tablero {
        -int filas
        -int columnas
        -CasillaMatrix celdas
        -booleanMatrix metas
        -Personaje personaje
        +Tablero(int filas, int columnas)
        +getFilas() int
        +getColumnas() int
        +obtenerCasilla(int f, int c) Casilla
        +actualizarCasilla(int f, int c, Casilla nuevaCasilla) void
        +esTransitable(int f, int c) bool
        +getPersonaje() Personaje
    }

    class Casilla {
        <<abstract>>
        -int fila
        -int columna
        +getFila() int
        +setFila(int fila) void
        +getColumna() int
        +setColumna(int columna) void
        +esTransitable()* bool
    }

    class Personaje {
        +Personaje(int fila, int columna)
        +mover(Direccion d, Tablero t) bool
        +mover(Direccion d, Tablero t, ManejadorColision cadenaColisiones) bool
        +esTransitable() bool
    }

    class Caja {
        -bool enMeta
        +Caja(int fila, int columna)
        +Caja(int fila, int columna, bool enMeta)
        +mover(Direccion d, Tablero t) bool
        +isEnMeta() bool
        +setEnMeta(bool enMeta) void
        +esTransitable() bool
    }

    class Pared {
        +Pared(int f, int c)
        +esTransitable() bool
    }

    class Meta {
        +Meta(int f, int c)
        +esTransitable() bool
    }

    class SueloComun {
        +SueloComun(int f, int c)
        +esTransitable() bool
    }

    class FabricaNiveles {
        +construirTablero(Nivel n) Tablero
    }

    class HistorialMovimientos {
        -int movimientosContador
        -Deque historial
        +registrarEstado(PartidaMomento e) void
        +extraerUltimoEstado() PartidaMomento
        +vaciarHistorial() void
        +getMovimientosContador() int
    }

    class Nivel {
        -bool completado
        -StringMatrix mapaDatos
        +Nivel(StringMatrix mapaDatos)
        +Nivel(StringMatrix mapaDatos, bool completado)
        +isCompletado() bool
        +setCompletado(bool completado) void
        +getMapaDatos() StringMatrix
        +marcarComoCompletado() void
    }

    class PartidaMomento {
        -Map posicionesCajas
        -Casilla posicionJugador
        +PartidaMomento(Map posicionesCajas, Casilla posicionJugador)
        +restaurarEnTablero(Tablero t) void
    }

    class GestorPersistencia {
        -String archivoRuta
        +GestorPersistencia(String archivoRuta)
        +guardarProgreso(List niveles) void
        +cargarProgreso() List
        +cargarNivelDesdeRecursos(int nivelId) Nivel
    }

    class ManejadorColision {
        <<interface>>
        +setSiguiente(ManejadorColision siguiente) void
        +procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir) bool
    }

    class ManejadorPared {
        -ManejadorColision siguiente
        +setSiguiente(ManejadorColision siguiente) void
        +procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir) bool
    }

    class ManejadorCaja {
        -ManejadorColision siguiente
        +setSiguiente(ManejadorColision siguiente) void
        +procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir) bool
    }

    class ManejadorMovimientoBase {
        -ManejadorColision siguiente
        +setSiguiente(ManejadorColision siguiente) void
        +procesarMovimiento(Tablero tablero, Casilla origen, Direccion dir) bool
    }

    ManejadorColision <|.. ManejadorPared : implements
    ManejadorColision <|.. ManejadorCaja : implements
    ManejadorColision <|.. ManejadorMovimientoBase : implements
    ManejadorColision --> ManejadorColision : siguiente

    Casilla <|-- Personaje : extends
    Casilla <|-- Caja : extends
    Casilla <|-- Pared : extends
    Casilla <|-- Meta : extends
    Casilla <|-- SueloComun : extends

    Tablero *--> Casilla : celdas
    Tablero --> Personaje : personaje
    JuegoSokoban --> Tablero : tableroActual
    JuegoSokoban --> ManejadorColision : cadenaColisiones
    JuegoSokoban --> HistorialMovimientos : historial
    JuegoSokoban --> GestorPersistencia : persistencia
    JuegoSokoban --> Nivel : nivelActual
    HistorialMovimientos o--> PartidaMomento : historial
```

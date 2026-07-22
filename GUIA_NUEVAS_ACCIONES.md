# Tutorial: Cómo Implementar Nuevas Acciones y Comportamientos de Nivel

Este documento es una guía práctica en formato **tutorial paso a paso** diseñada para que los equipos de desarrollo puedan añadir nuevas mecánicas (ej. Lava, Hielo, Cajas Explosivas, Resortes, etc.) al proyecto Sokoban de manera independiente, modular y sin generar acoplamiento (*Code Smells*).

---

## 🧠 1. Comprender la Arquitectura Básica (Concepto Clave)

Antes de escribir código, es importante entender el flujo de datos del juego:

```
[ Archivo nivel.txt ]
        │ (Lee el carácter 'L')
        ▼
[ FabricaNiveles ] ──(Instancia)──► [ Suelo ] ──(Agrega)──► [ MiAccion (Lava) ]
                                                                  │
                                                        ┌─────────┴─────────┐
                                                        ▼                   ▼
                                            (Validación de movimiento)  (Efecto visual / UI)
                                             [ GestorColisiones ]      [ PanelTablero ]
```

1. **Archivos `.txt`**: Definen la distribución espacial del nivel con símbolos.
2. **`FabricaNiveles`**: Lee el `.txt` y asigna las acciones a las casillas.
3. **`Accion` (Patrón Strategy)**: Es una clase que define la regla lógica y el efecto de la mecánica.
4. **`GestorColisiones`**: Pregunta a la acción si la entidad puede moverse y luego detona su efecto.
5. **`PanelTablero` (Patrón Visitor)**: Dibuja la celda y superpone el sprite visual de la acción.

---

## 🛠️ Tutorial Paso a Paso: Creando una Nueva Mecánica (Ejemplo: "Suelo de Lava")

Objetivo del tutorial: Crear un tipo de suelo de lava (`L`) donde si una caja cae, la caja se destruye; y si el personaje lo pisa, se reinicia/pierde.

---

### 📝 Paso 1: Definir el símbolo en el archivo del Nivel (`.txt`)

Abre o crea un mapa dentro de `src/main/resources/niveles/` (por ejemplo `4.txt`) y coloca el nuevo símbolo acordado (usaremos `L` para Lava):

```text
#####
#@  #
# $L#
#####
```

---

### 🖼️ Paso 2: Agregar la imagen del Sprite

Guarda la imagen del elemento en formato `.png` dentro del directorio de recursos visuales:
* **Ruta del archivo:** `src/main/resources/images/lava.png`
* **Recomendación:** Formato PNG transparente de aproximadamente 64x64 píxeles.

---

### ⚙️ Paso 3: Crear la clase de la Acción (`Lava.java`)

Crea la clase `Lava.java` en el paquete `ec.edu.epn.sokoban.model.escenario` implementando la interfaz `Accion`.

```java
package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

/**
 * Acción concreta que representa un terreno peligroso de Lava.
 */
public class Lava implements Accion {

    /**
     * 1. LÓGICA DE EJECUCIÓN (¿Qué pasa cuando alguien entra a esta casilla?)
     * Este método se ejecuta automáticamente DESPUÉS de que la entidad (Personaje o Caja)
     * ha sido movida con éxito a la casilla.
     */
    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        if (entidad instanceof Personaje) {
            // Ejemplo: El personaje pisa la lava
            System.out.println("¡El personaje ha caído en la lava!");
            // Aquí puedes llamar a una lógica de reinicio de nivel o fin de juego.
        } else if (entidad instanceof Caja) {
            // Ejemplo: Una caja cae en la lava -> La destruimos liberando la posición
            System.out.println("¡Una caja ha sido destruida por la lava!");
            tablero.restaurarCasillaBase(entidad.getFila(), entidad.getColumna());
        }
    }

    /**
     * 2. LÓGICA DE VALIDACIÓN PREVIA (¿La entidad tiene permitido entrar a esta casilla?)
     * Este método se ejecuta ANTES de realizar el movimiento.
     * Retorna true por defecto. Retorna false si el movimiento debe ser bloqueado.
     */
    @Override
    public boolean puedeIngresarEntidad(Tablero tablero, Casilla entidad) {
        // En el caso de la lava, sí se permite entrar a cualquier entidad (para que caiga en ella)
        return true; 
    }

    /**
     * 3. IDENTIFICADOR DEL SPRITE VISUAL
     * Retorna la clave con la que el PanelTablero buscará la imagen a dibujar.
     */
    @Override
    public String getSpriteKey() {
        return "LAVA";
    }
}
```

---

### 🏭 Paso 4: Conectar la Acción en la Fábrica (`FabricaNiveles.java`)

Abre `FabricaNiveles.java` y añade el nuevo caso en el método `crearCasilla()` para que al leer el carácter `'L'` instancie el suelo y le añada la acción:

```java
// En ec.edu.epn.sokoban.model.factory.FabricaNiveles.java

switch (simbolo) {
    case "#":
        return new Pared(fila, columna);

    case " ":
        return new Suelo(fila, columna);

    // ... casos existentes ...

    case "L": // ◄─── NUEVO CASO AGREGADO
        Suelo sueloLava = new Suelo(fila, columna);
        sueloLava.getGestorAcciones().agregarAccion(new Lava());
        return sueloLava;

    default:
        return new Suelo(fila, columna);
}
```

---

### 🎨 Paso 5: Registrar la representación gráfica en la UI (`PanelTablero.java`)

Abre `PanelTablero.java` y haz 2 pequeños registros en los métodos de configuración visual:

1. **Cargar la imagen** en el método `cargarSprites()`:
   ```java
   private void cargarSprites() {
       sprites.put("PARED", cargarImagen("/images/wall.png"));
       sprites.put("SUELO", cargarImagen("/images/floor.png"));
       sprites.put("META", cargarImagen("/images/goal.png"));
       sprites.put("CAJA", cargarImagen("/images/box.png"));
       sprites.put("JUGADOR", cargarImagen("/images/player.png"));
       sprites.put("PORTAL", cargarImagen("/images/portal.png"));
       sprites.put("LAVA", cargarImagen("/images/lava.png")); // ◄─── REGISTRO NUEVO
   }
   ```

2. **Asignar color de respaldo** en el método `obtenerColorRespaldoParaAccion()` (se usa por seguridad si la imagen PNG falla):
   ```java
   private Color obtenerColorRespaldoParaAccion(String spriteKey) {
       return switch (spriteKey) {
           case "PORTAL" -> Color.web("#8A2BE2");
           case "LAVA" -> Color.web("#FF4500"); // ◄─── COLOR NARANJA/ROJO DE RESPALDO
           default -> Color.TRANSPARENT;
       };
   }
   ```

---

## 💡 Variación: Crear una Acción asociada a una Caja (ej. "Caja Explosiva")

Si tu grupo necesita agregar una mecánica donde el elemento no es un suelo sino una **Caja especial**:

1. Creas la clase de la acción (ej. `Explosiva.java implements Accion`).
2. En `FabricaNiveles.java`, asignas un carácter (ej. `'X'`):
   ```java
   case "X":
       Caja cajaExplosiva = new Caja(fila, columna);
       cajaExplosiva.getGestorAcciones().agregarAccion(new Explosiva());
       return cajaExplosiva;
   ```
3. Registras el sprite `"CAJA_EXPLOSIVA"` en `PanelTablero.java`.

---

## 📋 Lista de Autochequeo (Checklist de Calidad)

Antes de hacer un `commit` o `push` de tu nueva acción, verifica:

* [ ] ¿Creaste una clase separada que implementa `Accion`?
* [ ] ¿Evitaste crear subclases como `SueloLava` o `CajaExplosiva`?
* [ ] ¿Revisaste que NO modificaste `Tablero.java` ni `GestorColisiones.java`?
* [ ] ¿Verificaste que no hay declaraciones de `instanceof` en clases fuera de tu propia `Accion`?
* [ ] ¿La imagen `.png` se encuentra en `src/main/resources/images/`?

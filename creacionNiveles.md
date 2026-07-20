# Manual Técnico de Creación de Niveles

Este documento describe la especificación técnica y las convenciones para diseñar y agregar nuevos niveles al juego Sokoban.

---

## 1. Mapeo de Caracteres en Texto Plano

Para definir los mapas de juego en archivos de texto plano (`.txt`), se utiliza la siguiente convención estándar de caracteres:

| Carácter | Elemento Físico | Clase Asociada | Descripción |
| :---: | :--- | :--- | :--- |
| **`#`** | Pared | `Pared` | Bloque sólido infranqueable. |
| **` `** (Espacio) | Suelo Común | `SueloComun` | Celda libre transitable. |
| **`.`** | Meta | `Meta` | Destino para colocar una caja (vacía). |
| **`$`** | Caja | `Caja` | Caja en suelo común (no satisfecha). |
| **`*`** | Caja en Meta | `Caja` colocada en `Meta` | Caja que ya se encuentra sobre una meta. |
| **`@`** | Personaje | `Personaje` | Operario del juego en suelo común. |
| **`+`** | Personaje en Meta | `Personaje` sobre `Meta` | El operario de pie sobre una meta. |
| **`T`** | Portal | `Suelo` con `Teletransportacion` | Suelo con acción de teletransportación para cajas. |

---

## 2. Nomenclatura Estricta de Archivos

Los archivos de nivel deben guardarse siguiendo la nomenclatura de ID numérico secuencial:
*   El primer nivel se nombrará: `1.txt`
*   El segundo nivel se nombrará: `2.txt`
*   El nivel *N* se nombrará: `N.txt`

Esta secuencia numérica permite al `GestorPersistencia` e `JuegoSokoban` descubrir y cargar dinámicamente los niveles por índice.

---

## 3. Ruta Centralizada en la Arquitectura

Todos los niveles oficiales creados deben almacenarse exclusivamente en la siguiente ruta del proyecto para ser empaquetados dentro de los recursos de Maven:

`src/main/resources/niveles/`

Cualquier nivel colocado fuera de esta ruta no se compilará dentro de los recursos de la aplicación y fallará al cargarse en tiempo de ejecución.

---

## 4. Ejemplo Visual de un Nivel

A continuación se muestra un ejemplo de un mapa básico de 8x8 representado en un archivo plano. Cada fila del archivo representa una fila de la matriz de la partida y debe mantener un tamaño homogéneo relleno de espacios para evitar errores de desbordamiento en el lector.

Ejemplo (`1.txt`):
```text
  ####  
###  ###
#      #
# @$.  #
###  ###
  ####  
```

Otro ejemplo clásico (`2.txt`):
```text
#####   
#   #   
#$  #   
### ### 
# .@  # 
#  $  # 
#  .  # 
####### 
```

---

## 5. Algoritmo de Carga de Niveles (Referencia)

El `GestorPersistencia` lee el archivo línea por línea para encontrar las dimensiones máximas. Luego genera una matriz bidimensional `String[][]` (o `char[][]`) que se transfiere al `Nivel`. Finalmente, la `FabricaNiveles` procesa cada carácter e inicializa el `Tablero` instanciando las subclases correspondientes de `Casilla` (Composite).

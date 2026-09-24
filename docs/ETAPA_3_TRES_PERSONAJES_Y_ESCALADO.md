# Etapa 3: tres personajes y mapa adaptable

Esta etapa permite observar el nuevo nivel con cualquiera de los tres personajes.
Se mantiene el alcance del laboratorio: caminar, saltar, caer y aterrizar sobre un
suelo fijo. Todavía no se programaron colisiones con las plataformas de la imagen.

## Resultado

- Nuevo fondo `mapa_nivel_1.jpeg`.
- Ventana maximizada según el monitor disponible.
- Mapa escalado sin deformar su relación entre ancho y alto.
- Ágil, Tirador y Tanque con cuadros de quieto, caminata y salto.
- Una sola vista y un solo controlador para los tres personajes.
- Personaje medido en `48 x 72` unidades del mundo.
- Mapa medido en `1600 x 481` unidades del mundo.

## Paso 1: elegir el personaje

La elección se hace en `LaboratorioMain`. Debe quedar activa una sola de estas
líneas:

```java
Personaje jugador = new PersonajeAgil("Agil");
// Personaje jugador = new PersonajeTirador("Tirador");
// Personaje jugador = new PersonajeTanque("Tanque");
```

Para probar el Tirador, se comenta la primera y se activa la segunda:

```java
// Personaje jugador = new PersonajeAgil("Agil");
Personaje jugador = new PersonajeTirador("Tirador");
// Personaje jugador = new PersonajeTanque("Tanque");
```

No hay que modificar `PanelJuego` ni `ControladorJuego` porque ambos trabajan con
el tipo general `Personaje`.

## Paso 2: selección automática del sprite

`SpritePersonaje.para(jugador)` reconoce la subclase instanciada y carga la lámina
correspondiente:

| Objeto creado | Recurso utilizado |
|---|---|
| `PersonajeAgil` | `assets/sprites/agil/` |
| `PersonajeTirador` | `assets/sprites/tirador/` |
| `PersonajeTanque` | `assets/sprites/tanque/` |

De las láminas originales se prepararon cuatro PNG transparentes por personaje:
uno quieto, dos caminando y uno saltando. Las láminas completas también se guardan
en `assets` como referencia, pero el juego carga directamente los PNG ya limpios.
Esto evita procesar imágenes en cada ejecución y mantiene `SpritePersonaje` corta.

Esta tarea pertenece a la vista. Las clases `PersonajeAgil`, `PersonajeTirador` y
`PersonajeTanque` siguen sin conocer imágenes ni clases de Swing.

## Paso 3: medidas lógicas

El código diferencia las medidas del mundo de las medidas reales del monitor:

```java
public static final int ANCHO_MUNDO = 1600;
public static final int ALTO_MUNDO = 481;
public static final int ANCHO_PERSONAJE = 48;
public static final int ALTO_PERSONAJE = 72;
```

El mapa aportado mide `1600 x 481` píxeles y sus bloques principales siguen una
cuadrícula aproximada de `32 x 32`. Por eso el personaje ocupa alrededor de una
casilla y media de ancho y dos casillas y cuarto de alto.

Estas son unidades lógicas: en un monitor grande se ven más grandes, pero la
relación entre personaje, plataformas y salto no cambia.

## Paso 4: adaptación a pantalla completa

`LaboratorioMain` maximiza el `JFrame`:

```java
ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
```

`PanelJuego` compara cuánto puede crecer el mapa horizontal y verticalmente, y usa
el menor valor como escala. De esta manera no se estira solamente un eje.

```java
return Math.min(escalaHorizontal, escalaVertical);
```

El mapa completo queda visible y apoyado en la parte inferior. En monitores altos
puede aparecer más espacio blanco arriba; es intencional porque representa cielo y
evita deformar el nivel.

## Paso 5: tamaño del personaje

Los tres comparten una caja visual de `48 x 72`. Cada cuadro conserva su proporción
y se centra dentro de esa caja. Para probar otro tamaño solo se modifican estas dos
constantes de `PanelJuego`:

```java
public static final int ANCHO_PERSONAJE = 48;
public static final int ALTO_PERSONAJE = 72;
```

Conviene cambiar ambas manteniendo una proporción parecida. Por ejemplo, `40 x 60`
lo hace más pequeño y `56 x 84` lo hace más grande.

## Paso 6: salto y suelo provisional

El suelo fijo está en la coordenada lógica `383`, que coincide con la parte superior
de la primera plataforma grande:

```java
public static final int Y_SUELO = 383;
```

El salto continúa definido en `Personaje`:

```java
private static final double GRAVEDAD = 0.6;
private static final double FUERZA_SALTO = 14.5;
```

Como todavía no existen colisiones, ese suelo invisible atraviesa todo el ancho del
nivel. El personaje puede caminar visualmente sobre huecos y atravesar bloques. Es
el comportamiento esperado en esta etapa.

## Paso 7: responsabilidades MVC

| Parte | Responsabilidad actual |
|---|---|
| Modelo | posición, velocidad, gravedad y salto del personaje |
| Vista | fondo, escala, sprite, animación y cartel de ayuda |
| Controlador | teclado, actualización cada 16 ms y `repaint()` |
| `LaboratorioMain` | instancia y conecta los objetos |

El controlador recibe un `Personaje`, no una subclase concreta. Por eso la misma
lógica de teclado funciona con Ágil, Tirador y Tanque. Cada uno conserva además la
velocidad definida en su propia clase.

## Cómo ejecutarlo

1. Abrir la carpeta completa del repositorio en Visual Studio Code.
2. Elegir una instancia en `src/keyos/LaboratorioMain.java`.
3. Abrir ese archivo.
4. Presionar `Run` sobre el método `main`.
5. Mover con flechas o `A`/`D` y saltar con espacio.

El programa debe ejecutarse desde la raíz del repositorio para encontrar la carpeta
`assets`.

## Qué no se agregó

- Colisiones con suelo, bloques o huecos.
- Hitboxes.
- Armas y proyectiles jugables.
- Enemigos o llaves.
- Cámara o desplazamiento del nivel.
- Lectura del archivo de Tiled.

## Criterios teóricos aplicados

- **Herencia:** las tres opciones son subclases de `Personaje`.
- **Polimorfismo:** vista y controlador reciben la referencia general `Personaje`.
- **Encapsulamiento:** posición y física continúan protegidas dentro del modelo.
- **MVC:** los recursos visuales no se mezclan con la lógica del personaje.
- **Responsabilidad única:** cada clase conserva un motivo principal para cambiar.
- **Composición:** `LaboratorioMain` crea y conecta modelo, vista y controlador.

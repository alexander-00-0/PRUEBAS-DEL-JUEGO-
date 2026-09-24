# Etapa 2: maqueta visual del bosque

> Nota: este documento conserva el proceso histórico de la etapa 2. Desde la etapa
> 3, `SpriteAgil` fue reemplazada por `SpritePersonaje` para admitir los tres
> personajes y los cuadros se cargan como PNG transparentes ya preparados.

Esta etapa transforma el laboratorio rectangular en una maqueta visual basada en el mapa completo diseñado por el equipo. El objetivo sigue siendo experimentar: todavía no hay colisiones, armas, enemigos ni lectura automática del archivo de Tiled.

## Resultado

- Ventana de 1200 x 650 píxeles.
- Captura del mapa del bosque como escenario completo.
- Personaje Ágil dibujado desde la lámina de sprites aportada por el equipo.
- Dos cuadros alternados al caminar, uno quieto y uno al saltar.
- Giro visual cuando cambia la dirección.
- Cuatro plataformas de prueba exclusivamente visuales.
- Movimiento horizontal, salto, gravedad y suelo fijo del laboratorio anterior.

## Paso 1: recursos visuales

Los archivos originales se guardan en `assets/`:

- `mapa_bosque_referencia.jpeg`: captura completa del nivel.
- `agil_sprites_referencia.jpeg`: lámina original del Ágil.

No se modifica el mapa ni se separan manualmente decenas de imágenes. `SpriteAgil` recorta solamente los cuatro cuadros necesarios durante el inicio del programa.

## Paso 2: modelo del obstáculo

`ObstaculoVisual` guarda `x`, `y`, `ancho` y `alto`. Es parte del modelo porque representa un elemento ubicado en el mundo, pero todavía no posee métodos de colisión.

Esto permite practicar composición de objetos sin adelantarnos a hitboxes:

```java
new ObstaculoVisual(230, 350, 90, 32)
```

## Paso 3: construcción en LaboratorioMain

`LaboratorioMain` crea un `PersonajeAgil`, define su posición inicial, instancia cuatro obstáculos y conecta el modelo con `PanelJuego` y `ControladorJuego`.

El `main` no dibuja ni calcula la física. Su única responsabilidad es construir y conectar el programa.

## Paso 4: carga de sprites

`SpriteAgil` carga la lámina, recorta los cuadros de quieto, caminando y saltando, y vuelve transparente el fondo oscuro conectado con los bordes.

La transparencia pertenece a la vista: el objeto `PersonajeAgil` no sabe qué imagen lo representa.

## Paso 5: dibujo en PanelJuego

`PanelJuego.paintComponent()` realiza este orden:

1. Dibuja la captura del bosque ajustada a la ventana.
2. Dibuja las plataformas visuales.
3. Selecciona y dibuja el cuadro actual del Ágil.
4. Dibuja el pequeño panel con los controles.

La vista consulta la posición del modelo, pero no cambia la física.

## Paso 6: animación y dirección

El controlador calcula una dirección:

- `-1`: izquierda.
- `0`: quieto.
- `1`: derecha.

Después se la comunica a la vista. `PanelJuego` alterna dos cuadros cada ocho actualizaciones y refleja la imagen si el jugador mira hacia la izquierda.

## Paso 7: movimiento y salto

La física sigue en `Personaje`:

```java
private static final double GRAVEDAD = 0.6;
private static final double FUERZA_SALTO = 14.5;
```

El `Timer` del controlador continúa actualizando el juego cada 16 milisegundos. Después de modificar el modelo llama a `repaint()` para solicitar un nuevo dibujo.

## Cómo ejecutarlo

1. Abrir el repositorio completo en Visual Studio Code.
2. Verificar que las imágenes estén dentro de `assets/`.
3. Abrir `src/keyos/LaboratorioMain.java`.
4. Presionar `Run` sobre el método `main`.

Controles:

- Flechas o `A`/`D`: mover.
- Espacio: saltar.

## Qué no hace todavía

Las plataformas se ven, pero el personaje puede atravesarlas. Eso es intencional: para detenerse sobre ellas primero habrá que estudiar hitboxes y detección de colisiones. Tampoco se cargan el archivo `.tmx`, enemigos, armas ni llaves.

## Criterios teóricos aplicados

- **Encapsulamiento:** la posición y la física permanecen dentro del modelo.
- **Herencia:** se instancia `PersonajeAgil`, que hereda de `Personaje` y `Entidad`.
- **MVC:** modelo, panel visual y controlador conservan responsabilidades diferentes.
- **Responsabilidad única:** `SpriteAgil` carga sprites, `PanelJuego` dibuja, `ControladorJuego` coordina y `LaboratorioMain` conecta los objetos.

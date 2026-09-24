# Etapa 4: hitbox, plataformas y colisiones

## Resultado de esta etapa

El mapa dejó de ser solamente una imagen. Ahora conserva esa imagen como fondo,
pero también posee rectángulos físicos alineados con sus pisos y plataformas.
Los tres personajes pueden caminar, saltar, caer, aterrizar y chocar contra esas
superficies.

Durante las pruebas se muestran dos ayudas:

- Verde: zona física de una plataforma.
- Rojo: hitbox del personaje.

## 1. Por qué las plataformas no son aleatorias

`mapa_nivel_1.jpeg` mide `1600 x 481` y está construido aproximadamente sobre
una grilla de `32 x 32`. Las plataformas declaradas en `Nivel` usan esas mismas
coordenadas. De este modo, el rectángulo lógico coincide con el bloque visible y
el jugador no choca contra lugares vacíos.

La imagen responde a la pregunta "¿qué ve el jugador?". Las plataformas del
modelo responden a "¿dónde puede caminar o chocar?".

## 2. Clases incorporadas

### `Colisionable`

Es una interfaz con el método `getHitbox()`. Funciona como contrato: cualquier
clase que lo implemente debe informar qué rectángulo ocupa dentro del mundo.

La interfaz no fue agregada a `Entidad`. `Personaje` y `Plataforma` la implementan
de forma explícita, por lo que una clase solo adopta colisiones si realmente las
necesita.

### `Plataforma`

Guarda `x`, `y`, `ancho` y `alto`. Su hitbox es un `Rectangle` con esas medidas.
El mismo rectángulo sirve como piso cuando se toca desde arriba, pared cuando se
toca desde un costado y techo cuando se golpea desde abajo.

### `Nivel`

Contiene el tamaño lógico del escenario, la posición inicial y la lista de
plataformas. Centralizar estos datos evita repartir coordenadas por el controlador
y la vista.

### `Personaje`

Implementa `Colisionable`. El sprite usa una caja visual de `48 x 72`, pero la
hitbox es de `32 x 64` y queda centrada horizontalmente. Es un poco menor para que
el contacto resulte tolerante y el dibujo no choque por píxeles transparentes.

## 3. Cómo se resuelve una colisión

El controlador separa los dos ejes:

1. Mueve en X y corrige una intersección lateral.
2. Aplica salto o gravedad en Y.
3. Si el personaje cae sobre una plataforma, lo ubica exactamente arriba.
4. Si sube y toca la parte inferior, detiene el ascenso.
5. Si camina fuera de una superficie, comienza a caer.
6. Si cae fuera del mapa, vuelve a la posición inicial.

Separar X e Y permite decidir con claridad si el contacto fue con un piso, una
pared o un techo.

## 4. Relación con MVC y POO

- Modelo: `Personaje`, `Plataforma`, `Nivel` y `Colisionable` representan estado,
  geometría y reglas del mundo.
- Vista: `PanelJuego` dibuja mapa, sprites y rectángulos de depuración.
- Controlador: `ControladorJuego` recibe el teclado y coordina movimiento y
  resolución de colisiones.
- Encapsulamiento: las posiciones se corrigen mediante métodos del personaje; el
  controlador no accede directamente a sus atributos.
- Interfaz: `Colisionable` expresa una capacidad común sin obligar a toda la
  jerarquía de `Entidad` a usarla.
- Polimorfismo: el menú continúa trabajando con una referencia `Personaje`, por
  eso la misma física funciona con Ágil, Tirador y Tanque.

## 5. Cómo probarlo

1. Ejecutar `src/LaboratorioMain.java`.
2. Elegir cualquiera de los tres personajes.
3. Presionar Play.
4. Mover con flechas o `A` y `D`.
5. Saltar con espacio.
6. Comprobar que el rectángulo rojo queda sobre los rectángulos verdes y no los
   atraviesa.

Esta es una versión de laboratorio. La superposición verde y roja debe conservarse
mientras se ajustan medidas; en la versión visual final podrá ocultarse sin cambiar
la física.

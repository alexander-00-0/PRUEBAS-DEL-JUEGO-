# Proyecto: Velocity rush

## 1. Integrantes del Equipo

- Rodríguez, Alexander
- Falasconi, Alex
- Olivero, Thiago
- Morales, Mauro

## 2. Dominio y Alcance del Sistema

### Descripción del Problema
Se busca desarrollar una aplicación de escritorio de un videojuego de plataformas y acción en 2D. El jugador deberá recorrer distintos mapas horizontales superando obstáculos y eliminando enemigos mediante el uso de proyectiles. El objetivo central de cada nivel es explorar el entorno para recolectar 3 llaves distribuidas en el escenario. Al obtenerlas todas, se invocará automáticamente al jefe (Boss) del nivel. El jugador debe destruir a este jefe para superar el nivel y avanzar al siguiente mapa.

### Objetivo del Sistema
El sistema será un juego funcional, fluido y extensible que permitirá al jugador experimentar mecánicas clásicas de plataformas. El diseño debera ser modular para facilitar la adición de nuevos mapas, variaciones de armas o tipos de enemigos en el futuro, aplicando rigurosamente los conceptos del paradigma orientado a objetos (herencia y polimorfismo para las distintas entidades).

### Funcionalidades Principales
- **Gestión de Personajes:**
  - El jugador puede seleccionar entre 3 personajes jugables con distintos atributos:
    - **Ágil:** Mayor velocidad de movimiento.
    - **Tirador:** Mayor cadencia de disparo (ataque más rápido).
    - **Tanque:** Mayor cantidad de puntos de vida (habilidad de resistencia).
  - El personaje cuenta con un arma básica para disparar y eliminar a los secuaces (enemigos de baja vida, mueren con aproximadamente 3 impactos).

- **Sistema de Progresión y Niveles:**
  - El juego contará con 4 niveles secuenciales con ambientaciones temáticas: Ciudad, Bosque, Desierto y Fábrica.
  - El mapa se desplaza de forma horizontal (scrolling) o a pantalla completa a medida que el personaje avanza desde la izquierda hacia la derecha.

- **Mecánicas de Juego y Objetivos:**
  - **Sistema de recolección:** Existen 3 llaves accesibles esparcidas en cada nivel.
  - **Invocación de Jefes:** Al recolectar la tercera llave, se libera y aparece automáticamente el Boss del escenario.
  - El Boss cuenta con su propia barra de vida y, al ser derrotado, desbloquea la puerta al siguiente nivel.

- **Interfaz Gráfica y Estado (IGU):**
  - Visualización del entorno 2D, plataformas, personaje, enemigos y objetos recolectables.
  - Panel indicador de llaves recolectadas (0/3), puntos de vida del jugador y barra de vida del Boss (cuando está activo).
  - El jugador tiene un número limitado de "vidas" y puntos de salud. El juego termina si las vidas llegan a cero.



//url del sitio web donde se formo el diagrama de clases: https://excalidraw.com/#room=abd0df29ef34a49aadfc,PkT8JNaxXoZTuA2gQDHhRQ

---------------------------------------------------------------------------------------------------------## Justificación de Diseño Arquitectónico (POO)

A continuación se detalla la justificación técnica de las decisiones de diseño implementadas en la clase principal (`Main`), demostrando la aplicación práctica de los pilares de la Programación Orientada a Objetos

### 1. Colecciones Polimórficas  

La estructura `Entidad[] equipo = {agil, tirador, tanque};` agrupa instancias de diferentes subclases concretas. Al utilizar la superclase abstracta `Entidad` como tipo de referencia común, logramos tratar a todos los personajes jugables de forma uniforme. Esto evidencia un correcto diseño en la jerarquía de herencia, donde el flujo principal del programa interactúa con la abstracción y no se acopla a las implementaciones concretas de cada personaje.

### 2 polimorfismo

El núcleo del bucle de combate ejecuta la instrucción `atacante.atacar(boss)`

* Si en esa iteración el objeto instanciado es un `PersonajeTirador`, se ejecuta de forma transparente su método sobrescrito (`@Override`), evaluando internamente su estado para aplicar, si corresponde, el doble impacto.
* Si es un `PersonajeAgil` o `PersonajeTanque`, se invoca el comportamiento estándar heredado de `Entidad`.
Esto evita la mala práctica de utilizar condicionales para consultar el tipo de clase de cada objeto antes de actuar, centralizando la lógica donde corresponde.

### 3. Interacción Polimórfica 

La instrucción `boss.atacar(atacante)` demuestra un diseño desacoplado. El enemigo (`Boss`) ignora por completo a qué subclase específica está atacando; simplemente delega el cálculo del daño enviando el mensaje `recibirDanio()` al objetivo (una `Entidad`). Sin embargo, si el objetivo resulta ser el `PersonajeTanque`, el polimorfismo garantiza que se ejecute su sobrescritura particular de `recibirDanio()`, mitigando el impacto si su escudo está activo. La responsabilidad de gestionar la defensa queda perfectamente encapsulada en la clase receptora.

### 4. Principio Abierto/Cerrado (OCP) y Extensibilidad

Métodos de control como `haySobrevivientes()` consultan únicamente la interfaz pública definida en la clase base (`estaViva()`). Esto asegura que el sistema cumpla con el Principio Abierto/Cerrado: el día de mañana se pueden agregar nuevas clases derivadas (como un sanador o un hechicero) con habilidades únicas, y la lógica del `Main` seguirá funcionando perfectamente sin necesidad de modificar una sola línea de código.

## Prototipo visual - Etapa 1

El laboratorio de movimiento ya conecta el modelo con una vista Swing y un controlador por teclado. Para probarlo, ejecutar `src/LaboratorioMain.java` desde Visual Studio Code.

- Movimiento: flechas izquierda/derecha o `A`/`D`.
- Salto: barra espaciadora.
- Explicación técnica: [`docs/ETAPA_1_LABORATORIO.md`](docs/ETAPA_1_LABORATORIO.md).

## Maqueta visual del bosque - Etapa 2

El repositorio de pruebas incorpora una maqueta visual basada en el mapa del bosque y en la lámina de sprites del personaje Ágil.

- Ejecutar: `src/LaboratorioMain.java`.
- Movimiento: flechas o `A`/`D`.
- Salto: espacio.
- Alcance: escenario, sprite, animación básica y obstáculos visuales; todavía sin colisiones, armas ni enemigos.
- Guía paso a paso: [`docs/ETAPA_2_MAQUETA_BOSQUE.md`](docs/ETAPA_2_MAQUETA_BOSQUE.md).

## Tres personajes y mapa adaptable - Etapa 3

La maqueta ahora utiliza el mapa de nivel 1 y permite probar Ágil, Tirador o Tanque
sin cambiar la vista ni el controlador.

- Ejecutar: `src/LaboratorioMain.java`.
- Elegir personaje: cambiar una sola instancia dentro de `LaboratorioMain`.
- Pantalla: la ventana se maximiza y conserva las proporciones del mapa.
- Medidas lógicas: mapa de `1600 x 481` y personaje de `48 x 72`.
- Alcance: movimiento, salto y animación básica; todavía sin colisiones.
- Guía paso a paso: [`docs/ETAPA_3_TRES_PERSONAJES_Y_ESCALADO.md`](docs/ETAPA_3_TRES_PERSONAJES_Y_ESCALADO.md).

## Hitbox y colisiones del nivel 1 - Etapa 4

El fondo del nivel ahora tiene plataformas físicas alineadas con sus bloques
visibles. Los tres personajes usan la misma lógica para piso, paredes, techo,
saltos y caídas.

- Verde: plataformas físicas del mapa.
- Rojo: hitbox del personaje (`32 x 64`) dentro de su caja visual (`48 x 72`).
- Diseño: `Personaje` y `Plataforma` implementan `Colisionable` explícitamente.
- Guía: [`docs/ETAPA_4_HITBOX_Y_COLISIONES.md`](docs/ETAPA_4_HITBOX_Y_COLISIONES.md).

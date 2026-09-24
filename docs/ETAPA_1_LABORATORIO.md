# Etapa 1: laboratorio de movimiento

Este prototipo permite probar el movimiento antes de adaptar el mapa del bosque. Al ejecutar `LaboratorioMain`, se abre una ventana de 800 x 600 con un personaje rectangular, un suelo fijo, movimiento lateral, salto y gravedad.

## Controles

- Flechas izquierda/derecha o `A`/`D`: mover.
- Barra espaciadora: saltar.

## Responsabilidad de cada clase

- `LaboratorioMain`: instancia el modelo, la vista y el controlador; luego los conecta y abre la ventana.
- `ControladorJuego`: escucha el teclado y ejecuta un ciclo de actualización cada 16 ms.
- `PanelJuego`: representa el estado actual del modelo con figuras simples.
- `Entidad`: guarda la posición común y permite el movimiento horizontal.
- `Personaje`: agrega velocidad vertical, fuerza de salto, gravedad y aterrizaje.

## Explicación para la defensa

`LaboratorioMain` es el punto de composición: crea los objetos necesarios, pero no contiene física ni dibujo. Esto mantiene separada la construcción del programa de sus responsabilidades internas.

`ControladorJuego` coordina el prototipo. Los eventos de teclado solamente registran la intención del usuario y el `Timer` actualiza el modelo cada 16 ms. Después solicita un nuevo dibujo con `repaint()`. Así se aplican eventos, temporización y la función de controlador de MVC.

`PanelJuego` es la vista. Consulta la posición del personaje y lo dibuja, pero no modifica su estado. Más adelante el rectángulo puede reemplazarse por un sprite sin cambiar la física.

`Entidad` incorpora `posicionY` porque todas las entidades del mundo necesitarán una ubicación bidimensional. El estado queda encapsulado: otras clases consultan la posición mediante getters y solicitan movimientos mediante métodos.

`Personaje` especializa la física del jugador. Al saltar, `velocidadY` toma un valor negativo; en cada actualización la gravedad aumenta esa velocidad. El personaje primero sube, llega a velocidad cero y luego cae. Al alcanzar el suelo, `aterrizar()` corrige la posición y restablece el estado.

Esta etapa usa MVC y responsabilidad única: el modelo conserva estado y comportamiento, la vista dibuja, el controlador coordina eventos y tiempo, y el `main` ensambla el sistema.

## Alcance actual

Todavía no hay plataformas, hitboxes, sprites, enemigos ni lectura del mapa de Tiled. Esos elementos quedan para las siguientes etapas, una vez calibrados el salto y las velocidades.

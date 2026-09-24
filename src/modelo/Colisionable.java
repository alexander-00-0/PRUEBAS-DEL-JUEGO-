package modelo;

import java.awt.Rectangle;

public interface Colisionable {
    Rectangle getHitbox();

    default boolean colisionaCon(Colisionable otro) {
        return otro != null && getHitbox().intersects(otro.getHitbox());
    }
}

/*
 * Criterio aplicado: la interfaz define un contrato para los objetos que ocupan
 * espacio físico. Cada clase decide expresamente si necesita ese comportamiento.
 */

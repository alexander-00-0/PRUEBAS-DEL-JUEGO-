package modelo;

import java.awt.Rectangle;

public class Plataforma implements Colisionable {
    private final int x;
    private final int y;
    private final int ancho;
    private final int alto;

    public Plataforma(int x, int y, int ancho, int alto) {
        if (x < 0 || y < 0 || ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("La plataforma necesita posicion y tamanio validos.");
        }
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, ancho, alto);
    }
}

/*
 * Criterio aplicado: Plataforma es un objeto del modelo. Su rectángulo coincide
 * con una superficie dibujada en el mapa y permite resolver piso, pared y techo.
 */

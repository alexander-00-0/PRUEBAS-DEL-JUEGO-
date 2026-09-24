package modelo;

import java.awt.Rectangle;

public abstract class Personaje extends Entidad implements Colisionable {
    public static final int ANCHO_VISUAL = 48;
    public static final int ALTO_VISUAL = 72;
    public static final int ANCHO_HITBOX = 32;
    public static final int ALTO_HITBOX = 64;
    public static final int OFFSET_HITBOX_X = 8;
    public static final int OFFSET_HITBOX_Y = 8;

    private static final double GRAVEDAD = 0.6;
    private static final double FUERZA_SALTO = 14.5;

    private int llaves;
    private boolean enElAire;
    private double velocidadY;

    protected Personaje(String nombre, int vida, Arma arma, int velocidad) {
        super(nombre, vida, arma, velocidad);
    }

    public int getLlaves() { return llaves; }
    public boolean estaEnElAire() { return enElAire; }
    public double getVelocidadY() { return velocidadY; }
    public boolean tieneTodasLasLlaves() { return llaves == 3; }

    @Override
    public Rectangle getHitbox() {
        int x = getPosicionX() + OFFSET_HITBOX_X;
        int y = (int) Math.round(getPosicionY()) + OFFSET_HITBOX_Y;
        return new Rectangle(x, y, ANCHO_HITBOX, ALTO_HITBOX);
    }

    public void recolectarLlave() {
        if (estaViva() && llaves < 3) {
            llaves++;
        }
    }

    public void saltar() {
        if (estaViva() && !enElAire) {
            // En Swing, una velocidad Y negativa mueve hacia arriba.
            velocidadY = -FUERZA_SALTO;
            enElAire = true;
        }
    }

    public void actualizarMovimientoVertical() {
        if (estaViva() && enElAire) {
            velocidadY += GRAVEDAD;
            moverVertical(velocidadY);
        }
    }

    public void iniciarCaida() {
        if (estaViva() && !enElAire) {
            velocidadY = 0;
            enElAire = true;
        }
    }

    public void aterrizar(double posicionY) {
        fijarPosicionY(posicionY);
        velocidadY = 0;
        enElAire = false;
    }

    public void golpearTecho(double posicionY) {
        fijarPosicionY(posicionY);
        velocidadY = 0;
        enElAire = true;
    }

    public void corregirPosicionX(int posicionX) {
        fijarPosicionX(posicionX);
    }

    public abstract void usarHabilidad();
}

/*
 * Criterio aplicado: Personaje implementa Colisionable de forma explícita. La
 * hitbox es menor que el sprite para que el contacto se sienta claro y tolerante.
 */

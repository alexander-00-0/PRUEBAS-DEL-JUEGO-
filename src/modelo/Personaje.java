package modelo;

public abstract class Personaje extends Entidad {
    private static final double GRAVEDAD = 0.6;
    private static final double FUERZA_SALTO = 14.5; //esto cambia la fuerza, con la que salta

    private int llaves;
    private boolean enElAire;
    private double velocidadY;

    protected Personaje(String nombre, int vida, Arma arma, int velocidad) {
        super(nombre, vida, arma, velocidad);
    }

    public int getLlaves() { return llaves; }
    public boolean estaEnElAire() { return enElAire; }
    public boolean tieneTodasLasLlaves() { return llaves == 3; }

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

    public void aterrizar(double posicionY) {
        fijarPosicionY(posicionY);
        velocidadY = 0;
        enElAire = false;
    }

    public abstract void usarHabilidad();
}

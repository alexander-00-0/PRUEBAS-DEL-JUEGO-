package modelo;

public abstract class Entidad {
    private final String nombre;
    private int vida;
    private final Arma arma;
    private int posicionX;
    private double posicionY;
    private final int velocidad;

    protected Entidad(String nombre, int vida, Arma arma, int velocidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (vida <= 0 || arma == null || velocidad <= 0) {
            throw new IllegalArgumentException("Vida y velocidad positivas; arma obligatoria.");
        }
        this.nombre = nombre.trim();
        this.vida = vida;
        this.arma = arma;
        this.velocidad = velocidad;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public Arma getArma() { return arma; }
    public int getPosicionX() { return posicionX; }
    public double getPosicionY() { return posicionY; }
    public int getVelocidad() { return velocidad; }
    public boolean estaViva() { return vida > 0; }

    public void ubicar(int posicionX, double posicionY) {
        if (posicionX < 0 || posicionY < 0) {
            throw new IllegalArgumentException("La posicion inicial no puede ser negativa.");
        }
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public void recibirDanio(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("El danio no puede ser negativo.");
        }
        vida = Math.max(0, vida - cantidad);
    }

    protected boolean puedeAtacar(Entidad objetivo) {
        if (objetivo == null) {
            throw new IllegalArgumentException("El objetivo no puede ser nulo.");
        }
        return estaViva() && objetivo.estaViva() && objetivo != this;
    }

    public void atacar(Entidad objetivo) {
        if (puedeAtacar(objetivo)) {
            objetivo.recibirDanio(arma.getDanio());
        }
    }

    public void avanzar() {
        moverHorizontal(1);
    }

    public void moverHorizontal(int direccion) {
        if (direccion < -1 || direccion > 1) {
            throw new IllegalArgumentException("La direccion debe ser -1, 0 o 1.");
        }
        if (estaViva()) {
            posicionX = Math.max(0, posicionX + direccion * velocidad);
        }
    }

    // Las subclases controlan su movimiento vertical sin exponer setters.
    protected void moverVertical(double desplazamiento) {
        posicionY += desplazamiento;
    }

    protected void fijarPosicionX(int posicionX) {
        this.posicionX = Math.max(0, posicionX);
    }

    protected void fijarPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    @Override
    public String toString() {
        return nombre + " | vida=" + vida + " | x=" + posicionX + " | y="
                + Math.round(posicionY) + " | arma=" + arma;
    }
}

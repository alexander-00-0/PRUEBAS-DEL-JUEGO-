package modelo;

public class Arma {
    private final String nombre;
    private final int danio;

    public Arma(String nombre, int danio) {
        if (nombre == null || nombre.trim().isEmpty() || danio <= 0) {
            throw new IllegalArgumentException("El arma necesita nombre y danio positivo.");
        }
        this.nombre = nombre.trim();
        this.danio = danio;
    }

    public String getNombre() { return nombre; }
    public int getDanio() { return danio; }

    @Override
    public String toString() { return nombre + " (danio: " + danio + ")"; }
}

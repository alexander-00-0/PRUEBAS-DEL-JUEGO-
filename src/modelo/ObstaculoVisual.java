package modelo;

// Representa una plataforma visible. En esta etapa todavia no produce colisiones.
public class ObstaculoVisual {
    private final int x;
    private final int y;
    private final int ancho;
    private final int alto;

    public ObstaculoVisual(int x, int y, int ancho, int alto) {
        if (x < 0 || y < 0 || ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("El obstaculo necesita posicion y tamanio validos.");
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
}

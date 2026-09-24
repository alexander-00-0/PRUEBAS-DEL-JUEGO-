package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import modelo.Nivel;
import modelo.Plataforma;
import modelo.Personaje;

public class PanelJuego extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int ANCHO_MUNDO = Nivel.ANCHO;
    public static final int ALTO_MUNDO = Nivel.ALTO;
    public static final int ANCHO_PERSONAJE = Personaje.ANCHO_VISUAL;
    public static final int ALTO_PERSONAJE = Personaje.ALTO_VISUAL;

    private static final String RUTA_MAPA = "src/assets/mapas/mapa_nivel_1.jpeg";
    private static final Color COLOR_CIELO = new Color(247, 247, 247);

    private final Personaje jugador;
    private final Nivel nivel;
    private final BufferedImage mapa;
    private final SpritePersonaje sprites;

    private boolean mirandoDerecha = true;
    private boolean enMovimiento;
    private int fotograma;
    private int contadorAnimacion;

    public PanelJuego(Personaje jugador, Nivel nivel) {
        if (jugador == null || nivel == null) {
            throw new IllegalArgumentException("Jugador y nivel son obligatorios.");
        }
        this.jugador = jugador;
        this.nivel = nivel;
        this.mapa = cargarMapa();
        this.sprites = SpritePersonaje.para(jugador);

        setPreferredSize(new Dimension(1280, 720));
        setBackground(COLOR_CIELO);
        setFocusable(true);
    }

    // El controlador informa la dirección; la vista elige el cuadro visual.
    public void actualizarMovimiento(int direccion) {
        enMovimiento = direccion != 0;

        if (direccion != 0) {
            mirandoDerecha = direccion > 0;
        }

        if (enMovimiento && !jugador.estaEnElAire()) {
            contadorAnimacion++;
            if (contadorAnimacion >= 8) {
                fotograma = (fotograma + 1) % 2;
                contadorAnimacion = 0;
            }
        } else {
            fotograma = 0;
            contadorAnimacion = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        double escala = calcularEscala();
        int anchoEscalado = (int) Math.round(ANCHO_MUNDO * escala);
        int altoEscalado = (int) Math.round(ALTO_MUNDO * escala);
        int origenX = (getWidth() - anchoEscalado) / 2;
        int origenY = getHeight() - altoEscalado;

        dibujarMapa(g2, origenX, origenY, anchoEscalado, altoEscalado);
        dibujarPlataformas(g2, escala, origenX, origenY);
        dibujarJugador(g2, escala, origenX, origenY);
        dibujarHitboxJugador(g2, escala, origenX, origenY);
        dibujarAyuda(g2);

        g2.dispose();
    }

    private double calcularEscala() {
        double escalaHorizontal = getWidth() / (double) ANCHO_MUNDO;
        double escalaVertical = getHeight() / (double) ALTO_MUNDO;

        // La misma escala en ambos ejes evita deformar el mapa.
        return Math.min(escalaHorizontal, escalaVertical);
    }

    private BufferedImage cargarMapa() {
        try {
            BufferedImage imagen = ImageIO.read(new File(RUTA_MAPA));
            if (imagen == null) {
                throw new IOException("El archivo no contiene una imagen válida");
            }
            return imagen;
        } catch (IOException error) {
            throw new IllegalStateException("No se pudo cargar " + RUTA_MAPA, error);
        }
    }

    private void dibujarMapa(Graphics2D g2, int x, int y, int ancho, int alto) {
        g2.drawImage(mapa, x, y, ancho, alto, null);
    }

    private void dibujarJugador(Graphics2D g2, double escala, int origenX, int origenY) {
        BufferedImage sprite = sprites.obtener(
                jugador.estaEnElAire(), enMovimiento, fotograma);

        int anchoCaja = (int) Math.round(ANCHO_PERSONAJE * escala);
        int altoCaja = (int) Math.round(ALTO_PERSONAJE * escala);
        double escalaSprite = Math.min(
                anchoCaja / (double) sprite.getWidth(),
                altoCaja / (double) sprite.getHeight());
        int anchoSprite = (int) Math.round(sprite.getWidth() * escalaSprite);
        int altoSprite = (int) Math.round(sprite.getHeight() * escalaSprite);

        int xCaja = origenX + (int) Math.round(jugador.getPosicionX() * escala);
        int yCaja = origenY + (int) Math.round(jugador.getPosicionY() * escala);
        int xSprite = xCaja + (anchoCaja - anchoSprite) / 2;
        int ySprite = yCaja + altoCaja - altoSprite;

        if (mirandoDerecha) {
            g2.drawImage(sprite, xSprite, ySprite, anchoSprite, altoSprite, null);
        } else {
            // El ancho negativo refleja el sprite sin duplicar archivos.
            g2.drawImage(sprite, xSprite + anchoSprite, ySprite,
                    -anchoSprite, altoSprite, null);
        }
    }

    private void dibujarPlataformas(Graphics2D g2, double escala, int origenX, int origenY) {
        for (Plataforma plataforma : nivel.getPlataformas()) {
            Rectangle hitbox = plataforma.getHitbox();
            int x = origenX + (int) Math.round(hitbox.x * escala);
            int y = origenY + (int) Math.round(hitbox.y * escala);
            int ancho = Math.max(1, (int) Math.round(hitbox.width * escala));
            int alto = Math.max(1, (int) Math.round(hitbox.height * escala));

            g2.setColor(new Color(35, 205, 95, 45));
            g2.fillRect(x, y, ancho, alto);
            g2.setColor(new Color(20, 150, 70, 220));
            g2.drawRect(x, y, ancho, alto);
        }
    }

    private void dibujarHitboxJugador(Graphics2D g2, double escala,
            int origenX, int origenY) {
        Rectangle hitbox = jugador.getHitbox();
        int x = origenX + (int) Math.round(hitbox.x * escala);
        int y = origenY + (int) Math.round(hitbox.y * escala);
        int ancho = Math.max(1, (int) Math.round(hitbox.width * escala));
        int alto = Math.max(1, (int) Math.round(hitbox.height * escala));

        g2.setColor(new Color(230, 45, 55, 55));
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(new Color(210, 25, 35, 230));
        g2.drawRect(x, y, ancho, alto);
    }

    private void dibujarAyuda(Graphics2D g2) {
        g2.setColor(new Color(5, 14, 24, 205));
        g2.fillRoundRect(15, 15, 535, 62, 14, 14);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g2.drawString(jugador.getNombre().toUpperCase()
                + " | PERSONAJE: 48 x 72 | MAPA: 1600 x 481", 30, 40);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        g2.drawString("Mover: flechas o A/D | Saltar: espacio | Verde: plataformas | Rojo: hitbox",
                30, 62);
    }
}

/*
 * Criterio aplicado: PanelJuego representa el estado del modelo, conserva la
 * escala del mapa y muestra las hitboxes como ayuda temporal de depuración.
 */

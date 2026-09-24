package vista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import modelo.Personaje;
import modelo.PersonajeAgil;
import modelo.PersonajeTanque;
import modelo.PersonajeTirador;

public final class SpritePersonaje {
    private final BufferedImage quieto;
    private final BufferedImage caminando1;
    private final BufferedImage caminando2;
    private final BufferedImage saltando;

    private SpritePersonaje(String carpeta) {
        quieto = cargar(carpeta + "/quieto.png");
        caminando1 = cargar(carpeta + "/caminando_1.png");
        caminando2 = cargar(carpeta + "/caminando_2.png");
        saltando = cargar(carpeta + "/saltando.png");
    }

    // El tipo concreto del modelo determina el conjunto de sprites.
    public static SpritePersonaje para(Personaje jugador) {
        if (jugador instanceof PersonajeAgil) {
            return new SpritePersonaje("src/assets/sprites/agil");
        }
        if (jugador instanceof PersonajeTirador) {
            return new SpritePersonaje("src/assets/sprites/tirador");
        }
        if (jugador instanceof PersonajeTanque) {
            return new SpritePersonaje("src/assets/sprites/tanque");
        }
        throw new IllegalArgumentException("No hay sprites para "
                + jugador.getClass().getSimpleName());
    }

    public BufferedImage obtener(boolean enElAire, boolean enMovimiento, int fotograma) {
        if (enElAire) {
            return saltando;
        }
        if (enMovimiento) {
            return fotograma == 0 ? caminando1 : caminando2;
        }
        return quieto;
    }

    private static BufferedImage cargar(String ruta) {
        try {
            BufferedImage imagen = ImageIO.read(new File(ruta));
            if (imagen == null) {
                throw new IOException("El archivo no contiene una imagen válida");
            }
            return imagen;
        } catch (IOException error) {
            throw new IllegalStateException("No se pudo cargar " + ruta, error);
        }
    }
}

/*
 * Criterio aplicado: esta clase concentra los recursos visuales para que
 * Personaje conserve únicamente su estado y comportamiento del modelo.
 */

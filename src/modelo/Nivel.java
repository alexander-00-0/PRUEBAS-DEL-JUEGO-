package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nivel {
    public static final int ANCHO = 1600;
    public static final int ALTO = 481;

    private final List<Plataforma> plataformas;
    private final int posicionInicialX;
    private final int posicionInicialY;

    private Nivel(List<Plataforma> plataformas, int posicionInicialX, int posicionInicialY) {
        this.plataformas = Collections.unmodifiableList(new ArrayList<>(plataformas));
        this.posicionInicialX = posicionInicialX;
        this.posicionInicialY = posicionInicialY;
    }

    public static Nivel crearNivelUno() {
        List<Plataforma> plataformas = new ArrayList<>();

        // Suelos alineados con los bloques inferiores del mapa.
        plataformas.add(new Plataforma(0, 383, 224, 98));
        plataformas.add(new Plataforma(224, 447, 96, 34));
        plataformas.add(new Plataforma(320, 383, 128, 98));
        plataformas.add(new Plataforma(448, 447, 64, 34));
        plataformas.add(new Plataforma(512, 383, 128, 98));
        plataformas.add(new Plataforma(640, 447, 32, 34));
        plataformas.add(new Plataforma(672, 383, 384, 98));
        plataformas.add(new Plataforma(1152, 383, 448, 98));

        // Plataformas flotantes ubicadas sobre los dibujos visibles.
        plataformas.add(new Plataforma(32, 290, 32, 29));
        plataformas.add(new Plataforma(224, 290, 96, 29));
        plataformas.add(new Plataforma(448, 322, 64, 29));
        plataformas.add(new Plataforma(640, 290, 32, 29));
        plataformas.add(new Plataforma(768, 319, 32, 32));
        plataformas.add(new Plataforma(864, 255, 32, 32));
        plataformas.add(new Plataforma(992, 287, 32, 32));
        plataformas.add(new Plataforma(1152, 319, 32, 32));
        plataformas.add(new Plataforma(1248, 319, 32, 32));

        int posicionInicialY = 383 - Personaje.ALTO_VISUAL;
        return new Nivel(plataformas, 80, posicionInicialY);
    }

    public List<Plataforma> getPlataformas() { return plataformas; }
    public int getPosicionInicialX() { return posicionInicialX; }
    public int getPosicionInicialY() { return posicionInicialY; }
    public int getAncho() { return ANCHO; }
    public int getAlto() { return ALTO; }
}

/*
 * Criterio aplicado: Nivel reúne la configuración física del escenario. La imagen
 * sigue siendo visual, mientras esta lista expresa dónde se puede caminar o chocar.
 */

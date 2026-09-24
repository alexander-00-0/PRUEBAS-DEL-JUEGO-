package controlador; 

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import modelo.Nivel;
import modelo.Plataforma;
import modelo.Personaje;
import vista.PanelJuego;

public class ControladorJuego {
    private static final int INTERVALO_MS = 16;

    private final Personaje jugador;
    private final PanelJuego vista;
    private final Nivel nivel;
    private final Timer timer;
    private boolean izquierdaPresionada;
    private boolean derechaPresionada;

    public ControladorJuego(Personaje jugador, PanelJuego vista, Nivel nivel) {
        if (jugador == null || vista == null || nivel == null) {
            throw new IllegalArgumentException("Jugador, vista y nivel son obligatorios.");
        }
        this.jugador = jugador;
        this.vista = vista;
        this.nivel = nivel;
        // El Timer funciona como ciclo de actualizacion del prototipo.
        this.timer = new Timer(INTERVALO_MS, evento -> actualizarJuego());
        registrarTeclado();
    }

    public void iniciar() {
        vista.requestFocusInWindow();
        timer.start();
    }

    private void registrarTeclado() {
        vista.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                cambiarEstadoTecla(evento.getKeyCode(), true);
                if (evento.getKeyCode() == KeyEvent.VK_SPACE) {
                    jugador.saltar();
                }
            }

            @Override
            public void keyReleased(KeyEvent evento) {
                cambiarEstadoTecla(evento.getKeyCode(), false);
            }
        });
    }

    private void cambiarEstadoTecla(int tecla, boolean presionada) {
        if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) {
            izquierdaPresionada = presionada;
        }
        if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) {
            derechaPresionada = presionada;
        }
    }

    private void actualizarJuego() {
        int direccion = (derechaPresionada ? 1 : 0) - (izquierdaPresionada ? 1 : 0);
        moverHorizontal(direccion);

        // La vista recibe el estado para elegir direccion y cuadro de animacion.
        vista.actualizarMovimiento(direccion);

        actualizarMovimientoVertical();

        if (jugador.getPosicionY() > nivel.getAlto() + Personaje.ALTO_VISUAL) {
            reiniciarJugador();
        }

        vista.repaint();
    }

    private void moverHorizontal(int direccion) {
        if (direccion == 0) {
            return;
        }

        jugador.moverHorizontal(direccion);
        int limiteDerecho = nivel.getAncho() - Personaje.ANCHO_VISUAL;

        if (jugador.getPosicionX() > limiteDerecho) {
            jugador.corregirPosicionX(limiteDerecho);
        }

        for (Plataforma plataforma : nivel.getPlataformas()) {
            if (jugador.colisionaCon(plataforma)) {
                corregirChoqueHorizontal(direccion, plataforma.getHitbox());
                break;
            }
        }
    }

    private void corregirChoqueHorizontal(int direccion, Rectangle obstaculo) {
        int posicionX;
        if (direccion > 0) {
            posicionX = obstaculo.x - Personaje.OFFSET_HITBOX_X
                    - Personaje.ANCHO_HITBOX;
        } else {
            posicionX = obstaculo.x + obstaculo.width - Personaje.OFFSET_HITBOX_X;
        }
        jugador.corregirPosicionX(posicionX);
    }

    private void actualizarMovimientoVertical() {
        if (!jugador.estaEnElAire() && !estaApoyado()) {
            jugador.iniciarCaida();
        }

        Rectangle hitboxAnterior = jugador.getHitbox();
        jugador.actualizarMovimientoVertical();
        Rectangle hitboxActual = jugador.getHitbox();

        for (Plataforma plataforma : nivel.getPlataformas()) {
            Rectangle obstaculo = plataforma.getHitbox();

            if (jugador.getVelocidadY() >= 0
                    && cruzaParteSuperior(hitboxAnterior, hitboxActual, obstaculo)) {
                double y = obstaculo.y - Personaje.OFFSET_HITBOX_Y
                        - Personaje.ALTO_HITBOX;
                jugador.aterrizar(y);
                return;
            }

            if (jugador.getVelocidadY() < 0
                    && cruzaParteInferior(hitboxAnterior, hitboxActual, obstaculo)) {
                double y = obstaculo.y + obstaculo.height - Personaje.OFFSET_HITBOX_Y;
                jugador.golpearTecho(y);
                return;
            }
        }
    }

    private boolean estaApoyado() {
        Rectangle hitbox = jugador.getHitbox();
        Rectangle pies = new Rectangle(hitbox.x, hitbox.y + hitbox.height,
                hitbox.width, 2);

        for (Plataforma plataforma : nivel.getPlataformas()) {
            if (pies.intersects(plataforma.getHitbox())) {
                return true;
            }
        }
        return false;
    }

    private boolean cruzaParteSuperior(Rectangle anterior, Rectangle actual,
            Rectangle obstaculo) {
        return seSuperponenHorizontalmente(actual, obstaculo)
                && anterior.y + anterior.height <= obstaculo.y
                && actual.y + actual.height >= obstaculo.y;
    }

    private boolean cruzaParteInferior(Rectangle anterior, Rectangle actual,
            Rectangle obstaculo) {
        int parteInferior = obstaculo.y + obstaculo.height;
        return seSuperponenHorizontalmente(actual, obstaculo)
                && anterior.y >= parteInferior
                && actual.y <= parteInferior;
    }

    private boolean seSuperponenHorizontalmente(Rectangle primero, Rectangle segundo) {
        return primero.x < segundo.x + segundo.width
                && primero.x + primero.width > segundo.x;
    }

    private void reiniciarJugador() {
        jugador.ubicar(nivel.getPosicionInicialX(), nivel.getPosicionInicialY());
        jugador.aterrizar(nivel.getPosicionInicialY());
    }
}

/*
 * Criterio aplicado: el controlador coordina entrada, física y colisiones por
 * ejes. El modelo guarda el estado y la vista solo representa el resultado.
 */

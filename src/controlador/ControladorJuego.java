package controlador; 

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import modelo.Personaje;
import vista.PanelJuego;

public class ControladorJuego {
    private static final int INTERVALO_MS = 16;

    private final Personaje jugador;
    private final PanelJuego vista;
    private final Timer timer;
    private boolean izquierdaPresionada;
    private boolean derechaPresionada;

    public ControladorJuego(Personaje jugador, PanelJuego vista) {
        this.jugador = jugador;
        this.vista = vista;
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
        int limiteDerecho = PanelJuego.ANCHO_MUNDO - PanelJuego.ANCHO_PERSONAJE;

        if ((direccion < 0 && jugador.getPosicionX() > 0)
                || (direccion > 0
                        && jugador.getPosicionX() + jugador.getVelocidad() <= limiteDerecho)) {
            jugador.moverHorizontal(direccion);
        }

        // La vista recibe el estado para elegir direccion y cuadro de animacion.
        vista.actualizarMovimiento(direccion);

        jugador.actualizarMovimientoVertical();

        double posicionSobreSuelo = PanelJuego.Y_SUELO - PanelJuego.ALTO_PERSONAJE;
        if (jugador.getPosicionY() >= posicionSobreSuelo) {
            jugador.aterrizar(posicionSobreSuelo);
        }

        vista.repaint();
    }
}

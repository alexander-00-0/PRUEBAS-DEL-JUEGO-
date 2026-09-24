package controlador;

import modelo.Personaje;
import modelo.PersonajeAgil;
import modelo.PersonajeTanque;
import modelo.PersonajeTirador;
import vista.PanelJuego;
import vista.VistaMenu;

public class ControladorMenu {
    private final VistaMenu vista;
    private String personajeSeleccionado = "Agil";
    private ControladorJuego controladorJuego;

    public ControladorMenu(VistaMenu vista) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista es obligatoria");
        }
        this.vista = vista;

        // Cada botón comunica una intención al controlador.
        vista.getBtnAgil().addActionListener(evento -> seleccionarPersonaje("Agil"));
        vista.getBtnTirador().addActionListener(evento -> seleccionarPersonaje("Tirador"));
        vista.getBtnTanque().addActionListener(evento -> seleccionarPersonaje("Tanque"));
        vista.getBtnPlay().addActionListener(evento -> iniciarJuego());

        vista.marcarSeleccion(personajeSeleccionado);
    }

    private void seleccionarPersonaje(String personaje) {
        personajeSeleccionado = personaje;
        vista.marcarSeleccion(personaje);
    }

    private void iniciarJuego() {
        Personaje jugador = crearPersonaje();
        double posicionInicialY = PanelJuego.Y_SUELO - PanelJuego.ALTO_PERSONAJE;
        jugador.ubicar(80, posicionInicialY);

        PanelJuego panelJuego = new PanelJuego(jugador);
        controladorJuego = new ControladorJuego(jugador, panelJuego);

        vista.mostrarJuego(panelJuego);
        controladorJuego.iniciar();
    }

    private Personaje crearPersonaje() {
        switch (personajeSeleccionado) {
            case "Tirador":
                return new PersonajeTirador("Tirador");
            case "Tanque":
                return new PersonajeTanque("Tanque");
            default:
                return new PersonajeAgil("Agil");
        }
    }
}

/*
 * Criterio aplicado: el controlador recibe los eventos del menú, crea el modelo
 * seleccionado y conecta ese objeto con la vista y el controlador del juego.
 */

package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VistaMenu {
    private static final String RUTA_FONDO = "src/assets/menu/fondo_menu.png";
    private static final String RUTA_PLAY = "src/assets/menu/btn_play.png";
    private static final String RUTA_AGIL = "src/assets/menu/btn_agil.png";
    private static final String RUTA_TIRADOR = "src/assets/menu/btn_tirador.png";
    private static final String RUTA_TANQUE = "src/assets/menu/btn_tanque.png";

    private final JFrame ventana;
    private final JButton btnPlay;
    private final JButton btnTirador;
    private final JButton btnAgil;
    private final JButton btnTanque;
    private final BufferedImage imagenFondo;

    public VistaMenu() {
        ventana = new JFrame("Velocity Rush - The Boss's Awakening");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1280, 720);

        imagenFondo = cargarImagen(RUTA_FONDO);

        JPanel panelFondo = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        btnPlay = crearBotonGrafico(RUTA_PLAY, "Jugar", 260, 90);
        btnTirador = crearBotonGrafico(RUTA_TIRADOR, "Tirador", 280, 170);
        btnAgil = crearBotonGrafico(RUTA_AGIL, "Agil", 280, 170);
        btnTanque = crearBotonGrafico(RUTA_TANQUE, "Tanque", 280, 170);

        JPanel panelPlay = new JPanel(new GridBagLayout());
        panelPlay.setOpaque(false);
        panelPlay.add(btnPlay);

        JPanel panelPersonajes = new JPanel(new GridLayout(1, 3, 20, 0));
        panelPersonajes.setOpaque(false);
        panelPersonajes.setBorder(BorderFactory.createEmptyBorder(0, 60, 35, 60));
        panelPersonajes.add(btnTirador);
        panelPersonajes.add(btnAgil);
        panelPersonajes.add(btnTanque);

        panelFondo.add(panelPlay, BorderLayout.CENTER);
        panelFondo.add(panelPersonajes, BorderLayout.SOUTH);

        ventana.setContentPane(panelFondo);
        ventana.setLocationRelativeTo(null);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private JButton crearBotonGrafico(String ruta, String nombre, int ancho, int alto) {
        BufferedImage imagen = cargarImagen(ruta);
        Image escalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        JButton boton = new JButton(new ImageIcon(escalada));
        boton.setToolTipText(nombre);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        return boton;
    }

    private BufferedImage cargarImagen(String ruta) {
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

    public void marcarSeleccion(String personaje) {
        marcarBoton(btnAgil, "Agil".equals(personaje));
        marcarBoton(btnTirador, "Tirador".equals(personaje));
        marcarBoton(btnTanque, "Tanque".equals(personaje));
    }

    private void marcarBoton(JButton boton, boolean seleccionado) {
        if (seleccionado) {
            boton.setBorder(BorderFactory.createLineBorder(new Color(255, 210, 40), 4));
        } else {
            boton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        }
    }

    public void mostrarJuego(PanelJuego panelJuego) {
        ventana.setContentPane(panelJuego);
        ventana.revalidate();
        ventana.repaint();

        // El foco permite que PanelJuego reciba el teclado después del cambio de vista.
        SwingUtilities.invokeLater(panelJuego::requestFocusInWindow);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }

    public JButton getBtnTirador() {
        return btnTirador;
    }

    public JButton getBtnAgil() {
        return btnAgil;
    }

    public JButton getBtnTanque() {
        return btnTanque;
    }
}

/*
 * Criterio aplicado: la vista solamente dibuja el menú, muestra la selección
 * y cambia el contenido visual de la ventana cuando el controlador lo solicita.
 */

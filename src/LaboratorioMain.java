import javax.swing.SwingUtilities;
import controlador.ControladorMenu;
import vista.VistaMenu;

public class LaboratorioMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // El punto de entrada construye y conecta vista y controlador.
            VistaMenu vista = new VistaMenu();
            new ControladorMenu(vista);
            vista.mostrar();
        });
    }
}

/*
 * Criterio aplicado: esta clase solo inicia la aplicación.
 * La lógica de selección queda en el controlador y el dibujo en las vistas.
 */

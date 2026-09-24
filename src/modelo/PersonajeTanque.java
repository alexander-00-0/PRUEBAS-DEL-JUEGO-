package modelo;

public class PersonajeTanque extends Personaje {
    private boolean escudoActivo;

    public PersonajeTanque(String nombre) {
        super(nombre, 160, new Arma("Canion", 20), 1);
    }

    public boolean tieneEscudoActivo() { return escudoActivo; }

    @Override
    public void usarHabilidad() {
        if (estaViva()) {
            escudoActivo = true;
        }
    }

    // Dura un impacto positivo. El danio impar se redondea hacia abajo,
    // con minimo 1. Danio cero no consume el escudo.
    @Override
    public void recibirDanio(int cantidad) {
        if (escudoActivo && estaViva() && cantidad > 0) {
            super.recibirDanio(Math.max(1, cantidad / 2));
            escudoActivo = false;
        } else {
            super.recibirDanio(cantidad);
        }
    }
}

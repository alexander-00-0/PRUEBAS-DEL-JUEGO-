package modelo;

public class PersonajeTirador extends Personaje {
    private boolean rafagaPreparada;

    public PersonajeTirador(String nombre) {
        super(nombre, 100, new Arma("Rifle", 15), 2);
    }

    public boolean tieneRafagaPreparada() { return rafagaPreparada; }

    @Override
    public void usarHabilidad() {
        if (estaViva()) {
            rafagaPreparada = true;
        }
    }

    // Dura un ataque valido. Activar varias veces no acumula rafagas.
    @Override
    public void atacar(Entidad objetivo) {
        if (!puedeAtacar(objetivo)) {
            return;
        }
        super.atacar(objetivo);
        if (rafagaPreparada) {
            super.atacar(objetivo);
            rafagaPreparada = false;
        }
    }
}

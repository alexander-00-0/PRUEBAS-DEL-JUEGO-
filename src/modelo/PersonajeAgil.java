package modelo;

public class PersonajeAgil extends Personaje {
    public PersonajeAgil(String nombre) {
        super(nombre, 80, new Arma("Pistola ligera", 10), 3);
    }


    @Override
    public void usarHabilidad() {
        avanzar();
       // avanzar();
    }
}

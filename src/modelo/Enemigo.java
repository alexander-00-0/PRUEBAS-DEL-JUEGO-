package modelo;

public abstract class Enemigo extends Entidad {
    protected Enemigo(String nombre, int vida, Arma arma, int velocidad) {
        super(nombre, vida, arma, velocidad);
    }
}

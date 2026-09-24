package modelo;

public class Boss extends Enemigo {
    public Boss(String nombre) {
        super(nombre, 180, new Arma("Lanzador pesado", 24), 1);
    }
    // Hereda atacar: mas vida y otra arma no requieren copiar ese metodo.
}

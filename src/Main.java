
import modelo.PersonajeAgil;
import modelo.PersonajeTirador;
import modelo.PersonajeTanque;
import modelo.Entidad;
import modelo.Boss;

public class Main {
    public static void main(String[] args) {
        PersonajeAgil agil = new PersonajeAgil("agil");
        PersonajeTirador tirador = new PersonajeTirador("Tirador");
        PersonajeTanque tanque = new PersonajeTanque("Tanque");
        Entidad boss = new Boss("Boss");
        
        tirador.usarHabilidad();
        tanque.usarHabilidad();

        Entidad[] equipo = {agil, tirador, tanque};
        int ronda = 1;

        while (boss.estaViva() && haySobrevivientes(equipo)) {
            System.out.println("\nRONDA " + ronda);
            for (Entidad atacante : equipo) {
                if (atacante.estaViva() && boss.estaViva()) {
                    int vidaAntes = boss.getVida();

                    // POLIMORFISMO: 
                    // agil y Tanque heredan el ataque simple de Entidad.
                    // Tirador ejecuta su sobrescritura: dos disparos si preparo rafaga.
                    atacante.atacar(boss);

                    System.out.println(atacante.getNombre() + " causo "
                            + (vidaAntes - boss.getVida()) + " de danio. Boss: "
                            + vidaAntes + " -> " + boss.getVida());

                    if (boss.estaViva()) {
                        int vidaAtacante = atacante.getVida();
                        
                        boss.atacar(atacante);
                        System.out.println("Boss responde a " + atacante.getNombre()
                                + ": " + vidaAtacante + " -> " + atacante.getVida());
                    }
                }
            }
            ronda++;
        }
        System.out.println(boss.estaViva() ? "\nEquipo derrotado." : "\nBoss superado.");
    }

    // Solo consulta estado. No pregunta de que clase es cada objeto.
    private static boolean haySobrevivientes(Entidad[] equipo) {
        for (Entidad entidad : equipo) {
            if (entidad.estaViva()) return true;
    }
        return false;
    }
}

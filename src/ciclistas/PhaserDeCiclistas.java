package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;

public class PhaserDeCiclistas extends Phaser {
    public static final int FASE_CASA_GASOLINERA = 0;
    public static final int FASE_GASOLINERA_TIENDA = 1;
    public static final int FASE_TIENDA_GASOLINERA = 2;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case FASE_CASA_GASOLINERA:
                System.out.printf("%s -> Hemos llegado a la gasolinea, vamos a la tienda. (ejecutado en %s)\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
                break;
            case FASE_GASOLINERA_TIENDA:
                System.out.printf("%s -> Hemos llegado a la tienda, vamos a la gasolinera. (ejecutado en %s)\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
                break;
            case FASE_TIENDA_GASOLINERA:
                System.out.printf("%s -> Hemos llegado a la gasolinea, vamos a la casa. (ejecutado en %s)\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());

                return true;
        }
        return super.onAdvance(phase, registeredParties);
    }
}

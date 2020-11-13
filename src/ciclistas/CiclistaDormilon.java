package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CiclistaDormilon implements Runnable {
    private final String nombre;
    private final Phaser phaser;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CiclistaDormilon(String nombre, Phaser phaser) {
        this.nombre = nombre;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        if (!phaser.isTerminated()) {
            int joinPhase = phaser.register();
            System.out.printf("%s -> %s se acaba de levantar, va de camnino a la tienda con los demás. Ha empezado su camino en l fase: #%d\n", LocalTime.now().format(dateTimeFormatter), nombre, joinPhase);
            ir_de_casa_a_gasolinera(joinPhase);
            ir_de_gasolinera_a_tienda(joinPhase);
            volver_de_tienda_a_gasolinera(joinPhase);
            volver_de_gasolinera_a_casa(joinPhase);
        }else {
            System.out.printf("%s -> %s se ha perdido la quedada ciclista...\n", LocalTime.now().format(dateTimeFormatter), nombre);
        }
    }

    private void volver_de_gasolinera_a_casa(int joinPhase) {
        try {
            System.out.printf("%s -> %s ha salido de la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(3+1)));
            System.out.printf("%s -> %s ha vuelto a casa.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia su casa.\n", nombre);
            return;
        }
    }

    private void volver_de_tienda_a_gasolinera(int joinPhase) {
        try {
            System.out.printf("%s -> %s ha salido de la tienda.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(5+5)));
            System.out.printf("%s -> %s ha vuelto a la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia la gasolinera.\n", nombre);
            return;
        }
        if(joinPhase <= PhaserDeCiclistas.FASE_TIENDA_GASOLINERA){
            try {
                phaser.awaitAdvanceInterruptibly(phaser.arrive());
            } catch (InterruptedException e) {
                System.out.printf("%s ha sido interrumpido mientras esperaba que los demás llegaran a la gasolinera.\n", nombre);
                return;
            }
        }
    }

    private void ir_de_gasolinera_a_tienda(int joinPhase) {
        try {
            System.out.printf("%s -> %s ha salido de la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(5+5)));
            System.out.printf("%s -> %s está en la tienda.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia la tienda.\n", nombre);
            return;
        }

        if(joinPhase <= PhaserDeCiclistas.FASE_GASOLINERA_TIENDA){
            try {
                phaser.awaitAdvanceInterruptibly(phaser.arrive());
            } catch (InterruptedException e) {
                System.out.printf("%s ha sido interrumpido mientras esperaba que los demás llegaran a la tienda.\n", nombre);
                return;
            }
        }
    }

    private void ir_de_casa_a_gasolinera(int joinPhase) {
        try {
            System.out.printf("%s -> %s ha salido de casa.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(3+1)));
            System.out.printf("%s -> %s está en la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia la gasolinera.\n", nombre);
            return;
        }

        if(joinPhase <= PhaserDeCiclistas.FASE_CASA_GASOLINERA){
            try {
                phaser.awaitAdvanceInterruptibly(phaser.arrive());
            } catch (InterruptedException e) {
                System.out.printf("%s ha sido interrumpido mientras esperaba que los demás llegaran a la gasolinera.\n", nombre);
                return;
            }
        }
    }
}

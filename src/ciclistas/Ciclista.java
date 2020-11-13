package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Ciclista implements Runnable {
    private final String nombre;
    private final Phaser phaser;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Ciclista(String nombre, Phaser phaser) {
        this.nombre = nombre;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        phaser.register();
        ir_de_casa_a_gasolinera();
        ir_de_gasolinera_a_tienda();
        volver_de_tienda_a_gasolinera();
        volver_de_gasolinera_a_casa();
    }

    private void volver_de_gasolinera_a_casa() {
        try {
            System.out.printf("%s -> %s ha salido de la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(3+1)));
            System.out.printf("%s -> %s ha vuelto a casa.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia su casa.\n", nombre);
            return;
        }
    }

    private void volver_de_tienda_a_gasolinera() {
        try {
            System.out.printf("%s -> %s ha salido de la tienda.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(5+5)));
            System.out.printf("%s -> %s ha vuelto a la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia la gasolinera.\n", nombre);
            return;
        }
        try {
            phaser.awaitAdvanceInterruptibly(phaser.arrive());
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras esperaba que los demás llegaran a la gasolinera.\n", nombre);
            return;
        }
    }

    private void ir_de_gasolinera_a_tienda() {
        try {
            System.out.printf("%s -> %s ha salido de la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(5+5)));
            System.out.printf("%s -> %s está en la tienda.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia la tienda.\n", nombre);
            return;
        }
        try {
            phaser.awaitAdvanceInterruptibly(phaser.arrive());
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras esperaba que los demás llegaran a la tienda.\n", nombre);
            return;
        }
    }

    private void ir_de_casa_a_gasolinera() {
        try {
            System.out.printf("%s -> %s ha salido de casa.\n", LocalTime.now().format(dateTimeFormatter), nombre);
            TimeUnit.SECONDS.sleep((ThreadLocalRandom.current().nextInt(3+1)));
            System.out.printf("%s -> %s está en la gasolinera.\n", LocalTime.now().format(dateTimeFormatter), nombre);
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras salía hacia la gasolinera.\n", nombre);
            return;
        }
        try {
            phaser.awaitAdvanceInterruptibly(phaser.arrive());
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras esperaba que los demás llegaran a la gasolinera.\n", nombre);
            return;
        }
    }


}

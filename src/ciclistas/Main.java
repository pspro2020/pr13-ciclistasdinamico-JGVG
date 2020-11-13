package ciclistas;

import java.util.concurrent.TimeUnit;

public class Main {
    private static final int NUM_CICLISTAS = 10;

    public static void main(String[] args) {
        int i;
        PhaserDeCiclistas phaser = new PhaserDeCiclistas();

        for(i = 0; i < NUM_CICLISTAS; i++){
            new Thread(new Ciclista("Ciclista #"+i, phaser), "Ciclista #"+i).start();
        }

        new Thread(new CiclistaImpaciente("CiclistaImpaciente #"+i, phaser), "CiclistaImpaciente #"+i).start();
        i++;

        try {
            TimeUnit.SECONDS.sleep(5);
            new Thread(new CiclistaDormilon("CiclistaDormilón #"+i, phaser), "CiclistaDormilón #"+i).start();
            i++;
            TimeUnit.SECONDS.sleep(30);
            new Thread(new CiclistaDormilon("CiclistaDespidado #"+i, phaser), "CiclistaDespidado #"+i).start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

package cy.olesiabokk.tradeisboomingapp.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Generator {
    private final Randomizer randomizer;


    public Generator(Randomizer randomizer){
        this.randomizer = randomizer;
    }

    public int addMaxCapacity(int from, int to){
        return randomizer.getRandomNum(from, to);
    }


}

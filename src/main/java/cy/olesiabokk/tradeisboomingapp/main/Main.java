package cy.olesiabokk.tradeisboomingapp.main;

import cy.olesiabokk.tradeisboomingapp.entity.*;
import cy.olesiabokk.tradeisboomingapp.util.Generator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {

        Generator generator = new Generator();

        ArrayList<Berth> berthList = new ArrayList<>();
        berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));

        Supervisor supervisor = new Supervisor();
        Port port = new Port(supervisor, berthList);

        Semaphore semaphore = new Semaphore(4);
        for (int i = 0; i < 10; i++) {
           Ship ship = new Ship(generator.addMaxCapacity(199, 501), generator.getRandomJob(), semaphore);
           new Thread(ship).start();
        }

    }
}

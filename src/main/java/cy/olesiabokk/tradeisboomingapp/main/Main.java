package cy.olesiabokk.tradeisboomingapp.main;

import cy.olesiabokk.tradeisboomingapp.entity.*;
import cy.olesiabokk.tradeisboomingapp.util.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Generator generator = new Generator();
        List<Berth> berthList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            berthList.add(new Berth(new Stock(generator.addMaxCapacity(4000, 10000))));
            berthList.get(i).setCurrStockAmount(generator.getRandomNum(0, berthList.get(i).getMaxStockCapacity()));
        }

        Supervisor supervisor = new Supervisor(berthList);
        Port port = new Port(supervisor, berthList);

        List<Ship> ships = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            ships.add(new Ship(generator.addMaxCapacity(200, 500), generator.getRandomJob(), supervisor));
            ships.get(i).setCurrentAmount(generator.getRandomNum(0, ships.get(i).getMaxCapacity()));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(45);
        for (Ship ship : ships) {
            executorService.submit(ship);
        }
        executorService.shutdown();
    }
}
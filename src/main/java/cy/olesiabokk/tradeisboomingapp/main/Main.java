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
        List<Ship> ships = new ArrayList<>();
        List<Berth> berthList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
            berthList.get(i).setCurrStockAmount(generator.getRandomNum(4000, berthList.get(i).getMaxStockCapacity()));
        }

        Supervisor supervisor = new Supervisor();
        supervisor.setBerthList(berthList);
        Port port = new Port(supervisor, berthList);

        for (int i = 0; i < 15; i++) {
            ships.add(new Ship(generator.addMaxCapacity(199, 501), generator.getRandomJob(), supervisor));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(15);
        for (Ship ship : ships) {
            executorService.submit(ship);
        }
        executorService.shutdown();
    }
}
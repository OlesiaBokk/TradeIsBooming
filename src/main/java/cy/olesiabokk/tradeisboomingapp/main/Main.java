package cy.olesiabokk.tradeisboomingapp.main;

import cy.olesiabokk.tradeisboomingapp.entity.*;
import cy.olesiabokk.tradeisboomingapp.util.Generator;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {

        Generator generator = new Generator();
        CyclicBarrier barrier = new CyclicBarrier(15);
        Ship[] ships = new Ship[15];


        ArrayList<Berth> berthList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
//            berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
//            berthList.get(i).setCurrStockAmount(generator.getRandomNum(4000, berthList.get(i).getMaxStockCapacity()));
            berthList.add(new Berth(new Stock(2000)));
            berthList.get(i).setCurrStockAmount(500);
        }
        //berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        //berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));

        Supervisor supervisor = new Supervisor();
        supervisor.setBerthList(berthList);
        Port port = new Port(supervisor, berthList);

        for (int i = 0; i < ships.length; i++) {
            ships[i] = new Ship(generator.addMaxCapacity(199, 501), generator.getRandomJob(), supervisor, barrier);
            ships[i].setCurrentAmount(generator.getRandomNum(200, ships[i].getMaxCapacity()));
            //ship.setCurrentAmount(20);
        }

        for (int i = 0; i < ships.length; i++) {
            Thread thread = new Thread(ships[i]);
            thread.start();
        }
    }
}
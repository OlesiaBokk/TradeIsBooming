package cy.olesiabokk.tradeisboomingapp.main;

import cy.olesiabokk.tradeisboomingapp.entity.*;
import cy.olesiabokk.tradeisboomingapp.util.Generator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Generator generator = new Generator();

        ArrayList<Berth> berthList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
            berthList.get(i).setCurrStockAmount(generator.getRandomNum(4000, berthList.get(i).getMaxStockCapacity()));
        }
        //berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        //berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));

        Supervisor supervisor = new Supervisor();
        supervisor.setBerthList(berthList);
        Port port = new Port(supervisor, berthList);

        //сделать локер для причалов, типа свободен\занят
        // запрашивать сколько места в берте в самом методе run
        // если есть место, выполняем джобку, если нет, освобождаем причал

        for (int i = 0; i < 2; i++) {
            Ship ship = new Ship(generator.addMaxCapacity(199, 501), generator.getRandomJob(), supervisor);
            ship.setCurrentAmount(generator.getRandomNum(200, ship.getMaxCapacity()));
            Thread thread = new Thread(ship);
            thread.start();
        }
    }
}
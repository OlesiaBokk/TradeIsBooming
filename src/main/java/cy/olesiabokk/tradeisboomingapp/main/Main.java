package cy.olesiabokk.tradeisboomingapp.main;

import cy.olesiabokk.tradeisboomingapp.entity.*;
import cy.olesiabokk.tradeisboomingapp.util.Generator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Generator generator = new Generator();

        ArrayList<Berth> berthList = new ArrayList<>();
        berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        //berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));
        //berthList.add(new Berth(new Stock(generator.addMaxCapacity(3999, 10001))));

        Supervisor supervisor = new Supervisor();
        supervisor.setBerthList(berthList);
        Port port = new Port(supervisor, berthList);

        //сделать локер для причалов, типа свободен\занят
        // запрашивать сколько места в берте в самом методе run
        // если есть место, выполняем джобку, если нет, освобождаем причал

        for (int i = 0; i < 4; i++) {
            Ship ship = new Ship(generator.addMaxCapacity(199, 501), generator.getRandomJob(), supervisor);
            Thread thread = new Thread(ship);
            thread.start();
        }
    }
}

package cy.olesiabokk.tradeisboomingapp.main;

import cy.olesiabokk.tradeisboomingapp.entity.*;
import cy.olesiabokk.tradeisboomingapp.util.Generator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Generator generator = new Generator();

        ArrayList<Dock> dockList = new ArrayList<>();
        dockList.add(new Dock(new Stock(generator.addMaxCapacity(3999, 10001))));
        dockList.add(new Dock(new Stock(generator.addMaxCapacity(3999, 10001))));
        dockList.add(new Dock(new Stock(generator.addMaxCapacity(3999, 10001))));
        dockList.add(new Dock(new Stock(generator.addMaxCapacity(3999, 10001))));

        Ship ship1 = new Ship(generator.addMaxCapacity(199, 501));
        Ship ship2 = new Ship(generator.addMaxCapacity(199, 501));
        Ship ship3 = new Ship(generator.addMaxCapacity(199, 501));
        Ship ship4 = new Ship(generator.addMaxCapacity(199, 501));

        Supervisor supervisor = new Supervisor();
        Port port = new Port(supervisor, dockList);

    }
}

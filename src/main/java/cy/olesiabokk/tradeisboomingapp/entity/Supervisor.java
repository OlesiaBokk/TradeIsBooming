package cy.olesiabokk.tradeisboomingapp.entity;

public class Supervisor {
    private final Port port;
    private Dock dock;
    private Ship ship;
    private Stock stock;

    public Supervisor(Port port) {
        this.port = port;
    }

    public Dock getDock() {
        return dock;
    }

    public Ship getShip() {
        return ship;
    }

    public Stock getStock() {
        return stock;
    }
}

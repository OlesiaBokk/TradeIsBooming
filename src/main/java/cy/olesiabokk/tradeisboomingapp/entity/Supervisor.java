package cy.olesiabokk.tradeisboomingapp.entity;

public class Supervisor {
    private Berth berth;
    private Ship ship;
    private Stock stock;

    public Long getDock() {
        return berth.getId();
    }

    public Ship getShip() {
        return ship;
    }

    public Stock getStock() {
        return stock;
    }

    public int getCurrentStockAmount(){
        return berth.getCurrentStockAmount();
    }

    public int getAvailStockPlace(){
        return berth.getAvailPlace();
    }

    public int getAvailShipCapacity(){
        return ship.getAvailablePlace();
    }
}

package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.List;

public class Supervisor {
    private List<Berth> berthList;
    private Berth berth;
    private Ship ship;
    private Stock stock;

    public List<Berth> getBerthList() {
        return berthList;
    }

    public void setBerthList(List<Berth> list){
        this.berthList = list;
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

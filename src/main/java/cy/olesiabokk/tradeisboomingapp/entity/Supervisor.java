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

    public Berth getBerth(){
        return berth;
    }

    public Long getBertId(){
        return berth.getId();
    }

    public Ship getShip() {
        return ship;
    }

    public Long getShipId(){
        return ship.getShipId();
    }

    public int getAvailStockPlace(){
        return berth.getAvailPlace();
    }

    public int getCurrentStockAmount(){
        return berth.getCurrentStockAmount();
    }

    public boolean needLoading(){
        return berth.needLoadStock();
    }

    public boolean needUnloading(){
        return berth.needUnloadStock();
    }

    public boolean berthIsLocked(){
        return berth.lock.isLocked();
    }

    public int getAvailShipCapacity(){
        return ship.getAvailablePlace();
    }
}

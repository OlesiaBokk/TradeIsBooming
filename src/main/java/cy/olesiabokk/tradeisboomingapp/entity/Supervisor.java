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

    public void setBerthList(List<Berth> list) {
        this.berthList = list;
    }

    public Berth getBerth() {
        return berth;
    }

    public Long getBertId() {
        return berth.getId();
    }

    public Ship getShip() {
        return ship;
    }

    public Long getShipId() {
        return ship.getShipId();
    }

    public int getAvailStockPlace() {
        return berth.getAvailPlace();
    }

    public int getCurrentStockAmount() {
        return berth.getCurrentStockAmount();
    }

    public boolean berthNeedLoading() {
        return berth.needLoadStock();
    }

    public boolean berthNeedUnloading() {
        return berth.needUnloadStock();
    }

    public boolean berthIsLocked() {
        return berth.lock.isLocked();
    }

    public int getAvailShipPlace() {
        return ship.getAvailablePlace();
    }

    public int getCurrentShipAmount() {
        return ship.getCurrentAmount();
    }

    public JobType getShipJobType() {
        return ship.getJobType();
    }

    public boolean getShipJobStatus() {
        return ship.getVisitedPort();
    }

    public void berthLocked(Long berthId, Long shipId) {
        String message = String.format("Berth %d is locked by %d.", berthId, shipId);
        printMessage(message);
    }

    public void berthUnlocked(Long berthId, Long shipId) {
        String message = String.format("Berth %d is unlocked by %d.", berthId, shipId);
        printMessage(message);
    }

    public void requireBerthUnload(Long berthId) {
        if (berthNeedUnloading()) {
            String message = String.format("Berth %d: Stock unloading required.", berthId);
            printMessage(message);
        }
    }

    public void requireBerthLoad(Long berthId) {
        if (berthNeedLoading()) {
            String message = String.format("Berth %d: Stock loading required.", berthId);
            printMessage(message);
        }
    }

    public void currentStockAmount(Long berthId, int currentAmount){
        String message = String.format("Berth %d: Stock current amount of goods is %d.", berthId, currentAmount);
        printMessage(message);
    }

    public void availableStockPlace(Long berthId, int availablePlace){
        String message = String.format("Berth %d: Stock available place is %d.", berthId, availablePlace);
        printMessage(message);
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

}

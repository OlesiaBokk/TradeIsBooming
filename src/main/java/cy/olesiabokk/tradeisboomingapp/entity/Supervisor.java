package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.List;

import static cy.olesiabokk.tradeisboomingapp.entity.JobType.LOAD;
import static cy.olesiabokk.tradeisboomingapp.entity.JobType.UNSHIP;

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
        String message = String.format("Berth %d is locked by Ship %d.", berthId, shipId);
        printMessage(message);
    }

    public void berthUnlocked(Long berthId, Long shipId) {
        String message = String.format("Ship %d unlocked Berth %d.",shipId, berthId);
        printMessage(message);
    }

    public void requireBerthUnload(Long berthId, boolean needUnload) {
        if (needUnload) {
            String message = String.format("Berth %d: Stock unloading required.", berthId);
            printMessage(message);
        }
    }

    public void requireBerthLoad(Long berthId, boolean needLoad) {
        if (needLoad) {
            String message = String.format("Berth %d: Stock loading required.", berthId);
            printMessage(message);
        }
    }

    public void currentStockAmount(Long berthId, int currentAmount) {
        String message = String.format("Berth %d: Stock current amount of goods is %d.", berthId, currentAmount);
        printMessage(message);
    }

    public void maxStockCapacity(Long berthId, int maxCapacity) {
        String message = String.format("Berth %d: Stock max capacity is %d.", berthId, maxCapacity);
        printMessage(message);
    }

    public void availableStockPlace(Long berthId, int availablePlace) {
        String message = String.format("Berth %d: Stock available place is %d.", berthId, availablePlace);
        printMessage(message);
    }

    public void shipEntersPort(Long shipId, JobType jobType, long time) {
        String message = String.format("Ship %d enters port. Job type is " + jobType + " . Expected shipping time: %d minutes, %d seconds", shipId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
    }

    public void shipLeavesPort(Long shipId, long time) {
        String message = String.format("Ship %d leaves port. Expected departure time: %d minutes, %d seconds", shipId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
    }

    public void currentShipAmount(Long shipID, int currAmount) {
        String message = String.format("Ship %d current amount of Goods: %d", shipID, currAmount);
        printMessage(message);
    }

    public void availableShipPlace(Long shipID, int availPlace) {
        String message = String.format("Ship %d available place: %d", shipID, availPlace);
        printMessage(message);
    }

    public void maxShipCapacity(Long shipID, int maxCapacity) {
        String message = String.format("Ship %d: max capacity is %d.", shipID, maxCapacity);
        printMessage(message);
    }

    public void shipJobStatus(Long shipID, boolean visitedPort) {
        if (visitedPort) {
            String message = String.format("Ship %d done its job", shipID);
            printMessage(message);
        } else {
            String message = String.format("Ship %d cannot do its job", shipID);
            printMessage(message);
        }
    }

    public void shipStartsUnship(Long shipId, Long berthId, long time){
        String message = String.format("Ship %d starts unshipping in berth %d. Expected unshipping time: %d minutes, %d seconds",
                shipId, berthId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
    }

    public void shipStartsLoad(Long shipId, Long berthId, long time){
        String message = String.format("Ship %d starts loading in berth %d. Expected unshipping time: %d minutes, %d seconds",
                shipId, berthId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
    }

    public void shipEndsUnship(Long shipId, Long berthId){
        String message = String.format("Ship %d ended unshipping in berth %d.", shipId, berthId);
        printMessage(message);
    }

    public void shipEndsLoad(Long shipId, Long berthId){
        String message = String.format("Ship %d ended loading in berth %d.", shipId, berthId);
        printMessage(message);
    }

    public void unshipProgress(int percent){
        String message = String.format("Loading progress %d%%", percent);
        printMessage(message);
    }

    public void loadingProgress(int percent){
        String message = String.format("Loading progress %d%%", percent);
        printMessage(message);
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}

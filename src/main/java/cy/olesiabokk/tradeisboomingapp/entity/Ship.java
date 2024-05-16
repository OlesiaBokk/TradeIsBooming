package cy.olesiabokk.tradeisboomingapp.entity;


import java.util.concurrent.atomic.AtomicLong;

public class Ship {
    private Long id;
    private int maxAmount;
    private int currentAmount;
    private static int timeLoading = 100;
    private static int timeUnloading = 100;
    private int timeEnterDock;
    private int timeLeaveDock;
    private boolean visitedPort = false;
    private static final AtomicLong counter = new AtomicLong(0);

    public Long getId() {
        return id;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getTimeEnterDock() {
        return timeEnterDock;
    }

    public int getTimeLoading(){
        return timeLoading;
    }

    public int getTimeUnloading(){
        return timeUnloading;
    }

    public void setTimeEnterDock(int currentAmount) {
        this.timeEnterDock = currentAmount * 10;
    }

    public int getTimeLeaveDock() {
        return timeLeaveDock;
    }

    public void setTimeLeaveDock(int currentAmount) {
        this.timeLeaveDock = currentAmount * 10;
    }

    public boolean isVisitedPort() {
        return visitedPort;
    }

    public void setVisitedPort(boolean visitedPort) {
        this.visitedPort = visitedPort;
    }

    public Ship(int maxAmount) {
        this.id = counter.addAndGet(1);
        this.maxAmount = maxAmount;
    }
}
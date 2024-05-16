package cy.olesiabokk.tradeisboomingapp.entity;

public class Stock {
    private final Dock dock;
    private final int maxAmount;
    private int currentAmount;

    public Stock(Dock dock, int maxAmount){
        this.dock = dock;
        this.maxAmount = maxAmount;
    }

    public Dock getDock() {
        return dock;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }
}

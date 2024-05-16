package cy.olesiabokk.tradeisboomingapp.entity;

public class Stock {
    private final int maxAmount;
    private int currentAmount;

    public Stock(int maxAmount){
        this.maxAmount = maxAmount;
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

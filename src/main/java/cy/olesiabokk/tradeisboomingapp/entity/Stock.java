package cy.olesiabokk.tradeisboomingapp.entity;

public class Stock {
    private final int maxCapacity;
    private int currentAmount;

    public Stock(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int newAmount) {
        this.currentAmount = newAmount;
    }

    public int getAvailablePlace(){
        return (maxCapacity - currentAmount);
    }

    public boolean needUnload(){
        return getAvailablePlace() <= 2000;
    }

    public boolean needLoad(){
        return getCurrentAmount() <= 1000;
    }
}

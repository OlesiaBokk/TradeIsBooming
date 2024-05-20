package cy.olesiabokk.tradeisboomingapp.entity;

public class Stock {
    private final int maxCapacity; // вместимость
    private int currentAmount; // сколько занято товарами

    public Stock(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount += currentAmount;
    }

    public int getAvailablePlace(){
        return (maxCapacity - currentAmount);
    }
}

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

    public void setCurrentAmount(int newAmount) {
        this.currentAmount += newAmount;
    }

    public int getAvailablePlace(){
        return (maxCapacity - currentAmount);
    }

    // нужно забрать товары из хранилища
    public boolean needUnload(){
        return getAvailablePlace() <= 2000;
    }

    // нужно привезти товары
    public boolean needLoad(){
        return getCurrentAmount() <= 1000;
    }
}

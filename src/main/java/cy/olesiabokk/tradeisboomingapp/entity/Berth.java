package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.concurrent.atomic.AtomicLong;

public class Berth {
    private Long id;
    private final Stock stock;
    private static final AtomicLong counter = new AtomicLong(0);

    public Berth(Stock stock){
        this.id = counter.addAndGet(1);
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public Stock getStock() {
        return stock;
    }

    public int getMaxStockCapacity(){
        return stock.getMaxCapacity();
    }

    public int getCurrentStockAmount(){
        return stock.getCurrentAmount();
    }

    public int getAvailPlace(){
        return stock.getAvailablePlace();
    }

    public void setCurrStockAmount(int newAmount){
        stock.setCurrentAmount(newAmount);
    }
}

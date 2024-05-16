package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.concurrent.atomic.AtomicLong;

public class Dock {
    private Long id;
    private final Stock stock;
    private static final AtomicLong counter = new AtomicLong(0);

    public Dock(Long id, Stock stock){
        this.id = counter.addAndGet(1);
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public Stock getStock() {
        return stock;
    }
}

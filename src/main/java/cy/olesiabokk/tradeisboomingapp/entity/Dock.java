package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.concurrent.atomic.AtomicLong;

public class Dock {
    private Long id;
    private final Port port;
    private final Stock stock;
    private final Supervisor supervisor;
    private static final AtomicLong counter = new AtomicLong(0);

    public Dock(Long id, Port port, Stock stock, Supervisor supervisor){
        this.id = counter.addAndGet(1);
        this.port = port;
        this.stock = stock;
        this.supervisor = supervisor;
    }

    public Long getId() {
        return id;
    }

    public Port getPort() {
        return port;
    }

    public Stock getStock() {
        return stock;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }
}

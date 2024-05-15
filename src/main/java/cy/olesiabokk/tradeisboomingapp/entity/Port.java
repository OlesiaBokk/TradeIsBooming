package cy.olesiabokk.tradeisboomingapp.entity;

public class Port {
    private Dock dock;
    private final Supervisor supervisor;

    public Dock getDock() {
        return dock;
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public Port(Supervisor supervisor, Dock dock){
        this.supervisor = supervisor;
        this.dock = dock;
    }
}

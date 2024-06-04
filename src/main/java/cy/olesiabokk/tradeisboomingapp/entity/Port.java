package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.List;

public class Port {
    private final List<Berth> berthList;
    private final Supervisor supervisor;

    public Port(Supervisor supervisor, List<Berth> berthList){
        this.supervisor = supervisor;
        this.berthList = berthList;
    }

    public List<Berth> getBerthList() {
        return berthList;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }
}

package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.ArrayList;

public class Port {
    private final ArrayList<Berth> berthList;
    private final Supervisor supervisor;

    public Port(Supervisor supervisor, ArrayList<Berth> berthList){
        this.supervisor = supervisor;
        this.berthList = berthList;
    }

    public ArrayList<Berth> getBerthList() {
        return berthList;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }
}

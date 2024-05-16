package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.ArrayList;

public class Port {
    private final ArrayList<Dock> dockList;
    private final Supervisor supervisor;

    public ArrayList<Dock> getDockList() {
        return dockList;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public Port(Supervisor supervisor, ArrayList<Dock> dockList){
        this.supervisor = supervisor;
        this.dockList = dockList;
    }
}

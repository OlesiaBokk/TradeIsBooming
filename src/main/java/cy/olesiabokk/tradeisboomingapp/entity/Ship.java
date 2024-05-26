package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Ship implements Runnable {
    private final Long id;
    private final int maxCapacity; // вместимость
    private int currentAmount; // сколько занято товарами
    private static final int timeLoading = 100;
    private static final int timeUnloading = 100;
    private static final int timeEnterBerth = 10;
    private static final int timeLeaveBerth = 10;
    private boolean visitedPort = false;
    private static final AtomicLong counter = new AtomicLong(0);
    private final JobType jobType;
    private final Supervisor supervisor;

    public Ship(int maxCapacity, JobType jobType, Supervisor supervisor) {
        this.id = counter.addAndGet(1);
        this.maxCapacity = maxCapacity;
        this.jobType = jobType;
        this.supervisor = supervisor;
    }

    public Long getShipId() {
        return id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getAvailablePlace() {
        return (maxCapacity - currentAmount);
    }

    public int getTimeEnterBerth() {
        return timeEnterBerth;
    }

    public int getTimeLoading() {
        return timeLoading;
    }

    public int getTimeUnloading() {
        return timeUnloading;
    }

    public int getTimeLeaveBerth() {
        return timeLeaveBerth;
    }

    public boolean isVisitedPort() {
        return visitedPort;
    }

    public void setVisitedPort(boolean visitedPort) {
        this.visitedPort = visitedPort;
    }

    //public void setJobType(Port port, Long berthID) {
    public void setJobType() throws InterruptedException {
        switch (jobType) {
            case UNSHIP:
                unship();
                //unship(port);
                break;
            case LOAD:
                //load(port, berthID);
                load();
                break;
            case UNSHIP_LOAD:
                //unshipThenLoad(port);
                unshipThenLoad();
                break;
        }
    }

    public JobType getJobType() {
        return this.jobType;
    }

    public void unship() throws InterruptedException {
        System.out.println("Ship " + getShipId() + " starts " + getJobType());
        System.out.println("Ship " + getShipId() + " visitedPort");
    }

    public void load() throws InterruptedException {
        System.out.println("Ship " + getShipId() + " starts " + getJobType());
        System.out.println("Ship " + getShipId() + " visitedPort");
    }

    public void unshipThenLoad() throws InterruptedException {
        System.out.println("Ship " + getShipId() + " starts " + getJobType());
        System.out.println("Ship " + getShipId() + " visitedPort");
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            setJobType();
            Thread.sleep(500);
            System.out.println("Ship " + getShipId() + " end work");
            semaphore.release();
            Thread.sleep(500);

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }


//    public void unship(Port port) {
//        Berth berth;
//        for (int i = 0; i < port.getBerthList().size(); i++) {
//            int freeStockPlace = port.getBerthList().get(i).getStock().getAvailablePlace();
//            if (freeStockPlace > 3000) {
//                berth = port.getBerthList().get(i);
//                int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
//                // 1. get new curr Amount of goods in Stock
//                berth.setCurrStockAmount(getCurrentAmount());
//                int timeToUnload = getCurrentAmount() * getTimeUnloading();
//                //2. set new curr Amount of goods at Ship
//                setCurrentAmount(0);
//                int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
//                setVisitedPort(true);
//                break;
//            }
//        }
//        if (!visitedPort) {
//            int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
//        }
//    }
//    public void load(Port port, Long berthID) {
//        Optional<Berth> optional = port.getBerthList().stream()
//                .filter(b -> b.getId().equals(berthID))
//                .findAny();
//        if (optional.isPresent()) {
//            Berth berth = optional.get();
//            int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
//            // 1. get new curr Amount of goods in Stock
//            int freeShipPlace = getAvailablePlace();
//            berth.setCurrStockAmount(berth.getCurrentStockAmount() - freeShipPlace);
//            int timeLoading = freeShipPlace * getTimeLoading();
//            //2. set new curr Amount of goods at Ship
//            setCurrentAmount(freeShipPlace);
//            int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
//            setVisitedPort(true);
//        } else {
//            int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
//        }
//    }

//    public void unshipThenLoad(Port port) {
//        Berth berth;
//        for (int i = 0; i < port.getBerthList().size(); i++) {
//            int freeStockPlace = port.getBerthList().get(i).getStock().getAvailablePlace();
//            if (freeStockPlace > 3000) {
//                berth = port.getBerthList().get(i);
//                int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
//                // 1. get new curr Amount of goods in Stock
//                berth.setCurrStockAmount(getCurrentAmount());
//                int timeToUnload = getCurrentAmount() * getTimeUnloading();
//                //2. set new curr Amount of goods at Ship
//                setCurrentAmount(0);
//                // 3. get new curr Amount of goods in Stock
//                int freeShipPlace = getAvailablePlace();
//                berth.setCurrStockAmount(berth.getCurrentStockAmount() - freeShipPlace);
//                int timeLoading = freeShipPlace * getTimeLoading();
//                //4. set new curr Amount of goods at Ship
//                setCurrentAmount(freeShipPlace);
//                int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
//                setVisitedPort(true);
//                break;
//            }
//        }
//        if (!visitedPort) {
//            int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
//        }
//    }

//    @Override
//    public void run() {
//        System.out.println("Ship work started");
//        System.out.println(getShipId() + " " + getJobType());
//    }
}
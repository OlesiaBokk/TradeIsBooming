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

    public boolean getVisitedPort() {
        return visitedPort;
    }

    public void setVisitedPort(boolean visitedPort) {
        this.visitedPort = visitedPort;
    }

    public void doJobType(Berth berth) throws InterruptedException {
        switch (jobType) {
            case UNSHIP:
                unship(berth);
                break;
            case LOAD:
                load(berth);
                break;
            case UNSHIP_LOAD:
                unshipThenLoad(berth);
                break;
        }
    }

    public JobType getJobType() {
        return this.jobType;
    }

    public void unship(Berth berth) {
        int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
        supervisor.shipEntersPort(getShipId(), getJobType(), timeEnterBerth);
        try {
            //Thread.sleep(timeEnterBerth);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // запросить свободное место причала
        supervisor.availableStockPlace(berth.getId(), berth.getAvailPlace());
        supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
        // запросить кол-во товаров на корабле
        supervisor.currentShipAmount(getShipId(), getCurrentAmount());
        // проверяем, что после разгрузки корабля останется 0 или больше товаров на корабле, выполняем работу
        if (berth.getAvailPlace() >= getCurrentAmount()) {
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + getCurrentAmount());
            setCurrentAmount(0);
            int timeUnship = getCurrentAmount() * getTimeUnloading();
            supervisor.shipDoesJob(getShipId(), getJobType(), berth.getId(), timeUnship);
            try {
                //Thread.sleep(timeUnship);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            setVisitedPort(true);
        } else if (berth.getAvailPlace() < getCurrentAmount() && berth.getAvailPlace() != 0) {
            int toUnship = berth.getAvailPlace();
            int timeUnship = toUnship * getTimeUnloading();
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + toUnship);
            setCurrentAmount(getCurrentAmount() - toUnship);
            supervisor.shipDoesJob(getShipId(), getJobType(), berth.getId(), timeUnship);
            try {
                //Thread.sleep(timeUnship);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            setVisitedPort(true);
        }
        leavePort(berth);
    }

    public void load(Berth berth) {
        int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
        supervisor.shipEntersPort(getShipId(), getJobType(), timeEnterBerth);
        try {
            //Thread.sleep(timeEnterBerth);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // запросить кол-во товаров для разгрузки причала
        supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
        supervisor.maxShipCapacity(getShipId(), getMaxCapacity());
        // запросить свободное место на корабле
        supervisor.availableShipPlace(getShipId(), getAvailablePlace());
        supervisor.currentShipAmount(getShipId(), getCurrentAmount());
        // проверить если своб место на корабле меньше или = кол-ву на складе, выполнить работу
        if (getAvailablePlace() <= berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = getAvailablePlace();
            berth.setCurrStockAmount(berth.getCurrentStockAmount() - getAvailablePlace());
            setCurrentAmount(getCurrentAmount() + getAvailablePlace());
            int timeLoading = toLoad * getTimeLoading();
            supervisor.shipDoesJob(getShipId(), getJobType(), berth.getId(), timeLoading);
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            setVisitedPort(true);
        } else if(getAvailablePlace() > berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0){
            int toLoad = berth.getCurrentStockAmount();
            berth.setCurrStockAmount(0);
            setCurrentAmount(getCurrentAmount() + toLoad);
            int timeLoading = toLoad * getTimeLoading();
            supervisor.shipDoesJob(getShipId(), getJobType(), berth.getId(), timeLoading);
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            setVisitedPort(true);
        }
        leavePort(berth);
    }

    public void unshipThenLoad(Berth berth) {
        int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
        supervisor.shipEntersPort(getShipId(), getJobType(), timeEnterBerth);
        try {
            //Thread.sleep(timeEnterBerth);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // запросить свободное место причала
        supervisor.availableStockPlace(berth.getId(), berth.getAvailPlace());
        // запросить кол-во товаров на корабле
        supervisor.currentShipAmount(getShipId(), getAvailablePlace());
        // если свободного места причала <= 500 -> уплыть из порта
        if (berth.getAvailPlace() < getCurrentAmount() && berth.getAvailPlace() <= 500) {
            int leaveBerth = getTimeLeaveBerth() * getCurrentAmount();
            supervisor.shipLeavesPort(getShipId(), leaveBerth);
            try {
               // Thread.sleep(leaveBerth);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } else {
            // 1. get new curr Amount of goods in Stock
            int timeToUnload = getCurrentAmount() * getTimeUnloading();
            supervisor.shipDoesJob(getShipId(), getJobType(), berth.getId(), timeToUnload);
            try {
               // Thread.sleep(timeToUnload);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            berth.setCurrStockAmount(getCurrentAmount());
            supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
            //2. set new curr Amount of goods at Ship
            setCurrentAmount(0);
            supervisor.currentShipAmount(getShipId(), getCurrentAmount());
            // 3. get new curr Amount of goods in Stock
            int freeShipPlace = getAvailablePlace();
            supervisor.availableShipPlace(getShipId(), freeShipPlace);
            int timeLoading = freeShipPlace * getTimeLoading();
            supervisor.shipDoesJob(getShipId(), getJobType(), berth.getId(), timeLoading);
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            berth.setCurrStockAmount(berth.getCurrentStockAmount() - freeShipPlace);
            supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
            //4. set new curr Amount of goods at Ship
            setCurrentAmount(freeShipPlace);
            supervisor.currentShipAmount(getShipId(), getCurrentAmount());
            int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
            setVisitedPort(true);
            supervisor.shipJobStatus(getShipId(), getVisitedPort());
            supervisor.shipLeavesPort(getShipId(), timeLeaveBerth);
            try {
                //Thread.sleep(timeLeaveBerth);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        try {
            List<Berth> berths = supervisor.getBerthList();
            for (int i = 0; i < berths.size(); i++) {
                if (berths.get(i).needUnloadStock() && getJobType().equals(JobType.LOAD)) {
                    berths.get(i).lock.lock();
                    supervisor.berthLocked(berths.get(i).getId(), getShipId());
                    supervisor.requireBerthUnload(berths.get(i).getId(), berths.get(i).needUnloadStock());
                    doJobType(berths.get(i));
                    supervisor.berthUnlocked(berths.get(i).getId(), getShipId());
                    berths.get(i).lock.unlock();
                    if (getVisitedPort()) {
                        return;
                    }

                } else if (berths.get(i).needLoadStock() && getJobType().equals(JobType.UNSHIP) ||
                        berths.get(i).needLoadStock() && getJobType().equals(JobType.UNSHIP_LOAD)) {
                    berths.get(i).lock.lock();
                    supervisor.berthLocked(berths.get(i).getId(), getShipId());
                    supervisor.requireBerthLoad(berths.get(i).getId(), berths.get(i).needLoadStock());
                    doJobType(berths.get(i));
                    supervisor.berthUnlocked(berths.get(i).getId(), getShipId());
                    berths.get(i).lock.unlock();
                    if (getVisitedPort()) {
                        return;
                    }

                } else {
                    berths.get(i).lock.lock();
                    supervisor.berthLocked(berths.get(i).getId(), getShipId());
                    doJobType(berths.get(i));
                    supervisor.berthUnlocked(berths.get(i).getId(), getShipId());
                    berths.get(i).lock.unlock();
                    if (getVisitedPort()) {
                        return;
                    }
                }
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }

    public void leavePort(Berth berth){
        if(getVisitedPort()) {
            supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
            supervisor.currentShipAmount(getShipId(), getCurrentAmount());
        }
        int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
        supervisor.shipJobStatus(getShipId(), getVisitedPort());
        supervisor.shipLeavesPort(getShipId(), timeLeaveBerth);
        try {
            //Thread.sleep(timeLeaveBerth);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
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

    public void doJobType(Berth berth) {
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
            System.err.println(e.getMessage());
        }
        // запросить свободное место причала
        supervisor.availableStockPlace(berth.getId(), berth.getAvailPlace());
        supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
        // запросить кол-во товаров на корабле
        supervisor.currentShipAmount(getShipId(), getCurrentAmount());
        // проверяем, что после разгрузки корабля останется 0 или больше товаров на корабле, выполняем работу
        if (berth.getAvailPlace() >= getCurrentAmount()) {
            int timeUnship = getCurrentAmount() * getTimeUnloading();
            supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship);
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + getCurrentAmount());
            setCurrentAmount(0);
            try {
                //Thread.sleep(timeUnship);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            supervisor.shipEndsUnship(getShipId(), berth.getId());
            setVisitedPort(true);
        } else if (berth.getAvailPlace() < getCurrentAmount() && berth.getAvailPlace() != 0) {
            int toUnship = berth.getAvailPlace();
            int timeUnship = toUnship * getTimeUnloading();
            supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship);
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + toUnship);
            setCurrentAmount(getCurrentAmount() - toUnship);
            try {
                //Thread.sleep(timeUnship);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            supervisor.shipEndsUnship(getShipId(), berth.getId());
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
            System.out.println(e.getMessage());
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
            int timeLoading = toLoad * getTimeLoading();
            supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading);
            berth.setCurrStockAmount(berth.getCurrentStockAmount() - getAvailablePlace());
            setCurrentAmount(getCurrentAmount() + getAvailablePlace());
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.shipEndsLoad(getShipId(), berth.getId());
            setVisitedPort(true);
        } else if (getAvailablePlace() > berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = berth.getCurrentStockAmount();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading);
            berth.setCurrStockAmount(0);
            setCurrentAmount(getCurrentAmount() + toLoad);
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.shipEndsLoad(getShipId(), berth.getId());
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
            System.out.println(e.getMessage());
        }
        // запросить свободное место причала
        supervisor.availableStockPlace(berth.getId(), berth.getAvailPlace());
        supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
        // запросить кол-во товаров на корабле
        supervisor.currentShipAmount(getShipId(), getCurrentAmount());
        // проверяем, что после разгрузки корабля останется 0 или больше товаров на корабле, выполняем работу
        if (berth.getAvailPlace() >= getCurrentAmount()) {
            int timeUnship = getCurrentAmount() * getTimeUnloading();
            supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship);
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + getCurrentAmount());
            setCurrentAmount(0);
            try {
                //Thread.sleep(timeUnship);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.shipEndsUnship(getShipId(), berth.getId());
        } else if (berth.getAvailPlace() < getCurrentAmount() && berth.getAvailPlace() != 0) {
            int toUnship = berth.getAvailPlace();
            int timeUnship = toUnship * getTimeUnloading();
            supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship);
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + toUnship);
            setCurrentAmount(getCurrentAmount() - toUnship);
            try {
                //Thread.sleep(timeUnship);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.shipEndsUnship(getShipId(), berth.getId());
        }

        ////// LOADING
        // запросить кол-во товаров для разгрузки причала
        supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount());
        supervisor.maxShipCapacity(getShipId(), getMaxCapacity());
        // запросить свободное место на корабле
        supervisor.availableShipPlace(getShipId(), getAvailablePlace());
        supervisor.currentShipAmount(getShipId(), getCurrentAmount());
        // проверить если своб место на корабле меньше или = кол-ву на складе, выполнить работу
        if (getAvailablePlace() <= berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = getAvailablePlace();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading);
            berth.setCurrStockAmount(berth.getCurrentStockAmount() - getAvailablePlace());
            setCurrentAmount(getCurrentAmount() + getAvailablePlace());
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.shipEndsLoad(getShipId(), berth.getId());
            setVisitedPort(true);
        } else if (getAvailablePlace() > berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = berth.getCurrentStockAmount();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading);
            berth.setCurrStockAmount(0);
            setCurrentAmount(getCurrentAmount() + toLoad);
            try {
                //Thread.sleep(timeLoading);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.shipEndsLoad(getShipId(), berth.getId());
            setVisitedPort(true);
        }
        leavePort(berth);
    }


    @Override
    public void run() {
        try {
            while (!getVisitedPort()) {
                List<Berth> berths = supervisor.getBerthList();
                for (int i = 0; i < berths.size(); i++) {
                    berths.get(i).lock.lock();
                    if (berths.get(i).needUnloadStock() && getJobType().equals(JobType.LOAD)) {
                        supervisor.berthLocked(berths.get(i).getId(), getShipId());
                        supervisor.requireBerthUnload(berths.get(i).getId(), berths.get(i).needUnloadStock());
                        doJobType(berths.get(i));
                        supervisor.berthUnlocked(berths.get(i).getId(), getShipId());
                        berths.get(i).lock.unlock();
                        if (getVisitedPort()) {
                            return;
                        }

                    } else if (berths.get(i).needLoadStock() && getJobType().equals(JobType.UNSHIP)) {
                        supervisor.berthLocked(berths.get(i).getId(), getShipId());
                        supervisor.requireBerthLoad(berths.get(i).getId(), berths.get(i).needLoadStock());
                        doJobType(berths.get(i));
                        supervisor.berthUnlocked(berths.get(i).getId(), getShipId());
                        berths.get(i).lock.unlock();
                        if (getVisitedPort()) {
                            return;
                        }

                    } else {
                        supervisor.berthLocked(berths.get(i).getId(), getShipId());
                        doJobType(berths.get(i));
                        supervisor.berthUnlocked(berths.get(i).getId(), getShipId());
                        berths.get(i).lock.unlock();
                        if (getVisitedPort()) {
                            return;
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void leavePort(Berth berth) {
        if (getVisitedPort()) {
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
}
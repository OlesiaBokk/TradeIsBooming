package cy.olesiabokk.tradeisboomingapp.entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static cy.olesiabokk.tradeisboomingapp.entity.JobType.*;

public class Ship implements Runnable {
    private final Long id;
    private final int maxCapacity;
    private int currentAmount;
    private static final int workTime = 100;
    private static final int movingTime = 10;
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
        return movingTime;
    }

    public int getTimeLoading() {
        return workTime;
    }

    public int getTimeUnloading() {
        return workTime;
    }

    public int getTimeLeaveBerth() {
        return movingTime;
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
        supervisor.printBerthLog(berth, supervisor.shipEntersPort(getShipId(), getJobType(), timeEnterBerth));
        try {
            Thread.sleep(timeEnterBerth);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        supervisor.printBerthLog(berth, supervisor.availableStockPlace(berth.getId(), berth.getAvailPlace()));
        supervisor.printBerthLog(berth, supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount()));
        supervisor.printBerthLog(berth, supervisor.currentShipAmount(getShipId(), getCurrentAmount()));
        if (berth.getAvailPlace() >= getCurrentAmount()) {
            int timeUnship = getCurrentAmount() * getTimeUnloading();
            supervisor.printBerthLog(berth, supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship));
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + getCurrentAmount());
            setCurrentAmount(0);
            try {
                calculateWorkTime(berth, timeUnship, UNSHIP);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsUnship(getShipId(), berth.getId()));
            setVisitedPort(true);
        } else if (berth.getAvailPlace() < getCurrentAmount() && berth.getAvailPlace() != 0) {
            int toUnship = berth.getAvailPlace();
            int timeUnship = toUnship * getTimeUnloading();
            supervisor.printBerthLog(berth, supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship));
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + toUnship);
            setCurrentAmount(getCurrentAmount() - toUnship);
            try {
                calculateWorkTime(berth, timeUnship, UNSHIP);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsUnship(getShipId(), berth.getId()));
            setVisitedPort(true);
        }
        leavePort(berth);
    }

    public void load(Berth berth) {
        int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
        supervisor.printBerthLog(berth, supervisor.shipEntersPort(getShipId(), getJobType(), timeEnterBerth));
        try {
            Thread.sleep(timeEnterBerth);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        supervisor.printBerthLog(berth, supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount()));
        supervisor.printBerthLog(berth, supervisor.maxShipCapacity(getShipId(), getMaxCapacity()));
        supervisor.printBerthLog(berth, supervisor.availableShipPlace(getShipId(), getAvailablePlace()));
        supervisor.printBerthLog(berth, supervisor.currentShipAmount(getShipId(), getCurrentAmount()));
        if (getAvailablePlace() <= berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = getAvailablePlace();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.printBerthLog(berth, supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading));
            berth.setCurrStockAmount(berth.getCurrentStockAmount() - getAvailablePlace());
            setCurrentAmount(getCurrentAmount() + getAvailablePlace());
            try {
                calculateWorkTime(berth, timeLoading, LOAD);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsLoad(getShipId(), berth.getId()));
            setVisitedPort(true);
        } else if (getAvailablePlace() > berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = berth.getCurrentStockAmount();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.printBerthLog(berth, supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading));
            berth.setCurrStockAmount(0);
            setCurrentAmount(getCurrentAmount() + toLoad);
            try {
                calculateWorkTime(berth, timeLoading, LOAD);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsLoad(getShipId(), berth.getId()));
            setVisitedPort(true);
        }
        leavePort(berth);
    }

    public void unshipThenLoad(Berth berth) {
        int timeEnterBerth = getTimeEnterBerth() * getCurrentAmount();
        supervisor.printBerthLog(berth, supervisor.shipEntersPort(getShipId(), getJobType(), timeEnterBerth));
        try {
            Thread.sleep(timeEnterBerth);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        supervisor.printBerthLog(berth, supervisor.availableStockPlace(berth.getId(), berth.getAvailPlace()));
        supervisor.printBerthLog(berth, supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount()));
        supervisor.printBerthLog(berth, supervisor.currentShipAmount(getShipId(), getCurrentAmount()));
        if (berth.getAvailPlace() >= getCurrentAmount()) {
            int timeUnship = getCurrentAmount() * getTimeUnloading();
            supervisor.printBerthLog(berth, supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship));
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + getCurrentAmount());
            setCurrentAmount(0);
            try {
                calculateWorkTime(berth, timeUnship, UNSHIP);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsUnship(getShipId(), berth.getId()));
        } else if (berth.getAvailPlace() < getCurrentAmount() && berth.getAvailPlace() != 0) {
            int toUnship = berth.getAvailPlace();
            int timeUnship = toUnship * getTimeUnloading();
            supervisor.printBerthLog(berth, supervisor.shipStartsUnship(getShipId(), berth.getId(), timeUnship));
            berth.setCurrStockAmount(berth.getCurrentStockAmount() + toUnship);
            setCurrentAmount(getCurrentAmount() - toUnship);
            try {
                calculateWorkTime(berth, timeUnship, UNSHIP);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsUnship(getShipId(), berth.getId()));
        }
        ////// LOADING
        supervisor.printBerthLog(berth, supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount()));
        supervisor.printBerthLog(berth, supervisor.maxShipCapacity(getShipId(), getMaxCapacity()));
        supervisor.printBerthLog(berth, supervisor.availableShipPlace(getShipId(), getAvailablePlace()));
        supervisor.printBerthLog(berth, supervisor.currentShipAmount(getShipId(), getCurrentAmount()));
        if (getAvailablePlace() <= berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = getAvailablePlace();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.printBerthLog(berth, supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading));
            berth.setCurrStockAmount(berth.getCurrentStockAmount() - getAvailablePlace());
            setCurrentAmount(getCurrentAmount() + getAvailablePlace());
            try {
                calculateWorkTime(berth, timeLoading, LOAD);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsLoad(getShipId(), berth.getId()));
            setVisitedPort(true);
        } else if (getAvailablePlace() > berth.getCurrentStockAmount() && berth.getCurrentStockAmount() != 0) {
            int toLoad = berth.getCurrentStockAmount();
            int timeLoading = toLoad * getTimeLoading();
            supervisor.printBerthLog(berth, supervisor.shipStartsLoad(getShipId(), berth.getId(), timeLoading));
            berth.setCurrStockAmount(0);
            setCurrentAmount(getCurrentAmount() + toLoad);
            try {
                calculateWorkTime(berth, timeLoading, LOAD);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            supervisor.printBerthLog(berth, supervisor.shipEndsLoad(getShipId(), berth.getId()));
            setVisitedPort(true);
        }
        leavePort(berth);
    }

    @Override
    public void run() {
        try {
            while (!getVisitedPort()) {
                List<Berth> berths = supervisor.getBerthList();
                supervisor.setLoggerToBerth(berths);
                for (int i = 0; i < berths.size(); i++) {
                    berths.get(i).lock.lock();
                    if (berths.get(i).needUnloadStock() && getJobType().equals(JobType.LOAD)) {
                        supervisor.printBerthLog(berths.get(i), supervisor.berthLocked(berths.get(i).getId(), getShipId()));
                        supervisor.printBerthLog(berths.get(i), supervisor.requireBerthUnload(berths.get(i).getId(), berths.get(i).needUnloadStock()));
                        doJobType(berths.get(i));
                        supervisor.printBerthLog(berths.get(i), supervisor.berthUnlocked(berths.get(i).getId(), getShipId()));
                        berths.get(i).lock.unlock();
                        if (getVisitedPort()) {
                            return;
                        }
                    } else if (berths.get(i).needLoadStock() && getJobType().equals(JobType.UNSHIP)) {
                        supervisor.printBerthLog(berths.get(i), supervisor.berthLocked(berths.get(i).getId(), getShipId()));
                        supervisor.printBerthLog(berths.get(i), supervisor.requireBerthLoad(berths.get(i).getId(), berths.get(i).needLoadStock()));
                        doJobType(berths.get(i));
                        supervisor.printBerthLog(berths.get(i), supervisor.berthUnlocked(berths.get(i).getId(), getShipId()));
                        berths.get(i).lock.unlock();
                        if (getVisitedPort()) {
                            return;
                        }
                    } else {
                        supervisor.printBerthLog(berths.get(i), supervisor.berthLocked(berths.get(i).getId(), getShipId()));
                        doJobType(berths.get(i));
                        supervisor.printBerthLog(berths.get(i), supervisor.berthUnlocked(berths.get(i).getId(), getShipId()));
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
            supervisor.printBerthLog(berth, supervisor.currentStockAmount(berth.getId(), berth.getCurrentStockAmount()));
            supervisor.printBerthLog(berth, supervisor.currentShipAmount(getShipId(), getCurrentAmount()));
        }
        int timeLeaveBerth = getTimeLeaveBerth() * getCurrentAmount();
        supervisor.printBerthLog(berth, supervisor.shipJobStatus(getShipId(), getVisitedPort()));
        supervisor.printBerthLog(berth, supervisor.shipLeavesPort(getShipId(), timeLeaveBerth));
        try {
            Thread.sleep(timeLeaveBerth);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public void calculateWorkTime(Berth berth, int workTime, JobType jobType) throws InterruptedException {
        int toLower = workTime / 10;
        int progress = 10;
        while (workTime != 0) {
            workTime = workTime - toLower;
            Thread.sleep(toLower);
            if (jobType.equals(UNSHIP)) {
                supervisor.printBerthLog(berth, supervisor.unshipProgress(berth.getId(),progress));
            } else {
                supervisor.printBerthLog(berth, supervisor.loadingProgress(berth.getId(), progress));
            }
            progress = progress + 10;
        }
    }
}
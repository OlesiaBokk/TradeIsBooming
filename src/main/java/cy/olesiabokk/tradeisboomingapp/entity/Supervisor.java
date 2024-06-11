package cy.olesiabokk.tradeisboomingapp.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Supervisor {
    private List<Berth> berthList;
    private Berth berth;
    private Ship ship;
    private Stock stock;
    private static Logger logger = LogManager.getLogger(Supervisor.class);

    public Supervisor(List<Berth> list){
        this.berthList = list;
    }

    public List<Berth> getBerthList() {
        return berthList;
    }

    public void setBerthList(List<Berth> list) {
        this.berthList = list;
    }

    public Berth getBerth() {
        return berth;
    }

    public Long getBertId() {
        return berth.getId();
    }

    public Ship getShip() {
        return ship;
    }

    public Long getShipId() {
        return ship.getShipId();
    }

    public int getAvailStockPlace() {
        return berth.getAvailPlace();
    }

    public int getCurrentStockAmount() {
        return berth.getCurrentStockAmount();
    }

    public boolean berthNeedLoading() {
        return berth.needLoadStock();
    }

    public boolean berthNeedUnloading() {
        return berth.needUnloadStock();
    }

    public boolean berthIsLocked() {
        return berth.lock.isLocked();
    }

    public int getAvailShipPlace() {
        return ship.getAvailablePlace();
    }

    public int getCurrentShipAmount() {
        return ship.getCurrentAmount();
    }

    public JobType getShipJobType() {
        return ship.getJobType();
    }

    public boolean getShipJobStatus() {
        return ship.getVisitedPort();
    }

    public String berthLocked(Long berthId, Long shipId) {
        String message = String.format("Berth %d is locked by Ship %d.", berthId, shipId);
        printMessage(message);
        return message;
    }

    public String berthUnlocked(Long berthId, Long shipId) {
        String message = String.format("Ship %d unlocked Berth %d.", shipId, berthId);
        printMessage(message);
        return message;
    }

    public String requireBerthUnload(Long berthId, boolean needUnload) {
        if (needUnload) {
            String message = String.format("Berth %d: Stock unloading required.", berthId);
            printMessage(message);
            return message;
        } else {
            return null;
        }
    }

    public String requireBerthLoad(Long berthId, boolean needLoad) {
        if (needLoad) {
            String message = String.format("Berth %d: Stock loading required.", berthId);
            printMessage(message);
            return message;
        } else {
            return null;
        }
    }

    public String currentStockAmount(Long berthId, int currentAmount) {
        String message = String.format("Berth %d: Stock current amount of goods is %d.", berthId, currentAmount);
        printMessage(message);
        return message;
    }

    public String maxStockCapacity(Long berthId, int maxCapacity) {
        String message = String.format("Berth %d: Stock max capacity is %d.", berthId, maxCapacity);
        printMessage(message);
        return message;
    }

    public String availableStockPlace(Long berthId, int availablePlace) {
        String message = String.format("Berth %d: Stock available place is %d.", berthId, availablePlace);
        printMessage(message);
        return message;
    }

    public String shipEntersPort(Long shipId, JobType jobType, long time) {
        String message = String.format("Ship %d enters port. Job type is " + jobType + " . Expected shipping time: %d minutes, %d seconds", shipId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
        return message;
    }

    public String shipLeavesPort(Long shipId, long time) {
        String message = String.format("Ship %d leaves port. Expected departure time: %d minutes, %d seconds", shipId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
        return message;
    }

    public String currentShipAmount(Long shipID, int currAmount) {
        String message = String.format("Ship %d current amount of Goods: %d", shipID, currAmount);
        printMessage(message);
        return message;
    }

    public String availableShipPlace(Long shipID, int availPlace) {
        String message = String.format("Ship %d available place: %d", shipID, availPlace);
        printMessage(message);
        return message;
    }

    public String maxShipCapacity(Long shipID, int maxCapacity) {
        String message = String.format("Ship %d: max capacity is %d.", shipID, maxCapacity);
        printMessage(message);
        return message;
    }

    public String shipJobStatus(Long shipID, boolean visitedPort) {
        if (visitedPort) {
            String message = String.format("Ship %d done its job", shipID);
            printMessage(message);
            return message;
        } else {
            String message = String.format("Ship %d cannot do its job", shipID);
            printMessage(message);
            return message;
        }
    }

    public String shipStartsUnship(Long shipId, Long berthId, long time) {
        String message = String.format("Ship %d starts unshipping in berth %d. Expected unshipping time: %d minutes, %d seconds",
                shipId, berthId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
        return message;
    }

    public String shipStartsLoad(Long shipId, Long berthId, long time) {
        String message = String.format("Ship %d starts loading in berth %d. Expected loading time: %d minutes, %d seconds",
                shipId, berthId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
        printMessage(message);
        return message;
    }

    public String shipEndsUnship(Long shipId, Long berthId) {
        String message = String.format("Ship %d ended unshipping in berth %d.", shipId, berthId);
        printMessage(message);
        return message;
    }

    public String shipEndsLoad(Long shipId, Long berthId) {
        String message = String.format("Ship %d ended loading in berth %d.", shipId, berthId);
        printMessage(message);
        return message;
    }

    public String unshipProgress(Long berthId, int percent) {
        String message = String.format("Unshipping progress %d%%", percent);
        printMessage(message);
        return message;
    }

    public String loadingProgress(Long berthId, int percent) {
        String message = String.format("Loading progress %d%%", percent);
        printMessage(message);
        return message;
    }

    private void printMessage(String message) {
        System.out.println(message);
        logger.log(Level.INFO, message);
    }

    public void printBerthLog(Berth berth, String message) {
        Logger berthLogger = LogManager.getLogger("Berth " + berth.getId());
        berthLogger.info(message);
    }

    public void setLoggerToBerth(List<Berth> berthList) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");

        for (Berth berth : berthList) {
            StringBuilder sb = new StringBuilder();
            String loggerName = "Berth " + berth.getId();
            String fileName = sb.append("logs/Berth ")
                    .append(berth.getId())
                    .append(" ")
                    .append(dateFormat.format(calendar.getTime()))
                    .append(".txt").toString();

            FileAppender appender = FileAppender.createAppender(
                    fileName,
                    "true",
                    "false",
                    "File - " + berth.getId(),
                    "true",
                    "false",
                    "false",
                    "5000",
                    PatternLayout.createLayout("%d [%t] %-5level %logger - %m%n",
                            config,
                            null,
                            null,
                            false,
                            false,
                            null, null),
                    null,
                    "false",
                    null,
                    config
            );
            appender.start();
            config.addAppender(appender);

            LoggerConfig loggerConfig = new LoggerConfig(loggerName, Level.INFO, true);
            loggerConfig.addAppender(appender, Level.INFO, null);
            config.addLogger(loggerName, loggerConfig);
        }
        context.updateLoggers();
    }
}

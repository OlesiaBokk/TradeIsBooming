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

    public Supervisor(List<Berth> list){
        this.berthList = list;
    }

    public List<Berth> getBerthList() {
        return berthList;
    }

    public String berthLocked(Long berthId, Long shipId) {
        return String.format("Berth %d is locked by Ship %d.", berthId, shipId);
    }

    public String berthUnlocked(Long berthId, Long shipId) {
        return String.format("Ship %d unlocked Berth %d.", shipId, berthId);
    }

    public String requireBerthUnload(Long berthId, boolean needUnload) {
        if (needUnload) {
            return String.format("Berth %d: Stock unloading required.", berthId);
        } else {
            return null;
        }
    }

    public String requireBerthLoad(Long berthId, boolean needLoad) {
        if (needLoad) {
            return String.format("Berth %d: Stock loading required.", berthId);
        } else {
            return null;
        }
    }

    public String currentStockAmount(Long berthId, int currentAmount) {
        return String.format("Berth %d: Stock current amount of goods is %d.", berthId, currentAmount);
    }

    public String maxStockCapacity(Long berthId, int maxCapacity) {
        return String.format("Berth %d: Stock max capacity is %d.", berthId, maxCapacity);
    }

    public String availableStockPlace(Long berthId, int availablePlace) {
        return String.format("Berth %d: Stock available place is %d.", berthId, availablePlace);
    }

    public String shipEntersPort(Long shipId, JobType jobType, long time) {
        return String.format("Ship %d enters port. Job type is " + jobType + " . Expected shipping time: %d minutes, %d seconds", shipId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
    }

    public String shipLeavesPort(Long shipId, long time) {
        return String.format("Ship %d leaves port. Expected departure time: %d minutes, %d seconds", shipId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
    }

    public String currentShipAmount(Long shipID, int currAmount) {
        return String.format("Ship %d current amount of Goods: %d", shipID, currAmount);
    }

    public String availableShipPlace(Long shipID, int availPlace) {
        return String.format("Ship %d available place: %d", shipID, availPlace);
    }

    public String maxShipCapacity(Long shipID, int maxCapacity) {
        return String.format("Ship %d: max capacity is %d.", shipID, maxCapacity);
    }

    public String shipJobStatus(Long shipID, boolean visitedPort) {
        if (visitedPort) {
            return String.format("Ship %d done its job", shipID);
        } else {
            return String.format("Ship %d cannot do its job", shipID);
        }
    }

    public String shipStartsUnship(Long shipId, Long berthId, long time) {
        return String.format("Ship %d starts unshipping in berth %d. Expected unshipping time: %d minutes, %d seconds",
                shipId, berthId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
    }

    public String shipStartsLoad(Long shipId, Long berthId, long time) {
        return String.format("Ship %d starts loading in berth %d. Expected loading time: %d minutes, %d seconds",
                shipId, berthId, (time / (1000 * 60)) % 60, (time / 1000) % 60);
    }

    public String shipEndsUnship(Long shipId, Long berthId) {
        return String.format("Ship %d ended unshipping in berth %d.", shipId, berthId);
    }

    public String shipEndsLoad(Long shipId, Long berthId) {
        return String.format("Ship %d ended loading in berth %d.", shipId, berthId);
    }

    public String unshipProgress(Long berthId, int percent) {
        return String.format("Unshipping progress %d%%", percent);
    }

    public String loadingProgress(Long berthId, int percent) {
        return String.format("Loading progress %d%%", percent);
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
                    "false",
                    "false",
                    "File - " + berth.getId(),
                    "true",
                    "false",
                    "true",
                    "5000",
                    PatternLayout.createLayout("%d{HH:mm:ss} - %m%n",
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

package cy.olesiabokk.tradeisboomingapp.util;

import java.io.File;

public class LogCleaner {
    public static void cleanLogs(String logPath) {
        File directory = new File(logPath);
        if (!directory.exists()) {
            return;
        }
        File[] logs = directory.listFiles();
        if (logs != null) {
            for (File file : logs) {
                file.delete();
            }
        }
    }
}

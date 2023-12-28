package main.java;

import java.util.logging.*;

public class AdvancedLogger {

    public static final Logger LOGGER = Logger.getLogger(AdvancedLogger.class.getName());

    static {
        try {
            // Customize the logging format
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new CustomFormatter());
            LOGGER.addHandler(consoleHandler);

            // Set log level (e.g., INFO, WARNING, SEVERE)
            LOGGER.setLevel(Level.INFO);

            // Uncomment the following line if you want to log to a file
            // LOGGER.addHandler(new FileHandler("logfile.log"));

        } catch (SecurityException e) {
            // Handle SecurityException
            e.printStackTrace();
        }
    }


    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void logWarning(String message) {
        LOGGER.warning(message);
    }

    public static void logError(String message) {
        LOGGER.severe(message);
    }

    public static void logException(Exception e) {
        LOGGER.log(Level.SEVERE, "Exception occurred", e);
    }

    private static class CustomFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return "[" + record.getLevel() + "] " +
                    record.getSourceClassName() + " - " +
                    record.getMessage() + "\n";
        }
    }
}

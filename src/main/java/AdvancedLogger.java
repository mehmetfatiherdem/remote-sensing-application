package main.java;

import java.io.IOException;
import java.util.logging.*;

public class AdvancedLogger {

    public static final Logger LOGGER = Logger.getLogger(AdvancedLogger.class.getName());

    static {
            logInfo("Initializing logger...");
        try {
            // Set log level (e.g., INFO, WARNING, SEVERE)
            LOGGER.setLevel(Level.INFO);

            // Uncomment the following line if you want to log to a file
            LOGGER.addHandler(new FileHandler("logfile.log"));

            logInfo("Logger initialized successfully.");

        } catch (SecurityException e) {
            // Handle SecurityException
            logInfo("SecurityException during logger initialization: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
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

}

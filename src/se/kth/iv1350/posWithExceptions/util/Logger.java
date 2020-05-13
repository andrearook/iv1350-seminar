package se.kth.iv1350.posWithExceptions.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The logger that logs exceptions of interest to the programmer in a file.
 */
public class Logger {
    private static final String LOG_FILE_NAME = "pos-log.txt";
    private PrintWriter loggerFile;

    /**
     * Creates a new instance that will be able to write to the file.
     *
     * @throws IOException when unable to create the file.
     */
    public Logger() throws IOException {
        loggerFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
    }

    /**
     * Logs information regarding a thrown exception.
     *
     * @param exc The exception to log.
     */
    public void logException(Exception exc) {
        String infoToLog = TimeUtil.getLocalizedTimeNow() + ", Exception was thrown: " + exc.getMessage();
        loggerFile.println(infoToLog);
        exc.printStackTrace(loggerFile);
    }
}

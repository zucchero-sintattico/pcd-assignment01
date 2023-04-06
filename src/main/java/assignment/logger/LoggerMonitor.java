package assignment.logger;

import lib.synchronization.Monitor;

public class LoggerMonitor extends Monitor implements Logger {
    private static LoggerMonitor instance = null;

    private LoggerMonitor() {}

    public static LoggerMonitor getInstance() {
        if (instance == null) {
            instance = new LoggerMonitor();
        }
        return instance;
    }

    @Override
    public void log(String message) {
        monitored(() -> System.out.println(message));
    }

}

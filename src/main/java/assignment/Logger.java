package assignment;

public class Logger {
    private static Logger instance = null;
    private static String log = "";

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public synchronized void log(String message) {
        log += message + ";\n";
        System.out.println(message);
    }

    public String getLog() {
        return log;
    }
}

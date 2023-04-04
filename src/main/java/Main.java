import assignment.FileAnalyzer;
import assignment.Logger;
import lib.synchronization.QueueMonitor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        QueueMonitor<Path> queueMonitor = new QueueMonitor<>();
        Path path = Paths.get("src/main/java/");
        FileAnalyzer fileAnalyzer = new FileAnalyzer(queueMonitor, path);
        fileAnalyzer.start();


    }
}

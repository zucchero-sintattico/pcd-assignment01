import assignment.FileAnalyzer;
import assignment.PathConsumer;
import assignment.Statistic;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;
import lib.synchronization.QueueMonitor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        QueueMonitor<Path> queueMonitor = new QueueMonitor<>();
        QueueMonitor<Statistic> statisticQueueMonitor = new QueueMonitor<>();
        Barrier barrier = new ActionBarrier(2, () -> {
            System.out.println("All threads are done");
        });

        Path path = Paths.get("src/main/java/");

        PathConsumer pathConsumer = new PathConsumer(queueMonitor, statisticQueueMonitor, barrier);

        FileAnalyzer fileAnalyzer = new FileAnalyzer(queueMonitor, path);

        pathConsumer.start();
        fileAnalyzer.start();
    }
}


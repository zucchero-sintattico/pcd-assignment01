import assignment.FileAnalyzer;
import assignment.PathConsumer;
import assignment.Statistic;
import assignment.queue.PathQueue;
import assignment.queue.StatisticQueue;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;
import lib.synchronization.QueueMonitor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        final PathQueue pathQueue = new PathQueue();
        final StatisticQueue statisticQueue = new StatisticQueue();

        Barrier barrier = new ActionBarrier(2, () -> {
            //statisticQueueMonitor.close();
            System.out.println("All threads are done");
        });

        Path path = Paths.get("src/main/java/");

        PathConsumer pathConsumer = new PathConsumer(pathQueue, statisticQueue, barrier);

        FileAnalyzer fileAnalyzer = new FileAnalyzer(pathQueue, path);

        pathConsumer.start();
        fileAnalyzer.start();
    }
}


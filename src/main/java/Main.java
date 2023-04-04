import assignment.PathProducer;
import assignment.PathConsumer;
import assignment.queue.PathQueue;
import assignment.queue.StatisticQueue;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        final PathQueue pathQueue = new PathQueue();
        final StatisticQueue statisticQueue = new StatisticQueue();

        Barrier barrier = new ActionBarrier(1, () -> {
            //statisticQueueMonitor.close();
            System.out.println("All threads are done");
        });

        Path path = Paths.get("src/main/java/");

        PathConsumer pathConsumer = new PathConsumer(pathQueue, statisticQueue, barrier);

        PathProducer pathProducer = new PathProducer(pathQueue, path);

        pathConsumer.start();
        pathProducer.start();
    }
}


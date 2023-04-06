import assignment.agents.PathProducer;
import assignment.agents.PathConsumer;
import assignment.agents.StatisticConsumer;
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

        final Barrier barrier = new ActionBarrier(1, statisticQueue::close);

        final Path path = Paths.get("src/main/java/");

        final PathConsumer pathConsumer = new PathConsumer(pathQueue, statisticQueue, barrier);

        final PathProducer pathProducer = new PathProducer(pathQueue, path);

        final StatisticConsumer statisticConsumer = new StatisticConsumer(statisticQueue);

        statisticConsumer.start();
        pathConsumer.start();
        pathProducer.start();

        try {
            pathProducer.join();
            pathConsumer.join();
            statisticConsumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


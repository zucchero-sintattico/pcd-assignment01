import assignment.AlgorithmConfiguration;
import assignment.agents.PathProducer;
import assignment.agents.PathConsumer;
import assignment.agents.StatisticConsumer;
import assignment.queue.PathQueue;
import assignment.queue.StatisticQueue;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class Main {
    public static void main(String[] args) {

        final AlgorithmConfiguration configuration = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(1)
                .withNumberOfPathConsumer(5)
                .withNumberOfStatisticsConsumer(1)
                .build();


        final PathQueue pathQueue = new PathQueue();
        final StatisticQueue statisticQueue = new StatisticQueue();

        final Barrier barrier = new ActionBarrier(configuration.numberOfPathConsumer, statisticQueue::close);
        final Path path = Paths.get("src/main/java/");

        final Set<PathProducer> pathProducers = IntStream.range(0, configuration.numberOfPathProducer)
                .mapToObj(i -> new PathProducer(pathQueue, path))
                .collect(toSet());

        final Set<PathConsumer> pathConsumers = IntStream.range(0, configuration.numberOfPathConsumer)
                .mapToObj(i -> new PathConsumer(pathQueue, statisticQueue, barrier))
                .collect(toSet());

        final Set<StatisticConsumer> statisticConsumers = IntStream.range(0, configuration.numberOfStatisticsConsumer)
                .mapToObj(i -> new StatisticConsumer(statisticQueue))
                .collect(toSet());

        statisticConsumers.forEach(Thread::start);
        pathConsumers.forEach(Thread::start);
        pathProducers.forEach(Thread::start);

        try {
            for (final Thread thread : pathProducers) {
                thread.join();
            }
            for (final Thread thread : pathConsumers) {
                thread.join();
            }
            for (final Thread thread : statisticConsumers) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


package assignment;

import assignment.AlgorithmConfiguration;
import assignment.agents.PathConsumer;
import assignment.agents.PathProducer;
import assignment.agents.StatisticConsumer;
import assignment.queue.PathQueue;
import assignment.queue.StatisticQueue;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;

import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class AssignmentAlgorithm {

    private final PathQueue pathQueue = new PathQueue();
    private final StatisticQueue statisticQueue = new StatisticQueue();
    private final Barrier statisticsBarrier;

    private final Set<PathProducer> pathProducers;
    private final Set<PathConsumer> pathConsumers;
    private final Set<StatisticConsumer> statisticConsumers;


    public AssignmentAlgorithm(final String path, final AlgorithmConfiguration configuration) {
        this.statisticsBarrier = new ActionBarrier(configuration.numberOfPathConsumer, statisticQueue::close);
        this.pathProducers = createAgents(configuration.numberOfPathProducer,
                () -> new PathProducer(pathQueue, Paths.get(path))
        );
        this.pathConsumers = createAgents(configuration.numberOfPathConsumer,
                () -> new PathConsumer(pathQueue, statisticQueue, statisticsBarrier)
        );
        this.statisticConsumers = createAgents(configuration.numberOfStatisticsConsumer,
                () -> new StatisticConsumer(statisticQueue)
        );
    }

    private <T> Set<T> createAgents(final int numberOfAgents, final Supplier<T> factory) {
        return IntStream.range(0, numberOfAgents)
                .mapToObj(i -> factory.get())
                .collect(toSet());
    }

    public void start() {
        statisticConsumers.forEach(Thread::start);
        pathConsumers.forEach(Thread::start);
        pathProducers.forEach(Thread::start);
    }

    public void join() throws InterruptedException {
        for (final Thread thread : pathProducers) {
            thread.join();
        }
        for (final Thread thread : pathConsumers) {
            thread.join();
        }
        for (final Thread thread : statisticConsumers) {
            thread.join();
        }
    }
}

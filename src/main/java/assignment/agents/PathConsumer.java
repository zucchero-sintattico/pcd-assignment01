package assignment.agents;

import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment.Statistic;
import assignment.queue.PathQueue;
import assignment.queue.StatisticQueue;
import lib.architecture.QueueConsumerThread;
import lib.synchronization.Barrier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathConsumer extends QueueConsumerThread<Path> {
    private final Logger logger = LoggerMonitor.getInstance();
    private final StatisticQueue statsQueue;
    private final Barrier barrier;

    public PathConsumer(final PathQueue pathQueue, final StatisticQueue statsQueue, final Barrier barrier) {
        super(pathQueue);
        this.statsQueue = statsQueue;
        this.barrier = barrier;
    }

    @Override
    public void consume(final Path filepath) {
        try {
            final int lines = Files.readAllLines(filepath).size();
            this.logger.log("Consumed " + filepath + " has " + lines + " lines");
            this.statsQueue.enqueue(new Statistic(filepath, lines));
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onQueueClosed() {
        try {
            this.barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

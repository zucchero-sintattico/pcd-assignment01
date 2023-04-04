package assignment;

import lib.architecture.QueueConsumerThread;
import lib.synchronization.Barrier;
import lib.synchronization.QueueMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathConsumer extends QueueConsumerThread<Path> {
    private final QueueMonitor<Statistic> statsQueue;
    private final Barrier barrier;

    public PathConsumer(final QueueMonitor<Path> queueMonitor, final QueueMonitor<Statistic> statsQueue, final Barrier barrier) {
        super(queueMonitor);
        this.statsQueue = statsQueue;
        this.barrier = barrier;
    }

    @Override
    public void consume(final Path filepath) {
        try {
            final int lines = Files.readAllLines(filepath).size();
            System.out.println(filepath + " has " + lines + " lines");
            this.statsQueue.enqueue(new Statistic(filepath, lines));;
        } catch (IOException e) {
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

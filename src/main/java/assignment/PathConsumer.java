package assignment;

import lib.architecture.QueueConsumerThread;
import lib.synchronization.QueueMonitor;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathConsumer extends QueueConsumerThread<Path> {
    private QueueMonitor<Statistic> statsQueue;
    public PathConsumer(QueueMonitor<Path> queueMonitor, QueueMonitor<Statistic> statsQueue) {
        super(queueMonitor);
        this.statsQueue = statsQueue;
    }

    @Override
    public void consume(Path filepath) {
        int lines = Files.readAllLines(filepath).size();
        System.out.println(filepath + " has " + lines + " lines");
        this.statsQueue.enqueue(new Statistic(filepath, lines));;
    }

    @Override
    public void onQueueClosed() {
        this.statsQueue.close();
    }
}

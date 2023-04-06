package assignment.agents;

import assignment.Statistic;
import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment.queue.StatisticQueue;
import lib.architecture.QueueConsumerThread;

public class StatisticConsumer extends QueueConsumerThread<Statistic> {
    private final Logger logger = LoggerMonitor.getInstance();

    public StatisticConsumer(final StatisticQueue queue) {
        super(queue);
    }

    @Override
    public void consume(Statistic value) {
        this.logger.log("Consuming " + value);
    }

    @Override
    public void onQueueClosed() {
        this.logger.log("Statistic Consumer finished");
    }

}

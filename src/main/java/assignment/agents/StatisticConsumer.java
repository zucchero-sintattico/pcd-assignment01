package assignment.agents;

import assignment.Statistic;
import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment.queue.StatisticQueue;
import assignment.mvc.Model;
import lib.architecture.QueueConsumerThread;


/**
 * Consumes statistics from the queue.
 */
public class StatisticConsumer extends QueueConsumerThread<Statistic> {

    private final Model model;
    private final Logger logger = LoggerMonitor.getInstance();

    public StatisticConsumer(final StatisticQueue queue, final Model model) {
        super(queue);
        this.model = model;
    }

    @Override
    public void consume(final Statistic value) {
        this.logger.log("Consuming " + value);
        this.model.addStatistic(value);
    }

    @Override
    public void onQueueClosed() {
        this.logger.log("Statistic Consumer finished");
    }

}

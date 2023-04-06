package assignment.agents;

import assignment.Statistic;
import assignment.queue.StatisticQueue;
import lib.architecture.QueueConsumer;
import lib.architecture.QueueConsumerThread;

public class StatisticConsumer extends QueueConsumerThread<Statistic> {

    public StatisticConsumer(final StatisticQueue queue) {
        super(queue);
    }

    @Override
    public void consume(Statistic value) {
        System.out.println("Consuming: " + value);
    }

    @Override
    public void onQueueClosed() {
        System.out.println("Queue closed");
    }

}

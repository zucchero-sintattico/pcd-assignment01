package assignment.jpf.agents;

import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;


public class PathProducerSimplified extends QueueProducerThread<Integer> {

    public PathProducerSimplified(final QueueMonitor<Integer> queue) {
        super(queue);
    }

    @Override
    public void run() {
        this.produce(1);
        this.closeQueue();
    }
}
package assignment.jpf.agents;

import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;


public class PathProducerSimplified extends QueueProducerThread<Integer> {
    private final StopMonitor stopMonitor;

    public PathProducerSimplified(QueueMonitor<Integer> queue, final StopMonitor stopMonitor) {
        super(queue);
        this.stopMonitor = stopMonitor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1; i++) {
            if (this.stopMonitor.hasToBeStopped()) {
                break;
            } else {
                this.produce(i);
            }
        }
        this.closeQueue();
    }
}
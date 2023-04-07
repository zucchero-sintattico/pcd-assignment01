package assignment.jpf;

import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;


public class PathProducerSemplified extends QueueProducerThread<Integer> {
    private final StopMonitor stopMonitor;

    public PathProducerSemplified(QueueMonitor<Integer> queue, final StopMonitor stopMonitor) {
        super(queue);
        this.stopMonitor = stopMonitor;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (this.stopMonitor.hasToBeStopped()) {
                this.closeQueue();
            } else {
                this.produce(i);
                LoggerMonitor.getInstance().log("Produced " + i);
            }
        }
    }
}
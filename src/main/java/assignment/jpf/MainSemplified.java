package assignment.jpf;

import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;

public class MainSemplified {
    public static void main(String[] args) {
        QueueMonitor<Integer> queue = new QueueMonitor<>();
        StopMonitor stopMonitor = new StopMonitor();
        PathProducerSemplified producer = new PathProducerSemplified(queue, stopMonitor);
        producer.start();
    }
}

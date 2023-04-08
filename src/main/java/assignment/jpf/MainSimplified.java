package assignment.jpf;

import assignment.jpf.agents.PathConsumerSimplified;
import assignment.jpf.agents.PathProducerSimplified;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;

public class MainSimplified {
    public static void main(String[] args) throws InterruptedException {
        QueueMonitor<Integer> pathQueue = new QueueMonitor<>();
        QueueMonitor<String> statsQueue = new QueueMonitor<>();
        Barrier barrier = new ActionBarrier(1, statsQueue::close);
        StopMonitor stopMonitor = new StopMonitor();
        PathProducerSimplified producer = new PathProducerSimplified(pathQueue, stopMonitor);
        PathConsumerSimplified consumer = new PathConsumerSimplified(pathQueue, statsQueue, barrier, stopMonitor);
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
    }
}
